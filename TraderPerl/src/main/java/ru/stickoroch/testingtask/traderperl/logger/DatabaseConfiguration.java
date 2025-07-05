package ru.stickoroch.testingtask.traderperl.logger;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.stickoroch.testingtask.traderperl.logger.model.PlayerModel;
import ru.stickoroch.testingtask.traderperl.logger.model.TraderModel;
import ru.stickoroch.testingtask.traderperl.logger.model.TransactionModel;

import java.io.File;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DatabaseConfiguration {

    @NonNull
    Plugin plugin;
    @NonNull
    File file;
    @Getter
    @NonFinal
    SessionFactory sessionFactory;

    public DatabaseConfiguration(@NonNull Plugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "database-config.yml");
    }

    public void init() {
        try {
            if (!file.exists()) {
                plugin.saveResource("database-config.yml", false);
            }

            ConfigurationSection yaml = YamlConfiguration.loadConfiguration(file).getConfigurationSection("database");

            Configuration cfg = new Configuration();

            cfg.setProperty("hibernate.connection.url", yaml.getString("url"));
            cfg.setProperty("hibernate.connection.username", yaml.getString("username"));
            cfg.setProperty("hibernate.connection.password", yaml.getString("password"));
            cfg.setProperty("hibernate.connection.driver_class", yaml.getString("driver"));
            cfg.setProperty("hibernate.dialect", yaml.getString("dialect"));
            cfg.setProperty("hibernate.show_sql", yaml.getString("show_sql"));
            cfg.setProperty("hibernate.hbm2ddl.auto", yaml.getString("hbm2ddl_auto"));


            cfg.addAnnotatedClass(PlayerModel.class);
            cfg.addAnnotatedClass(TraderModel.class);
            cfg.addAnnotatedClass(TransactionModel.class);

            sessionFactory = cfg.buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Не удалось инициализировать хибернейт " + e);
        }
    }

}
