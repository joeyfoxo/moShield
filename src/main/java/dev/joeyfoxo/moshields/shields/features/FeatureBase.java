package dev.joeyfoxo.moshields.shields.features;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class FeatureBase implements Listener {

    public FeatureBase() {
        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(MoShields.class));
        feature();
    }

    @EventHandler
    public void onAttemptedDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player && player.isBlocking()) {

            if (!UtilClass.isHoldingCustomShield(player.getActiveItem().getItemMeta())) {
                return;
            }
            eventBasedFeature(player);
            if (event.getDamager() instanceof Projectile projectile) {
                eventBasedFeature(player, projectile);
            }

        }

    }

    protected abstract void eventBasedFeature(Player player);

    public abstract void eventBasedFeature(Player player, Projectile projectile);

    public abstract void feature();

}
