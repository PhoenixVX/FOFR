package me.zero.fofr.client;

import dev.architectury.registry.level.entity.EntityRendererRegistry;
import me.zero.fofr.entity.renderer.GownEntityRenderer;
import me.zero.fofr.registry.ModEntities;

public class ClientRendererSetup {
    public static void init() {
        EntityRendererRegistry.register(ModEntities.ELDRITCH_GOWN::get, GownEntityRenderer::new);
    }
}
