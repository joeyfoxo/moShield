package dev.joeyfoxo.moshields.shields.features.ability;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public interface SpecialAbility extends Ability {

    void activateAbility(Player player);

}
