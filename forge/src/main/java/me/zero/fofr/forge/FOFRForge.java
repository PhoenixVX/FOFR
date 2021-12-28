package me.zero.fofr.forge;

import dev.architectury.platform.forge.EventBuses;
import me.zero.fofr.FOFR;
import me.zero.fofr.client.ClientRendererSetup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;

@Mod(FOFR.MOD_ID)
public class FOFRForge {
    public FOFRForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(FOFR.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        GeckoLib.initialize();
        FOFR.init();

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClientRendererSetup::init);
    }
}
