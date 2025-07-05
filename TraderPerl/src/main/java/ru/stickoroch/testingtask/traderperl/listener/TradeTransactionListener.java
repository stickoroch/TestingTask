package ru.stickoroch.testingtask.traderperl.listener;

import io.papermc.paper.event.player.PlayerTradeEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.plugin.Plugin;
import ru.stickoroch.testingtask.traderperl.logger.TransactionLoggerService;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TradeTransactionListener implements Listener {

    TransactionLoggerService transactionLoggerService;
    Plugin plugin;


    @EventHandler
    public void onTrade(PlayerTradeEvent e) {
        if (e.getVillager() instanceof WanderingTrader wanderingTrader) {
            if (isPerlTrade(e.getTrade())) {
                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                    transactionLoggerService.logTransaction(wanderingTrader.getUniqueId(),
                            e.getPlayer().getUniqueId(),
                            e.getTrade().getIngredients().get(0).getAmount());
                });
            }
        }
    }

    private boolean isPerlTrade(MerchantRecipe recipe) {
        return recipe.getIngredients().size() == 1
                && recipe.getIngredients().get(0).getType() == Material.GOLD_INGOT
                && recipe.getResult().getType() == Material.ENDER_PEARL;
    }
}
