package ru.itis.activerewards.forms;

import lombok.Data;

import java.util.List;


@Data
public class SendMoneyForm {
    private Double sum;
    private Long receiverId;
    private String taskComment;
    private List<Long> specsIds;
}
