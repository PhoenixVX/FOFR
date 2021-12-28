package me.zero.fofr.fabric;

import me.zero.fofr.FOFR;
import net.fabricmc.api.ModInitializer;
import software.bernie.geckolib3.GeckoLib;

public class FOFRFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        GeckoLib.initialize();
        FOFR.init();
    }
}
