package dev.sebastianb.ffactions.mixin;

import net.minecraft.server.network.ServerPlayerInteractionManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerInteractionManager.class)
public class MixinServerPlayerInteraction {

    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    private void cancelBlockBreak(CallbackInfo ci) {
        ci.cancel();
    }

}
