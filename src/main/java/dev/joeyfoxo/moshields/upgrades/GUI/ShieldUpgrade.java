package dev.joeyfoxo.moshields.upgrades.GUI;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.shields.ShieldType;
import dev.joeyfoxo.moshields.upgrades.items.*;
import dev.joeyfoxo.moshields.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShieldUpgrade implements Listener {

    ItemStack echoUpgrade = EchoUpgrade.getUpgrade();
    ItemStack mirrorUpgrade = MirrorUpgrade.getUpgrade();
    ItemStack reinforcedUpgrade = ReinforcedUpgrade.getUpgrade();
    ItemStack slimeUpgrade = SlimeUpgrade.getUpgrade();
    ItemStack spikedUpgrade = SpikedUpgrade.getUpgrade();

    Set<ItemStack> unPlaceableBlocks = new HashSet<>(List.of(echoUpgrade, mirrorUpgrade, reinforcedUpgrade, slimeUpgrade, spikedUpgrade));

    ShieldType upgradedShield;

    public ShieldUpgrade() {
        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(MoShields.class));
    }

    @EventHandler
    public void TEMPJOIN(PlayerJoinEvent event) {

        event.getPlayer().getInventory().addItem(slimeUpgrade);
        event.getPlayer().getInventory().addItem(reinforcedUpgrade);
        event.getPlayer().getInventory().addItem(mirrorUpgrade);
        event.getPlayer().getInventory().addItem(echoUpgrade);
        event.getPlayer().getInventory().addItem(spikedUpgrade);

    }
    @EventHandler
    public void playerPlaceEvent(BlockPlaceEvent event) {

        if (unPlaceableBlocks.contains(event.getItemInHand())) {
            event.getPlayer().sendMessage(Component.text().content("You cannot place shield upgrades!").color(TextColor.color(255, 100, 100)).build());
            event.setCancelled(true);
        }

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
                || !UtilClass.isHoldingCustomShield(inventory.getFirstItem().getItemMeta())) {

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

            if (inventory.getSecondItem().equals(SpikedUpgrade.getUpgrade())) {
                upgradedShield = ShieldType.SPIKED;
                return;
            }

            if (inventory.getSecondItem().equals(new ItemStack(Material.NETHERITE_INGOT))) {
                upgradedShield = ShieldType.NETHERITE;
            }

        } else {

            if (UtilClass.getCustomModelEnum(inventory.getFirstItem().getItemMeta()) == ShieldType.SLIME
                    && inventory.getSecondItem().equals(MirrorUpgrade.getUpgrade())) {
                upgradedShield = ShieldType.MIRROR;
            }

        }
    }

}

