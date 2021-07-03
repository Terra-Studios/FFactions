package dev.sebastianb.ffactions;

import dev.sebastianb.ffactions.command.FFCommand;
import dev.sebastianb.ffactions.command.GetChunkCommand;
import dev.sebastianb.ffactions.database.DatabaseInitializer;
import dev.sebastianb.ffactions.event.ServerEvents;
import net.fabricmc.api.ModInitializer;

import java.util.logging.Logger;

public class FFactions implements ModInitializer {

    /*  ᗜˬᗜ  | fumo */
    public static String MOD_ID = "ffactions";
    public static Logger LOGGER = Logger.getLogger(MOD_ID);

    @Override
    public void onInitialize() {

        FFCommand.register();
        ServerEvents.SERVER_STARTED.register(server -> {
            DatabaseInitializer.createTable();
        });
    }
}
