package dev.joeyfoxo.moshields.manager;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.shields.ShieldType;
import dev.joeyfoxo.moshields.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static dev.joeyfoxo.moshields.shields.Shield.ShieldDurabilityNamespace;

public class DurabilityHandler implements Listener {

    public DurabilityHandler() {
        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(MoShields.class));
    }

    @EventHandler
    public void getDurabilityChange(PlayerItemDamageEvent event) {

        ItemStack itemStack = event.getItem();
        ShieldType shieldType = UtilClass.getCustomModelEnum(itemStack.getItemMeta());

        if (UtilClass.isCustomShield(itemStack.getItemMeta()) && shieldType.hasCustomDurability()) {

            applyDamageToShield(event,
                    event.getItem(),
                    shieldType);
        }

    }

    public static void applyDamageToShield(PlayerItemDamageEvent event, ItemStack heldItem, ShieldType type) {

        Damageable meta = (Damageable) heldItem.getItemMeta();
        int shieldMaxDurability = type.getDurability();

        @NotNull PersistentDataContainer data = meta.getPersistentDataContainer();
        int shieldCurDurability;
        if (!data.has(ShieldDurabilityNamespace)) {
            return;
        }
        shieldCurDurability = meta.getPersistentDataContainer().get(ShieldDurabilityNamespace, PersistentDataType.INTEGER);

        int shieldNewDurability = shieldCurDurability - event.getOriginalDamage();

        // set Shield durability nbt
        meta.getPersistentDataContainer().set(ShieldDurabilityNamespace, PersistentDataType.INTEGER, shieldNewDurability);

        float rawShieldExpectedDamage = ((1 - (float) shieldNewDurability / shieldMaxDurability)) * Material.SHIELD.getMaxDurability(); //gives the wanted durability of the shield
        int damageToShield = (int) Math.floor(rawShieldExpectedDamage - meta.getDamage());
        event.setDamage(damageToShield); // Should break shield if shield is negative

        if (meta.hasLore()) {
            List<Component> lore = meta.lore();

            boolean isRemoved = lore.removeIf(currentLineIterator -> hasDurabilityDamage(currentLineIterator.toString()));

            Component currentLine = Component.text().content("Durability: " + shieldCurDurability + " / " + shieldMaxDurability)
                    .color(TextColor.color(255, 255, 255))
                    .decoration(TextDecoration.ITALIC, false).build();

            if (isRemoved || lore.stream().noneMatch(line -> hasDurabilityDamage(line.toString()))) {
                lore.add(currentLine);
                meta.lore(lore);
            }
        } else {

            List<Component> lore = new ArrayList<>();
            lore.add(Component.text().content("Durability: " + shieldCurDurability + " / " + shieldMaxDurability)
                    .color(TextColor.color(255, 255, 255))
                    .decoration(TextDecoration.ITALIC, false).build());
            meta.lore(lore);
        }
        heldItem.setItemMeta(meta);

    }

    public static void applyDamageToShield(ItemStack heldItem, ShieldType type, int damageDone) {

        Damageable meta = (Damageable) heldItem.getItemMeta();
        int shieldMaxDurability = type.getDurability();

        @NotNull PersistentDataContainer data = meta.getPersistentDataContainer();
        int shieldCurDurability;
        if (!data.has(ShieldDurabilityNamespace)) {
            return;
        }
        shieldCurDurability = meta.getPersistentDataContainer().get(ShieldDurabilityNamespace, PersistentDataType.INTEGER);

        int shieldNewDurability = shieldCurDurability - damageDone;

        // set Shield durability nbt
        meta.getPersistentDataContainer().set(ShieldDurabilityNamespace, PersistentDataType.INTEGER, shieldNewDurability);

        int damageSet = meta.getDamage() + damageDone;
        meta.setDamage(damageSet);

        if (meta.hasLore()) {
            List<Component> lore = meta.lore();

            boolean isRemoved = lore.removeIf(currentLineIterator -> hasDurabilityDamage(currentLineIterator.toString()));

            Component currentLine = Component.text().content(shieldCurDurability + " / " + shieldMaxDurability)
                    .color(TextColor.color(255, 255, 255))
                    .decoration(TextDecoration.ITALIC, false).build();

            if (isRemoved || lore.stream().noneMatch(line -> hasDurabilityDamage(line.toString()))) {
                lore.add(currentLine);
                meta.lore(lore);
            }
        } else {

            List<Component> lore = new ArrayList<>();
            lore.add(Component.text().content(shieldCurDurability + " / " + shieldMaxDurability)
                    .color(TextColor.color(255, 255, 255))
                    .decoration(TextDecoration.ITALIC, false).build());
            meta.lore(lore);
        }
        heldItem.setItemMeta(meta);
    }

    private static boolean hasDurabilityDamage(String string) {

        Pattern pattern = Pattern.compile("\\d+\\s*\\/\\s*\\d+");
        return pattern.matcher(string).find();
    }
}
