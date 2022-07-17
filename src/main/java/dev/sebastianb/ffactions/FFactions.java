package dev.sebastianb.ffactions;

import dev.sebastianb.ffactions.command.FFCommand;
import dev.sebastianb.ffactions.compact.ModCompact;
import dev.sebastianb.ffactions.database.DatabaseInitializer;
import dev.sebastianb.ffactions.event.ServerEvents;
import net.fabricmc.api.ModInitializer;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
import java.util.logging.Logger;

public class FFactions implements ModInitializer {

    /*  ᗜˬᗜ  | fumo */
    public static String MOD_ID = "ffactions";
    public static Logger LOGGER = Logger.getLogger(MOD_ID);

    @Override
    public void onInitialize() {

        ModCompact.register();
        FFCommand.register();
        // TODO: may want to start this on a new thread tbh. Also move the callback to
        ServerEvents.SERVER_STARTED.register(server -> {
            // create table with faction data
            DatabaseInitializer.executeSQL(
                            "CREATE TABLE IF NOT EXISTS faction (" +
                            "fac_uuid UUID, " + // the UUID of each faction stored
                            "fac_owner_uuid UUID, " + // the UUID for the faction owner
                            "created DATETIME, " + // date for faction creation (YYYY-MM-DD hh:mm:ss[.fraction]) < lmao this won't work new years 10,000AD
                            "fac_name TINYTEXT, " +
                            "fac_tag TINYTEXT " +
                            ");"
            );

            // database with table of where each member should be in a faction
            DatabaseInitializer.executeSQL(
                            "CREATE TABLE IF NOT EXISTS faction_members (" +
                            "fac_uuid UUID, " + // the UUID of the faction a player should belong to
                            "player_uuid UUID, " + // the UUID of the player inside a faction
                            "created DATETIME, " + // date for player faction join (YYYY-MM-DD hh:mm:ss[.fraction]) < lmao this won't work new years 10,000AD
                            "rank TINYTEXT" + // the specific permissions group a member may belong to
                            ");"
            );

        });

    }
}
