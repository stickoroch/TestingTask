package ru.stickoroch.testingtask.twohotbars.storage.database;

import jakarta.persistence.*;
import lombok.*;
import org.bukkit.inventory.ItemStack;
import ru.stickoroch.testingtask.twohotbars.storage.PlayerHotBar;

import java.util.UUID;

@Entity
@Table(name = "player_hotbars")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlayerHotbarModel implements PlayerHotBar {


    @Convert(converter = ItemStackArrayToByteArrayDatabaseConverter.class)
    @Lob
    ItemStack[] hotBar;
    @Id
    @NonNull
    private UUID ownerUUID;

    public PlayerHotbarModel(@NonNull UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
        this.hotBar = new ItemStack[9];
    }
}
