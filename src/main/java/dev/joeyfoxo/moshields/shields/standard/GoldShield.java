package dev.joeyfoxo.moshields.shields.standard;

import dev.joeyfoxo.moshields.shields.Shield;
import dev.joeyfoxo.moshields.shields.ShieldType;
import dev.joeyfoxo.moshields.util.UtilClass;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class GoldShield extends Shield {

    NamespacedKey key;

    public GoldShield(ShieldType shieldType, Component title, NamespacedKey key) {
        super(shieldType, title);
        this.key = key;
        createRecipe();
    }

    public void createRecipe() {

        ShapedRecipe recipe = new ShapedRecipe(key, createShieldItem());
        recipe.shape("G G", "GGG", " G ");
        recipe.setIngredient('G', Material.GOLD_INGOT);
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
        return ShieldType.GOLD;
    }
}
