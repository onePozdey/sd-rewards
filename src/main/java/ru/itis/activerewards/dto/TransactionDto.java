package ru.itis.activerewards.dto;

import lombok.Builder;
import lombok.Data;
import ru.itis.activerewards.utils.DateTimeUtils;
import ru.itis.activerewards.models.Transaction;

import java.util.List;
import java.util.stream.Collectors;


@Data
@Builder
public class TransactionDto {
    private Long id;
    private UserDto fromUser;
    private UserDto toUser;
    private Double sum;
    private String status;
    private String dateTime;
    private String cancelDateTime;
    private String cancelComment;
    private String taskComment;
    private List<SpecDto> specs;

    public static TransactionDto from(Transaction transaction) {
        return TransactionDto.builder()
                .id(transaction.getId())
                .fromUser(UserDto.from(transaction.getFromUser()))
                .toUser(UserDto.from(transaction.getToUser()))
                .sum(transaction.getSum())
                .status(transaction.getStatus().name())
                .dateTime(transaction.getDateTime().format(DateTimeUtils.dateTimeFormat))
                .cancelDateTime(transaction.getCancelDateTime() != null ?
                        transaction.getCancelDateTime().format(DateTimeUtils.dateTimeFormat) : null)
                .cancelComment(transaction.getCancelComment())
                .taskComment(transaction.getTaskComment())
                .specs(SpecDto.from(transaction.getSpecs()))
                .build();
    }

    public static List<TransactionDto> from(List<Transaction> transactions) {
        return transactions.stream()
                .map(TransactionDto::from)
                .collect(Collectors.toList());
    }


}
