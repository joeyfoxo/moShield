package dev.joeyfoxo.moshields.upgrades.items.reinforced;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ReinforcedUpgrade {

    public static ItemStack getUpgrade() {

        ItemStack itemStack = new ItemStack(Material.IRON_INGOT);
        ItemMeta meta = itemStack.getItemMeta();

        meta.displayName(Component.text()
                .content("Reinforced Shield Upgrade")
                .color(TextColor.color(182, 201, 203))
                .decoration(TextDecoration.ITALIC, false).build());

        itemStack.setItemMeta(meta);

        return itemStack;


    }

}
