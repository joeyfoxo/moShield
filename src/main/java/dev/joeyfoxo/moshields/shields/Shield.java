package dev.joeyfoxo.moshields.shields;

import dev.joeyfoxo.moshields.MoShields;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Shield implements ShieldTypeRequirement {

    ItemStack itemStack;
    Component title;

    int maxDurability;

    public static NamespacedKey ShieldDurabilityNamespace = new NamespacedKey(JavaPlugin.getPlugin(MoShields.class), "ShieldDurability");

    public Shield(ItemStack itemStack, Component title, int maxDurability) {
        this.itemStack = itemStack;
        this.title = title;
        this.maxDurability = maxDurability;
        shieldAbilities();
    }

    public ItemStack createShieldItem() {

        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(title);
        meta.getPersistentDataContainer().set(ShieldDurabilityNamespace, PersistentDataType.INTEGER, maxDurability);

        modifyMeta(meta);
        itemStack.setItemMeta(meta);
        return itemStack;

    }

    abstract void modifyMeta(ItemMeta meta);

    abstract void shieldAbilities();

}
