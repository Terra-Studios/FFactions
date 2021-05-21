package dev.sebastianb.ffactions;

import dev.sebastianb.ffactions.command.FFCommand;
import dev.sebastianb.ffactions.command.GetChunkCommand;
import net.fabricmc.api.ModInitializer;

public class FFactions implements ModInitializer {

    /*  ᗜˬᗜ  | fumo */

    @Override
    public void onInitialize() {

        FFCommand.register();


    }
}
