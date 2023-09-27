package dev.joeyfoxo.moshields.manager;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.shields.*;
import dev.joeyfoxo.moshields.shields.special.MirrorShield;
import dev.joeyfoxo.moshields.shields.special.ReinforcedShield;
import dev.joeyfoxo.moshields.shields.special.SlimeShield;
import dev.joeyfoxo.moshields.shields.standard.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.NamespacedKey;

public class ShieldManager {

    public ShieldManager(MoShields main) {

        new WoodenShield(ShieldType.WOODEN,
                Component.text().content("Wooden Shield")
                        .decoration(TextDecoration.ITALIC, false).build(),
                new NamespacedKey(main, "wooden_shield"));

        new StoneShield(ShieldType.STONE,
                Component.text().content("Stone Shield")
                        .decoration(TextDecoration.ITALIC, false).build(),
                new NamespacedKey(main, "stone_shield"));

        new IronShield(ShieldType.IRON,
                Component.text().content("Iron Shield")
                        .decoration(TextDecoration.ITALIC, false).build(),
                new NamespacedKey(main, "iron_shield"));

        new GoldShield(ShieldType.GOLD,
                Component.text().content("Golden Shield")
                        .decoration(TextDecoration.ITALIC, false).build(),
                new NamespacedKey(main, "gold_shield"));

        new DiamondShield(ShieldType.DIAMOND,
                Component.text().content("Diamond Shield")
                        .decoration(TextDecoration.ITALIC, false).build(),
                new NamespacedKey(main, "diamond_shield"));

        new NetheriteShield(ShieldType.NETHERITE,
                Component.text().content("Netherite Shield")
                        .decoration(TextDecoration.ITALIC, false).build(),
                new NamespacedKey(main, "netherite_shield"));

        new SlimeShield(ShieldType.SLIME, Component.text().content("Slime Shield")
                .decoration(TextDecoration.ITALIC, false).build(),
                new NamespacedKey(main, "slime_shield"));

        new ReinforcedShield(ShieldType.REINFORCED, Component.text().content("Reinforced Shield")
                .decoration(TextDecoration.ITALIC, false).build(),
                new NamespacedKey(main, "reinforced_shield"));

        new MirrorShield(ShieldType.MIRROR, Component.text().content("Mirrored Shield")
                .decoration(TextDecoration.ITALIC, false).build(),
                new NamespacedKey(main, "mirror_shield"));

    }

}
