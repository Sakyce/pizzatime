package me.sakyce.pizzatime.registry;

import net.minecraftforge.eventbus.api.IEventBus;

public class Registry {
    public static void register(IEventBus eventBus) {
        ModBlocks.register(eventBus);
        ModItems.register(eventBus);
        ModSounds.register(eventBus);
    }
}
