package dev.joeyfoxo.moshields;

import dev.joeyfoxo.moshields.manager.FeatureManager;
import dev.joeyfoxo.moshields.manager.ShieldManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MoShields extends JavaPlugin {

    @Override
    public void onEnable() {
        new ShieldManager(this);
        new FeatureManager();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
