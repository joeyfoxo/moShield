package dev.joeyfoxo.moshields.items.shields.features;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.items.shields.Shield;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SinkFeature implements Listener {

    Set<UUID> playersSinking = new HashSet<>();

    Shield shield;
    public SinkFeature(Shield shield) {
        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(MoShields.class));
        this.shield = shield;
        sinkPlayer();

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEnterWater(PlayerMoveEvent event) {

        Player player = event.getPlayer();
        if (!player.isUnderWater()) {
            return;
        }

        if ((player.getInventory().getItemInOffHand().hasItemMeta()
                && player.getInventory().getItemInOffHand().getItemMeta().getCustomModelData() == (shield.getItem().getItemMeta().getCustomModelData()))
                || (player.getInventory().getItemInMainHand().hasItemMeta()
                && player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == (shield.getItem().getItemMeta().getCustomModelData()))) {
            playersSinking.add(player.getUniqueId());

        } else {
            playersSinking.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {

        if (event.getEntity() instanceof  Player player) {

            if (player.isBlocking()) {

                System.out.println(player.getInventory().getItemInMainHand().getType().getMaxDurability());

            }

        }

    }

    private void sinkPlayer() {

        Bukkit.getScheduler().runTaskTimer(JavaPlugin.getPlugin(MoShields.class), () -> playersSinking.forEach(uuid -> {
            Player player = Bukkit.getPlayer(uuid);

            if (player != null) {
                player.setVelocity(player.getVelocity().clone().add(new Vector(0, -0.1, 0)));
            }

        }), 0, 1);

    }

}
