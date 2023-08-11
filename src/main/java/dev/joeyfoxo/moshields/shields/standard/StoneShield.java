package dev.joeyfoxo.moshields.shields.standard;

import dev.joeyfoxo.moshields.shields.Shield;
import dev.joeyfoxo.moshields.shields.ShieldType;
import dev.joeyfoxo.moshields.util.UtilClass;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class StoneShield extends Shield {

    NamespacedKey key;

    public StoneShield(ShieldType shieldType, Component title, NamespacedKey key) {
        super(shieldType, title);
        this.key = key;
        createRecipe();
    }

    public void createRecipe() {

        ShapedRecipe recipe = new ShapedRecipe(key, createShieldItem());
        recipe.shape("SIS", "SSS", " S ");
        recipe.setIngredient('S', new RecipeChoice.MaterialChoice(
                Material.STONE,
                Material.SMOOTH_STONE,
                Material.MOSSY_COBBLESTONE,
                Material.COBBLESTONE,
                Material.DIORITE,
                Material.ANDESITE,
                Material.DEEPSLATE,
                Material.COBBLED_DEEPSLATE));
        recipe.setIngredient('I', Material.IRON_INGOT);
        Bukkit.addRecipe(recipe);

    }

     @Override
     protected void modifyMeta(ItemMeta meta) {
        UtilClass.setCustomModelID(meta, key, getShieldType());
        // ALWAYS INCLUDE THIS LINE

    }

    @Override
    protected void shieldAbilities() {

    }

    @Override
    public ShieldType getShieldType() {
        return ShieldType.STONE;
    }
}
