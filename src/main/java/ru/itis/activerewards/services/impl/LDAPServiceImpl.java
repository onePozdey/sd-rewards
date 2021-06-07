package ru.itis.activerewards.services.impl;

import com.unboundid.asn1.ASN1OctetString;
import com.unboundid.ldap.sdk.*;
import com.unboundid.ldap.sdk.controls.SimplePagedResultsControl;
import com.unboundid.util.LDAPTestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.itis.activerewards.models.User;
import ru.itis.activerewards.repositories.UsersRepository;
import ru.itis.activerewards.security.role.Role;
import ru.itis.activerewards.services.LDAPService;
import ru.itis.activerewards.services.UserService;

@Service
@Slf4j
@Profile("prod")
public class LDAPServiceImpl implements LDAPService {

    @Autowired
    private UserService userService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private LDAPConnectionPool connectionPool;

    private String bindDN = "CN=%s,CN=Users,DC=shire,DC=local";
    private String usersDN = "CN=Users,DC=shire,DC=local";
    private String userFilter = "(objectClass=organizationalPerson)";

    @Override
    public boolean isAuthenticated(String login, String password) {
        try {
            String result = connectionPool.getConnection()
                    .bind(String.format(bindDN, login), password).getResultCode().getName();
            return result.equals("success");
        } catch (LDAPException e) {
            return false;
        }
    }

    private SearchRequest performUserSearchRequest() {
        try {
            return new SearchRequest(usersDN, SearchScope.SUB, userFilter);
        } catch (LDAPException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void uploadUsers() {
        try {
            LDAPConnection connection = connectionPool.getConnection();
            int numSearches = 0;
            int totalEntriesReturned = 0;
            SearchRequest searchRequest = performUserSearchRequest();
            ASN1OctetString resumeCookie = null;
            while (true) {
                searchRequest.setControls(new SimplePagedResultsControl(500, resumeCookie));
                SearchResult searchResult = connection.search(searchRequest);
                numSearches++;
                totalEntriesReturned += searchResult.getEntryCount();

                searchResult.getSearchEntries().forEach(this::processUser);

                LDAPTestUtils.assertHasControl(searchResult, SimplePagedResultsControl.PAGED_RESULTS_OID);
                SimplePagedResultsControl responseControl = SimplePagedResultsControl.get(searchResult);
                if (responseControl.moreResultsToReturn()) {
                    resumeCookie = responseControl.getCookie();
                } else {
                    break;
                }
            }
            log.info("Total users found {}", totalEntriesReturned);
            log.info("With num searches {}", numSearches);
        } catch (LDAPException ignored) {
        }
    }

    private void processUser(SearchResultEntry user) {
        String login = user.getAttributeValue("cn");
        if (login == null || login.isEmpty()) return;

        Role role = getUserRole(user);
        User.UserState state = getUserState(user);

        if (usersRepository.existsByLogin(login)) {
            userService.updateRoleAndState(login, role, state);
        } else {
            userService.createUser(login, role, state);
        }
    }

    private User.UserState getUserState(SearchResultEntry user) {
        String[] descriptions = user.getAttributeValues("description");
        if (descriptions != null) {
            for (String description : descriptions) {
                if (description.toLowerCase().trim().startsWith("working")) {
                    return User.UserState.ACTIVE;
                }
            }
        }
        return User.UserState.BANNED;
    }

    private Role getUserRole(SearchResultEntry user) {
        String[] memberOfs = user.getAttributeValues("memberOf");
        if (memberOfs == null) return Role.USER;
        for (String memberOf : user.getAttributeValues("memberOf")) {
            if (memberOf.toLowerCase().contains("admin")) {
                return Role.ADMIN;
            }
        }
        return Role.USER;
    }
}
