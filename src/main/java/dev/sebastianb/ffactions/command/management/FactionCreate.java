package dev.sebastianb.ffactions.command.management;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.sebastianb.ffactions.claim.FactionManagement;
import dev.sebastianb.ffactions.command.ICommand;
import dev.sebastianb.ffactions.database.DatabaseInitializer;
import dev.sebastianb.ffactions.util.SebaUtils;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

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
            // TODO: add check for if the player is a member of a existing faction.

            // check if a player already owns a faction
            if (FactionManagement.isFactionOwner(commandContext.getSource().getPlayer())) {
                SebaUtils.ChatUtils.saySimpleMessage(commandContext,
                        new LiteralText("You already own a faction! If you'd like to delete your faction, run /f remove")); // TODO: Replace this with a translatable
                return 0; // 0 for FAIL
            }
            String factionName;
            String factionTag;

            SebaUtils.ChatUtils.saySimpleMessage(commandContext, new LiteralText("Successfully created faction")); // TODO: Replace with translatable
            FactionManagement.createFaction(commandContext.getSource().getPlayer());


        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        return Command.SINGLE_SUCCESS;
    }

}
