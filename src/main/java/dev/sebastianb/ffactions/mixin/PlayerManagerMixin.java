package dev.sebastianb.ffactions.mixin;

import dev.sebastianb.ffactions.admin.FactionManagement;
import net.minecraft.network.MessageType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
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
        if (!type.equals(MessageType.CHAT)) {
            return; // returns to prevent game join coloring and such from being messed with
        }
        String tag = new TranslatableText("ffactions.chat.tag", FactionManagement.getFactionTag(sender)).getString();
        if (tag.equals(new TranslatableText("ffactions.chat.tag", "").getString())) {
            tag = ""; // does this to get rid of translatable stuff at beginning if no tag
        }
        Text newText = Text.of(tag + message.getString());

        // override of vanilla code here
        this.server.sendSystemMessage(newText, sender);
        for (ServerPlayerEntity serverPlayerEntity : this.players) {
            serverPlayerEntity.sendMessage(newText, type, sender);
        }
        ci.cancel();
    }

}
