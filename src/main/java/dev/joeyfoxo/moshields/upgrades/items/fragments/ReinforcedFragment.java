package dev.joeyfoxo.moshields.upgrades.items.fragments;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.upgrades.items.reinforced.ReinforcedUpgrade;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class ReinforcedFragment extends Fragments {

    public ReinforcedFragment() {
        shapedRecipe();
    }

    public static ItemStack getFragment() {

        ItemStack itemStack = new ItemStack(Material.IRON_NUGGET);
        ItemMeta meta = itemStack.getItemMeta();

        meta.displayName(Component.text()
                .content("Reinforced Fragment")
                .color(TextColor.color(87, 91, 91))
                .decoration(TextDecoration.ITALIC, false).build());

        itemStack.setItemMeta(meta);

        return itemStack;

    }

    @Override
    public ShapedRecipe shapedRecipe() {

        ShapedRecipe shapedRecipe = new ShapedRecipe(
                new NamespacedKey(
                        JavaPlugin.getPlugin(MoShields.class)
                        , "reinforced_upgrade"),
                ReinforcedUpgrade.getUpgrade());

        shapedRecipe.shape("FFF", "FFF", "FFF");
        shapedRecipe.setIngredient('F', getFragment());

        return shapedRecipe;
    }
}
