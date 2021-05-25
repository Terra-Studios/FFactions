package dev.sebastianb.ffactions.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.server.command.ServerCommandSource;

public interface ICommand {


    LiteralCommandNode<ServerCommandSource> registerNode(CommandDispatcher<ServerCommandSource> dispatcher);

    String commandName();


}
