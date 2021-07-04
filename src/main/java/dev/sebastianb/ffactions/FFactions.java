package dev.sebastianb.ffactions;

import dev.sebastianb.ffactions.command.FFCommand;
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

        FFCommand.register();
        // TODO: may want to start this on a new thread tbh
        ServerEvents.SERVER_STARTED.register(server -> {
            // create table with faction data
            DatabaseInitializer.executeSQL(
                            "CREATE TABLE IF NOT EXISTS faction (" +
                            "fac_uuid UUID, " + // the UUID of each faction stored
                            "fac_owner_uuid UUID, " + // the UUID for the faction owner
                            "created DATETIME " + // date for faction creation (YYYY-MM-DD hh:mm:ss[.fraction]) < lmao this won't work new years 10,000AD
                            ");"
            );
            // insert data into faction table
//            UUID uuid = UUID.randomUUID();
//            DatabaseInitializer.executeSQL(
//                            "INSERT INTO faction (fac_uuid, fac_owner_uuid, created) " +
//                            "values ('" + UUID.randomUUID() + "'," + uuid + " '" + LocalDateTime.now() + "');" // creates a random faction UUID and inserts the time "now"
//            );

        });

    }
}
