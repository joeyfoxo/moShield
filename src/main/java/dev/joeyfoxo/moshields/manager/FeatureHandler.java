package dev.joeyfoxo.moshields.manager;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.shields.features.Features;
import dev.joeyfoxo.moshields.shields.features.ReflectFeature;
import dev.joeyfoxo.moshields.shields.features.SinkFeature;
import dev.joeyfoxo.moshields.shields.features.interactive.CircleProtectFeature;
import dev.joeyfoxo.moshields.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Set;

import static dev.joeyfoxo.moshields.shields.features.Features.playersSinking;

public class FeatureHandler implements Listener {

    SinkFeature sinkFeature = new SinkFeature();
    ReflectFeature reflectFeature = new ReflectFeature();
    CircleProtectFeature circleProtectFeature = new CircleProtectFeature();

    public FeatureHandler() {
        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(MoShields.class));
        new SinkFeature();
    }

    @EventHandler
    public void onAttemptedDamage(EntityDamageByEntityEvent event) {

        if (event.getEntity() instanceof Player player) {
            Set<ShieldType> shieldTypes = UtilClass.getHeldShields(player);

            if (!UtilClass.isHoldingCustomShield(player)) {
                return;
            }
            for (ShieldType type : shieldTypes) {

                if (player.isBlocking()) {
                    if (event.getDamager() instanceof Projectile projectile
                            && Features.getReflectionShields()
                            .contains(type)) {
                        reflectFeature.reflectArrow(player, projectile);
                    }
                }
                //Some shields if they have abilities don't need to be blocking
                if (Features.getCircularProtectShields().contains(type)) {
                    if (circleProtectFeature.isAbilityActive()) {
                        circleProtectFeature.performAbility(event);
                    }


                }

            }

        }

    }

    @EventHandler
    public void onShieldAbilityActivation(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!UtilClass.isHoldingCustomShield(player)) {
            return;
        }


        if (player.isSneaking() && event.getAction().isRightClick()) {
            for (ShieldType shieldType : UtilClass.getHeldShields(player)) {
                if (Features.getCircularProtectShields().contains(shieldType)) {
                    circleProtectFeature.activateAbility(player);

                }
            }
        }

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEnterWater(PlayerMoveEvent event) {

        Player player = event.getPlayer();
        if (!player.isUnderWater() || !player.isInWater()) {
            return;
        }

        for (ShieldType shieldType : UtilClass.getHeldShields(player)) {
            if (Features.getSinkableShields().contains(shieldType)) {
                playersSinking.add(player.getUniqueId());
            } else {
                playersSinking.remove(player.getUniqueId());
            }

        }
    }
}
