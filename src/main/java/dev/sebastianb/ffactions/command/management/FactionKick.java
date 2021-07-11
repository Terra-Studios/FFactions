package dev.sebastianb.ffactions.command.management;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.sebastianb.ffactions.command.ICommand;
import net.minecraft.server.command.ServerCommandSource;

// TODO: Implement this
public class FactionKick implements ICommand {
    @Override
    public String commandName() {
        return "kick";
    }

    @Override
    public LiteralArgumentBuilder<ServerCommandSource> registerNode() {
        return null;
    }
}
