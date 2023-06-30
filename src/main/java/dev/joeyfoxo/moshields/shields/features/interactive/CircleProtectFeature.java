package dev.joeyfoxo.moshields.shields.features.interactive;

import dev.joeyfoxo.moshields.shields.features.FeatureBase;
import dev.joeyfoxo.moshields.shields.features.Features;
import dev.joeyfoxo.moshields.util.UtilClass;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;

public class CircleProtectFeature extends FeatureBase {


    private Vector getReflectedVector(Location playerLoc, Location entityLoc) {
        return entityLoc.subtract(playerLoc).toVector().divide(new Vector(3, 0, 3)) .normalize();
    }

    @Override
    protected void eventBasedFeature(Player player) {


    }

    @Override
    public void eventBasedFeature(Player player, Projectile projectile) {

        if (!player.isSneaking() || !Features.getCircularProtectShields().contains(
                UtilClass.getCustomModelEnum(player.getActiveItem().getItemMeta()))) {
            return;
        }

        Entity shooter = (Entity) projectile.getShooter();
        projectile.remove();
        player.launchProjectile(projectile.getClass()).setVelocity(getReflectedVector(player.getLocation(), shooter.getLocation()));


    }

    @Override
    public void feature() {

    }
}
