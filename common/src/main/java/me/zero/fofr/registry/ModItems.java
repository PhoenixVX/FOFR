package me.zero.fofr.registry;

import dev.architectury.registry.registries.DeferredRegister;
import me.zero.fofr.FOFR;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(FOFR.MOD_ID, Registry.ITEM_KEY);

    // Block Items
    public static final Supplier<BlockItem> ZUUBEE_MOUND = ITEMS.register("zuubee_mound", () -> new BlockItem(ModBlocks.ZUUBEE_MOUND.get(), new Item.Settings().group(FOFR.ITEM_GROUP)));
    public static final Supplier<BlockItem> ZUUBEE_BLOCK = ITEMS.register("zuubee_block", () -> new BlockItem(ModBlocks.ZUUBEE_BLOCK.get(), new Item.Settings().group(FOFR.ITEM_GROUP)));
}
