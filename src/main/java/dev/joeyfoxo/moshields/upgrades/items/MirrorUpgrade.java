package dev.joeyfoxo.moshields.upgrades.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MirrorUpgrade {

    public static ItemStack getUpgrade() {

        ItemStack itemStack = new ItemStack(Material.WHITE_STAINED_GLASS);
        ItemMeta meta = itemStack.getItemMeta();

        meta.displayName(Component.text()
                .content("Mirrored Shield Upgrade")
                .color(TextColor.color(83, 98, 100))
                .decoration(TextDecoration.ITALIC, false).build());

        itemStack.setItemMeta(meta);

        return itemStack;


    }

}
