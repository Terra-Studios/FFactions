package dev.sebastianb.ffactions.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import dev.sebastianb.ffactions.util.SebaUtils;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TextColor;
import net.minecraft.text.TranslatableText;

import java.util.ArrayList;

public class HelpCommand implements ICommand {

    @Override
    public String commandName() {
        return "help";
    }

//    public void register() {
//        CommandRegistrationCallback.EVENT.register((commandDispatcher, dedicated) -> {
//            commandDispatcher.register(
//                    CommandManager.literal("faction")
//                        .requires(serverCommandSource -> serverCommandSource.hasPermissionLevel(2))
//                        .executes(HelpCommand::test)
//                        .then(CommandManager.literal("help")
//                            .executes(HelpCommand::test)
//                            .then(CommandManager.argument("pageNumber", IntegerArgumentType.integer())
//                            .executes(HelpCommand::help)))
//
//            );
//        });
//    }

    @Override
    public LiteralCommandNode registerNode(CommandDispatcher<ServerCommandSource> dispatcher) {
        return dispatcher.register(CommandManager.literal("help")
                    .executes(HelpCommand::test)
                    .then(CommandManager.argument("pageNumber", IntegerArgumentType.integer())
                    .executes(HelpCommand::help)));
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
        int tempMaxValue = 69; // TODO: Replace this value

        SebaUtils.sayEmptyMessage(context);

        SebaUtils.saySimpleMessage(context,
                new TranslatableText( "ffactions.command.help_title")
                        .styled(style -> style.withBold(true).withColor(TextColor.fromRgb(16755200))));

        // TODO: For loop iterating through all the translatable text on that specific page
        for (int x = 1; x <= 7; x++) {
            SebaUtils.saySimpleMessage(context, new LiteralText("TESTING! " + x));
        }


        SebaUtils.saySimpleMessage(context, new TranslatableText("ffactions.command.help_page", pageNum, tempMaxValue)
                        .styled(style -> style.withBold(true).withColor(TextColor.fromRgb(16755200))));


        return Command.SINGLE_SUCCESS;
    }

}
