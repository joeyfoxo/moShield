package dev.joeyfoxo.moshields.shields.features.specialabilities;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.shields.features.Abilities;
import dev.joeyfoxo.moshields.shields.features.Features;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.HashSet;

public class TrackingReflect {

    BukkitTask task;

    public void activateAbility(Player player) {

        Features.setActiveSpecialAbility(player.getUniqueId(), Abilities.PROJECTILE_TRACKING_REFLECTION);

    }

    public void performAbility(EntityDamageByEntityEvent event) {


        if (event.getEntity() instanceof Player player
                && event.getDamager() instanceof Projectile projectile
                && projectile.getShooter() instanceof Entity entity) {

            summonTrackingProjectile(player, entity, projectile);
        }

    }

    public void performAbility(EntityDamageByEntityEvent event, HashSet<Projectile> deflectableProjectiles) {

        if (event.getEntity() instanceof Player player
                && event.getDamager() instanceof Projectile projectile
                && projectile.getShooter() instanceof Entity entity
                && deflectableProjectiles.contains(projectile)) {

            summonTrackingProjectile(player, entity, projectile);
        }

    }

    private Vector getReflectedVector(Location projectileLoc, Location entityLoc) {
        return entityLoc.subtract(projectileLoc).toVector().add(new Vector(0, 1, 0)).normalize();
    }

    private void summonTrackingProjectile(Player player, Entity entity, Projectile projectile) {
        Projectile spawnedProjectile = player.launchProjectile(projectile.getClass());
        task = Bukkit.getScheduler().runTaskTimer(JavaPlugin.getPlugin(MoShields.class), () -> {

            if (!Features.isAbilityActive(player.getUniqueId())) {
                if (task != null) {
                    task.cancel();
                    return;
                }
            }

            Location projectileLocation = spawnedProjectile.getLocation();
            Location entityLocation = entity.getLocation();
            spawnedProjectile.setVelocity(getReflectedVector(projectileLocation, entityLocation));
            projectile.remove();
        }, 0, 1);

    }

}
