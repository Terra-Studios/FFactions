package dev.sebastianb.ffactions.permission;

import net.minecraft.server.network.ServerPlayerEntity;

public interface FactionPermissions {

    static boolean hasPermission(ServerPlayerEntity player, FactionMemberRanks rank) {
        return true;
    }

}
