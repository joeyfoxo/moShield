package dev.joeyfoxo.moshields.shields;

import dev.joeyfoxo.moshields.MoShields;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Shield implements Listener {

    ItemStack itemStack;
    Component title;

    int maxDurability;

    public static NamespacedKey ShieldDurabilityNamespace = new NamespacedKey(JavaPlugin.getPlugin(MoShields.class), "ShieldDurability");

    public Shield(ItemStack itemStack, Component title, int maxDurability) {

        this(itemStack, title);
        this.maxDurability = maxDurability;
    }

    public ItemStack createShieldItem() {


        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(title);
        meta.getPersistentDataContainer().set(ShieldDurabilityNamespace, PersistentDataType.INTEGER, maxDurability);

        // ^^ ERROR as it has the same key, every shield has the same durability, to avoid this use a new key for each shield

        modifyMeta(meta);
        itemStack.setItemMeta(meta);
        return itemStack;

    }

    public ItemStack getItem() {
        return itemStack;
    }

    abstract void features();

    abstract void modifyMeta(ItemMeta meta);

}
