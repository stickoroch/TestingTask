package ru.stickoroch.testingtask.traderperl;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class TraderPerlPlugin extends JavaPlugin {

    WanderingTraderRecipeFactory wanderingTraderRecipeFactory;
    Random random;

    @Override
    public void onEnable() {
        random = new Random();
        wanderingTraderRecipeFactory = new WanderingTraderRecipeFactory(random);

        Bukkit.getPluginManager().registerEvents(new TraderSpawnListener(wanderingTraderRecipeFactory), this);
    }
}
