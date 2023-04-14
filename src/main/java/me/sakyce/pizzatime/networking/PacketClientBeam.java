package me.sakyce.pizzatime.networking;

import com.google.common.graph.Network;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class PacketClientBeam {
    private static final NetworkDirection NETWORK_DIRECTION = NetworkDirection.PLAY_TO_CLIENT;
    private BlockPos pos;
    private Level level;
    public static void register(SimpleChannel channel, int id) {
        channel.messageBuilder(PacketClientBeam.class, id, NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PacketClientBeam::new)
                .encoder(PacketClientBeam::encoder)
                .consumerMainThread(PacketClientBeam::handle);
    }
    public PacketClientBeam(Level level, BlockPos blockPos) {
        this.pos = blockPos;
        this.level = level;
    }

    public PacketClientBeam(FriendlyByteBuf friendlyByteBuf) {
    }

    public void encoder(FriendlyByteBuf buf) {

    }
    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            //todo
        });
        return true;
    }
}