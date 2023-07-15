package dev.joeyfoxo.moshields.shields.features;

public enum Abilities {

    CIRCULAR_PROTECTION(true),
    PROJECTILE_TRACKING_REFLECTION(true),
    REFLECT(false),
    SINK(false);

    public final boolean isSpecialAbility;

    public boolean isSpecialAbility() {
        return isSpecialAbility;
    }

    Abilities(boolean isSpecialAbility) {
        this.isSpecialAbility = isSpecialAbility;
    }


}

