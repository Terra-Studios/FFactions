package dev.sebastianb.ffactions.util;

import com.google.common.collect.Lists;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class SebaUtils {

    public static class ChatUtils {

        public static void sayEmptyMessage(CommandContext<ServerCommandSource> context) {
            context.getSource().sendFeedback(new LiteralText(""), false);
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

    }

    public static BlockPos VectorToBlockPos(Vec3d vec3d) {
        return new BlockPos(vec3d.getX(), vec3d.getY(), vec3d.getZ());
    }

    public static <T>List<List<T>> splitArrayIntoParts(List<T> list, int pageSize) {
        return Lists.partition(list, pageSize);
    }

}
