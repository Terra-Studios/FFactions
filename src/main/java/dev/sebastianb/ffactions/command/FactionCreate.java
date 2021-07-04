package dev.sebastianb.ffactions.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.sebastianb.ffactions.database.DatabaseInitializer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.time.LocalDateTime;
import java.util.UUID;

public class FactionCreate implements ICommand {

    @Override
    public String commandName() {
        return "create";
    }

    @Override
    public LiteralArgumentBuilder<ServerCommandSource> registerNode() {
        return CommandManager.literal(commandName())
                .executes(context -> ICommand.argumentFeedback(context, this))
                .then(CommandManager.argument("faction_name", StringArgumentType.word())
                .then(CommandManager.argument("tag", StringArgumentType.word())
                        .executes(FactionCreate::createFaction)));
    }

    private static int createFaction(CommandContext<ServerCommandSource> commandContext) {
        try {
            DatabaseInitializer.executeSQL(
                    "INSERT INTO faction (fac_uuid, fac_owner_uuid, created) " +
                            "values ('" + UUID.randomUUID() + "', '" + commandContext.getSource().getPlayer().getUuid() + "', '" + LocalDateTime.now() + "');" // creates a random faction UUID and inserts the time "now"
            );
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        return Command.SINGLE_SUCCESS;
    }

}
