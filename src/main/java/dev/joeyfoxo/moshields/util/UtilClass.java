package dev.joeyfoxo.moshields.util;

import dev.joeyfoxo.moshields.manager.ShieldType;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Set;

public class UtilClass {

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

    public static boolean isCustomShield(ItemMeta meta) {

        Set<NamespacedKey> keys = meta.getPersistentDataContainer().getKeys();
        for (NamespacedKey key : keys) {
            Integer ordinalType = getCustomModelID(meta, key);
            if (ordinalType == null) {
                return false;
            }
            for (ShieldType types : ShieldType.values()) {
                if (types.ordinal() == ordinalType) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int getOrdinal(ItemMeta meta) {

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
        return -1;
    }
}
