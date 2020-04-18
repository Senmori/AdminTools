package net.senmori.admintools.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.senmori.admintools.AdminTools;

public class ModCommands
{
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralCommandNode<CommandSource> cmgui = dispatcher.register(
                Commands.literal(AdminTools.MODID)
                        .then(CommandGui.register(dispatcher))
        );
        dispatcher.register(Commands.literal("at").redirect(cmgui));
    }
}
