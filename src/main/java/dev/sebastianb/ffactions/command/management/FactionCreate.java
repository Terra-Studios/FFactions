package dev.sebastianb.ffactions.command.management;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.sebastianb.ffactions.admin.FactionManagement;
import dev.sebastianb.ffactions.command.ICommand;
import dev.sebastianb.ffactions.util.SebaUtils;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
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
                .then(CommandManager.argument("faction_name", StringArgumentType.word()) // StringArgumentType.string() is a security risk since SQL injections would be possible
                .then(CommandManager.argument("tag", StringArgumentType.word()) // StringArgumentType.word() would be the method **YOU NEED** to use
                        .executes(FactionCreate::createFaction)));
        /*

            /f create "test', 'tag'); DROP TABLE faction; --" "we can run anything we like"

            ^ this is comedy

         */

    }

    private static int createFaction(CommandContext<ServerCommandSource> commandContext) {
        try {
            ServerPlayerEntity player = commandContext.getSource().getPlayer();
            // check if player is a member of any existing faction
            if (FactionManagement.isInFaction(player) && !FactionManagement.isFactionOwner(player)) {
                SebaUtils.ChatUtils.saySimpleMessage(commandContext,
                        new LiteralText("You are already in a faction! To create a new faction, leave your current faction.")); // TODO: Replace this with a translatable
                return 0; // 0 for FAIL
            }
            // check if a player already owns a faction
            if (FactionManagement.isFactionOwner(player)) {
                SebaUtils.ChatUtils.saySimpleMessage(commandContext,
                        new LiteralText("You already own a faction! If you'd like to delete your faction, run /f remove")); // TODO: Replace this with a translatable
                return 0; // 0 for FAIL
            }
            String factionName = StringArgumentType.getString(commandContext, "faction_name");
            String factionTag = StringArgumentType.getString(commandContext, "tag");

            SebaUtils.ChatUtils.saySimpleMessage(commandContext,
                    new LiteralText("Successfully created faction \"" + factionName + "\" with the tag of \"" + factionTag + "\"")); // TODO: Replace with translatable
            FactionManagement.createFaction(commandContext.getSource().getPlayer(), factionName, factionTag);


        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        return Command.SINGLE_SUCCESS;
    }

}
