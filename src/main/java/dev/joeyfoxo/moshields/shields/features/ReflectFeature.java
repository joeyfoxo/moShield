package dev.joeyfoxo.moshields.shields.features;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.manager.ShieldType;
import dev.joeyfoxo.moshields.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class ReflectFeature implements Listener {
    boolean reflectMagic;
    boolean reflectProjectiles;

    ShieldType shieldType;

    public ReflectFeature(boolean reflectMagic, boolean reflectProjectiles, ShieldType shieldType) {
        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(MoShields.class));
        this.reflectMagic = reflectMagic;
        this.reflectProjectiles = reflectProjectiles;
        this.shieldType = shieldType;
    }

    @EventHandler
    public void onAttemptedDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player && player.isBlocking()) {

            if (!UtilClass.isCorrectShield(player, shieldType)) {
                return;
            }

            if (event.getDamager() instanceof Projectile projectile && reflectProjectiles) {
                reflectProjectile(player, projectile);
            }

            if (event.getCause() == EntityDamageEvent.DamageCause.SONIC_BOOM) {


            }
        }


    }

    private void reflectProjectile(Player player, Projectile projectile) {
        Entity entity = (Entity) projectile.getShooter();
        Location location = entity.getLocation();
        projectile.remove();
        player.launchProjectile(projectile.getClass()).setVelocity(getReflectedVector(player.getLocation(), location));


    }

    private void reflectMagic() {


    }

    private Vector getReflectedVector(Location playerLoc, Location entityLoc) {

        return entityLoc.subtract(playerLoc).toVector().add(new Vector(0, 1, 0)).normalize();

    }
}