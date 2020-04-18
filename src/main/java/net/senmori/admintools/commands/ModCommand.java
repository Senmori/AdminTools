package net.senmori.admintools.commands;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class ModCommand
{
    public ModCommand() {

    }

    public ModCommand(PacketBuffer buffer) {

    }

    public abstract void handle(Supplier<NetworkEvent.Context> context);

    public abstract void toBytes(PacketBuffer buffer);
}
