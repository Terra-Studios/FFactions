package dev.sebastianb.ffactions;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.mixin.event.lifecycle.ServerWorldMixin;
import net.minecraft.server.command.SetWorldSpawnCommand;
import net.minecraft.server.network.ServerPlayerEntity;

public class FFactions implements ModInitializer {
    @Override
    public void onInitialize() {
        System.out.println("loaded mod factions");
    }
}
