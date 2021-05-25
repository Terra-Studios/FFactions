package dev.sebastianb.ffactions.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

public interface ICommand {

    LiteralCommandNode<ServerCommandSource> registerNode(CommandDispatcher<ServerCommandSource> dispatcher);

    String commandName();

    /**
     * Short summary about what the command does
     */
    TranslatableText commandInfo();

    /**
     * Tooltip information on hover for command args
     */
    TranslatableText commandTooltip();



}
