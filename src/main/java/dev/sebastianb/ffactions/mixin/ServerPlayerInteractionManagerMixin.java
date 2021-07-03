package dev.sebastianb.ffactions.mixin;

import com.mojang.brigadier.Command;
import dev.sebastianb.ffactions.claim.ClaimUtils;
import dev.sebastianb.ffactions.claim.FactionChunk;
import dev.sebastianb.ffactions.util.SebaUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.ButtonClickC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.BlockEvent;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {


    @Shadow
    ServerWorld world;

    @Shadow
    ServerPlayerEntity player;

    // TODO: replace both methods with custom callback
    // https://fabricmc.net/wiki/tutorial:events

    // cancels block place
    @Inject(method = "interactBlock", at = @At("HEAD"), cancellable = true)
    private void cancelBlockPlace(ServerPlayerEntity player, World world, ItemStack stack, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {

        // cir.setReturnValue(ActionResult.PASS);

    }

    @Inject(method = "tryBreakBlock", at = @At("HEAD"), cancellable = true)
    private void cancelBlockBreak(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {


        FactionChunk currentChunk = new FactionChunk(new ChunkPos(pos));
        for (FactionChunk facChunk : ClaimUtils.claimsList) {
            if (facChunk.getChunkPos().x == currentChunk.getChunkPos().x
                    && facChunk.getChunkPos().z == currentChunk.getChunkPos().z) {
                player.sendMessage(new LiteralText("Chunk already here!!"), false);
                cir.setReturnValue(false);
            }
        }
//        ChunkPos blocked = new ChunkPos(new BlockPos(0,0,0));
//        ChunkPos currentChunk = new ChunkPos(pos);
//
//        if (blocked.x == currentChunk.x && blocked.z == currentChunk.z) {
//            player.sendMessage(new LiteralText("Test!"), false);
//            cir.setReturnValue(false);
//        }
        // cir.setReturnValue(false);

    }

    // cancel any used items
    @Inject(method = "interactItem", at = @At("HEAD"), cancellable = true)
    private void cancelItemInteract(ServerPlayerEntity player, World world, ItemStack stack, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        // cir.setReturnValue(ActionResult.PASS);
    }

}
