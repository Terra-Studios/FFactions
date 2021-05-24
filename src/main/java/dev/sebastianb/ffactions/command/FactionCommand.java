package dev.sebastianb.ffactions.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import dev.sebastianb.ffactions.util.SebaUtils;
import fr.catcore.server.translations.api.text.LocalizableText;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TextColor;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.Registry;

public class FactionCommand {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((commandDispatcher, dedicated) -> {
            commandDispatcher.register(
                    CommandManager.literal("faction")
                        .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
                        .executes(FactionCommand::test)
                        .then(CommandManager.literal("help")
                            .executes(FactionCommand::test)
                            .then(CommandManager.argument("pageNumber", IntegerArgumentType.integer())
                            .executes(FactionCommand::help)))
            );
        });
    }

    // Help page with number!
    private static int help(CommandContext<ServerCommandSource> context) {
        int pageNum = IntegerArgumentType.getInteger(context, "pageNumber");

        return broadcastHelp(context, pageNum);
    }

    // first page of help
    private static int test(CommandContext<ServerCommandSource> context) {
        int pageNum = 1;

        return broadcastHelp(context, pageNum);
    }

    private static int broadcastHelp(CommandContext<ServerCommandSource> context, int pageNum) {
        SebaUtils.saySimpleMessage(context,
                new TranslatableText( "ffactions.command.help_title")
                        .styled(style -> style.withBold(true).withColor(TextColor.fromRgb(16755200))));
        SebaUtils.saySimpleMessage(context, new LiteralText("The page number is " + pageNum));


        return Command.SINGLE_SUCCESS;
    }

}
