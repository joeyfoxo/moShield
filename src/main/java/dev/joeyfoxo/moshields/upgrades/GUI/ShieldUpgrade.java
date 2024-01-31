package dev.joeyfoxo.moshields.upgrades.GUI;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.shields.ShieldType;
import dev.joeyfoxo.moshields.upgrades.items.EchoUpgrade;
import dev.joeyfoxo.moshields.upgrades.items.MirrorUpgrade;
import dev.joeyfoxo.moshields.upgrades.items.ReinforcedUpgrade;
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
        event.getPlayer().getInventory().addItem(ReinforcedUpgrade.getUpgrade());
        event.getPlayer().getInventory().addItem(MirrorUpgrade.getUpgrade());
        event.getPlayer().getInventory().addItem(EchoUpgrade.getUpgrade());

    }


    @EventHandler
    public void shieldUpgradeEvent(PrepareAnvilEvent event) {

        AnvilInventory inventory = event.getInventory();
        inventory.setMaximumRepairCost(200);
        upgradedShield = null;

        if (inventory.getFirstItem() == null || inventory.getSecondItem() == null ||
                inventory.getFirstItem().getAmount() > 1 || inventory.getSecondItem().getAmount() > 1) {
            return;
        }

        upgradeSelector(inventory);

        if (upgradedShield == null || !upgradedShield.isUpgradeable()) {
            return;
        }
        inventory.setRepairCost(upgradedShield.getUpgradeCost());
        event.setResult(UtilClass.getShieldItemFromType(upgradedShield));
        event.getViewers().forEach(humanEntity -> ((Player) humanEntity).updateInventory());
    }

    private void upgradeSelector(AnvilInventory inventory) {


        //If the first item is a standard shield
        if (UtilClass.getCustomModelEnum(inventory.getFirstItem().getItemMeta()) == null
                || !UtilClass.isCustomShield(inventory.getFirstItem().getItemMeta())) {

            if (inventory.getSecondItem().equals(SlimeUpgrade.getUpgrade())) {
                upgradedShield = ShieldType.SLIME;
                return;
            }

            if (inventory.getSecondItem().equals(ReinforcedUpgrade.getUpgrade())) {
                upgradedShield = ShieldType.REINFORCED;
                return;
            }

            if (inventory.getSecondItem().equals(EchoUpgrade.getUpgrade())) {
                upgradedShield = ShieldType.ECHO;
                return;
            }

            if (inventory.getSecondItem().equals(new ItemStack(Material.NETHERITE_INGOT))) {
                upgradedShield = ShieldType.NETHERITE;
                return;
            }

        } else {

            if (UtilClass.getCustomModelEnum(inventory.getFirstItem().getItemMeta()) == ShieldType.SLIME
                    && inventory.getSecondItem().equals(MirrorUpgrade.getUpgrade())) {
                upgradedShield = ShieldType.MIRROR;
                return;
            }

        }
    }

}

