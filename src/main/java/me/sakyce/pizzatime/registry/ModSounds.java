package me.sakyce.pizzatime.registry;

import me.sakyce.pizzatime.Pizzatime;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static me.sakyce.pizzatime.Pizzatime.MODID;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MODID);

    public static final RegistryObject<SoundEvent> PIZZATIME = registerNewSound("pizzatime");
    public static final RegistryObject<SoundEvent> DESERVIOLI = registerNewSound("deservioli");
    public static final RegistryObject<SoundEvent> HURRYUP = registerNewSound("hurryup");
    public static final RegistryObject<SoundEvent> NEWLAP = registerNewSound("newlap");
    public static final RegistryObject<SoundEvent> VICTORY = registerNewSound("victory");
    public static RegistryObject<SoundEvent> registerNewSound(String name) {
        return SOUNDS.register(name, () -> new SoundEvent(new ResourceLocation(MODID, name)));
    }
    public static void register(IEventBus eventBus) {
        SOUNDS.register(eventBus);
    }
}
