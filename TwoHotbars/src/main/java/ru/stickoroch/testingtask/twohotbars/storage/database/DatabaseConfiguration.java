package ru.stickoroch.testingtask.twohotbars.storage.database;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.bukkit.configuration.ConfigurationSection;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DatabaseConfiguration {

    @NonNull
    org.bukkit.configuration.Configuration configuration;
    @Getter
    @NonFinal
    SessionFactory sessionFactory;

    public DatabaseConfiguration(@NonNull org.bukkit.configuration.Configuration configuration) {
        this.configuration = configuration;
    }

    public void init() {
        try {
            Configuration cfg = new Configuration();

            ConfigurationSection cfgSec = configuration.getConfigurationSection("hibernate-settings");

            cfg.setProperty("hibernate.connection.url", cfgSec.getString("url"));
            cfg.setProperty("hibernate.connection.username", cfgSec.getString("username"));
            cfg.setProperty("hibernate.connection.password", cfgSec.getString("password"));
            cfg.setProperty("hibernate.connection.driver_class", cfgSec.getString("driver"));
            cfg.setProperty("hibernate.dialect", cfgSec.getString("dialect"));
            cfg.setProperty("hibernate.show_sql", cfgSec.getString("show_sql"));
            cfg.setProperty("hibernate.hbm2ddl.auto", cfgSec.getString("hbm2ddl_auto"));


            cfg.addAnnotatedClass(PlayerHotbarModel.class);
            cfg.addAttributeConverter(ItemStackArrayToByteArrayDatabaseConverter.class);

            sessionFactory = cfg.buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Не удалось инициализировать хибернейт " + e);
        }
    }

}
