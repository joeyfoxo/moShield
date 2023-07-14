package dev.joeyfoxo.moshields;

import dev.joeyfoxo.moshields.manager.DurabilityHandler;
import dev.joeyfoxo.moshields.manager.FeatureHandler;
import dev.joeyfoxo.moshields.manager.ShieldManager;
import dev.joeyfoxo.moshields.manager.CooldownManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MoShields extends JavaPlugin {

    @Override
    public void onEnable() {

        new ShieldManager(this);
        new CooldownManager().run();
        new DurabilityHandler();
        new FeatureHandler();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
