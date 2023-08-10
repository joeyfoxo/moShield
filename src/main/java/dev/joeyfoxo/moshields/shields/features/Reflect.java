package dev.joeyfoxo.moshields.shields.features;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import java.util.HashSet;

public class Reflect {

    public void reflectArrow(EntityDamageByEntityEvent event) {

        if (event.getEntity() instanceof Player player
                && event.getDamager() instanceof Projectile projectile
                && projectile.getShooter() instanceof Entity entity) {
            summonReflectable(player, projectile, entity);
        }
    }

    public void reflectArrow(EntityDamageByEntityEvent event, HashSet<Projectile> deflectableProjectiles) {

        if (event.getEntity() instanceof Player player
                && event.getDamager() instanceof Projectile projectile
                && projectile.getShooter() instanceof Entity entity
                && deflectableProjectiles.contains(projectile)) {
            summonReflectable(player, projectile, entity);
        }
    }

    private void summonReflectable(Player player, Projectile projectile, Entity entity) {
        Location location = entity.getLocation();
        projectile.remove();
        player.launchProjectile(projectile.getClass()).setVelocity(getReflectedVector(player.getLocation(), location));

    }


    private Vector getReflectedVector(Location playerLoc, Location entityLoc) {
        return entityLoc.subtract(playerLoc).toVector().add(new Vector(0, 1, 0)).normalize();
    }

}