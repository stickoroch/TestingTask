package ru.stickoroch.testingtask.twohotbars.storage;

import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.UUID;

public interface PlayerHotBar extends Serializable {

    @NonNull
    UUID getOwnerUUID();

    @NonNull
    ItemStack[] getHotBar();

    void setHotBar(@NonNull ItemStack[] hotBar);
}
