package ru.stickoroch.testingtask.twohotbars.storage.database;

import lombok.NonNull;
import org.bukkit.inventory.ItemStack;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.stickoroch.testingtask.twohotbars.storage.HotBarStorage;
import ru.stickoroch.testingtask.twohotbars.storage.PlayerHotBar;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class HotBarDatabaseStorage implements HotBarStorage {

    @NonNull
    private final SessionFactory sessionFactory;

    public HotBarDatabaseStorage(@NonNull SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void init() {
    }

    @Override
    public @NonNull CompletableFuture<PlayerHotBar> loadPlayerHotBar(@NonNull UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            try (Session session = sessionFactory.openSession()) {
                return Objects.requireNonNullElseGet(session.get(PlayerHotbarModel.class, uuid), () -> new PlayerHotbarModel(uuid));
            } catch (Exception e) {
                e.printStackTrace();
                return new PlayerHotbarModel(uuid);
            }
        });
    }

    @Override
    public @NonNull CompletableFuture<Void> savePlayerHotBar(@NonNull PlayerHotBar hotBar) {
        return CompletableFuture.runAsync(() -> save(hotBar));
    }

    @Override
    public @NonNull PlayerHotBar createHotBar(@NonNull UUID uuid, ItemStack @NonNull [] items) {
        return new PlayerHotbarModel(uuid, items);
    }

    @Override
    public void saveAll(@NonNull Collection<@NonNull PlayerHotBar> hotBars) {
        hotBars.forEach(this::save);
    }

    private void save(@NonNull PlayerHotBar hotBar) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            PlayerHotbarModel playerHotbarModel = session.get(PlayerHotbarModel.class, hotBar.getOwnerUUID());
            if (playerHotbarModel == null) {
                playerHotbarModel = new PlayerHotbarModel(hotBar.getOwnerUUID());
            }
            playerHotbarModel.setHotBar(hotBar.getHotBar());

            session.persist(playerHotbarModel);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
