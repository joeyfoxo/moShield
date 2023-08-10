package dev.joeyfoxo.moshields.shields;

import dev.joeyfoxo.moshields.shields.features.Abilities;
import dev.joeyfoxo.moshields.shields.features.Features;
import dev.joeyfoxo.moshields.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ObsidianShield extends Shield {

    NamespacedKey key;


    public ObsidianShield(ShieldType shieldType, Component title, NamespacedKey key) {
        super(shieldType, title);
        this.key = key;
        createRecipe();
    }


    public void createRecipe() {

        ShapedRecipe recipe = new ShapedRecipe(key, createShieldItem());
        recipe.shape("OIO", "OOO", " O ");
        recipe.setIngredient('O', Material.OBSIDIAN);
        recipe.setIngredient('I', Material.IRON_INGOT);
        Bukkit.addRecipe(recipe);
    }

    @Override
    void modifyMeta(ItemMeta meta) {
        UtilClass.setCustomModelID(meta, key, getShieldType());

        List<TextComponent> lore = new ArrayList<>();

        if (Features.hasShieldAbility(shieldType)) {

            lore.add(Component.text().content("ABILITY")
                    .decoration(TextDecoration.BOLD, true)
                    .decoration(TextDecoration.ITALIC, false)
                    .color(TextColor.color(255, 165, 0)).build());

            lore.add(Component.text(""));

            lore.add(Component.text().content("Special Ability: TBD")
                    .color(TextColor.color(100, 100, 100))
                    .decoration(TextDecoration.ITALIC, false).build());

            meta.lore(lore);

        }
    }

    @Override
    void shieldAbilities() {

        Features.addShieldAbility(getShieldType(), Abilities.SINK, Abilities.FORCEFIELD);
    }

    @Override
    public ShieldType getShieldType() {
        return ShieldType.OBSIDIAN;
    }


}
