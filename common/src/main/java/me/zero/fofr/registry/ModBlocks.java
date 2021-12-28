package me.zero.fofr.registry;

import dev.architectury.registry.registries.DeferredRegister;
import me.zero.fofr.FOFR;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.registry.Registry;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(FOFR.MOD_ID, Registry.BLOCK_KEY);

    public static final Supplier<Block> ZUUBEE_MOUND = BLOCKS.register("zuubee_mound", () -> new Block(AbstractBlock.Settings.of(Material.METAL).hardness(4.0F).nonOpaque()));
    public static final Supplier<Block> ZUUBEE_BLOCK = BLOCKS.register("zuubee_block", () -> new Block(AbstractBlock.Settings.of(Material.METAL).hardness(4.0F)));
}
