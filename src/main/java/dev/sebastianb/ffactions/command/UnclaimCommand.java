package dev.sebastianb.ffactions.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class UnclaimCommand implements ICommand {
    @Override
    public String commandName() {
        return "unclaim";
    }

    @Override
    public LiteralArgumentBuilder<ServerCommandSource> registerNode() {
        return CommandManager.literal(commandName())
                .executes(UnclaimCommand::unclaim);
    }

    private static int unclaim(CommandContext<ServerCommandSource> commandContext) {

        return Command.SINGLE_SUCCESS;
    }


}
