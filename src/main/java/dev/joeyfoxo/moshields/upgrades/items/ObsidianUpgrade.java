package dev.joeyfoxo.moshields.upgrades.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ObsidianUpgrade {

    public static ItemStack getUpgrade() {

        ItemStack itemStack = new ItemStack(Material.OBSIDIAN);
        ItemMeta meta = itemStack.getItemMeta();

        meta.displayName(Component.text()
                .content("H")
                .decoration(TextDecoration.OBFUSCATED, true)
                .color(TextColor.color(255, 255, 255))
                .append(Component.text().content("Obsidian Shield Upgrade")
                        .decoration(TextDecoration.OBFUSCATED, false)
                        .color(TextColor.color(100, 100, 100)))
                .append(Component.text().content("H")
                        .decoration(TextDecoration.OBFUSCATED, true)
                        .color(TextColor.color(255, 255, 255)))
                .decoration(TextDecoration.ITALIC, false).build());

        meta.addEnchant(Enchantment.LUCK, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(meta);

        return itemStack;


    }

}
