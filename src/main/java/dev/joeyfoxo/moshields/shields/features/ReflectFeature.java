package dev.joeyfoxo.moshields.shields.features;

import dev.joeyfoxo.moshields.MoShields;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ReflectFeature implements Listener {


    public ReflectFeature(boolean reflectMagic, boolean reflectItems) {
        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(MoShields.class));

        if (reflectMagic) {
            reflectMagic();
        }

        if (reflectItems) {
            reflectItems();
        }

    }

    @EventHandler
    public void onAttemptedDamage(EntityDamageByEntityEvent event) {

        if (event.getEntity() instanceof Player player) {

            if (!player.isBlocking()) {

                return;
            }


        }


    }

    private void reflectItems() {



    }

    private void reflectMagic() {


    }


}
