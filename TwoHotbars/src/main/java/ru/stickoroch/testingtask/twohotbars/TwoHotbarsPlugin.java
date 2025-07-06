package ru.stickoroch.testingtask.twohotbars;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.stickoroch.testingtask.twohotbars.storage.HotBarStorage;
import ru.stickoroch.testingtask.twohotbars.storage.database.DatabaseConfiguration;
import ru.stickoroch.testingtask.twohotbars.storage.database.HotBarDatabaseStorage;
import ru.stickoroch.testingtask.twohotbars.storage.file.FileHotBarStorage;

@FieldDefaults(level = AccessLevel.PACKAGE)
public class TwoHotbarsPlugin extends JavaPlugin {

    HotBarStorage hotBarStorage;
    PlayerHotBarService playerHotBarService;

    @Override
    public void onLoad() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        if (getConfig().getBoolean("hibernate-enabled")) {
            DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration(getConfig());
            databaseConfiguration.init();

            hotBarStorage = new HotBarDatabaseStorage(databaseConfiguration.getSessionFactory());
        } else {
            hotBarStorage = new FileHotBarStorage(this);
        }

        hotBarStorage.init();
    }

    @Override
    public void onEnable() {
        playerHotBarService = new PlayerHotBarService(hotBarStorage);

        Bukkit.getPluginManager().registerEvents(playerHotBarService, this);
    }

    @Override
    public void onDisable() {
        playerHotBarService.saveAllToStorage();
    }
}
