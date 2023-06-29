package dev.joeyfoxo.moshields.shields;

import dev.joeyfoxo.moshields.manager.ShieldType;
import dev.joeyfoxo.moshields.shields.features.Features;
import dev.joeyfoxo.moshields.util.UtilClass;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class ObsidianShield extends Shield {

    NamespacedKey key;
    public static int maxDurability = 500;

    public ObsidianShield(ItemStack itemStack, Component title, NamespacedKey key) {
        super(itemStack, title, maxDurability);
        this.key = key;

    }


    @Override
     void createRecipe() {

        ShapedRecipe recipe = new ShapedRecipe(key, createShieldItem());
        recipe.shape("OIO", "OOO", " O ");
        recipe.setIngredient('O', Material.OBSIDIAN);
        recipe.setIngredient('I', Material.IRON_INGOT);
        Bukkit.addRecipe(recipe);
    }

    @Override
    void modifyMeta(ItemMeta meta) {
        meta.addEnchant(Enchantment.KNOCKBACK, 1, true);
        UtilClass.setCustomModelID(meta, key, getShieldType());
    }

    @Override
    void shieldAbilities() {
        Features.addSinkableShield(getShieldType());
    }


    @Override
    public ShieldType getShieldType() {
        return ShieldType.OBSIDIAN;
    }


}
