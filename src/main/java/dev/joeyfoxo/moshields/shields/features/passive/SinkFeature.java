package dev.joeyfoxo.moshields.shields.features.passive;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.shields.features.FeatureBase;
import dev.joeyfoxo.moshields.shields.features.Features;
import dev.joeyfoxo.moshields.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SinkFeature extends FeatureBase implements Listener {

    Set<UUID> playersSinking = new HashSet<>();

    public SinkFeature() {
        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(MoShields.class));
    }

    @Override
    protected void eventBasedFeature(Player player) {

    }

    @Override
    public void eventBasedFeature(Player player, Projectile projectile) {

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEnterWater(PlayerMoveEvent event) {

        Player player = event.getPlayer();
        if (!player.isUnderWater() || !player.isInWater()) {
            return;
        }

        ItemStack mainHandItem = player.getInventory().getItemInMainHand();
        ItemStack offHandItem = player.getInventory().getItemInOffHand();

        if ((Features.getSinkableShields().contains(UtilClass.getCustomModelEnum(mainHandItem.getItemMeta())))
                || Features.getSinkableShields().contains(UtilClass.getCustomModelEnum(offHandItem.getItemMeta()))) {
            playersSinking.add(player.getUniqueId());
        } else {
            playersSinking.remove(player.getUniqueId());
        }

    }

    @Override
    public void feature() {

        Bukkit.getScheduler().runTaskTimer(JavaPlugin.getPlugin(MoShields.class), () -> playersSinking.forEach(uuid -> {
            Player player = Bukkit.getPlayer(uuid);

            if (player != null) {
                player.setVelocity(player.getVelocity().clone().add(new Vector(0, -0.1, 0)));
            }

        }), 0, 1);
        
    }

}
