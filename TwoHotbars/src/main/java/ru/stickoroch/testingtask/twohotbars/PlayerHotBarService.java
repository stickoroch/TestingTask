package ru.stickoroch.testingtask.twohotbars;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import ru.stickoroch.testingtask.twohotbars.storage.HotBarStorage;
import ru.stickoroch.testingtask.twohotbars.storage.PlayerHotBar;

import java.util.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlayerHotBarService implements Listener {

    @NonNull
    Map<UUID, PlayerHotBar> playerSecondHotBars;

    @NonNull
    Set<UUID> loadingHotBars;

    @NonNull
    HotBarStorage hotBarStorage;

    public PlayerHotBarService(@NonNull HotBarStorage hotBarStorage) {
        this.hotBarStorage = hotBarStorage;
        this.playerSecondHotBars = new HashMap<>();
        this.loadingHotBars = new HashSet<>();
    }

    @EventHandler
    public void onJoin(@NonNull PlayerJoinEvent e) {
        if (loadingHotBars.contains(e.getPlayer().getUniqueId())) return;
        loadingHotBars.add(e.getPlayer().getUniqueId());

        hotBarStorage.loadPlayerHotBar(e.getPlayer().getUniqueId()).thenAccept(x -> {
            playerSecondHotBars.put(e.getPlayer().getUniqueId(), x);
            loadingHotBars.remove(e.getPlayer().getUniqueId());
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (playerSecondHotBars.containsKey(e.getPlayer().getUniqueId())) {
            hotBarStorage.savePlayerHotBar(playerSecondHotBars.get(e.getPlayer().getUniqueId())).thenAccept(x -> {
                playerSecondHotBars.remove(e.getPlayer().getUniqueId());
            });
        }

    }

    @EventHandler
    public void onSwap(PlayerSwapHandItemsEvent e) {
        if (!e.getPlayer().isSneaking()) return;
        e.setCancelled(true);

        UUID playerId = e.getPlayer().getUniqueId();
        if (loadingHotBars.contains(playerId)) return;

        ItemStack[] b = getPlayerHotBarArray(e.getPlayer());

        if (playerSecondHotBars.containsKey(playerId)) {
            setPlayerHotBarArray(e.getPlayer(), playerSecondHotBars.get(playerId).getHotBar());

            playerSecondHotBars.replace(playerId, hotBarStorage.createHotBar(playerId, b));
        } else {
            setPlayerHotBarArray(e.getPlayer(), new ItemStack[9]);
            playerSecondHotBars.put(playerId, hotBarStorage.createHotBar(playerId, b));
        }
    }

    public void saveAllToStorage() {
        hotBarStorage.saveAll(playerSecondHotBars.values());
    }

    private ItemStack @NonNull [] getPlayerHotBarArray(@NonNull Player player) {
        ItemStack[] items = new ItemStack[9];
        for (int i = 0; i < 9; i++) {
            ItemStack stack = player.getInventory().getItem(i);
            if (stack != null) stack = stack.clone();
            items[i] = stack;
        }
        return items;
    }

    private void setPlayerHotBarArray(@NonNull Player player, ItemStack @NonNull [] items) {
        for (int i = 0; i < 9; i++) {
            ItemStack item = items[i];
            if (item != null) item = item.clone();
            player.getInventory().setItem(i, item);
        }
    }
}
