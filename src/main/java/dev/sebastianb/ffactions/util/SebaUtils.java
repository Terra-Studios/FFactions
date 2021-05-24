package dev.sebastianb.ffactions.util;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class SebaUtils {

    public static BlockPos VectorToBlockPos(Vec3d vec3d) {
        return new BlockPos(vec3d.getX(), vec3d.getY(), vec3d.getZ());
    }

    public static void saySimpleMessage(CommandContext<ServerCommandSource> context, Text message) {
        saySimpleMessage(context,message, false);
    }

    public static void saySimpleMessage(CommandContext<ServerCommandSource> context, Text message, boolean broadcastToOps) {
        context.getSource().sendFeedback(message, broadcastToOps);
    }

}
