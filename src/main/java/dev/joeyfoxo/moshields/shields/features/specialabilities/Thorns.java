package dev.joeyfoxo.moshields.shields.features.specialabilities;

import dev.joeyfoxo.moshields.MoShields;
import dev.joeyfoxo.moshields.manager.DurabilityHandler;
import dev.joeyfoxo.moshields.shields.ShieldType;
import dev.joeyfoxo.moshields.shields.features.Features;
import dev.joeyfoxo.moshields.shields.features.ability.SpecialAbility;
import dev.joeyfoxo.moshields.util.UtilClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Thorns implements SpecialAbility {

    BukkitTask task;

    public void activateAbility(Player player) {
        Features.setActiveSpecialAbility(player.getUniqueId(), Abilities.THORNS);

        //TODO REDUCE THE DURABILITY


    }

    public void performAbility(EntityDamageByEntityEvent event) {

        if (!(event.getDamager() instanceof LivingEntity)) {
            return;
        }

        if (event.getEntity() instanceof Player player) {

            if (!Features.isAbilityActive(player.getUniqueId())) {
                return;
            }

            if (!player.getNearbyEntities(3, 3, 3).contains(event.getDamager())) {
                return;
            }
            summonThorns(player, (LivingEntity) event.getDamager(), event.getDamage());
        }

    }

    private void summonThorns(Player player, LivingEntity attacker, double damage) {
        ArrayList<ArmorStand> swordsSpawned = new ArrayList<>();
        World world = player.getWorld();
        Location location = player.getLocation();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();

        double spreadRadius = 0.4; // Adjust this radius as needed

        //TODO Further polishing on the sword animation

        for (int i = 0; i < 5; i++) {
            double randomXOffset = (Math.random() * spreadRadius * 2) - spreadRadius;
            double randomZOffset = (Math.random() * spreadRadius * 2) - spreadRadius;
            double randomYOffset = ThreadLocalRandom.current().nextDouble(-0.9, 0.6);

            // Calculate the rotated offsets based on the player's yaw
            double rotatedXOffset = randomXOffset * Math.cos(Math.toRadians(yaw)) + randomZOffset * Math.sin(Math.toRadians(yaw));
            double rotatedZOffset = -randomXOffset * Math.sin(Math.toRadians(yaw)) + randomZOffset * Math.cos(Math.toRadians(yaw));

            swordsSpawned.add(world.spawn(new Location(world, x + rotatedXOffset, y + randomYOffset, z + rotatedZOffset, yaw, pitch), ArmorStand.class, armorStand -> {

                ItemStack sword = getItemStack(damage);
                sword.addUnsafeEnchantment(Enchantment.LUCK, 1);

                armorStand.getEquipment().setItemInMainHand(sword);
                armorStand.setArms(true);
                armorStand.setVisible(false);
                armorStand.setInvulnerable(true);
                armorStand.setGravity(false);
                armorStand.addEquipmentLock(EquipmentSlot.HAND, ArmorStand.LockType.REMOVING_OR_CHANGING);
                armorStand.setRotation(yaw, pitch);
                // ENSURE THAT NO PLAYER CAN TAKE THE SWORD
            }));
        }

        attacker.damage(damage / 2);
        cancelAbility(swordsSpawned, player);
    }

    @NotNull
    private ItemStack getItemStack(double damage) {
        Material swordType = Material.WOODEN_SWORD;

        if (damage <= 2) {
            swordType = Material.STONE_SWORD;
        }

        if (damage > 2 && damage <= 3) {
            swordType = Material.IRON_SWORD;
        }

        if (damage > 3 && damage <= 4) {
            swordType = Material.DIAMOND_SWORD;
        }

        if (damage > 5) {
            swordType = Material.NETHERITE_SWORD;
        }

        return new ItemStack(swordType);
    }

    private void cancelAbility(ArrayList<ArmorStand> armorStandsSpawned, Player player) {

        if (!Features.isAbilityActive(player.getUniqueId()) || task != null) {
            task.cancel();
        }

        task = Bukkit.getScheduler().runTaskLater(JavaPlugin.getPlugin(MoShields.class), () -> {

            for (Entity entity : armorStandsSpawned) {
                entity.remove();
            }
            armorStandsSpawned.clear();
        }, 5);

    }


    @Override
    public void applyEffects() {

    }


}
