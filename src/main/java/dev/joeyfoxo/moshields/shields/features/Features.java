package dev.joeyfoxo.moshields.shields.features;

import dev.joeyfoxo.moshields.manager.ShieldType;

import java.util.*;

public record Features() {

    private static final Set<ShieldType> sinkableShields = new HashSet<>();
    private static final Set<ShieldType> reflectionShields = new HashSet<>();
    private static final Set<ShieldType> circularProtectShields = new HashSet<>();

    public static final Set<UUID> playersSinking = new HashSet<>();

    public static Set<ShieldType> getSinkableShields() {
        return sinkableShields;
    }

    public static Set<ShieldType> getReflectionShields() {
        return reflectionShields;
    }

    public static Set<ShieldType> getCircularProtectShields() {
        return circularProtectShields;
    }

    public static void addSinkableShield(ShieldType shieldType) {
        sinkableShields.add(shieldType);
    }

    public static void addReflectionShield(ShieldType shieldType) {
        reflectionShields.add(shieldType);
    }

    public static void addCircularProtectShield(ShieldType shieldType) {
        circularProtectShields.add(shieldType);
    }
}
