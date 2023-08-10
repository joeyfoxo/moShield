package dev.joeyfoxo.moshields.shields;

public enum ShieldType {

    WOODEN(100),
    OBSIDIAN(500);

    private final int durability;

    ShieldType(int durability) {
        this.durability = durability;
    }

    public int getDurability() {
        return durability;
    }

    public boolean hasCustomDurability() {
        return durability > 0;
    }
}
