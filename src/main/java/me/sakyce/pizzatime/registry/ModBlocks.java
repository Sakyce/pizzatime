package me.sakyce.pizzatime.registry;

import me.sakyce.pizzatime.Pizzatime;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Pizzatime.MODID);
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
