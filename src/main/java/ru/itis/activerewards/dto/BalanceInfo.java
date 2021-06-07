package ru.itis.activerewards.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class BalanceInfo {
    private Double balance;
    private Double giftBalance;
}
