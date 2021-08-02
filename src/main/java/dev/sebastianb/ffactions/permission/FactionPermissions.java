package dev.sebastianb.ffactions.permission;

import dev.sebastianb.ffactions.admin.FactionManagement;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public interface FactionPermissions {

    // All permission strings are held in this class
    String FACTION_INVITE_PERMISSION = "ffaction.player.invite";

    /**
     *
     * @param player the specified player we're checking
     * @param permission the permission node we're checking
     * @param ranks ArrayList of all ranks that "should" have permission
     * @return if the player in the faction
     */
    static boolean hasPermission(ServerPlayerEntity player, String permission, ArrayList<FactionMemberRanks> ranks) {
        UUID playerFactionUUID = FactionManagement.getFactionUUID(player);
        FactionMemberRanks playerRank = null; // get the rank a player is "in the faction"
        // TODO: Loop over permissions held by the database for the specific faction
        return true;
    }

    // maybe we want to be dumb and have multiple inferred args
    static boolean hasPermission(ServerPlayerEntity player, String permission, FactionMemberRanks... ranks) {
        return hasPermission(player, permission, new ArrayList<>(Arrays.asList(ranks)));
    }

}