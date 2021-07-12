package dev.sebastianb.ffactions.command.management;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.sebastianb.ffactions.command.ICommand;
import dev.sebastianb.ffactions.util.SebaUtils;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.GameProfileArgumentType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.dedicated.command.BanCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;

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
            // TODO: Do check for if player running has permission

            // prevent @a operator. Really hacky way but it works
            Collection<GameProfile> profiles = GameProfileArgumentType.getProfileArgument(commandContext, "player");
            if (profiles.size() > 1) {
                SebaUtils.ChatUtils.saySimpleMessage(commandContext, new LiteralText("You can't select multiple people!")); // Replace with translatable
                return 0;
            }
            for (GameProfile profile : profiles) {
                System.out.println(profile);
            }


        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }

        System.out.println();

        return Command.SINGLE_SUCCESS;
    }

}
