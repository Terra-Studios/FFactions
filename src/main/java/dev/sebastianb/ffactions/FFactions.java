package dev.sebastianb.ffactions;

import dev.sebastianb.ffactions.util.SebaUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.registry.BlockConstructedCallback;
import net.fabricmc.fabric.mixin.event.lifecycle.ServerWorldMixin;
import net.minecraft.block.Blocks;
import net.minecraft.server.command.SetWorldSpawnCommand;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public class FFactions implements ModInitializer {
    @Override
    public void onInitialize() {
        System.out.println("loaded mod factions");

        PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, entity) -> {
            return world.setBlockState(pos, state);
        });

    }
}
