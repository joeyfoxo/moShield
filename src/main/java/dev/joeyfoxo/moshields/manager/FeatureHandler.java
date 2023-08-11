package dev.joeyfoxo.moshields.manager;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.shields.ShieldType;
import dev.joeyfoxo.moshields.shields.features.Abilities;
import dev.joeyfoxo.moshields.shields.features.Features;
import dev.joeyfoxo.moshields.shields.features.Reflect;
import dev.joeyfoxo.moshields.shields.features.Sink;
import dev.joeyfoxo.moshields.shields.features.specialabilities.CircleInvulnerability;
import dev.joeyfoxo.moshields.shields.features.specialabilities.ForceField;
import dev.joeyfoxo.moshields.shields.features.specialabilities.TrackingReflect;
import dev.joeyfoxo.moshields.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;

import static dev.joeyfoxo.moshields.manager.Cooldown.*;
import static dev.joeyfoxo.moshields.shields.features.Features.getShieldAbilities;
import static dev.joeyfoxo.moshields.shields.features.Features.playersSinking;

public class FeatureHandler implements Listener {

    Reflect reflect = new Reflect();
    CircleInvulnerability circleInvulnerability = new CircleInvulnerability();
    TrackingReflect trackingReflect = new TrackingReflect();
    ForceField forceField = new ForceField();

    public FeatureHandler() {
        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(MoShields.class));
        new Sink();
    }

    @EventHandler
    public void onAttemptedDamage(EntityDamageByEntityEvent event) {

        if (event.getEntity() instanceof Player player) {

            if (!UtilClass.isHoldingCustomShield(player)) {
                return;
            }

            for (ShieldType heldShield : UtilClass.getHeldShields(player)) {

                HashSet<Abilities> abilitiesList = getShieldAbilities(heldShield);
                for (Abilities ability : abilitiesList) {

                    if (ability.isSpecialAbility()) {
                        if (!Features.isAbilityActive(player.getUniqueId())) {
                            return;
                        }
                        switch (ability) {
                            case PROJECTILE_TRACKING_REFLECTION -> {
                                if (player.isBlocking()) {
                                    trackingReflect.performAbility(event);
                                }
                            }
                            case CIRCULAR_PROTECTION -> {
                                circleInvulnerability.performAbility(event);

                            }

                            case FORCEFIELD -> {

                                if (player.isBlocking()) {
                                    forceField.performAbility(event);
                                }
                            }
                        }
                    } else {
                        switch (ability) {
                            case REFLECT -> {
                                if (player.isBlocking()) {
                                    reflect.reflectArrow(event);
                                }
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

            ItemStack heldItem = event.getItem();

            ShieldType heldShield = UtilClass.getCustomModelEnum(heldItem.getItemMeta());

            if (shieldIsOnCooldown(player.getUniqueId(), heldShield)) {
                player.sendMessage(Component.text().content("Sorry you need to wait " + getShieldCooldown(player.getUniqueId(), heldShield) + " more seconds to use this ability!").color(TextColor.color(255, 100, 100)).build());
                return;
            }

            if (Features.hasShieldAbility(heldShield)) {

                Features.getShieldAbilities(heldShield).stream().filter(Abilities::isSpecialAbility).forEach(ability -> {
                    switch (ability) {
                        case CIRCULAR_PROTECTION -> {
                            circleInvulnerability.activateAbility(player);
                            DurabilityHandler.applyDamageToShield(heldItem, heldShield, 10);

                        }
                        case PROJECTILE_TRACKING_REFLECTION -> {
                            trackingReflect.activateAbility(player);
                            DurabilityHandler.applyDamageToShield(heldItem, heldShield, 10);
                        }
                        case FORCEFIELD -> {
                            forceField.activateAbility(player);
                            DurabilityHandler.applyDamageToShield(heldItem, heldShield, 10);
                        }
                    }

                    displayAbilityTimer(player, heldShield);
                    applyCooldownToShield(player, heldShield, ability.getCooldown());
                    Bukkit.getScheduler().runTaskLater(JavaPlugin.getPlugin(MoShields.class), () -> Features.getActiveSpecialAbilityMap().remove(player.getUniqueId()), ability.getActivationLength() * 20L); //times 20 because its in ticks

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


    private void displayAbilityTimer(Player player, ShieldType shieldType) {
        new AbilityTimer(player, shieldType);
    }
}
