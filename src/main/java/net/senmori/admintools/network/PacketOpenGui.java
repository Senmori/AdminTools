package net.senmori.admintools.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.senmori.admintools.client.SimpleScreen;
import net.senmori.admintools.commands.ModCommand;

import java.util.function.Supplier;

public class PacketOpenGui extends ModCommand
{
    public PacketOpenGui() {

    }

    public PacketOpenGui(PacketBuffer buffer) {

    }

    public void toBytes(PacketBuffer buffer) {

    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> Minecraft.getInstance().displayGuiScreen(new SimpleScreen()));
        ctx.get().setPacketHandled(true);
    }
}
