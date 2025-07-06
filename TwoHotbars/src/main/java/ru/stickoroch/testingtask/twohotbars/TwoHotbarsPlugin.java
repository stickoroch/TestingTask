package ru.stickoroch.testingtask.twohotbars;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.stickoroch.testingtask.twohotbars.storage.HotBarStorage;
import ru.stickoroch.testingtask.twohotbars.storage.file.FileHotBarStorage;

@FieldDefaults(level = AccessLevel.PACKAGE)
public class TwoHotbarsPlugin extends JavaPlugin {

    HotBarStorage hotBarStorage;
    PlayerHotBarService playerHotBarService;

    @Override
    public void onLoad() {
        hotBarStorage = new FileHotBarStorage(this);
        hotBarStorage.init();
    }

    @Override
    public void onEnable() {
        playerHotBarService = new PlayerHotBarService(hotBarStorage);

        Bukkit.getPluginManager().registerEvents(playerHotBarService, this);
    }
}
