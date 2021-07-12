package dev.sebastianb.ffactions.command.management;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.sebastianb.ffactions.claim.FactionManagement;
import dev.sebastianb.ffactions.command.ICommand;
import dev.sebastianb.ffactions.command.management.status.FactionPlayerStatus;
import dev.sebastianb.ffactions.util.SebaUtils;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import java.util.UUID;

// run command to accept faction invitation
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
        try {
            ServerPlayerEntity player = commandContext.getSource().getPlayer();
            if (FactionManagement.isInFaction(player)) {
                SebaUtils.ChatUtils.saySimpleMessage(commandContext,
                        new LiteralText("You're currently in a faction")); // TODO: Replace with translatable

                return 0;
            }
            if (!FactionPlayerStatus.invitedPlayerAndFactionUUID.containsKey(player.getUuid())) {
                SebaUtils.ChatUtils.saySimpleMessage(commandContext,
                        new LiteralText("You have no pending invites")); // TODO: Replace with translatable
                return 0;
            }
            UUID factionUUID = FactionPlayerStatus.invitedPlayerAndFactionUUID.get(player.getUuid());
            String factionName = FactionManagement.getFactionName(factionUUID);
            FactionPlayerStatus.invitedPlayerAndFactionUUID.remove(player.getUuid());
            SebaUtils.ChatUtils.saySimpleMessage(commandContext,
                    new LiteralText("You've now joined the " + factionName + " faction"));
            FactionManagement.invitePlayerOrJoinFaction(factionUUID, player);

        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        return Command.SINGLE_SUCCESS;
    }

}
