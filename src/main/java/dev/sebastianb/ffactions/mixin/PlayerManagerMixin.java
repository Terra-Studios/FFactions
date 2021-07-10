package dev.sebastianb.ffactions.mixin;

import com.google.common.collect.Lists;
import dev.sebastianb.ffactions.claim.FactionManagement;
import net.minecraft.network.MessageType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {

    @Final @Shadow private MinecraftServer server;
    @Final @Shadow private List<ServerPlayerEntity> players;

    // METHOD CALLED ON EVERY PLAYER RESPAWN
    @Inject(method = "respawnPlayer", at = @At("HEAD"), cancellable = true)
    private void respawnPlayer(ServerPlayerEntity player, boolean alive, CallbackInfoReturnable<ServerPlayerEntity> cir) {
        player.setSpawnPoint(player.getSpawnPointDimension(), new BlockPos(0,100,0), 20f, true, true);
    }

    // method to display faction tags. Overriden vanilla method but it works. NEED to look into setting this the lowest prio so it doesn't mess with other mods
    @Inject(method = "broadcastChatMessage", at = @At("HEAD"), cancellable = true)
    private void onBroadcastChat(Text message, MessageType type, UUID sender, CallbackInfo ci) {
        Text newText = Text.of(FactionManagement.getFactionTag(sender) + message.getString());

        this.server.sendSystemMessage(newText, sender);
        Iterator<ServerPlayerEntity> var4 = this.players.iterator();

        while(var4.hasNext()) {
            ServerPlayerEntity serverPlayerEntity = var4.next();
            serverPlayerEntity.sendMessage(newText, type, sender);
        }
        ci.cancel();
    }

}
