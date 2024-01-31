package dev.joeyfoxo.moshields.shields.features;

import dev.joeyfoxo.moshields.shields.features.ability.Ability;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import java.util.HashSet;

public class Reflect implements Ability {

    @Override
    public void performAbility(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player
                && event.getDamager() instanceof Arrow arrow
                && arrow.getShooter() instanceof Entity shooter) {
            if (event.getFinalDamage() == 0) {
                summonReflectable(player, arrow, shooter);
            }
        }
    }

    @Override
    public void applyEffects() {

    }

    private Vector getReflectedVector(Location playerLoc, Location shooterLoc) {
        return shooterLoc.subtract(playerLoc).toVector().add(new Vector(0,1,0)).normalize();
    }

    private void summonReflectable(Player player, Projectile projectile, Entity shooter) {
        projectile.remove();
        Projectile newProjectile = player.launchProjectile(projectile.getClass());
        newProjectile.setVelocity(getReflectedVector(player.getLocation(), shooter.getLocation()));

    }

}