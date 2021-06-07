package ru.itis.activerewards.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "reward_order")
public class RewardOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User receiver;

    @ManyToOne
    private Reward reward;

    @Enumerated(EnumType.STRING)
    private RewardOrderStatus status;

    private LocalDateTime dateTime;

    public enum RewardOrderStatus {
        WAITING, COMPLETED
    }

}
