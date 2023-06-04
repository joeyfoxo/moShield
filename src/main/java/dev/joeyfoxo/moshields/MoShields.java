package dev.joeyfoxo.moshields;

import dev.joeyfoxo.moshields.manager.ShieldManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MoShields extends JavaPlugin {

    @Override
    public void onEnable() {
        new ShieldManager();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
