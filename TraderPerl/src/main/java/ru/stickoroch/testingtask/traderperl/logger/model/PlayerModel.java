package ru.stickoroch.testingtask.traderperl.logger.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "players")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlayerModel {
    @Id
    @NonNull
    private UUID id;

    @NonNull
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private List<TransactionModel> transactions = new ArrayList<>();
}