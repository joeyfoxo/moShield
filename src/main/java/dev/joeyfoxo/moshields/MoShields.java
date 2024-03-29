package dev.joeyfoxo.moshields;

import dev.joeyfoxo.moshields.loottable.StructureListener;
import dev.joeyfoxo.moshields.manager.DurabilityHandler;
import dev.joeyfoxo.moshields.manager.FeatureHandler;
import dev.joeyfoxo.moshields.manager.ShieldManager;
import dev.joeyfoxo.moshields.manager.Cooldown;
import dev.joeyfoxo.moshields.upgrades.handler.UpgradeListener;
import dev.joeyfoxo.moshields.upgrades.items.fragments.EchoFragment;
import dev.joeyfoxo.moshields.upgrades.items.fragments.ReinforcedFragment;
import org.bukkit.plugin.java.JavaPlugin;

public final class MoShields extends JavaPlugin {

    @Override
    public void onEnable() {

        new ShieldManager(this);
        new Cooldown().run();
        new DurabilityHandler();
        new FeatureHandler();
        new StructureListener();

        //Upgradeable Shields
        new UpgradeListener();


        //Fragments
        new ReinforcedFragment();
        new EchoFragment();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
