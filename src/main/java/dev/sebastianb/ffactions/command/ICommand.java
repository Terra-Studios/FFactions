package dev.sebastianb.ffactions.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

public interface ICommand {

    LiteralArgumentBuilder<ServerCommandSource> registerNode(CommandDispatcher<ServerCommandSource> dispatcher);

    String commandName();

    /**
     * Short summary about what the command does
     */
    default String commandInfo() {
        return "ffactions.command.info." + commandName();
    }

    /**
     * Tooltip information on hover for command args
     */
    default String commandTooltip() {
        return "ffactions.command.tooltip." + commandName();
    }

}
