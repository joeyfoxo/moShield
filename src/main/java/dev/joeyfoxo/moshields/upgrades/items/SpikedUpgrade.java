package dev.joeyfoxo.moshields.upgrades.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpikedUpgrade {

    public static ItemStack getUpgrade() {

        ItemStack itemStack = new ItemStack(Material.POINTED_DRIPSTONE);
        ItemMeta meta = itemStack.getItemMeta();

        meta.displayName(Component.text()
                .content("Spiked Shield Upgrade")
                .color(TextColor.color(54, 54, 54))
                .decoration(TextDecoration.ITALIC, false).build());

        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(meta);

        return itemStack;


    }

}
