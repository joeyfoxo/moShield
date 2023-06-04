package dev.joeyfoxo.moshields.manager;

import dev.joeyfoxo.moshields.items.data.ShieldType;
import dev.joeyfoxo.moshields.items.shields.ObsidianShield;
import dev.joeyfoxo.moshields.items.shields.StoneShield;
import dev.joeyfoxo.moshields.packet.ClientPacket;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ShieldManager {



    //TODO: Change durability Client Side
    ClientPacket packet = new ClientPacket();

    public ShieldManager() {

        ObsidianShield obsidianShield = new ObsidianShield(new ItemStack(Material.SHIELD),
                Component.text().content("Obsidian Shield")
                        //.color(TextColor.color(19, 19, 19))
                        .decoration(TextDecoration.ITALIC, false).build(), 1,
                        //.decoration(TextDecoration.BOLD, true).build(), 1,
                ShieldType.OBSIDIAN);


        StoneShield stoneShield =  new StoneShield(new ItemStack(Material.SHIELD),
                Component.text().content("Stone Shield")
                        //.color(TextColor.color(100, 100, 100))
                        .decoration(TextDecoration.ITALIC, false).build(), 0,
                        //.decoration(TextDecoration.BOLD, true).build(), 0,
                ShieldType.STONE);


        //packet.customDurabilityRender(obsidianShield);



    }

}
