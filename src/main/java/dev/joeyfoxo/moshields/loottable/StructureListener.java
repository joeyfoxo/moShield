package dev.joeyfoxo.moshields.loottable;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.upgrades.items.EchoUpgrade;
import dev.joeyfoxo.moshields.upgrades.items.SlimeUpgrade;
import dev.joeyfoxo.moshields.upgrades.items.SpikedUpgrade;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.generator.structure.Structure;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StructureSearchResult;

public class StructureListener implements Listener {

    public StructureListener() {
        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(MoShields.class));
    }

    public boolean isStructureNearby(Location location, Structure structure, int radius) {
        World world = location.getWorld();
        StructureSearchResult structureSearchResult = world.locateNearestStructure(location, structure, radius, false);
        return structureSearchResult != null;
    }

    @EventHandler
    public void playerOpenChest(PlayerInteractEvent event) {

        LootPopulator populator = new LootPopulator();

        if (event.getClickedBlock() == null) {
            return;
        }

        if (event.getClickedBlock().getState() instanceof Chest chest && event.getAction().isRightClick()) {

            if (chest.hasBeenFilled()) {
                return;
            }

            Location chestLocation = chest.getLocation();
            Inventory inventory = chest.getBlockInventory();

            if (isStructureNearby(chestLocation, Structure.ANCIENT_CITY, 10)) {
                populator.populateLoot(inventory, EchoUpgrade.getUpgrade(), 1);
                return;
            }

            if (isStructureNearby(chestLocation, Structure.JUNGLE_PYRAMID, 10)) {
                populator.populateLoot(inventory, SlimeUpgrade.getUpgrade(), 0.04);
                return;
            }

            if (isStructureNearby(chestLocation, Structure.BASTION_REMNANT, 10)) {
                populator.populateLoot(inventory, SpikedUpgrade.getUpgrade(), 0.06);
                return;
            }

        }
    }

}
