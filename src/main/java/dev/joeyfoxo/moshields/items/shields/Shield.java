package dev.joeyfoxo.moshields.items.shields;

import dev.joeyfoxo.moshields.items.data.ShieldType;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class Shield {

    ItemStack itemStack;
    Component title;
    int UID;

    ShieldType shieldType;

    public Shield(ItemStack itemStack, Component title, int UID, ShieldType shieldType) {

        this.itemStack = itemStack;
        this.title = title;
        this.UID = UID;
        this.shieldType = shieldType;
        features();
    }

    protected ShieldType getShieldType() {
        return shieldType;
    }

    public ItemStack createShieldItem() {

        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(title);
        meta.setCustomModelData(UID);
        modifyMeta(meta);

        try {
            Material item = itemStack.getType();
            Field durabilityField = item.getClass().getDeclaredField("durability");
            durabilityField.setAccessible(true);
            durabilitySize(item, durabilityField);

        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        itemStack.setItemMeta(meta);
        return itemStack;

    }

    public ItemStack getItem() {
        return itemStack;
    }

    abstract void features();

    abstract void modifyMeta(ItemMeta meta);

    abstract void durabilitySize(Material item, Field durabilityField);

}
