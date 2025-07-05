package ru.stickoroch.testingtask.traderperl.logger.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int price;

    @NonNull
    private LocalDateTime timestamp;

    @ManyToOne
    @NonNull
    @JoinColumn(name = "player_id")
    private PlayerModel player;

    @ManyToOne
    @NonNull
    @JoinColumn(name = "trader_id")
    private TraderModel trader;

}