package dev.joeyfoxo.moshields.shields.features.potion;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.shields.features.Features;
import dev.joeyfoxo.moshields.shields.features.ability.Ability;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Blindness implements Ability {

    @Override
    public void performAbility(EntityDamageByEntityEvent event) {

    }

    @Override
    public void applyEffects() {
        Bukkit.getScheduler().runTaskTimer(JavaPlugin.getPlugin(MoShields.class), () -> Features.playersBlinded.forEach(uuid -> {
            Player player = Bukkit.getPlayer(uuid);

            if (player != null) {
                if (!player.hasPotionEffect(PotionEffectType.BLINDNESS)) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 5, 1, true, false));
                }
            }

        }), 0, 1);
    }
}
