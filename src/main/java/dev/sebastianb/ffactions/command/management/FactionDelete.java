package dev.sebastianb.ffactions.command.management;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.sebastianb.ffactions.claim.FactionManagement;
import dev.sebastianb.ffactions.command.ICommand;
import dev.sebastianb.ffactions.util.SebaUtils;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

public class FactionDelete implements ICommand {
    @Override
    public String commandName() {
        return "remove";
    }

    @Override
    public LiteralArgumentBuilder<ServerCommandSource> registerNode() {
        return CommandManager.literal(commandName())
                .executes(FactionDelete::deleteFaction);
//                .then(CommandManager.argument("faction_name", StringArgumentType.word())
//                        .then(CommandManager.argument("tag", StringArgumentType.word())
//                                .executes(FactionDelete::deleteFaction)));
    }

    private static int deleteFaction(CommandContext<ServerCommandSource> commandContext) {
        try {
            // TODO: add check for if the player is a member of a existing faction.
            // The player "shouldn't" be on any other faction list like this

            // check if a player already owns a faction
            if (FactionManagement.isFactionOwner(commandContext.getSource().getPlayer())) {
                FactionManagement.deleteFaction(commandContext.getSource().getPlayer());
                SebaUtils.ChatUtils.saySimpleMessage(commandContext, new LiteralText("Faction deleted")); // TODO: Replace with translatable
                return Command.SINGLE_SUCCESS;
            }
            SebaUtils.ChatUtils.saySimpleMessage(commandContext, new LiteralText("Not in any faction so can't delete")); // TODO: Replace with translatable
            // ^ think of better wording


        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        return Command.SINGLE_SUCCESS;
    }


}
