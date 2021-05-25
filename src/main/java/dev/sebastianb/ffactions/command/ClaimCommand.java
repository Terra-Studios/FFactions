package dev.sebastianb.ffactions.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

public class ClaimCommand implements ICommand {

    @Override
    public LiteralCommandNode<ServerCommandSource> registerNode(CommandDispatcher<ServerCommandSource> dispatcher) {
        return dispatcher.register(
                CommandManager.literal("claim")
        );
    }

    @Override
    public String commandName() {
        return "claim";
    }

    @Override
    public TranslatableText commandInfo() {
        return null;
    }

    @Override
    public TranslatableText commandTooltip() {
        return null;
    }

}
