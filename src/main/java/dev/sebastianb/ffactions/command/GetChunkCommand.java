package dev.sebastianb.ffactions.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.sebastianb.ffactions.util.SebaUtils;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.Chunk;

public class GetChunkCommand implements ICommand {

    @Override
    public String commandName() {
        return "getchunk";
    }

    @Override
    public LiteralArgumentBuilder<ServerCommandSource> registerNode() {
        return CommandManager.literal(commandName())
                .executes(GetChunkCommand::getSinglePlayerChunk)
                .then(CommandManager.argument("player", EntityArgumentType.player())
                .executes(GetChunkCommand::getChunk));
    }

    private static int getChunk(CommandContext<ServerCommandSource> context) {
        final int[] status = {0}; // 0 == fail
        context.getSource().getServer().execute(() -> {
            try {

                ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
                ChunkPos chunkPos = SebaUtils.WorldUtils.getChunkPosFromPlayer(player);

                SebaUtils.ChatUtils.saySimpleMessage(context, new LiteralText(String.format("%s's chunk position is X: %s and Z: %s", player.getName().getString(), chunkPos.x, chunkPos.z)));

                status[0] = Command.SINGLE_SUCCESS;

            } catch (CommandSyntaxException e) {

                status[0] = 0; // fail

            }
        });
        return status[0]; // returns result
    }

    private static int getSinglePlayerChunk(CommandContext<ServerCommandSource> context) {
        // pretty sure this should run if no player in arguments
        try {
            ServerPlayerEntity player = context.getSource().getPlayer();
            ServerWorld world = player.getServerWorld();
            Chunk chunk = world.getChunk(player.getBlockPos());
            ChunkPos chunkPos = chunk.getPos();
            SebaUtils.ChatUtils.saySimpleMessage(context, new LiteralText(String.format("Your chunk position is X: %s and Z: %s", chunkPos.x, chunkPos.z)));
        } catch (CommandSyntaxException commandSyntaxException) {
            commandSyntaxException.printStackTrace();
            return 0;
        }
        return Command.SINGLE_SUCCESS;

    }

}
