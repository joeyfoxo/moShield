package dev.joeyfoxo.moshields.shields;

import dev.joeyfoxo.moshields.MoShields;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public abstract class Shield implements Listener {

    ItemStack itemStack;
    Component title;


    public Shield(ItemStack itemStack, Component title) {

        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(MoShields.class));

        this.itemStack = itemStack;
        this.title = title;
        features();
    }

    @EventHandler
    public void getDurabilityChange(PlayerItemDamageEvent event) {

        if (!UtilClass.isCustomShield(event.getItem().getItemMeta())) {
            return;
        }

        ItemStack itemStack = event.getItem();
        Damageable meta = (Damageable) itemStack.getItemMeta();

        int fakeDurability = Material.SHIELD.getMaxDurability();

        switch (UtilClass.getCustomModelEnum(meta)) {
            case OBSIDIAN -> fakeDurability = UtilClass.getDurability(ObsidianShield.durabilityNameSpaceKey, meta);
            case STONE -> fakeDurability = UtilClass.getDurability(StoneShield.durabilityNameSpaceKey, meta);
        }

        System.out.println(fakeDurability);

        double damage = (double) ((fakeDurability - meta.getDamage()) / fakeDurability) * Material.SHIELD.getMaxDurability();

        event.setDamage((int) Math.round(fakeDurability - damage));
        meta.lore(List.of(Component.text().content(" ").build(), Component.text()
                .content(fakeDurability - meta.getDamage() + " / " + fakeDurability)
                .color(TextColor.color(255, 255, 255))
                .decoration(TextDecoration.ITALIC, false).build()));
        itemStack.setItemMeta(meta);

    }

    public ItemStack createShieldItem() {


        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(title);
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
