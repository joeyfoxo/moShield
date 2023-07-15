package dev.joeyfoxo.moshields.shields.features;

import dev.joeyfoxo.moshields.util.UtilClass;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;

import static dev.joeyfoxo.moshields.manager.CooldownManager.applyCooldownToShield;

public class ReflectFeature {

    public void reflectArrow(Player player, Projectile projectile) {
        if (Features.getReflectionShields().contains(
                UtilClass.getCustomModelEnum(player.getActiveItem().getItemMeta()))) {
            Entity entity = (Entity) projectile.getShooter();
            Location location = entity.getLocation();
            projectile.remove();
            player.launchProjectile(projectile.getClass()).setVelocity(getReflectedVector(player.getLocation(), location));
        }
    }



    private Vector getReflectedVector(Location playerLoc, Location entityLoc) {
        return entityLoc.subtract(playerLoc).toVector().add(new Vector(0, 1, 0)).normalize();
    }

}