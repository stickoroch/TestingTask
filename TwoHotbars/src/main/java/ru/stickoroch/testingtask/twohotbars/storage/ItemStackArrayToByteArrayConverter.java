package ru.stickoroch.testingtask.twohotbars.storage;

import it.unimi.dsi.fastutil.io.FastByteArrayInputStream;
import it.unimi.dsi.fastutil.io.FastByteArrayOutputStream;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.IOException;

public class ItemStackArrayToByteArrayConverter {

    public byte[] encode(ItemStack @NonNull [] items) throws IOException {
        FastByteArrayOutputStream outputStream = new FastByteArrayOutputStream();
        BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
        dataOutput.writeObject(items);
        dataOutput.close();
        return outputStream.array;
    }


    public ItemStack @NonNull [] decode(byte[] data) throws IOException, ClassNotFoundException {
        FastByteArrayInputStream inputStream = new FastByteArrayInputStream(data);
        BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
        ItemStack[] items = (ItemStack[]) dataInput.readObject();
        dataInput.close();
        return items;
    }

}
