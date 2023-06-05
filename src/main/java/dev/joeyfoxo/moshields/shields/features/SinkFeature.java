package dev.joeyfoxo.moshields.shields.features;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.manager.ShieldType;
import dev.joeyfoxo.moshields.shields.Shield;
import dev.joeyfoxo.moshields.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
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
        if (!player.isUnderWater() || !player.isInWater()) {
            return;
        }


        //Check if the player should sink

        if ((UtilClass.isCustomShield(player.getInventory().getItemInOffHand().getItemMeta())
                && UtilClass.getCustomModelEnum(player.getInventory().getItemInOffHand().getItemMeta()) == ShieldType.OBSIDIAN)
                || (UtilClass.isCustomShield(player.getInventory().getItemInMainHand().getItemMeta())
                && UtilClass.getCustomModelEnum(player.getInventory().getItemInMainHand().getItemMeta()) == ShieldType.OBSIDIAN)) {
            playersSinking.add(player.getUniqueId());

        } else {
            playersSinking.remove(player.getUniqueId());
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
