package dev.joeyfoxo.moshields.manager;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.manager.events.DurabilityHandler;
import dev.joeyfoxo.moshields.shields.ObsidianShield;
import dev.joeyfoxo.moshields.shields.StoneShield;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

public class ShieldManager {


    //TODO: Change durability Client Side

    public ShieldManager(MoShields main) {

        new DurabilityHandler();

        new ObsidianShield(new ItemStack(Material.SHIELD),
                Component.text().content("Obsidian Shield")
                        .decoration(TextDecoration.ITALIC, false).build(),
                new NamespacedKey(main, "obsidian_shield"));


        new StoneShield(new ItemStack(Material.SHIELD),
                Component.text().content("Stone Shield")
                        //.color(TextColor.color(100, 100, 100))
                        .decoration(TextDecoration.ITALIC, false).build(),
                new NamespacedKey(main, "stone_shield"));


    }

}
