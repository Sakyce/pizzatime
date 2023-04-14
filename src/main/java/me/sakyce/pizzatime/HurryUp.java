package me.sakyce.pizzatime;

import me.sakyce.pizzatime.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

// server
public class HurryUp {
    private final ServerPlayer serverPlayer;
    private final Vec3 startPos;
    private final Level startLevel;
    private final BlockPos spawnPos;
    private int timeLeft;
    private int laps;
    private int ticks;
    private final int maxTime;
    private final ServerBossEvent bossEvent;
    public int getMaxTime() {
        return this.maxTime;
    }
    public HurryUp(Player player) {
        this.serverPlayer = (ServerPlayer) player;

        this.timeLeft = 120;
        this.maxTime = this.timeLeft;
        this.ticks = 0;
        this.startPos = player.getPosition(0);
        this.startLevel = player.getLevel();
        this.laps = 1;

        BlockPos spawnPosDef = this.serverPlayer.getRespawnPosition();
        if (spawnPosDef == null) {
            spawnPosDef = Objects.requireNonNull(this.serverPlayer.getServer()).overworld().getSharedSpawnPos();
        }
        this.spawnPos = spawnPosDef;

        double x = this.serverPlayer.getX();
        double y = this.serverPlayer.getY();
        double z = this.serverPlayer.getZ();

        this.bossEvent = new ServerBossEvent(Component.literal("Go back to the start!"), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS);

        this.bossEvent.addPlayer(this.serverPlayer);
        Pizzatime.playSoundToPlayer(this.serverPlayer, ModSounds.PIZZATIME.get(), SoundSource.MASTER, 1, 1);
    }
    public Item getLoot() {
        if (laps >= 2) {
            return Items.NETHER_STAR;
        }
        return Items.DIAMOND;
    }
    public void newlap() {
        this.laps++;
        this.serverPlayer.level = this.startLevel;
        this.serverPlayer.teleportTo(this.startPos.x, this.startPos.y, this.startPos.z);

        Pizzatime.stopSoundToPlayer(this.serverPlayer, ModSounds.PIZZATIME);
        if (this.laps == 2) {
            Pizzatime.playSoundToPlayer(this.serverPlayer, ModSounds.DESERVIOLI.get(), SoundSource.MASTER, 1, 1);
        }
        Pizzatime.playSoundToPlayer(this.serverPlayer, ModSounds.NEWLAP.get(), SoundSource.MASTER, 1, 1);
    }
    public void stop() {
        Pizzatime.stopSoundToPlayer(this.serverPlayer, ModSounds.PIZZATIME);
        Pizzatime.stopSoundToPlayer(this.serverPlayer, ModSounds.DESERVIOLI);
        this.bossEvent.removeAllPlayers();
        HurryUpHandler.remove(this.serverPlayer);
    }
    public void tick() {
        this.bossEvent.setProgress((float) this.timeLeft/this.maxTime);

        Pizzatime.getLogger().info(Double.toString(
                this.spawnPos.distToCenterSqr(this.serverPlayer.position())
        ));
        // check for spawn
        if (this.spawnPos.distToCenterSqr(this.serverPlayer.position()) <= 10) {
            this.victory();
            return;
        }

        if (this.ticks >= 300) {
            Pizzatime.getLogger().info(Integer.toString(this.timeLeft));
            this.timeLeft--;
            this.ticks = 0;
            if (this.timeLeft <= 0) {
                this.expire();
            }
        } else {
            this.ticks += 1;
        }
    }
    public void expire() {
        //this.serverPlayer.level.explode(null, this.serverPlayer.getX(), this.serverPlayer.getY(), this.serverPlayer.getZ(), 4.0F, Explosion.BlockInteraction.NONE);
        this.serverPlayer.kill();
        this.stop();
    }
    public void victory() {
        Pizzatime.playSoundToPlayer(this.serverPlayer, ModSounds.VICTORY.get(), SoundSource.MASTER, 1, 1);
        this.serverPlayer.addItem(new ItemStack(getLoot()));
        this.stop();
    }
}