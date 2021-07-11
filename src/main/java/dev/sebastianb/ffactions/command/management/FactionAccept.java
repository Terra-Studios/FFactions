package dev.sebastianb.ffactions.command.management;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import dev.sebastianb.ffactions.command.ICommand;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class FactionAccept implements ICommand {
    @Override
    public String commandName() {
        return "accept";
    }

    @Override
    public LiteralArgumentBuilder<ServerCommandSource> registerNode() {
        return CommandManager.literal(commandName())
                .executes(FactionAccept::inviteAccept);
    }

    private static int inviteAccept(CommandContext<ServerCommandSource> commandContext) {

        return Command.SINGLE_SUCCESS;
    }

}
