package dev.sebastianb.ffactions.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.sebastianb.ffactions.util.SebaUtils;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ChunkSerializer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.WorldChunk;

public class ClaimCommand implements ICommand {

    @Override
    public String commandName() {
        return "claim";
    }

    @Override
    public LiteralArgumentBuilder<ServerCommandSource> registerNode() {
        return CommandManager.literal(commandName())
                .executes(ClaimCommand::claimChunk);
    }



    private static int claimChunk(CommandContext<ServerCommandSource> context) {
        try {

            ServerPlayerEntity player = context.getSource().getPlayer();
            ServerWorld world = player.getServerWorld();
            Chunk chunk = world.getChunk(player.getBlockPos());

            ChunkPos chunkPos = SebaUtils.WorldUtils.getChunkPosFromPlayer(player);

            SebaUtils.ChatUtils.sayEmptyMessage(context);

            SebaUtils.ChatUtils.saySimpleMessage(context,
                    new LiteralText("RegX" + chunkPos.getRegionX() + " RegZ" + chunkPos.getRegionZ() + " | "
                    + "RelX" + chunkPos.getRegionRelativeX() + " RelZ" + chunkPos.getRegionRelativeZ())
            );

            SebaUtils.ChatUtils.saySimpleMessage(context,
                    new LiteralText("TotalX" + chunkPos.x + " TotalZ" + chunkPos.z)

            );



        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }

        return Command.SINGLE_SUCCESS;
    }

}
