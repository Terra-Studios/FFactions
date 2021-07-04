package dev.sebastianb.ffactions.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import dev.sebastianb.ffactions.database.DatabaseInitializer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

// This class is only called in a development environment. Useful for testing specific methods
public class DebugCommand implements ICommand {
    @Override
    public String commandName() {
        return "debug";
    }

    @Override
    public LiteralArgumentBuilder<ServerCommandSource> registerNode() {
        return CommandManager.literal(commandName())
                .executes(context -> ICommand.argumentFeedback(context, this))
                .then(CommandManager.literal("sql")
                        .executes(DebugCommand::testSQL));
    }

    // test command to
    private static int testSQL(CommandContext<ServerCommandSource> context) {
        DatabaseInitializer.getSQL("select * from faction");
        return Command.SINGLE_SUCCESS;
    }

}
