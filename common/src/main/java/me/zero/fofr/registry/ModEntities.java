package me.zero.fofr.registry;

import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import me.zero.fofr.FOFR;
import me.zero.fofr.entity.GownEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(FOFR.MOD_ID, Registry.ENTITY_TYPE_KEY);

    public static final Supplier<EntityType<GownEntity>> ELDRITCH_GOWN = ENTITY_TYPES.register("eldritch_gown", () -> EntityType.Builder.create(GownEntity::new, SpawnGroup.MONSTER).setDimensions(1.4F, 2.75F).build("eldritch_gown"));

    public static void initializeAttributes() {
        EntityAttributeRegistry.register(ELDRITCH_GOWN::get, GownEntity::createEldritchGownAttributes);
    }
}
