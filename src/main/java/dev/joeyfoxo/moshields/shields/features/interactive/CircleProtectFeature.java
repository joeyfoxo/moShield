package dev.joeyfoxo.moshields.shields.features.interactive;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.manager.DurabilityHandler;
import dev.joeyfoxo.moshields.manager.ShieldType;
import dev.joeyfoxo.moshields.util.UtilClass;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.EulerAngle;

import java.util.ArrayList;

import static dev.joeyfoxo.moshields.manager.CooldownManager.*;
import static org.bukkit.entity.ArmorStand.LockType.ADDING_OR_CHANGING;

public class CircleProtectFeature {

    final int cooldown = 120;
    final int abilityLength = 10;
    private boolean abilityActive;

    public void activateAbility(Player player) {
        for (ShieldType type : UtilClass.getHeldShields(player)) {

            if (shieldIsOnCooldown(player.getUniqueId(), type)) {
                player.sendMessage(Component.text()
                        .content("Sorry you need to wait " + getShieldCooldown(player.getUniqueId(), type) + " more seconds to use this ability!")
                        .color(TextColor.color(255, 100, 100)).build());
                return;
            }

            abilityActive = true;
            applyCooldownToShield(player,
                    type, cooldown);

        }
    }

    public boolean isAbilityActive() {
        return abilityActive;
    }

    private void spawnSurroundingShields(Player player) {
        ArrayList<Entity> armorStandsSpawned = new ArrayList<>();

        World world = player.getWorld();
        double x, y, z;
        double radius = 0.8;
        for (int i = 0; i < 360; i += 45) {
            x = player.getLocation().getX() + radius * Math.sin(Math.toRadians(i));
            y = player.getLocation().getY() - 0.6;
            z = player.getLocation().getZ() + radius * Math.cos(Math.toRadians(i));

            float rotation = i;

            armorStandsSpawned.add(world.spawn(new Location(world, x,y,z), ArmorStand.class, armorStand -> {
                armorStand.getEquipment().setItemInMainHand(new ItemStack(Material.SHIELD));
                armorStand.setArms(true);
                armorStand.setVisible(false);
                armorStand.setInvulnerable(true);
                armorStand.setGravity(false);
                armorStand.setRightArmPose(new EulerAngle(Math.toRadians(-90), Math.toRadians(-90), 0));
                armorStand.addEquipmentLock(EquipmentSlot.HAND, ADDING_OR_CHANGING);
                //ENSURE THAT NO PLAYER CAN TAKE THE SHIELD
                armorStand.setRotation(360 - rotation, 0);
            }));

        }

        Bukkit.getScheduler().runTaskLater(JavaPlugin.getPlugin(MoShields.class), () -> {
            for (Entity entity : armorStandsSpawned) {
                entity.remove();
            }
            armorStandsSpawned.clear();
        }, 20);

    }

    public void performAbility(EntityDamageByEntityEvent event) {

        if (event.getEntity() instanceof Player player) {
            spawnSurroundingShields(player);
            event.setDamage(event.getDamage() / 3);

            Bukkit.getScheduler().runTaskLater(JavaPlugin.getPlugin(MoShields.class), () -> abilityActive = false, abilityLength * 20); //times 20 because its in ticks
        }


    }

}
