package dev.joeyfoxo.moshields.shields;

public enum ShieldType {

    WOODEN(200, 0),
    STONE(250, 0),
    IRON(350, 0),
    GOLD(450, 0),
    DIAMOND(600, 0),
    NETHERITE(800, 20),
    SLIME(300, 30),
    REINFORCED(500, 40),
    MIRROR(500, 40);

    private final int durability;
    private final int upgradeCost;

    ShieldType(int durability, int upgradeCost) {
        this.durability = durability;
        this.upgradeCost = upgradeCost;
    }

    public int getDurability() {
        return durability;
    }

    public int getUpgradeCost() {
        return upgradeCost;
    }

    public boolean isUpgradeable() {
        return upgradeCost > 0;
    }

    public boolean hasCustomDurability() {
        return durability > 0;
    }
}
