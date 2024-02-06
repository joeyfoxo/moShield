package dev.joeyfoxo.moshields.shields;

public enum ShieldType {

    WOODEN(337),
    STONE(350),
    IRON(400),
    GOLD(500),
    DIAMOND(600),
    NETHERITE(800, 5),
    SLIME(300, 10),
    REINFORCED(500, 30),
    MIRROR(500, 39),
    SPIKED(400, 10),
    ECHO(200, 30);

    private final int durability;
    private final int upgradeCost;

    ShieldType(int durability, int upgradeCost) {
        this.durability = durability;
        this.upgradeCost = upgradeCost;
    }

    ShieldType(int durability) {
        this.durability = durability;
        this.upgradeCost = 0;
    }


    ShieldType() {
        this.durability = 0;
        this.upgradeCost = 0;
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
