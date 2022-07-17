package dev.sebastianb.ffactions.util;

import com.google.common.collect.Lists;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.Chunk;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.List;

public class SebaUtils {

    public static class ChatUtils {

        public static void sayEmptyMessage(CommandContext<ServerCommandSource> context) {
            context.getSource().sendFeedback(Text.literal(""), false);
        }

        public static void saySimpleMessage(CommandContext<ServerCommandSource> context, Text message) {
            saySimpleMessage(context,message, false);
        }

        public static void saySimpleMessage(CommandContext<ServerCommandSource> context, Text message, boolean broadcastToOps) {
            context.getSource().sendFeedback(message, broadcastToOps);
        }

    }

    // https://www.rapidtables.com/convert/number/hex-to-decimal.html
    public static class Colors {

        public static final int LIGHT_PASTEL_PURPLE = 13353955; // # CBC3E3
        public static final int GOLD                = 16755200; // # FFAA00
        public static final int RED                 = 16711680; // # FF0000
        public static final int BABY_LIGHT_PURPLE   = 15459327; // # EBE3FF

    }

    public static class WorldUtils {

        public static ChunkPos getChunkPosFromPlayer(ServerPlayerEntity player) {
            ServerWorld world = player.getWorld();
            Chunk chunk = world.getChunk(player.getBlockPos());
            return chunk.getPos();
        }

    }

    public static BlockPos VectorToBlockPos(Vec3d vec3d) {
        return new BlockPos(vec3d.getX(), vec3d.getY(), vec3d.getZ());
    }

    public static <T>List<List<T>> splitArrayIntoParts(List<T> list, int pageSize) {
        return Lists.partition(list, pageSize);
    }

    // https://stackoverflow.com/questions/2169732/most-efficient-solution-for-reading-clob-to-string-and-string-to-clob-in-java
    public static String clobToString(Clob clobObject) {
        try {
            InputStream in = clobObject.getAsciiStream();
            StringWriter w = new StringWriter();
            IOUtils.copy(in, w);
            return w.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
