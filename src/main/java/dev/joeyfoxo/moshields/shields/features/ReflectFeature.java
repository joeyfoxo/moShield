package dev.joeyfoxo.moshields.shields.features;

import dev.joeyfoxo.moshields.MoShields;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

public class ReflectFeature implements Listener {

    static int reflectCost = 10;
    boolean reflectMagic = false;
    boolean reflectProjectiles = false;

    public ReflectFeature(boolean reflectMagic, boolean reflectProjectiles) {
        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(MoShields.class));
        this.reflectMagic = reflectMagic;
        this.reflectProjectiles = reflectProjectiles;

    }

    @EventHandler
    public void onAttemptedDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player.isBlocking()) {
                if (reflectProjectiles && event.getDamager() instanceof Projectile projectile) {
                    event.setCancelled(true);
                    reflectProjectile(player, projectile, event);
                }
            }
        }
    }

    private void reflectProjectile(Player player, Projectile projectile, EntityDamageByEntityEvent event) {
        ProjectileSource shooter = projectile.getShooter();

        if (shooter instanceof Entity shooterEntity) {
            PlayerItemDamageEvent itemDamageEvent = new PlayerItemDamageEvent(player, player.getActiveItem(), reflectCost);
            Bukkit.getPluginManager().callEvent(itemDamageEvent);

            Vector direction = projectile.getVelocity().normalize();
            double angle = player.getLocation().getDirection().angle(direction);

            Vector newVelocity = direction.clone().multiply(-1).rotateAroundY(Math.toRadians(angle));
            System.out.println(newVelocity);

            projectile.setVelocity(newVelocity);
        }
    }

    private void reflectMagic() {


    }
}