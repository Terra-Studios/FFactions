package dev.sebastianb.ffactions.command.management;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.sebastianb.ffactions.claim.FactionManagement;
import dev.sebastianb.ffactions.command.ICommand;
import dev.sebastianb.ffactions.command.management.status.FactionPlayerStatus;
import dev.sebastianb.ffactions.util.SebaUtils;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FactionInvite implements ICommand {
    @Override
    public String commandName() {
        return "invite";
    }

    @Override
    public LiteralArgumentBuilder<ServerCommandSource> registerNode() {
        return CommandManager.literal(commandName())
                .executes(context -> ICommand.argumentFeedback(context, this))
                .then(CommandManager.argument("invited_player", EntityArgumentType.player())
                .executes(FactionInvite::invite));
    }

    private static int invite(CommandContext<ServerCommandSource> commandContext) {
        try {
            ServerPlayerEntity inviter = commandContext.getSource().getPlayer();
            ServerPlayerEntity invitedPlayer = EntityArgumentType.getPlayer(commandContext, "invited_player");

            if (inviter.getUuid().equals(invitedPlayer.getUuid())) {
                // TODO: Replace with a translatable
                SebaUtils.ChatUtils.saySimpleMessage(commandContext,
                        new LiteralText("Silly bean you can't invite yourself.")
                );
                return 0;
            }

            if (!FactionManagement.isInFaction(inviter)) {
                // TODO: Replace with a translatable
                SebaUtils.ChatUtils.saySimpleMessage(commandContext,
                        new LiteralText("Unable to invite " + invitedPlayer.getName().getString() + " as you're not in a faction.")
                );
                return 0;
            }

            if (FactionManagement.isInFaction(invitedPlayer)) {
                // TODO: Replace with a translatable
                SebaUtils.ChatUtils.saySimpleMessage(commandContext,
                        new LiteralText("Unable to invite " + invitedPlayer.getName().getString() + " as they're already in a faction.")
                );
                return 0;
            }

            // TODO: Make group based permissions for users in a faction. For now, only the owner can invite
            if (!FactionManagement.isFactionOwner(inviter)) {
                // TODO: Replace with a translatable
                SebaUtils.ChatUtils.saySimpleMessage(commandContext,
                        new LiteralText("Unable to invite " + invitedPlayer.getName().getString() + " as you don't have permission to invite.")
                );
                return 0;
            }
            // TODO: Replace with a translatable
            SebaUtils.ChatUtils.saySimpleMessage(commandContext,
                    new LiteralText("You sent a invite request to " + invitedPlayer.getName().getString())
            );

            // based on my implementation, it'd be really funny if someone constantly invites someone to prevent joining lmao. Maybe fix?
            if (FactionPlayerStatus.invitedPlayerAndFactionUUID.containsKey(invitedPlayer.getUuid())) {
                    SebaUtils.ChatUtils.saySimpleMessage(commandContext,
                            new LiteralText(invitedPlayer.getName().getString() + " has already been invited to a faction.") // TODO: Replace with a translatable
                    );
                return 0;
            }

            // TODO: Replace with a translatable
            invitedPlayer.sendMessage(
                    new LiteralText(String.format("%s invited you to a faction named %s.\nTo join, run the command /f accept", invitedPlayer.getName().getString(), FactionManagement.getFactionName(inviter))),
                    false
            );
            FactionPlayerStatus.runThread(invitedPlayer, FactionManagement.getFactionUUID(inviter.getUuid()), 15);

        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        return Command.SINGLE_SUCCESS;
    }

}
