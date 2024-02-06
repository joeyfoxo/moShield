package dev.joeyfoxo.moshields.loottable;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.upgrades.items.EchoUpgrade;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.generator.structure.Structure;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StructureSearchResult;

public class StructureListener implements Listener {

    public StructureListener() {
        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(MoShields.class));
    }

    @EventHandler
    public void playerOpenChest(PlayerInteractEvent event) {

        if (event.getClickedBlock() == null ||
                !(event.getClickedBlock().getType() == Material.CHEST || event.getClickedBlock().getType() == Material.TRAPPED_CHEST) ||
                !event.getAction().isRightClick()) {
            return;
        }

        Chest chest = (Chest) event.getClickedBlock().getState();
        Location chestLocation = chest.getLocation();
        World world = chestLocation.getWorld();
        StructureSearchResult structureSearchResult = world.locateNearestStructure(chestLocation, Structure.ANCIENT_CITY, 10, false);

        System.out.println(structureSearchResult);

        if (structureSearchResult == null) {
            return;
        }

        chest.getBlockInventory().addItem(EchoUpgrade.getUpgrade());

    }

}
