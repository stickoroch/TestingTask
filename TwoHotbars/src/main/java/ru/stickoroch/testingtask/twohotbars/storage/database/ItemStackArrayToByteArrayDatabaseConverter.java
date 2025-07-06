package ru.stickoroch.testingtask.twohotbars.storage.database;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;
import ru.stickoroch.testingtask.twohotbars.storage.ItemStackArrayToByteArrayConverter;

import java.io.IOException;

@Converter(autoApply = true)
public class ItemStackArrayToByteArrayDatabaseConverter implements AttributeConverter<ItemStack @NonNull [], byte[]> {
    private final ItemStackArrayToByteArrayConverter byteArrayConverter;

    public ItemStackArrayToByteArrayDatabaseConverter() {
        byteArrayConverter = new ItemStackArrayToByteArrayConverter();
    }


    @Override
    public byte[] convertToDatabaseColumn(ItemStack @NonNull [] itemStacks) {
        try {
            return byteArrayConverter.encode(itemStacks);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ItemStack @NonNull [] convertToEntityAttribute(byte[] s) {
        try {
            return byteArrayConverter.decode(s);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
