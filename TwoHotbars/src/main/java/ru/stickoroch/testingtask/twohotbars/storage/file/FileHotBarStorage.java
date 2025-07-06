package ru.stickoroch.testingtask.twohotbars.storage.file;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import ru.stickoroch.testingtask.twohotbars.storage.HotBarStorage;
import ru.stickoroch.testingtask.twohotbars.storage.PlayerHotBar;

import java.io.*;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileHotBarStorage implements HotBarStorage {

    @NonNull
    File directory;

    public FileHotBarStorage(@NonNull Plugin plugin) {
        this.directory = new File(plugin.getDataFolder(), "hotbars");
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
            try {
                File hotBarFile = new File(directory, uuid + ".hotbar");
                if (!hotBarFile.exists()) {
                    return new FilePlayerHotBar(uuid);
                }
                try (FileInputStream reader = new FileInputStream(hotBarFile)) {
                    ByteArrayInputStream bis = new ByteArrayInputStream(reader.readAllBytes());
                    ObjectInputStream ois = new ObjectInputStream(bis);
                    PlayerHotBar playerHotBar = (PlayerHotBar) ois.readObject();
                    bis.close();
                    ois.close();
                    return playerHotBar;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new FilePlayerHotBar(uuid);
            }
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

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(playerHotBar);
            oos.flush();
            try (FileOutputStream fos = new FileOutputStream(hotBarFile)) {
                fos.write(bos.toByteArray());
            }
            bos.flush();
            oos.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public @NonNull PlayerHotBar createHotBar(@NonNull UUID uuid, @NotNull @NonNull ItemStack[] items) {
        return new FilePlayerHotBar(uuid, items);
    }

    @Override
    public void save(@NonNull Collection<@NonNull PlayerHotBar> hotBars) {
        hotBars.forEach(this::save);
    }
}
