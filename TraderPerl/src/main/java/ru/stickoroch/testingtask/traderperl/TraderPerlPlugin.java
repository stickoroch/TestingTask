package ru.stickoroch.testingtask.traderperl;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import ru.stickoroch.testingtask.traderperl.listener.TradeTransactionListener;
import ru.stickoroch.testingtask.traderperl.listener.TraderSpawnListener;
import ru.stickoroch.testingtask.traderperl.logger.DatabaseConfiguration;
import ru.stickoroch.testingtask.traderperl.logger.TransactionLoggerService;

import java.util.Random;

public class TraderPerlPlugin extends JavaPlugin {

    WanderingTraderRecipeFactory wanderingTraderRecipeFactory;
    DatabaseConfiguration databaseConfiguration;
    TransactionLoggerService transactionLoggerService;
    Random random;

    @Override
    public void onLoad() {
        databaseConfiguration = new DatabaseConfiguration(this);
        databaseConfiguration.init();
        transactionLoggerService = new TransactionLoggerService(databaseConfiguration.getSessionFactory());
    }

    @Override
    public void onEnable() {
        random = new Random();
        wanderingTraderRecipeFactory = new WanderingTraderRecipeFactory(random);

        Bukkit.getPluginManager().registerEvents(new TraderSpawnListener(wanderingTraderRecipeFactory), this);
        Bukkit.getPluginManager().registerEvents(new TradeTransactionListener(transactionLoggerService, this), this);
    }
}
