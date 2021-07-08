package dev.sebastianb.ffactions.claim;

import dev.sebastianb.ffactions.FFactions;
import dev.sebastianb.ffactions.database.DatabaseInitializer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Different implementations for any type of storage system for all faction related actions
 */
public class FactionManagement {

    private static final StorageSystem storageSystem = StorageSystem.H2; // TODO: make a actual config system

    public static void createFaction(ServerPlayerEntity player, String factionName, String factionTag) {
        switch (storageSystem) {
            case H2:
                DatabaseInitializer.executeSQL(
                        "INSERT INTO faction (fac_uuid, fac_owner_uuid, created, fac_name, fac_tag) " +
                                "values ('" + UUID.randomUUID() + "', '" + player.getUuid() + "', '" + LocalDateTime.now() + "', '" + factionName + "', '" + factionTag + "');"
                        // creates a random faction UUID, gets Player UUID, and inserts the time "now"
                );
                break;
            case NOSQL:
                FFactions.LOGGER.info("I haven't made a implementation yet. What the fuck did you do to my poor mod");
                break;
            default:
                FFactions.LOGGER.info("No storage system selected");

        }
    }

    public static void deleteFaction(ServerPlayerEntity playerEntity) {
        switch (storageSystem) {
            case H2:
                DatabaseInitializer.executeSQL("DELETE FROM faction WHERE fac_owner_uuid='" + playerEntity.getUuid() + "';"); // TODO: make a way to delete a existing faction entry off table
                break;

            case NOSQL:
                FFactions.LOGGER.info("I haven't made a implementation yet. What the fuck did you do to my poor mod");
                break;

            default:
                FFactions.LOGGER.info("No storage system selected");
        }
    }

    public static boolean isFactionOwner(ServerPlayerEntity playerEntity) {
        switch (storageSystem) {
            case H2:
                return DatabaseInitializer.hasMatching("select * from faction", "fac_owner_uuid", playerEntity.getUuid());
            case NOSQL:
                FFactions.LOGGER.info("I haven't made a implementation yet. What the fuck did you do to my poor mod");
                break;
            default:
                FFactions.LOGGER.info("No storage system selected");
                return false;
        }
        FFactions.LOGGER.info("PLEASE REPORT ME THIS SHOULD NOT HAPPEN. WHAT DID YOU DO!\n-SebaSphere");
        return false; // will return false if something wacky happened for some reason. This shouldn't happen
    }



}
