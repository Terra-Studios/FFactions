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
import net.minecraft.text.Text;

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
        if (FactionManagement.isInFaction(commandContext.getSource().getPlayer()) && !FactionManagement.isFactionOwner(commandContext.getSource().getPlayer())) {
            SebaUtils.ChatUtils.saySimpleMessage(commandContext, Text.literal("You cannot delete since you are not the faction owner.")); // TODO: Replace with translatable
            return 0;
        }
        // check if a player already owns a faction
        if (FactionManagement.isFactionOwner(commandContext.getSource().getPlayer())) {
            FactionManagement.deleteFaction(commandContext.getSource().getPlayer());
            SebaUtils.ChatUtils.saySimpleMessage(commandContext, Text.literal("Faction deleted")); // TODO: Replace with translatable
            return Command.SINGLE_SUCCESS;
        }
        SebaUtils.ChatUtils.saySimpleMessage(commandContext, Text.literal("You cannot delete since you are not a member of any faction")); // TODO: Replace with translatable
        return Command.SINGLE_SUCCESS;
    }


}
