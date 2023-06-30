package dev.joeyfoxo.moshields.manager;

import dev.joeyfoxo.moshields.shields.features.interactive.CircleProtectFeature;
import dev.joeyfoxo.moshields.shields.features.interactive.ReflectFeature;
import dev.joeyfoxo.moshields.shields.features.passive.SinkFeature;

public class FeatureManager {

    public FeatureManager() {

        //BASED ON INTERACTION
        new ReflectFeature();
        new CircleProtectFeature();

        //PASSIVE
        new SinkFeature();

    }

}
