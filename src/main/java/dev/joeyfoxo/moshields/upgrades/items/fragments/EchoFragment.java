package dev.joeyfoxo.moshields.upgrades.items.fragments;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.upgrades.items.EchoUpgrade;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
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

import java.util.ArrayList;

public class EchoFragment extends Fragments {

    public EchoFragment() {
        shapedRecipe();
    }

    public static ItemStack getFragment() {

        ItemStack itemStack = new ItemStack(Material.DISC_FRAGMENT_5);
        ItemMeta meta = itemStack.getItemMeta();

        meta.displayName(Component.text()
                .content("Echo Fragment")
                .color(TextColor.color(0, 153, 105))
                .decoration(TextDecoration.ITALIC, false).build());


        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.values());

        itemStack.setItemMeta(meta);

        return itemStack;

    }

    @Override
    public ShapedRecipe shapedRecipe() {

        ShapedRecipe shapedRecipe = new ShapedRecipe(
                new NamespacedKey(
                        JavaPlugin.getPlugin(MoShields.class)
                        , "echo_upgrade"),
                EchoUpgrade.getUpgrade());

        shapedRecipe.shape("FFF", "FFF", "FFF");
        shapedRecipe.setIngredient('F', getFragment());

        return shapedRecipe;
    }
}
