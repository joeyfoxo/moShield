package dev.joeyfoxo.moshields.shields.features.interactive;

import dev.joeyfoxo.moshields.shields.features.FeatureBase;
import dev.joeyfoxo.moshields.shields.features.Features;
import dev.joeyfoxo.moshields.util.UtilClass;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;

public class ReflectFeature extends FeatureBase {


    private Vector getReflectedVector(Location playerLoc, Location entityLoc) {
        return entityLoc.subtract(playerLoc).toVector().add(new Vector(0, 1, 0)).normalize();
    }

    @Override
    protected void eventBasedFeature() {

    }

    public void eventBasedFeature(Player player, Projectile projectile) {

        if (Features.getReflectionShields().contains(
                UtilClass.getCustomModelEnum(player.getActiveItem().getItemMeta()))) {
            Entity entity = (Entity) projectile.getShooter();
            Location location = entity.getLocation();
            projectile.remove();
            player.launchProjectile(projectile.getClass()).setVelocity(getReflectedVector(player.getLocation(), location));
        }
    }

    @Override
    public void feature() {

    }
}