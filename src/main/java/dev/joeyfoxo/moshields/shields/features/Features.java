package dev.joeyfoxo.moshields.shields.features;

import dev.joeyfoxo.moshields.manager.ShieldType;

import java.util.*;

public record Features() {

    private static final HashMap<UUID, Abilities> activeAbilityMap = new HashMap<>();
    private static final Set<ShieldType> sinkableShields = new HashSet<>();
    private static final Set<ShieldType> reflectionShields = new HashSet<>();
    private static final Set<ShieldType> circleInvulnerabilityShields = new HashSet<>();
    private static final Set<ShieldType> trackingReflectionShields = new HashSet<>();

    public static final Set<UUID> playersSinking = new HashSet<>();

    public static Set<ShieldType> getSinkableShields() {
        return sinkableShields;
    }

    public static Set<ShieldType> getReflectionShields() {
        return reflectionShields;
    }

    public static Set<ShieldType> getCircleInvulnerabilityShields() {
        return circleInvulnerabilityShields;
    }

    public static Set<ShieldType> getTrackingReflectionShields() {
        return trackingReflectionShields;
    }

    public static void addSinkableShield(ShieldType shieldType) {
        sinkableShields.add(shieldType);
    }

    public static void addReflectionShield(ShieldType shieldType) {
        reflectionShields.add(shieldType);
    }

    public static void addCircularProtectShield(ShieldType shieldType) {
        circleInvulnerabilityShields.add(shieldType);
    }
    public static void addTrackingReflectionShields(ShieldType shieldType) {
        trackingReflectionShields.add(shieldType);
    }


    public static HashMap<UUID, Abilities> getActiveAbilityMap() {
        return activeAbilityMap;
    }

    public static boolean hasActiveAbility(UUID uuid) {
        return getActiveAbilityMap().containsKey(uuid);
    }

    public static void setActiveAbility(UUID uuid, Abilities ability) {
        getActiveAbilityMap().put(uuid, ability);
    }
}
