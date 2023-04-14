package me.sakyce.pizzatime;

import com.mojang.logging.LogUtils;
import me.sakyce.pizzatime.networking.PizzatimePacketHandler;
import me.sakyce.pizzatime.registry.ModSounds;
import me.sakyce.pizzatime.registry.Registry;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.List;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Pizzatime.MODID)
public class Pizzatime {
    public static final String MODID = "pizzatime";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static Logger getLogger() {
        return LOGGER;
    }
    public Pizzatime() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        Registry.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::commonSetup);
    }
    public static void playSoundToPlayer(Player player, SoundEvent soundEvent, SoundSource soundSource, float volume, float pitch) {
        ServerPlayer serverPlayer = (ServerPlayer) player;

        serverPlayer.connection.send(
                new ClientboundSoundPacket(
                    soundEvent, soundSource, player.getX(), player.getY(), player.getZ(), volume, pitch, serverPlayer.getRandom().nextLong()
                )
        );
    }
    public static void stopSoundToPlayer(Player player, RegistryObject<SoundEvent> sound) {
        ServerPlayer serverPlayer = (ServerPlayer) player;
        serverPlayer.connection.send(
                new ClientboundStopSoundPacket(sound.getId(), null)
        );
    }
    private void commonSetup(final FMLCommonSetupEvent event) {
        PizzatimePacketHandler.register();
    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }
    @SubscribeEvent
    public void tick(TickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            HurryUpHandler.tick();
        }
    }
}