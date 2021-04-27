package dev.sebastianb.ffactions.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class SebaUtils {
    public static BlockPos VectorToBlockPos(Vec3d vec3d) {
        return new BlockPos(vec3d.getX(), vec3d.getY(), vec3d.getZ());
    }
}
