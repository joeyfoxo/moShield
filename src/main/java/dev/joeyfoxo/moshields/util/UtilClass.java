package dev.joeyfoxo.moshields.util;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.shields.ShieldType;
import dev.joeyfoxo.moshields.shields.features.Features;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class UtilClass {

    public static HashMap<ShieldType, ItemStack> shieldItemMap = new HashMap<>();


    public static Boolean percentChance(double chance) {
        return (Math.random() <= chance);
    }
    public static void setCustomModelID(ItemMeta meta, NamespacedKey key, ShieldType type) {
        meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, type.ordinal());
    }

    public static Integer getCustomModelID(ItemMeta meta, NamespacedKey key) {
        return meta.getPersistentDataContainer().get(key, PersistentDataType.INTEGER);
    }

    public static ShieldType getCustomModelEnum(ItemMeta meta) {

        int ordinal = getOrdinal(meta);

        if (ordinal != -1) {

            for (ShieldType types : ShieldType.values()) {
                if (types.ordinal() == ordinal) {
                    return types;
                }

            }
        }
        return null;
    }

    public static boolean isHoldingCustomShield(Player player) {

        return (getCustomModelEnum(player.getInventory().getItemInMainHand().getItemMeta()) != null)
                || (getCustomModelEnum(player.getInventory().getItemInOffHand().getItemMeta()) != null);
    }

    public static boolean isHoldingCustomShield(ItemMeta meta) {

        return getCustomModelEnum(meta) != null;
    }

    public static int getOrdinal(ItemMeta meta) {

        if (meta != null) {
            Set<NamespacedKey> keys = meta.getPersistentDataContainer().getKeys();
            for (NamespacedKey key : keys) {
                Integer ordinalType = getCustomModelID(meta, key);

                if (ordinalType == null) {
                    return -1;
                }
                for (ShieldType types : ShieldType.values()) {
                    if (types.ordinal() == ordinalType) {
                        return ordinalType;
                    }
                }
            }
        }
        return -1;
    }

    public static Set<ShieldType> getHeldShields(Player player) {

        Set<ShieldType> heldShields = new HashSet<>();

        if (getCustomModelEnum(player.getInventory().getItemInMainHand().getItemMeta()) != null) {
            heldShields.add(getCustomModelEnum(player.getInventory().getItemInMainHand().getItemMeta()));
        }

        if (getCustomModelEnum(player.getInventory().getItemInOffHand().getItemMeta()) != null) {
            heldShields.add(getCustomModelEnum(player.getInventory().getItemInOffHand().getItemMeta()));
        }
        return heldShields;
    }

    public static ItemStack getShieldItemFromType(ShieldType shieldType) {

        return shieldItemMap.getOrDefault(shieldType, null);

    }

}