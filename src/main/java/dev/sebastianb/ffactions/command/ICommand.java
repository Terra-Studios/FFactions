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
    String commandInfo();

    /**
     * Tooltip information on hover for command args
     */
    String commandTooltip();



}
