package dev.joeyfoxo.moshields.shields.features;

import dev.joeyfoxo.moshields.exception.MultipleSpecialAbilitiesException;
import dev.joeyfoxo.moshields.shields.ShieldType;
import dev.joeyfoxo.moshields.shields.features.ability.Ability;
import dev.joeyfoxo.moshields.shields.features.ability.Ability.Abilities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public record Features() {

    private static final HashMap<UUID, Ability.Abilities> activeSpecialAbilityMap = new HashMap<>();
    private static final HashMap<ShieldType, HashSet<Abilities>> shieldAbilityMap = new HashMap<>();

    public static final Set<UUID> playersSinking = new HashSet<>();
    public static final Set<UUID> playersSlowed = new HashSet<>();
    public static final Set<UUID> playersBlinded = new HashSet<>();

    public static HashSet<Abilities> getShieldAbilities(ShieldType shieldType) {

        if (shieldAbilityMap.get(shieldType) == null) {
            return new HashSet<>();
        } else {
            return shieldAbilityMap.get(shieldType);
        }
    }

    public static boolean hasShieldSpecialAbility(ShieldType shieldType) {
        if (getShieldAbilities(shieldType) == null) {
            return false;
        } else {
            return getShieldAbilities(shieldType).stream().anyMatch(Abilities::isSpecialAbility);

        }
    }

    public static void addShieldAbility(ShieldType shieldType, Ability.Abilities... ability) {

        int i = 0;

        for (Ability.Abilities abilities : ability) {

            if (abilities.isSpecialAbility()) {
                i++;
            }
            if (i > 1) {
                throw new MultipleSpecialAbilitiesException("A Shield can only have 1 special ability, consider removing " + abilities.name());
            }


            if (shieldAbilityMap.containsKey(shieldType)) {
                getShieldAbilities(shieldType).add(abilities);
            } else {
                shieldAbilityMap.put(shieldType, new HashSet<>(Set.of(abilities)));
            }
        }
    }

    public static boolean isAbilityActive(UUID uuid) {

        return getActiveSpecialAbilityMap().containsKey(uuid);
    }

    public static Abilities getAcitveAbility(UUID uuid) {
        return getActiveSpecialAbilityMap().get(uuid);
    }

    public static HashMap<UUID, Abilities> getActiveSpecialAbilityMap() {
        return activeSpecialAbilityMap;
    }

    public static void setActiveSpecialAbility(UUID uuid, Abilities ability) {
        getActiveSpecialAbilityMap().put(uuid, ability);
    }

    public static int getAbilityLength(UUID uuid) {
        return getActiveSpecialAbilityMap().get(uuid).getActivationLength();
    }
}
