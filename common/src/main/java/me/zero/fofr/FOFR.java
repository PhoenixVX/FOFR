package me.zero.fofr;

import dev.architectury.registry.CreativeTabRegistry;
import me.zero.fofr.registry.ModBlocks;
import me.zero.fofr.registry.ModEntities;
import me.zero.fofr.registry.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class FOFR {
    public static final String MOD_ID = "fofr";

    public static final ItemGroup ITEM_GROUP = CreativeTabRegistry.create(new Identifier(MOD_ID, "item_group"), () -> new ItemStack(Items.BOWL));

    public static void init() {
        ModBlocks.BLOCKS.register();
        ModItems.ITEMS.register();
        ModEntities.ENTITY_TYPES.register();
        ModEntities.initializeAttributes();
    }
}
