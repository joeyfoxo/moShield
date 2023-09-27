package dev.joeyfoxo.moshields.shields.features;

import dev.joeyfoxo.moshields.MoShields;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Slowness {

    public Slowness() {
        Bukkit.getScheduler().runTaskTimer(JavaPlugin.getPlugin(MoShields.class), () -> Features.playersSlowed.forEach(uuid -> {
            Player player = Bukkit.getPlayer(uuid);

            if (player != null) {
                if (!player.hasPotionEffect(PotionEffectType.SLOW)) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 1, 2, true, false));
                }
            }

        }), 0, 1);
    }


}
