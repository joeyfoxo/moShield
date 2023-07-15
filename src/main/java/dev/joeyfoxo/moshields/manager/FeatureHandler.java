package dev.joeyfoxo.moshields.manager;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.shields.features.Features;
import dev.joeyfoxo.moshields.shields.features.ReflectFeature;
import dev.joeyfoxo.moshields.shields.features.SinkFeature;
import dev.joeyfoxo.moshields.shields.features.specialabilities.CircleInvulnerabilityFeature;
import dev.joeyfoxo.moshields.shields.features.specialabilities.TrackingReflectFeature;
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
    CircleInvulnerabilityFeature circleInvulnerabilityFeature = new CircleInvulnerabilityFeature();

    TrackingReflectFeature trackingReflectFeature = new TrackingReflectFeature();

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
                    if (Features.hasActiveAbility(player.getUniqueId())) {

                        switch (Features.getActiveAbilityMap().get(player.getUniqueId())) {
                            case TRACKING_REFLECT -> trackingReflectFeature.performAbility(event);
                        }


                    }

                }
                //Some shields if they have abilities don't need to be blocking
                if (Features.hasActiveAbility(player.getUniqueId())) {
                    switch (Features.getActiveAbilityMap().get(player.getUniqueId())) {
                        case CIRCLE_PROTECT -> circleInvulnerabilityFeature.performAbility(event);
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
                if (Features.getCircleInvulnerabilityShields().contains(shieldType)) {
                    circleInvulnerabilityFeature.activateAbility(player);

                }

                if (Features.getTrackingReflectionShields().contains(shieldType)) {
                    trackingReflectFeature.activateAbility(player);
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

        if (!UtilClass.isHoldingCustomShield(player)) {
            playersSinking.remove(player.getUniqueId());
        }

        for (ShieldType shieldType : UtilClass.getHeldShields(player)) {
            if (Features.getSinkableShields().contains(shieldType)) {
                playersSinking.add(player.getUniqueId());
            }

        }
    }
}
