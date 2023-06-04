package dev.joeyfoxo.moshields.items.shields;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.items.data.ShieldType;
import dev.joeyfoxo.moshields.items.shields.features.SinkFeature;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class ObsidianShield extends Shield {


    public ObsidianShield(ItemStack itemStack, Component title, int UID, ShieldType shieldType) {
        super(itemStack, title, UID, shieldType);
        createRecipe();
    }

    @Override
    void features() {
        new SinkFeature(this);
    }


    public void createRecipe() {

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(JavaPlugin.getPlugin(MoShields.class), "obsidian_shield"), createShieldItem());
        recipe.shape("OIO", "OOO", " O ");
        recipe.setIngredient('O', Material.OBSIDIAN);
        recipe.setIngredient('I', Material.IRON_INGOT);
        Bukkit.addRecipe(recipe);
    }

    @Override
    void modifyMeta(ItemMeta meta) {
        meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
    }

    @Override
    void durabilitySize(Material item, Field durabilityField) {
        try {
            durabilityField.setShort(item, (short) 300);
        }
        catch (IllegalAccessException ignored) {}
    }


}
