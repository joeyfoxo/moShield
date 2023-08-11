package dev.joeyfoxo.moshields.upgrades.GUI;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.shields.ShieldType;
import dev.joeyfoxo.moshields.upgrades.items.SlimeUpgrade;
import dev.joeyfoxo.moshields.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ShieldUpgrade implements Listener {

    ShieldType upgradedShield;

    public ShieldUpgrade() {
        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(MoShields.class));
    }

    @EventHandler
    public void TEMPJOIN(PlayerJoinEvent event) {

        event.getPlayer().getInventory().addItem(SlimeUpgrade.getUpgrade());

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

        upgradeSelector(inventory);

        if (!upgradedShield.isUpgradeable()) {
            return;
        }
        inventory.setRepairCost(upgradedShield.getUpgradeCost());
        event.setResult(UtilClass.getShieldItemFromType(upgradedShield));
        event.getViewers().forEach(humanEntity -> ((Player) humanEntity).updateInventory());
    }

    private void upgradeSelector(AnvilInventory inventory) {

        if (inventory.getSecondItem().equals(SlimeUpgrade.getUpgrade())) {
            upgradedShield = ShieldType.SLIME;
        }

        if (inventory.getSecondItem().equals(new ItemStack(Material.NETHERITE_INGOT))) {
            upgradedShield = ShieldType.NETHERITE;
        }

    }

}

