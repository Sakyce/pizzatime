package me.sakyce.pizzatime.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.sakyce.pizzatime.HurryUpHandler;
import me.sakyce.pizzatime.Pizzatime;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class StartCommand {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        LiteralArgumentBuilder<CommandSourceStack> literal = Commands.literal("pizzatime");
        literal.requires(s -> s.hasPermission(4)).executes(args -> {
            CommandSourceStack source = args.getSource();
            HurryUpHandler.start(source.getPlayer());
            return 0;
        });
        LiteralCommandNode<CommandSourceStack> command = event.getDispatcher().register(literal);
    }
}
