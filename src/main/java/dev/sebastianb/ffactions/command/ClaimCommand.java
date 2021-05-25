package dev.sebastianb.ffactions.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

public class ClaimCommand implements ICommand {

    @Override
    public LiteralArgumentBuilder<ServerCommandSource> registerNode(CommandDispatcher<ServerCommandSource> dispatcher) {
        return CommandManager.literal("claim");
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
