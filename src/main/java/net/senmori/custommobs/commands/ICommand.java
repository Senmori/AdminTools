package net.senmori.custommobs.commands;

import com.mojang.brigadier.CommandDispatcher;

public interface ICommand {

    void register(CommandDispatcher dispatcher);
}
