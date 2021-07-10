package dev.sebastianb.ffactions.claim;

import dev.sebastianb.ffactions.FFactions;
import dev.sebastianb.ffactions.database.DatabaseInitializer;
import dev.sebastianb.ffactions.util.SebaUtils;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

import java.sql.Clob;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Different implementations for any type of storage system for all faction related actions
 */
public class FactionManagement {

    // TODO: replace all LOGGER variables with a translatable inside

    private static final StorageSystem storageSystem = StorageSystem.H2; // TODO: make a actual config system

    public static void createFaction(ServerPlayerEntity player, String factionName, String factionTag) {
        switch (storageSystem) {
            case H2:
                UUID factionUUID = UUID.randomUUID(); // how funny would it be if there was two similar UUID's
                LocalDateTime timeNow = LocalDateTime.now();
                // create the faction into database
                DatabaseInitializer.executeSQL(
                        "INSERT INTO faction (fac_uuid, fac_owner_uuid, created, fac_name, fac_tag) " +
                                "values ('" + factionUUID + "', '" + player.getUuid() + "', '" + timeNow + "', '" + factionName + "', '" + factionTag + "');"
                        // creates a random faction UUID, gets Player UUID, and inserts the time "now". Also displays name and tag
                );
                // adds the correlating faction the "player" is apart of to a list in the database
                DatabaseInitializer.executeSQL(
                        "INSERT INTO faction_members (fac_uuid, player_uuid, created, rank) " +
                            "values('" + factionUUID + "', '" + player.getUuid() + "', '" + timeNow + "', '" + FactionMembers.OWNER + "');");
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
                DatabaseInitializer.executeSQL("DELETE FROM faction WHERE fac_owner_uuid='" + playerEntity.getUuid() + "';");
                DatabaseInitializer.executeSQL("DELETE FROM faction_members WHERE player_uuid='" + playerEntity.getUuid() + "';"); // TODO:
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

    /**
     *
     * @return the faction tag of a specified player
     */
    public static String getFactionTag(UUID uuid) {
        switch (storageSystem) {
            case H2:
                // check if the player is not in a faction. Will return a empty String
                if (!DatabaseInitializer.hasMatching("select * from faction_members", "player_uuid", uuid)) {
                    FFactions.LOGGER.info("TEST MESSAGE!");
                    break;
                }
                // get the faction UUID of a player THEN gets the tag from faction UUID
                UUID factionUUID = DatabaseInitializer.getObject("select * from faction_members", "player_uuid", "fac_uuid", uuid);
                String tag = SebaUtils.clobToString(DatabaseInitializer.getObject("select * from faction", "fac_uuid", "fac_tag", factionUUID));

                return new TranslatableText("ffactions.chat.tag", tag).getString();
            case NOSQL:
                FFactions.LOGGER.info("I haven't made a implementation yet. What the fuck did you do to my poor mod");
                break;
            default:
                FFactions.LOGGER.info("No storage system selected");
        }
        return "";
    }

}
