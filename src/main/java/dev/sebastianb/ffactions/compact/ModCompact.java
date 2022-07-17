package dev.sebastianb.ffactions.compact;

import dev.sebastianb.ffactions.FFactions;
import dev.sebastianb.ffactions.admin.FactionManagement;
import eu.pb4.placeholders.api.PlaceholderResult;
import eu.pb4.placeholders.api.Placeholders;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModCompact {

    public static void register() {
        if (FabricLoader.getInstance().isModLoaded("placeholder-api")) {
            new PlaceholderAPI().init();
        }
    }

    public static class PlaceholderAPI {

        public void init() {
            registerPlaceholders();
        }

        private void registerPlaceholders() {
            // the specific tag of a faction
            Placeholders.register(new Identifier(FFactions.MOD_ID, "faction_tag"), (ctx, arg) -> {
                if (ctx.hasPlayer()) {
                    return PlaceholderResult.value(
                            Text.translatable("ffactions.chat.tag",
                            FactionManagement.getFactionTag(ctx.player().getUuid())).getString());
                } else {
                    return PlaceholderResult.invalid();
                }
            });
        }
    }

}
