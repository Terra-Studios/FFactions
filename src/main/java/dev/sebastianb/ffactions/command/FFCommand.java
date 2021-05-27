package dev.sebastianb.ffactions.command;


import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.MessageCommand;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;

public class FFCommand {

    private static ArrayList<ICommand> commands = new ArrayList<>();

    private static final String[] commandLiterals = new String[]{"faction", "fac", "f"};

    public static void register() {

        commands.add(new HelpCommand());
        commands.add(new GetChunkCommand());
        commands.add(new ClaimCommand());
        commands.add(new FactionCreate());

            CommandRegistrationCallback.EVENT.register((dispatcher, b) -> {
                for (ICommand command : commands) {
                    for (String literal : commandLiterals) {
                        LiteralArgumentBuilder<ServerCommandSource> builder =
                                CommandManager.literal(literal)
                                        .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                                        .then(command.registerNode());
                        dispatcher.register(builder);
                    }
                }
            });

    }

    protected static ArrayList<ICommand> getCommands() {
        return commands;
    }

}
