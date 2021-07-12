package dev.sebastianb.ffactions.command.management.status;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;

import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

// A thread to check if a player is currently waiting on a faction invitation
public class FactionPlayerStatus implements Runnable {

    public static volatile ConcurrentHashMap<UUID, UUID> invitedPlayerAndFactionUUID = new ConcurrentHashMap<>(); // holds player UUID and faction UUID

    private final ScheduledExecutorService executor;

    public static void runThread(ServerPlayerEntity invitedPlayer, UUID factionUUID, int maxSecondsAlive) {
        ThreadFactory namedThreadFactory =
                new ThreadFactoryBuilder().setNameFormat(invitedPlayer.getUuid() + "-%d").build();
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(3, namedThreadFactory); // TODO: Make a config option for amount of threads

        executor.scheduleAtFixedRate(new FactionPlayerStatus(invitedPlayer, factionUUID, maxSecondsAlive, executor), 0, 1, TimeUnit.SECONDS);
    }


    private final AtomicInteger secondsAlive;
    private final ServerPlayerEntity invitedPlayer;
    private final UUID invitedPlayerUUID;
    private final UUID factionUUID;

    public FactionPlayerStatus(ServerPlayerEntity invitedPlayer, UUID factionUUID, int maxSecondsAlive, ScheduledExecutorService executor) {
        this.invitedPlayer = invitedPlayer;
        invitedPlayerUUID = invitedPlayer.getUuid();
        this.factionUUID = factionUUID;
        this.secondsAlive = new AtomicInteger(maxSecondsAlive);
        this.executor = executor;
        invitedPlayerAndFactionUUID.put(invitedPlayerUUID, factionUUID);
    }

    @Override
    public synchronized void run() {
        if (!invitedPlayerAndFactionUUID.containsKey(invitedPlayer.getUuid())) {
            executor.shutdown();
        }
        invitedPlayer.sendMessage(new LiteralText(secondsAlive.toString()), false); // TODO: replace with translatable
        if (secondsAlive.decrementAndGet() < 0) {
            invitedPlayer.sendMessage(new LiteralText("Faction invite expired"), false); // TODO: replace with translatable
            invitedPlayerAndFactionUUID.remove(invitedPlayerUUID);
            executor.shutdown();
        }

    }
}
