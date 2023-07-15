package dev.joeyfoxo.moshields.shields.features;

import dev.joeyfoxo.moshields.exception.InvalidAbilityException;
import dev.joeyfoxo.moshields.exception.MultipleSpecialAbilitiesException;
import dev.joeyfoxo.moshields.manager.ShieldType;

import java.util.*;

public record Features() {

    private static final HashMap<UUID, Abilities> activeSpecialAbilityMap = new HashMap<>();
    private static final HashMap<ShieldType, Set<Abilities>> shieldAbilityMap = new HashMap<>();

    public static final Set<UUID> playersSinking = new HashSet<>();

    public static Set<Abilities> getShieldAbilities(ShieldType shieldType) {
        return shieldAbilityMap.get(shieldType);
    }

    public static void addShieldAbility(ShieldType shieldType, Abilities... ability) {

        for (Abilities abilities : ability) {

            if (!(Arrays.stream(Abilities.values()).toList().contains(abilities))) {
                throw new InvalidAbilityException(abilities.name() + " isn't a valid ability");
            }


            if (shieldAbilityMap.containsKey(shieldType)) {
                int i = 0;
                for (Abilities abilitiesInMap : getShieldAbilities(shieldType)) {
                    if (abilitiesInMap.isSpecialAbility) {
                        i++;
                    }
                }
                if (i > 1) {
                    throw new MultipleSpecialAbilitiesException("A Shield can only have 1 special ability");
                }

                getShieldAbilities(shieldType).add(abilities);
            } else {
                shieldAbilityMap.put(shieldType, Set.of(abilities));
            }
        }
    }


    public static HashMap<UUID, Abilities> getActiveSpecialAbilityMap() {
        return activeSpecialAbilityMap;
    }

    public static void setActiveSpecialAbility(UUID uuid, Abilities ability) {
        getActiveSpecialAbilityMap().put(uuid, ability);
    }
}
