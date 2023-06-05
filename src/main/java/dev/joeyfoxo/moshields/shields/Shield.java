package dev.joeyfoxo.moshields.shields;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class Shield implements Listener {

    ItemStack itemStack;
    Component title;

    int maxDurability = 500;

    static NamespacedKey ShieldDurabilityNamespace = new NamespacedKey(JavaPlugin.getPlugin(MoShields.class), "ShieldDurability");


    public Shield(ItemStack itemStack, Component title) {

        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(MoShields.class));

        this.itemStack = itemStack;
        this.title = title;
        features();
    }

    public Shield(ItemStack itemStack, Component title, int maxDurability) {

        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(MoShields.class));

        this.itemStack = itemStack;
        this.title = title;
        this.maxDurability = maxDurability;
        features();
    }

    @EventHandler
    public void getDurabilityChange(PlayerItemDamageEvent event) {

        if (!UtilClass.isCustomShield(event.getItem().getItemMeta())) {
            return;
        }

        ItemStack itemStack = event.getItem();
        Damageable meta = (Damageable) itemStack.getItemMeta();

        int shieldMaxDurability = switch (UtilClass.getCustomModelEnum(meta)) {
            case OBSIDIAN -> ObsidianShield.maxDurability;
            case STONE -> StoneShield.maxDurability;
            default -> 0;
        };

        @NotNull PersistentDataContainer data = meta.getPersistentDataContainer();
        int shieldCurDurability;
        if (data.has(ShieldDurabilityNamespace)) {
            shieldCurDurability = meta.getPersistentDataContainer().get(ShieldDurabilityNamespace, PersistentDataType.INTEGER);
        } else {
            System.err.println("NBT MISSING");
            return;
        }

        int damageTaken = event.getOriginalDamage(); // This line
        int shieldNewDurability = shieldCurDurability - damageTaken;

        // set Shield durability nbt
        meta.getPersistentDataContainer().set(ShieldDurabilityNamespace, PersistentDataType.INTEGER, shieldNewDurability);

        float rawShieldExpectedDamage = ((1 - (float) shieldNewDurability / shieldMaxDurability)) * Material.SHIELD.getMaxDurability(); //gives the wanted durability of the shield
        int damageToShield = (int) Math.floor(rawShieldExpectedDamage - meta.getDamage());

        event.setDamage(damageToShield); // Should break shield if shield is negative

        meta.lore(List.of(Component.text().content(" ").build(), Component.text()
                .content(shieldCurDurability + " / " + shieldMaxDurability)
                .color(TextColor.color(255, 255, 255))
                .decoration(TextDecoration.ITALIC, false).build()));
        itemStack.setItemMeta(meta);

    }

    public ItemStack createShieldItem() {


        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(title);
        meta.getPersistentDataContainer().set(ShieldDurabilityNamespace, PersistentDataType.INTEGER, maxDurability);

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
