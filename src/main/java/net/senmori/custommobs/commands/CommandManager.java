package net.senmori.custommobs.commands;

import com.mojang.brigadier.CommandDispatcher;

import java.util.Collection;
import java.util.HashSet;

public class CommandManager {
    public static Collection<ICommand>  COMMANDS = new HashSet<>();

    public static void register(ICommand command) {
        COMMANDS.add( command );
    }

    public static void registerCommands(CommandDispatcher dispatcher) {
        COMMANDS.forEach( cmd -> cmd.register( dispatcher ) );
    }
}
