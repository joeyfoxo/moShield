package dev.joeyfoxo.moshields.loottables;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.upgrades.items.SlimeUpgrade;
import dev.joeyfoxo.moshields.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.LootTables;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class LootTablePopulate implements Listener {

    public LootTablePopulate() {

        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(MoShields.class));

    }

    @EventHandler
    public void onChestOpen(PlayerInteractEvent event) {

        if (event.getClickedBlock() != null) {

            if (event.getAction().isRightClick() &&
                    event.getClickedBlock().getType() == Material.CHEST) {

                Chest chest = (Chest) event.getClickedBlock().getState();
                Inventory chestInventory = chest.getInventory();
                if (chest.hasLootTable()) {

                    LootTable lootTable = chest.getLootTable();
                    LootContext context = new LootContext.Builder(
                            chest.getLocation()).build();

                    Collection<ItemStack> lootItems =
                            lootTable.populateLoot(new Random(), context);
                    generateShieldUpgrade(lootTable, lootItems);

                    lootItems.forEach(item -> {
                        chestInventory.setItem(ThreadLocalRandom.current()
                                        .nextInt(0, chestInventory.getSize()),
                                item);
                    });
                }

            }
        }
    }

    private void generateShieldUpgrade(LootTable lootTable, Collection<ItemStack> lootItems) {

        if (LootTables.VILLAGE_FISHER.getLootTable().equals(lootTable)) {
            if (UtilClass.percentChance(0.2)) { //20% chance in fishing village chests
                lootItems.add(SlimeUpgrade.getUpgrade());
            }
        }
    }


}

