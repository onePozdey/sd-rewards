package ru.itis.activerewards.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.activerewards.dto.SpecCountDto;
import ru.itis.activerewards.dto.SpecDto;
import ru.itis.activerewards.forms.SpecForm;
import ru.itis.activerewards.models.User;
import ru.itis.activerewards.models.UserSpec;
import ru.itis.activerewards.repositories.UserSpecsRepository;
import ru.itis.activerewards.repositories.UsersRepository;
import ru.itis.activerewards.services.UserSpecsService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserSpecsServiceImpl implements UserSpecsService {
    @Autowired
    private UserSpecsRepository specsRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<SpecDto> getAllSpecs() {
        return SpecDto.from(specsRepository.findAll());
    }

    @Override
    public List<SpecCountDto> getUserSpecs(Long id) {
        User user = usersRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return SpecCountDto.from(specsRepository.getUserSpecs(user)
                .stream()
                .filter(x -> x.getName() != null)
                .collect(Collectors.toList())
        );
    }

    @Override
    public void addSpec(SpecForm form) {
        UserSpec spec = UserSpec.builder()
                .name(form.getName())
                .build();
        specsRepository.save(spec);
    }
}
