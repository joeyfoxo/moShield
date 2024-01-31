package dev.joeyfoxo.moshields.shields.standard;

import dev.joeyfoxo.moshields.shields.Shield;
import dev.joeyfoxo.moshields.shields.ShieldType;
import dev.joeyfoxo.moshields.util.UtilClass;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;

public class NetheriteShield extends Shield {

    NamespacedKey key;


    public NetheriteShield(ShieldType shieldType, Component title, NamespacedKey key) {
        super(shieldType, title);
        this.key = key;
        createShieldItem();
    }

    @Override
    protected void modifyMeta(ItemMeta meta) {
        UtilClass.setCustomModelID(meta, key, getShieldType());
    }

    @Override
   protected void shieldAbilities() {
    }

    @Override
    public ShieldType getShieldType() {
        return ShieldType.NETHERITE;
    }


}
