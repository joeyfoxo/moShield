package dev.joeyfoxo.moshields.shields.features.potion;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.shields.features.Features;
import dev.joeyfoxo.moshields.shields.features.ability.Ability;
import dev.joeyfoxo.moshields.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class RepeatedAbilityApplier implements Ability {

    @Override
    public void performAbility(EntityDamageByEntityEvent event) {

    }

    @Override
    public void applyEffects() {
        new BukkitRunnable() {
            @Override
            public void run() {

                if (Bukkit.getOnlinePlayers().stream().noneMatch(UtilClass::isHoldingCustomShield)) {
                    return;
                }

                Features.playersSinking.forEach(uuid -> {
                    Player player = Bukkit.getPlayer(uuid);

                    if (player != null) {
                        player.setVelocity(player.getVelocity().clone().add(new Vector(0, -0.1, 0)));
                    }
                });

                Features.playersBlinded.forEach(uuid -> {
                    Player player = Bukkit.getPlayer(uuid);

                    if (player != null) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 2, 1, true, false));
                    }
                });

                Features.playersSlowed.forEach(uuid -> {
                    Player player = Bukkit.getPlayer(uuid);

                    if (player != null) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 2, 1, true, false));
                    }
                });

            }
        }.runTaskTimer(JavaPlugin.getPlugin(MoShields.class), 0, 20);

    }
}
