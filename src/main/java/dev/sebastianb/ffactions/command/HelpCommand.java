package dev.sebastianb.ffactions.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import dev.sebastianb.ffactions.util.SebaUtils;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.*;

import java.util.List;

public class HelpCommand implements ICommand {

    @Override
    public String commandName() {
        return "help";
    }


    @Override
    public LiteralArgumentBuilder<ServerCommandSource> registerNode() {
        return CommandManager.literal(commandName())
                    .executes(HelpCommand::helpFirstPage)
                    .then(CommandManager.argument("pageNumber", IntegerArgumentType.integer())
                    .executes(HelpCommand::help));
    }

    private static final int maxPageSize = 7;
    private static final List<List<ICommand>> listOfList = SebaUtils.splitArrayIntoParts(FFCommand.getCommands(), maxPageSize);

    // Help page with number!
    private static int help(CommandContext<ServerCommandSource> context) {
        int pageNum = IntegerArgumentType.getInteger(context, "pageNumber");

        return broadcastHelp(context, pageNum);
    }

    // first page of help
    private static int helpFirstPage(CommandContext<ServerCommandSource> context) {
        int pageNum = 1;

        return broadcastHelp(context, pageNum);
    }

    // .withHoverEvent(HoverEvent.Action.SHOW_TEXT.buildHoverEvent(new LiteralText("SEX!")))

    // ^ that one is used for tooltips

    private static int broadcastHelp(CommandContext<ServerCommandSource> context, int pageNum) {

        pageNum = pageNum - 1; // normalize for array values
        List<ICommand> commandList = listOfList.get(pageNum);

        SebaUtils.ChatUtils.sayEmptyMessage(context);

        SebaUtils.ChatUtils.saySimpleMessage(context,
                Text.translatable( "ffactions.command.help_title")
                        .styled(style -> style.withBold(true).withColor(TextColor.fromRgb(SebaUtils.Colors.GOLD))));

        for (ICommand command : commandList) {
            SebaUtils.ChatUtils.saySimpleMessage(context,
                    Text.translatable(command.commandInfo(),
                            Text.literal("/f " + command.commandName())
                                .styled(style -> style.withColor(TextColor.fromRgb(SebaUtils.Colors.LIGHT_PASTEL_PURPLE))))
                    .styled(style -> style.withHoverEvent(HoverEvent.Action.SHOW_TEXT.buildHoverEvent(Text.translatable(command.commandTooltip(), "/faction ")))));
        }

        // Empty space after commands
        int diff = maxPageSize - commandList.size();
        for (int x = 0; x < diff; x++) {
            SebaUtils.ChatUtils.sayEmptyMessage(context);
        }

        SebaUtils.ChatUtils.saySimpleMessage(context, Text.translatable("ffactions.command.help_page", pageNum + 1, listOfList.size())
                        .styled(style -> style.withBold(true).withColor(TextColor.fromRgb(SebaUtils.Colors.GOLD))));

        return Command.SINGLE_SUCCESS;
    }

}
