package dev.joeyfoxo.moshields.upgrades.items.fragments;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ShapedRecipe;

public abstract class Fragments {

    public Fragments(){
        Bukkit.addRecipe(shapedRecipe());
    }

    public abstract ShapedRecipe shapedRecipe();

}
