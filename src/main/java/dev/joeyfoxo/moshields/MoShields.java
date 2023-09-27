package dev.joeyfoxo.moshields;

import dev.joeyfoxo.moshields.manager.DurabilityHandler;
import dev.joeyfoxo.moshields.manager.FeatureHandler;
import dev.joeyfoxo.moshields.manager.ShieldManager;
import dev.joeyfoxo.moshields.manager.Cooldown;
import dev.joeyfoxo.moshields.upgrades.GUI.ShieldUpgrade;
import org.bukkit.plugin.java.JavaPlugin;

public final class MoShields extends JavaPlugin {

    @Override
    public void onEnable() {

        new ShieldManager(this);
        new Cooldown().run();
        new DurabilityHandler();
        new FeatureHandler();

        //Upgradeable Shields
        new ShieldUpgrade();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
