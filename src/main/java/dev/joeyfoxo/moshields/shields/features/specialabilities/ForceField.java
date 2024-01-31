package dev.joeyfoxo.moshields.shields.features.specialabilities;

import com.destroystokyo.paper.ParticleBuilder;
import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.shields.features.Features;
import dev.joeyfoxo.moshields.shields.features.ability.SpecialAbility;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public class ForceField implements SpecialAbility {

    double sizeRadius = 0.5;

    public void activateAbility(Player player) {
        Features.setActiveSpecialAbility(player.getUniqueId(), Abilities.FORCEFIELD);
        summonForceField(player);
    }

    public void performAbility(EntityDamageByEntityEvent event) {

        if (event.getEntity() instanceof Player) {
            event.setCancelled(true);

        }

    }

    private void summonForceField(Player player) {

        new BukkitRunnable() {

            @Override
            public void run() {

                if (!Features.isAbilityActive(player.getUniqueId())) {
                    cancel();
                    return;
                }

                Location location = player.getLocation();
                if (sizeRadius < 2.5) {
                    sizeRadius += 0.5;
                } else {
                    sizeRadius = 0.5;
                }
                for (double verticleY = 0; verticleY <= Math.PI / 2; verticleY += Math.PI / 10) {
                    double radius = Math.sin(verticleY);
                    double y = Math.cos(verticleY) * sizeRadius;
                    for (double horizontalZX = 0; horizontalZX < Math.PI * 2; horizontalZX += Math.PI / 10) {
                        double x = (Math.cos(horizontalZX) * radius) * sizeRadius;
                        double z = (Math.sin(horizontalZX) * radius) * sizeRadius;

                        location.add(x, y, z);

                        if (player.isBlocking()) {

                            List<Entity> surroundingEnemies = player.getNearbyEntities(3, 3, 3);
                            surroundingEnemies.forEach(
                                    enemy -> {

                                        if (enemy instanceof LivingEntity || enemy instanceof Projectile) {
                                            enemy.setVelocity(enemy.getLocation().subtract(player.getLocation()).toVector().add(new Vector(0, 1, 0)).normalize());
                                        }
                                    });

                            surroundingEnemies.clear();

                            new ParticleBuilder(Particle.SMOKE_NORMAL)
                                    .location(location)
                                    .receivers(5, 5, 5)
                                    .source(player)
                                    .count(0)
                                    .spawn();

                        }

                        location.subtract(x, y, z);

                    }
                }
            }
        }.runTaskTimer(JavaPlugin.getPlugin(MoShields.class), 0, 6);
    }

    @Override
    public void applyEffects() {

    }

}
