package dev.sebastianb.ffactions.command.management;

import com.mojang.brigadier.Command;
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

// command to leave the faction you're in
public class FactionLeave implements ICommand {
    @Override
    public String commandName() {
        return "leave";
    }

    @Override
    public LiteralArgumentBuilder<ServerCommandSource> registerNode() {
        return CommandManager.literal(commandName())
                .executes(FactionLeave::leave);
    }

    // TODO: Replace all literals with translatable
    private static int leave(CommandContext<ServerCommandSource> commandContext) {
        try {
            ServerPlayerEntity player = commandContext.getSource().getPlayer();
            if (FactionManagement.isFactionOwner(player)) {
                SebaUtils.ChatUtils.saySimpleMessage(commandContext,
                        new LiteralText("You can't leave a faction that you own \nTo delete your faction, run /f remove"));
                return 0;
            }
            String factionName = FactionManagement.getFactionName(player);
            FactionManagement.removePlayerFromCurrentFaction(player);
            SebaUtils.ChatUtils.saySimpleMessage(commandContext,
                    new LiteralText("You've successfully left the faction named " + factionName));
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        return Command.SINGLE_SUCCESS;
    }

}
