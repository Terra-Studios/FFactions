package dev.sebastianb.ffactions.command;


import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.sebastianb.ffactions.command.management.*;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;

public class FFCommand {


    // TODO: My system feels too static. It'd be cool if you're able to register multiple commands in one class

    private static final ArrayList<ICommand> commands = new ArrayList<>();

    private static final String[] commandLiterals = new String[]{"faction", "fac", "f"};

    public static void register() {

        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            commands.add(new DebugCommand());
        }

        commands.add(new HelpCommand());
        commands.add(new GetChunkCommand());
        commands.add(new FactionCreate());
        commands.add(new FactionDelete());
        commands.add(new FactionKick());
        commands.add(new ClaimCommand());
        commands.add(new FactionInvite());
        commands.add(new FactionAccept());
        commands.add(new FactionLeave());

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
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
