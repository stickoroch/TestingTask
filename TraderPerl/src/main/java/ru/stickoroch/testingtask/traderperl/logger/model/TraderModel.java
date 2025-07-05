package ru.stickoroch.testingtask.traderperl.logger.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "traders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TraderModel {
    @Id
    @NonNull
    private UUID id;

    @NonNull
    @OneToMany(mappedBy = "trader", cascade = CascadeType.ALL)
    private List<TransactionModel> transactions = new ArrayList<>();

}