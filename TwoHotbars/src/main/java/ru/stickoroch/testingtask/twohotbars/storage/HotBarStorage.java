package ru.stickoroch.testingtask.twohotbars.storage;

import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface HotBarStorage {

    void init();

    @NonNull
    CompletableFuture<PlayerHotBar> loadPlayerHotBar(@NonNull UUID uuid);

    @NonNull
    CompletableFuture<Void> savePlayerHotBar(@NonNull PlayerHotBar hotBar);

    @NonNull
    PlayerHotBar createHotBar(@NonNull UUID uuid, ItemStack @NonNull [] items);

    void saveAll(@NonNull Collection<@NonNull PlayerHotBar> hotBars);
}
