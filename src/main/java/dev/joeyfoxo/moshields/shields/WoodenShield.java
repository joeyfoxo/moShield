package dev.joeyfoxo.moshields.shields;

import dev.joeyfoxo.moshields.util.UtilClass;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.stream.Collectors;

public class WoodenShield extends Shield {

    NamespacedKey key;

    public WoodenShield(ShieldType shieldType, Component title, NamespacedKey key) {
        super(shieldType, title);
        this.key = key;
        createRecipe();
    }

    public void createRecipe() {

        ShapedRecipe recipe = new ShapedRecipe(key, createShieldItem());
        recipe.shape("WIW", "WWW", " W ");
        recipe.setIngredient('W', new RecipeChoice.MaterialChoice(                Material.ACACIA_PLANKS,
                Material.BAMBOO_PLANKS,
                Material.BIRCH_PLANKS,
                Material.CRIMSON_PLANKS,
                Material.JUNGLE_PLANKS,
                Material.CHERRY_PLANKS,
                Material.DARK_OAK_PLANKS,
                Material.SPRUCE_PLANKS,
                Material.MANGROVE_PLANKS,
                Material.WARPED_PLANKS,
                Material.OAK_PLANKS,
                Material.ACACIA_PLANKS));
        recipe.setIngredient('I', Material.IRON_INGOT);
        Bukkit.addRecipe(recipe);

    }

    @Override
    void modifyMeta(ItemMeta meta) {
        UtilClass.setCustomModelID(meta, key, getShieldType());
        // ALWAYS INCLUDE THIS LINE

    }

    @Override
    void shieldAbilities() {

    }

    @Override
    public ShieldType getShieldType() {
        return ShieldType.WOODEN;
    }
}
