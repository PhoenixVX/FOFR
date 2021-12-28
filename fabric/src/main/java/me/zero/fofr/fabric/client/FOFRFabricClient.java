package me.zero.fofr.fabric.client;

import me.zero.fofr.client.ClientRendererSetup;
import net.fabricmc.api.ClientModInitializer;

public class FOFRFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientRendererSetup.init();
    }
}
