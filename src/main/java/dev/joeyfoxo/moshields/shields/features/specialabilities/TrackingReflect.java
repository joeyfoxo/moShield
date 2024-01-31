package dev.joeyfoxo.moshields.shields.features.specialabilities;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.shields.features.Features;
import dev.joeyfoxo.moshields.shields.features.ability.SpecialAbility;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import dev.joeyfoxo.moshields.shields.features.ability.Ability.Abilities;

import java.util.HashSet;

public class TrackingReflect implements SpecialAbility {

    @Override
    public void activateAbility(Player player) {
        Features.setActiveSpecialAbility(player.getUniqueId(), Abilities.PROJECTILE_TRACKING_REFLECTION);
    }

    @Override
    public void performAbility(EntityDamageByEntityEvent event) {

        if (event.getEntity() instanceof Player player
                && event.getDamager() instanceof Projectile projectile
                && projectile.getShooter() instanceof Entity entity) {
            summonTrackingProjectile(player, entity, projectile);
        }

    }

    @Override
    public void applyEffects() {

    }


    private Vector getReflectedVector(Location projectileLoc, Location entityLoc) {
        return entityLoc.subtract(projectileLoc).toVector().normalize();
    }

    private void summonTrackingProjectile(Player player, Entity entity, Projectile projectile) {
        projectile.remove();
        Projectile spawnedProjectile = player.launchProjectile(projectile.getClass());
        spawnedProjectile.setVelocity(getReflectedVector(player.getLocation(), entity.getLocation()));

        new BukkitRunnable() {
            @Override
            public void run() {

                if (!Features.isAbilityActive(player.getUniqueId())) {
                    cancel();
                }
                spawnedProjectile.setVelocity(getReflectedVector(spawnedProjectile.getLocation(), entity.getLocation().add(0,1, 0)));

            }
        }.runTaskTimer(JavaPlugin.getPlugin(MoShields.class), 0, 1);
    }

}
