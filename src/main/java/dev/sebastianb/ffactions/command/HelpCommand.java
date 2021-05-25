package dev.sebastianb.ffactions.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import dev.sebastianb.ffactions.util.SebaUtils;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TextColor;
import net.minecraft.text.TranslatableText;

import java.util.List;

public class HelpCommand implements ICommand {

    @Override
    public String commandName() {
        return "help";
    }

    @Override
    public TranslatableText commandInfo() {
        return new TranslatableText("ffactions.command.info.help");
    }

    @Override
    public TranslatableText commandTooltip() {
        return new TranslatableText("ffactions.command.tooltip.help");
    }

    @Override
    public LiteralArgumentBuilder<ServerCommandSource> registerNode(CommandDispatcher<ServerCommandSource> dispatcher) {
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

        SebaUtils.sayEmptyMessage(context);

        SebaUtils.saySimpleMessage(context,
                new TranslatableText( "ffactions.command.help_title")
                        .styled(style -> style.withBold(true).withColor(TextColor.fromRgb(16755200))));

        List<ICommand> commandList = listOfList.get(pageNum);

        for (ICommand command : commandList) { // 7
            SebaUtils.saySimpleMessage(context, new LiteralText(command.commandName()));
        }

        int diff = maxPageSize - commandList.size();

        for (int x = 0; x < diff; x++) {
            SebaUtils.sayEmptyMessage(context);
        }

        SebaUtils.saySimpleMessage(context, new TranslatableText("ffactions.command.help_page", pageNum + 1, listOfList.size())
                        .styled(style -> style.withBold(true).withColor(TextColor.fromRgb(16755200))));

        return Command.SINGLE_SUCCESS;
    }

}
