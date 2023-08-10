package dev.joeyfoxo.moshields.shields;

import dev.joeyfoxo.moshields.MoShields;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import static dev.joeyfoxo.moshields.util.UtilClass.shieldItemMap;

public abstract class Shield implements ShieldRequirements {

    ItemStack itemStack;
    Component title;
    ShieldType shieldType;
    int maxDurability;
    public static NamespacedKey ShieldDurabilityNamespace = new NamespacedKey(JavaPlugin.getPlugin(MoShields.class), "ShieldDurability");

    public Shield(ShieldType shieldType, Component title) {
        this.itemStack = new ItemStack(Material.SHIELD);
        this.title = title;
        this.shieldType = shieldType;
        this.maxDurability = shieldType.getDurability();
        shieldAbilities();
    }

    public ItemStack createShieldItem() {

        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(title);
        meta.getPersistentDataContainer().set(ShieldDurabilityNamespace, PersistentDataType.INTEGER, maxDurability);

        modifyMeta(meta);
        itemStack.setItemMeta(meta);
        shieldItemMap.put(shieldType, itemStack);
        return itemStack;

    }

    abstract void modifyMeta(ItemMeta meta);

    abstract void shieldAbilities();

}
