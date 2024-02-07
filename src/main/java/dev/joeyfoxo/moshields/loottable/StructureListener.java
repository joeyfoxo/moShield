package dev.joeyfoxo.moshields.loottable;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.upgrades.items.EchoUpgrade;
import dev.joeyfoxo.moshields.upgrades.items.fragments.EchoFragment;
import dev.joeyfoxo.moshields.upgrades.items.fragments.ReinforcedFragment;
import dev.joeyfoxo.moshields.upgrades.items.reinforced.ReinforcedUpgrade;
import dev.joeyfoxo.moshields.upgrades.items.SlimeUpgrade;
import io.papermc.paper.math.Position;
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

public class StructureListener implements Listener {

    public StructureListener() {
        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(MoShields.class));
    }

    @EventHandler
    public void playerOpenChest(PlayerInteractEvent event) {

        if (event.getClickedBlock() == null) {
            return;
        }


        if (event.getClickedBlock().getState() instanceof Chest chest && event.getAction().isRightClick()) {

            if (chest.hasBeenFilled()) {
                return;
            }

            Location chestLocation = chest.getLocation();
            Inventory inventory = chest.getBlockInventory();
            World world = chestLocation.getWorld();


            if (world.hasStructureAt(Position.block(chestLocation), Structure.ANCIENT_CITY)) {
                LootPopulator.populateLoot(inventory, EchoFragment.getFragment(), 20);
                return;
            }

            if (world.hasStructureAt(Position.block(chestLocation), Structure.JUNGLE_PYRAMID)) {
                LootPopulator.populateLoot(inventory, SlimeUpgrade.getUpgrade(), 0.1);
                return;
            }

            if (world.hasStructureAt(Position.block(chestLocation), Structure.STRONGHOLD)) {
                LootPopulator.populateLoot(inventory, ReinforcedFragment.getFragment(), 30);
                return;
            }


        }
    }

}
