package dev.joeyfoxo.moshields.manager;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.shields.features.Abilities;
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

import static dev.joeyfoxo.moshields.shields.features.Features.getShieldAbilities;
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


            if (!UtilClass.isHoldingCustomShield(player)) {
                return;
            }
            for (ShieldType heldShield : UtilClass.getHeldShields(player)) {

                for (Abilities abilities : getShieldAbilities(heldShield)) {

                    if (player.isBlocking()) {

                        switch (abilities) {
                            case REFLECT -> {
                                if (event.getDamager() instanceof Projectile projectile) {
                                    reflectFeature.reflectArrow(player, projectile);
                                }
                            }
                            case PROJECTILE_TRACKING_REFLECTION -> {
                                trackingReflectFeature.performAbility(event);
                            }
                        }
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
            for (ShieldType heldShield : UtilClass.getHeldShields(player)) {
                Features.getShieldAbilities(heldShield).stream().filter(Abilities::isSpecialAbility).forEach(ability -> {
                    switch (ability) {
                        case CIRCULAR_PROTECTION -> circleInvulnerabilityFeature.activateAbility(player);
                        case PROJECTILE_TRACKING_REFLECTION -> trackingReflectFeature.activateAbility(player);
                    }

                });
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
            if (Features.getShieldAbilities(shieldType).contains(Abilities.SINK)) {
                playersSinking.add(player.getUniqueId());
            }

        }
    }
}
