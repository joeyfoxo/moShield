package dev.joeyfoxo.moshields.manager;

import dev.joeyfoxo.moshields.MoShields;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

public class CooldownManager implements Runnable {

    private static final HashMap<UUID, HashMap<ShieldType, Integer>> playerAbilityCooldown = new HashMap<>();

    @Override
    public void run() {

        Bukkit.getScheduler().runTaskTimer(JavaPlugin.getPlugin(MoShields.class),
                () -> getCooldownMap().forEach((uuid, shieldTypeIntegerHashMap)
                        -> {

                    if (getCooldownMap().get(uuid).isEmpty()) {
                        getCooldownMap().remove(uuid);
                        return;
                    }

                    for (ShieldType shieldType : shieldTypeIntegerHashMap.keySet()) {
                        if (shieldTypeIntegerHashMap.get(shieldType) <= 0) {
                            shieldTypeIntegerHashMap.remove(shieldType);
                        } else {
                            shieldTypeIntegerHashMap.replace(shieldType, shieldTypeIntegerHashMap.get(shieldType) - 1);
                        }

                    }
                }), 0, 20);
    }


    public static HashMap<UUID, HashMap<ShieldType, Integer>> getCooldownMap() {
        return playerAbilityCooldown;
    }

    public static void applyCooldownToShield(Player player, ShieldType shieldType, int cooldown) {

        UUID uuid = player.getUniqueId();

        if (!playerAbilityCooldown.containsKey(uuid)) {
            playerAbilityCooldown.put(uuid, new HashMap<>(Collections.singletonMap(shieldType, cooldown)));
        }
        else {
            playerAbilityCooldown.get(uuid).put(shieldType, cooldown);
        }
    }

    public static boolean shieldIsOnCooldown(UUID uuid, ShieldType type) {

        if (!playerAbilityCooldown.containsKey(uuid)) {
            return false;
        }

        return playerAbilityCooldown.get(uuid).containsKey(type);

    }

    public static Integer getShieldCooldown(UUID uuid, ShieldType shieldType) {
        return playerAbilityCooldown.get(uuid).get(shieldType);

    }
}
