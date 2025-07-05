package ru.stickoroch.testingtask.traderperl;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.List;
import java.util.Random;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WanderingTraderRecipeFactory {

    @NonNull
    Random random;

    public WanderingTraderRecipeFactory(@NonNull Random random) {
        this.random = random;
    }

    @NonNull
    public MerchantRecipe createRandomizeMerchantRecipe() {
        MerchantRecipe recipe = new MerchantRecipe(new ItemStack(Material.ENDER_PEARL, 1), 999);
        recipe.setIngredients(List.of(new ItemStack(Material.GOLD_INGOT, random.nextInt(3) + 1)));
        return recipe;
    }
}
