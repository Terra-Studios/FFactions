package dev.sebastianb.ffactions.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    // THIS METHOD IS ONLY CALLED IF YOU'VE SLEPT IN A BED!!!
    // I need to find the method to control respawn w/o bed
    @Inject(method = "findRespawnPosition", at = @At("HEAD"), cancellable = true)
    private static void respawnPosition(ServerWorld world, BlockPos pos, float f, boolean bl, boolean bl2, CallbackInfoReturnable<Optional<Vec3d>> cir) {
        // cir.setReturnValue(Optional.of(new Vec3d(0,100,0)));
    }


}
