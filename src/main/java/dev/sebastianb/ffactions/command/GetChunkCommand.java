package dev.sebastianb.ffactions.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.sebastianb.ffactions.util.SebaUtils;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;

public class GetChunkCommand {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((commandDispatcher, dedicated) -> {
            commandDispatcher.register(
                    CommandManager.literal("getchunk")
                    .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                    .executes(GetChunkCommand::getSinglePlayerChunk)
                    .then(CommandManager.argument("player", EntityArgumentType.player())
                    .executes(GetChunkCommand::getChunk))
            );
        });
    }

    private static int getChunk(CommandContext<ServerCommandSource> context) {
        final int[] status = {0}; // 0 == fail
        context.getSource().getMinecraftServer().execute(() -> {
            try {
                ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
                ServerWorld world = player.getServerWorld();
                Chunk chunk = world.getChunk(player.getBlockPos());
                ChunkPos chunkPos = chunk.getPos();
                SebaUtils.saySimpleMessage(context, String.format("%s's chunk position is X: %s and Z: %s", player.getName().getString(), chunkPos.x, chunkPos.z));
                status[0] = Command.SINGLE_SUCCESS;
            } catch (CommandSyntaxException e) {

                status[0] = 0;

            }
        });
        return status[0];
    }

    private static int getSinglePlayerChunk(CommandContext<ServerCommandSource> context) {
        // pretty sure this should run if no player in arguments
        try {
            System.out.println("h");
            ServerPlayerEntity player = context.getSource().getPlayer();
            ServerWorld world = player.getServerWorld();
            Chunk chunk = world.getChunk(player.getBlockPos());
            ChunkPos chunkPos = chunk.getPos();
            SebaUtils.saySimpleMessage(context, String.format("Your chunk position is X: %s and Z: %s", chunkPos.x, chunkPos.z));
        } catch (CommandSyntaxException commandSyntaxException) {
            commandSyntaxException.printStackTrace();
            return 0;
        }
        return Command.SINGLE_SUCCESS;

    }



}
