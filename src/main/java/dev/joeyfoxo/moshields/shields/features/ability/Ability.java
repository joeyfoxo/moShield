package dev.joeyfoxo.moshields.shields.features.ability;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public interface Ability {

    void performAbility(EntityDamageByEntityEvent event);

    void applyEffects();

   enum Abilities {
        CIRCULAR_PROTECTION(true, 50, 120),
        PROJECTILE_TRACKING_REFLECTION(true, 60, 120),
        FORCEFIELD(true, 60, 120),
        REFLECT,
        SINK,
        SLOW,
        BLINDNESS;

        private final boolean isSpecialAbility;
        private final int activationLength;
        private final int cooldown;

        public boolean isSpecialAbility() {
            return isSpecialAbility;
        }

        public int getActivationLength() {
            return activationLength;
        }

        public int getCooldown() {
            return cooldown;
        }

        Abilities(boolean isSpecialAbility, int abilityLength, int abilityCooldown) {
            this.isSpecialAbility = isSpecialAbility;
            this.activationLength = abilityLength;
            this.cooldown = abilityCooldown;
        }

        Abilities() {
            this.cooldown = 0;
            this.activationLength = 0;
            this.isSpecialAbility = false;
        }
    }

}

