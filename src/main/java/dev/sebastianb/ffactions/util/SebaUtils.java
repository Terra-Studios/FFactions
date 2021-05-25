package dev.sebastianb.ffactions.util;

import com.google.common.collect.Lists;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class SebaUtils {

    public static BlockPos VectorToBlockPos(Vec3d vec3d) {
        return new BlockPos(vec3d.getX(), vec3d.getY(), vec3d.getZ());
    }

    public static void sayEmptyMessage(CommandContext<ServerCommandSource> context) {
        context.getSource().sendFeedback(new LiteralText(""), false);
    }

    public static void saySimpleMessage(CommandContext<ServerCommandSource> context, Text message) {
        saySimpleMessage(context,message, false);
    }

    public static void saySimpleMessage(CommandContext<ServerCommandSource> context, Text message, boolean broadcastToOps) {
        context.getSource().sendFeedback(message, broadcastToOps);
    }

//    /**
//     * Method to split a array of any specified type into parts
//     *
//     * @param arrayList Your generic array list
//     * @param maxPageSize Max size of the array held inside a array
//     * @return The arrays split into separate arrays
//     */
//    public static ArrayList<ArrayList<?>> splitIntoArrays(ArrayList<?> arrayList, int maxPageSize) {
//        ArrayList<ArrayList<?>> arrayOfArrays = new ArrayList<>();
//
//
//
//        return arrayOfArrays;
//    }

    public static <T>List<List<T>> splitArrayIntoParts(List<T> list, int pageSize) {
        return Lists.partition(list, pageSize);
    }

}
