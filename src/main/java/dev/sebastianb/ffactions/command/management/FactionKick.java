package dev.sebastianb.ffactions.command.management;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.sebastianb.ffactions.admin.FactionManagement;
import dev.sebastianb.ffactions.command.ICommand;
import dev.sebastianb.ffactions.util.SebaUtils;
import net.minecraft.command.argument.GameProfileArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Collection;

// TODO: make kicking configurable by a permissions system per rank
public class FactionKick implements ICommand {
    @Override
    public String commandName() {
        return "kick";
    }

    @Override
    public LiteralArgumentBuilder<ServerCommandSource> registerNode() {
        return CommandManager.literal(commandName())
                .then(CommandManager.argument("player", GameProfileArgumentType.gameProfile())
                .executes(FactionKick::kick));
    }

    private static int kick(CommandContext<ServerCommandSource> commandContext) {
        try {
            ServerPlayerEntity playerRanCommand = commandContext.getSource().getPlayer();
            Collection<GameProfile> profiles = GameProfileArgumentType.getProfileArgument(commandContext, "player");
            for (GameProfile profile : profiles) {
                if (!profile.getId().equals(playerRanCommand.getUuid())) {
                    if (!FactionManagement.isInFaction(playerRanCommand)) {
                        SebaUtils.ChatUtils.saySimpleMessage(commandContext,
                                Text.literal("You need to be in a faction with permission to use this command")); // TODO: Replace with translatable
                        return 0;
                    }
                    // TODO: Do check for if player running has permission to kick player
                    if (!FactionManagement.getFactionUUID(playerRanCommand).equals(FactionManagement.getFactionUUID(profile.getId()))) {
                        SebaUtils.ChatUtils.saySimpleMessage(commandContext,
                                Text.literal("You can't kick players outside your faction!")); // TODO: Replace with translatable
                        return 0;
                    }
                    FactionManagement.removePlayerFromCurrentFaction(profile.getId());
                    SebaUtils.ChatUtils.saySimpleMessage(commandContext,
                            Text.literal("Successfully kicked " + profile.getName() + " from the faction " + FactionManagement.getFactionName(playerRanCommand))); // TODO: Replace with translatable
                } else {
                    if (profiles.size() == 1) {
                        SebaUtils.ChatUtils.saySimpleMessage(commandContext,
                                Text.literal("You can't kick yourself!")); // TODO: Replace with translatable
                        return 0;
                    }
                }
            }

            return Command.SINGLE_SUCCESS;

        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
