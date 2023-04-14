package me.sakyce.pizzatime.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.sakyce.pizzatime.HurryUpHandler;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class StopCommand {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        LiteralArgumentBuilder<CommandSourceStack> literal = Commands.literal("stoppizzatime");
        literal.requires(s -> s.hasPermission(4)).executes(args -> {
            CommandSourceStack source = args.getSource();
            HurryUpHandler.victory(source.getPlayer());
            return 0;
        });
        LiteralCommandNode<CommandSourceStack> command = event.getDispatcher().register(literal);
    }
}
