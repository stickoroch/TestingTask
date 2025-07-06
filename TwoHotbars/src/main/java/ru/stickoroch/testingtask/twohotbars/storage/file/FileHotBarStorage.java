package ru.stickoroch.testingtask.twohotbars.storage.file;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import ru.stickoroch.testingtask.twohotbars.storage.HotBarStorage;
import ru.stickoroch.testingtask.twohotbars.storage.ItemStackArrayToByteArrayConverter;
import ru.stickoroch.testingtask.twohotbars.storage.PlayerHotBar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileHotBarStorage implements HotBarStorage {

    @NonNull
    File directory;
    @NonNull
    ItemStackArrayToByteArrayConverter converter;

    public FileHotBarStorage(@NonNull Plugin plugin) {
        this.directory = new File(plugin.getDataFolder(), "hotbars");
        this.converter = new ItemStackArrayToByteArrayConverter();
    }

    @Override
    public void init() {
        if (!directory.exists()) {
            directory.getParentFile().mkdirs();
            directory.mkdirs();
        }
    }

    @Override
    public @NonNull CompletableFuture<PlayerHotBar> loadPlayerHotBar(@NonNull UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            FilePlayerHotBar playerHotBar = new FilePlayerHotBar(uuid);
            try {
                File hotBarFile = new File(directory, uuid + ".hotbar");
                if (!hotBarFile.exists()) {
                    return playerHotBar;
                }
                try (FileInputStream reader = new FileInputStream(hotBarFile)) {
                    playerHotBar.setHotBar(converter.decode(reader.readAllBytes()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return playerHotBar;
        });
    }

    @Override
    public @NonNull CompletableFuture<Void> savePlayerHotBar(@NonNull PlayerHotBar playerHotBar) {
        return CompletableFuture.runAsync(() -> save(playerHotBar));
    }

    private void save(@NonNull PlayerHotBar playerHotBar) {
        try {
            File hotBarFile = new File(directory, playerHotBar.getOwnerUUID() + ".hotbar");
            if (!hotBarFile.exists()) {
                hotBarFile.createNewFile();
            }

            try (FileOutputStream fos = new FileOutputStream(hotBarFile)) {
                fos.write(converter.encode(playerHotBar.getHotBar()));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public @NonNull PlayerHotBar createHotBar(@NonNull UUID uuid, ItemStack @NonNull [] items) {
        return new FilePlayerHotBar(uuid, items);
    }

    @Override
    public void saveAll(@NonNull Collection<@NonNull PlayerHotBar> hotBars) {
        hotBars.forEach(this::save);
    }
}
