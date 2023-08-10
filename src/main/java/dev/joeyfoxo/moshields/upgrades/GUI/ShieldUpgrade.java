package dev.joeyfoxo.moshields.upgrades.GUI;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.shields.ShieldType;
import dev.joeyfoxo.moshields.upgrades.items.ObsidianUpgrade;
import dev.joeyfoxo.moshields.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ShieldUpgrade implements Listener {

    public ShieldUpgrade() {
        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(MoShields.class));
    }

    @EventHandler
    public void TEMPJOIN(PlayerJoinEvent event) {

        event.getPlayer().getInventory().addItem(ObsidianUpgrade.getUpgrade());

    }


    @EventHandler
    public void shieldUpgradeEvent(PrepareAnvilEvent event) {

        AnvilInventory inventory = event.getInventory();
        inventory.setMaximumRepairCost(200);

        if (inventory.getFirstItem() == null || inventory.getSecondItem() == null) {
            return;
        }

        if (!inventory.getFirstItem().equals(new ItemStack(Material.SHIELD))) {
            return;
        }

        if (inventory.getSecondItem().equals(ObsidianUpgrade.getUpgrade())) {
            inventory.setRepairCost(20);
            event.setResult(UtilClass.getShieldItemFromType(ShieldType.OBSIDIAN));

        }

        event.getViewers().forEach(humanEntity -> ((Player) humanEntity).updateInventory());
    }

}

