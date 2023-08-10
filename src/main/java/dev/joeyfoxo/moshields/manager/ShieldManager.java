package dev.joeyfoxo.moshields.manager;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.shields.ObsidianShield;
import dev.joeyfoxo.moshields.shields.ShieldType;
import dev.joeyfoxo.moshields.shields.WoodenShield;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.NamespacedKey;

public class ShieldManager {

    public ShieldManager(MoShields main) {

        new ObsidianShield(ShieldType.OBSIDIAN, Component.text().content("Obsidian Shield")
                .decoration(TextDecoration.ITALIC, false).build(),
                new NamespacedKey(main, "obsidian_shield"));

        new WoodenShield(ShieldType.WOODEN,
                Component.text().content("Wooden Shield")
                        .decoration(TextDecoration.ITALIC, false).build(),
                new NamespacedKey(main, "wooden_shield"));
    }

}
