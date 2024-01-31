package dev.joeyfoxo.moshields.shields.special.reflection;

import dev.joeyfoxo.moshields.shields.Shield;
import dev.joeyfoxo.moshields.shields.ShieldType;
import dev.joeyfoxo.moshields.shields.features.Features;
import dev.joeyfoxo.moshields.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import dev.joeyfoxo.moshields.shields.features.ability.Ability.Abilities;

import java.util.ArrayList;
import java.util.List;

public class MirrorShield extends Shield {

    NamespacedKey key;


    public MirrorShield(ShieldType shieldType, Component title, NamespacedKey key) {
        super(shieldType, title);
        this.key = key;
        createShieldItem();
    }

    @Override
    protected void modifyMeta(ItemMeta meta) {
        UtilClass.setCustomModelID(meta, key, getShieldType());

        List<TextComponent> lore = new ArrayList<>();

        lore.add(Component.text(""));

        lore.add(Component.text().content("+")
                .color(TextColor.color(0, 255, 0))
                .decoration(TextDecoration.ITALIC, false)
                .decoration(TextDecoration.BOLD, true).build());

        lore.add(Component.text().content("Special Ability: Reflects projectiles and tracks the attacker")
                .color(TextColor.color(100, 100, 100))
                .decoration(TextDecoration.ITALIC, false).build());

        lore.add(Component.text().content("Ability: Reflects projectiles")
                .color(TextColor.color(100, 100, 100))
                .decoration(TextDecoration.ITALIC, false).build());

        lore.add(Component.text(""));

        lore.add(Component.text().content("-")
                .color(TextColor.color(255, 0, 0))
                .decoration(TextDecoration.ITALIC, false)
                .decoration(TextDecoration.BOLD, true).build());

        lore.add(Component.text().content("Wielders sink whilst equipped")
                .color(TextColor.color(100, 100, 100))
                .decoration(TextDecoration.ITALIC, false).build());

        meta.lore(lore);

    }

    @Override
    protected void shieldAbilities() {

        Features.addShieldAbility(getShieldType(), Abilities.PROJECTILE_TRACKING_REFLECTION, Abilities.REFLECT, Abilities.SINK);
    }

    @Override
    public ShieldType getShieldType() {
        return ShieldType.MIRROR;
    }


}
