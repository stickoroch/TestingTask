package ru.stickoroch.testingtask.twohotbars.storage.file;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bukkit.inventory.ItemStack;
import ru.stickoroch.testingtask.twohotbars.storage.PlayerHotBar;

import java.util.UUID;

@AllArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FilePlayerHotBar implements PlayerHotBar {

    @NonNull
    final UUID ownerUUID;
    ItemStack @NonNull [] hotBar;

    public FilePlayerHotBar(@NonNull UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
        this.hotBar = new ItemStack[9];
    }
}
