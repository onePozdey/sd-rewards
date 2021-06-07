package ru.itis.activerewards.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User fromUser;
    @ManyToOne(fetch = FetchType.EAGER)
    private User toUser;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<UserSpec> specs;

    @Column(nullable = false)
    private Double sum;
    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private LocalDateTime cancelDateTime;

    private String cancelComment;

    private String taskComment;

    public enum TransactionStatus {
        SUCCESS, CANCELED
    }
}
