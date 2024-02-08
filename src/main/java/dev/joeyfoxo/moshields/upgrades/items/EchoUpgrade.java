package dev.joeyfoxo.moshields.upgrades.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class EchoUpgrade {

    public static ItemStack getUpgrade() {

        ItemStack itemStack = new ItemStack(Material.ECHO_SHARD);
        ItemMeta meta = itemStack.getItemMeta();
        ArrayList<TextComponent> lore = new ArrayList<>();

        meta.displayName(Component.text()
                .content("Echo Shield Upgrade")
                .color(TextColor.color(0, 153, 105))
                .decoration(TextDecoration.ITALIC, false).build());

        lore.add(Component.text().content("Used to craft Echo Shield")
                .color(TextColor.color(100, 100, 100))
                .decoration(TextDecoration.ITALIC, false).build());

        meta.lore(lore);
        itemStack.setItemMeta(meta);

        return itemStack;


    }

}
