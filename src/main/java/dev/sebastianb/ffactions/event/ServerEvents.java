package dev.sebastianb.ffactions.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.MinecraftServer;

public final class ServerEvents {

    /**
     * Called when a Minecraft server has started and is about to tick for the first time.
     *
     * <p>At this stage, all worlds are live.
     */
    public static final Event<ServerStarted> SERVER_STARTED = EventFactory.createArrayBacked(ServerStarted.class, (callbacks) -> (server) -> {
        for (ServerStarted callback : callbacks) {
            callback.onServerStarted(server);
        }
    });

    @FunctionalInterface
    public interface ServerStarted {
        void onServerStarted(MinecraftServer server);
    }

}
