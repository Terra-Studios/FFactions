package dev.sebastianb.ffactions.command;


import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.MessageCommand;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;

public class FFCommand {

    private static ArrayList<ICommand> commands = new ArrayList<>();


    public static void register() {

        commands.add(new GetChunkCommand());
        commands.add(new HelpCommand());

        for (ICommand command : commands) {
            CommandRegistrationCallback.EVENT.register((dispatcher, b) -> {

                LiteralCommandNode<ServerCommandSource> factionNode = dispatcher.register(
                        CommandManager.literal("faction")
                                .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                                .then(command.registerNode(dispatcher))

                );
                dispatcher.register(CommandManager.literal("f").redirect(factionNode));
            });
        }

    }

    protected static ArrayList<ICommand> getCommands() {
        return commands;
    }

}
