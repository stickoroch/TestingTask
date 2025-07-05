package ru.stickoroch.testingtask.traderperl.logger;

import lombok.NonNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.stickoroch.testingtask.traderperl.logger.model.PlayerModel;
import ru.stickoroch.testingtask.traderperl.logger.model.TraderModel;
import ru.stickoroch.testingtask.traderperl.logger.model.TransactionModel;

import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionLoggerService {

    @NonNull
    private final SessionFactory sessionFactory;

    public TransactionLoggerService(@NonNull SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void logTransaction(@NonNull UUID traderId, @NonNull UUID playerId, int price) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            TraderModel trader = session.get(TraderModel.class, traderId);
            if (trader == null) {
                trader = new TraderModel();
                trader.setId(traderId);
                session.persist(trader);
            }

            PlayerModel player = session.get(PlayerModel.class, playerId);
            if (player == null) {
                player = new PlayerModel();
                player.setId(playerId);
                session.persist(player);
            }

            TransactionModel transaction = new TransactionModel();
            transaction.setTrader(trader);
            transaction.setPlayer(player);
            transaction.setPrice(price);
            transaction.setTimestamp(LocalDateTime.now());

            session.persist(transaction);

            tx.commit();
        }
    }
}