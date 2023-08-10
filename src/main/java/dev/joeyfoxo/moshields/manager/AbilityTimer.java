package dev.joeyfoxo.moshields.manager;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.shields.ShieldType;
import dev.joeyfoxo.moshields.shields.features.Features;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class AbilityTimer {

    Player player;
    int abilityTime;
    BukkitTask task;
    ShieldType type;

    public AbilityTimer(Player player, ShieldType type) {

        this.player = player;
        abilityTime = Features.getAbilityLength(player.getUniqueId());
        this.type = type;
        runAbilityTimer();

    }

    private void runAbilityTimer() {

        if (!Features.isAbilityActive(player.getUniqueId())) {
            return;
        }

        task = Bukkit.getScheduler().runTaskTimer(JavaPlugin.getPlugin(MoShields.class), () -> {

            player.sendActionBar(Component.text().content(
                            "Shield Ability active for: " +
                                    abilityTime + " seconds")
                    .color(TextColor.color(0, 255, 0)).build());

            abilityTime--;

            if (abilityTime <= 0) {
                task.cancel();
            }

        }, 0, 20);

    }
}
