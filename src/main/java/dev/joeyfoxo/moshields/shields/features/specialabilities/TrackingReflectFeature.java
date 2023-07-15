package dev.joeyfoxo.moshields.shields.features.specialabilities;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.manager.ShieldType;
import dev.joeyfoxo.moshields.shields.features.Abilities;
import dev.joeyfoxo.moshields.shields.features.Features;
import dev.joeyfoxo.moshields.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import static dev.joeyfoxo.moshields.manager.CooldownManager.*;

public class TrackingReflectFeature {

    final int cooldown = 120;
    final int abilityLength = 60;

    public void activateAbility(Player player) {
        for (ShieldType type : UtilClass.getHeldShields(player)) {

            if (shieldIsOnCooldown(player.getUniqueId(), type)) {
                player.sendMessage(Component.text()
                        .content("Sorry you need to wait " + getShieldCooldown(player.getUniqueId(), type) + " more seconds to use this ability!")
                        .color(TextColor.color(255, 100, 100)).build());
                return;
            }

            Features.setActiveSpecialAbility(player.getUniqueId(), Abilities.PROJECTILE_TRACKING_REFLECTION);
            applyCooldownToShield(player,
                    type, cooldown);

        }
    }

    public void performAbility(EntityDamageByEntityEvent event) {

        //TODO: When performing ability take away durability from shield

        if (event.getEntity() instanceof Player player
                && event.getDamager() instanceof Projectile projectile
                && projectile.getShooter() instanceof Entity entity) {

            Projectile spawnedProjectile =  player.launchProjectile(projectile.getClass());
            Bukkit.getScheduler().runTaskTimer(JavaPlugin.getPlugin(MoShields.class), () -> {

                Location projectileLocation = spawnedProjectile.getLocation();
                Location entityLocation = entity.getLocation();
                spawnedProjectile.setVelocity(getReflectedVector(projectileLocation, entityLocation));
                projectile.remove();

            }, 0, 1);

            Bukkit.getScheduler().runTaskLater(JavaPlugin.getPlugin(MoShields.class), () -> Features.getActiveSpecialAbilityMap().remove(player.getUniqueId()), abilityLength * 20); //times 20 because its in ticks

        }

    }

    private Vector getReflectedVector(Location projectileLoc, Location entityLoc) {
        return entityLoc.subtract(projectileLoc).toVector().add(new Vector(0, 1, 0)).normalize();
    }

}
