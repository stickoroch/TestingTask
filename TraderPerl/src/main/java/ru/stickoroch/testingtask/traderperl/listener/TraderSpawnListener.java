package ru.stickoroch.testingtask.traderperl.listener;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.MerchantRecipe;
import ru.stickoroch.testingtask.traderperl.WanderingTraderRecipeFactory;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TraderSpawnListener implements Listener {

    @NonNull
    WanderingTraderRecipeFactory wanderingTraderRecipeFactory;

    public TraderSpawnListener(@NonNull WanderingTraderRecipeFactory wanderingTraderRecipeFactory) {
        this.wanderingTraderRecipeFactory = wanderingTraderRecipeFactory;
    }

    @EventHandler
    public void onTraderSpawn(CreatureSpawnEvent e) {
        if (e.getEntity() instanceof WanderingTrader wanderingTrader) {
            List<MerchantRecipe> recipes = new ArrayList<>(wanderingTrader.getRecipes());
            recipes.add(wanderingTraderRecipeFactory.createRandomizeMerchantRecipe());
            wanderingTrader.setRecipes(recipes);
        }
    }

}
