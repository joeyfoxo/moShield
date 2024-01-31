package dev.joeyfoxo.moshields.shields.features;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.shields.features.ability.Ability;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Sink implements Ability {

    @Override
    public void performAbility(EntityDamageByEntityEvent event) {

    }

    @Override
    public void applyEffects() {
        Bukkit.getScheduler().runTaskTimer(JavaPlugin.getPlugin(MoShields.class), () -> Features.playersSinking.forEach(uuid -> {
            Player player = Bukkit.getPlayer(uuid);

            if (player != null) {
                player.setVelocity(player.getVelocity().clone().add(new Vector(0, -0.1, 0)));
            }

        }), 0, 1);
    }
}
