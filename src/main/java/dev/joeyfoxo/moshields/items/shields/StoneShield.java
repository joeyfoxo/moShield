package dev.joeyfoxo.moshields.items.shields;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.items.data.ShieldType;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class StoneShield extends Shield {


    public StoneShield(ItemStack itemStack, Component title, int UID, ShieldType shieldType) {
        super(itemStack, title, UID, shieldType);
        createRecipe();
    }

    @Override
    void features() {

    }


    public void createRecipe() {

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(JavaPlugin.getPlugin(MoShields.class), "stone_shield"), createShieldItem());
        recipe.shape("SIS", "SSS", " S ");
        recipe.setIngredient('S', new RecipeChoice.MaterialChoice(
                Material.STONE,
                Material.COBBLESTONE,
                Material.BLACKSTONE,
                Material.SMOOTH_STONE));

        recipe.setIngredient('I', Material.IRON_INGOT);


        Bukkit.addRecipe(recipe);
    }

    @Override
    void modifyMeta(ItemMeta meta) {

        meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);

    }

    @Override
    void durabilitySize(Material item, Field durabilityField) {

    }


}
