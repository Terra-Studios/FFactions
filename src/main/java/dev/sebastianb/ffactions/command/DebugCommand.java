package dev.sebastianb.ffactions.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.sebastianb.ffactions.database.DatabaseInitializer;
import dev.sebastianb.ffactions.util.SebaUtils;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;


import net.minecraft.text.Text;

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

    // test command
    private static int testSQL(CommandContext<ServerCommandSource> context) {
        boolean matched = DatabaseInitializer.hasMatching("select * from faction", "fac_owner_uuid", context.getSource().getPlayer().getUuid());
        SebaUtils.ChatUtils.saySimpleMessage(context, Text.literal(String.valueOf(matched)));
        return Command.SINGLE_SUCCESS;
    }

}
