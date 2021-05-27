package dev.sebastianb.ffactions.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import dev.sebastianb.ffactions.util.SebaUtils;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TextColor;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public interface ICommand {

    String commandName();

    LiteralArgumentBuilder<ServerCommandSource> registerNode();

    /**
     * Short summary about what the command does
     */
    default String commandInfo() {
        return "ffactions.command.info." + commandName();
    }

    /**
     * Tooltip information on hover for command args
     */
    default String commandArgs() {
        return "ffactions.command.args." + commandName();
    }

    /**
     * Tooltip information on hover for command args
     */
    default String commandTooltip() {
        return commandArgs();
    }

    /**
     * Implementation for specific commands to give feedback about usage
     */
    static int argumentFeedback(CommandContext<ServerCommandSource> context, ICommand command) {

        SebaUtils.ChatUtils.saySimpleMessage(context,
                new TranslatableText("ffactions.command.usage",
                        new LiteralText(new TranslatableText(command.commandArgs(), "/f ").getString())
                                .styled(style -> style.withColor(TextColor.fromRgb(SebaUtils.Colors.BABY_LIGHT_PURPLE))))
                        .styled(style -> style.withColor(TextColor.fromRgb(SebaUtils.Colors.RED))
        ));

        return Command.SINGLE_SUCCESS;
    }

}
