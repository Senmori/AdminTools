package net.senmori.admintools.network;

import com.electronwill.nightconfig.core.CommentedConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import net.senmori.admintools.AdminTools;
import net.senmori.admintools.client.SimpleScreen;
import net.senmori.admintools.commands.ModCommand;
import net.senmori.admintools.config.ClientConfig;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class PacketOpenGui extends ModCommand
{
    public PacketOpenGui() {

    }

    public PacketOpenGui(PacketBuffer buffer) {

    }

    public void toBytes(PacketBuffer buffer) {

    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        //ctx.get().enqueueWork(() -> Minecraft.getInstance().displayGuiScreen(new SimpleScreen()));
        Set<? extends CommentedConfig.Entry> map = ClientConfig.get().getConfig().entrySet();
        String str = map.stream().map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.joining(", ", "{", "}"));
        ctx.get().enqueueWork(() -> AdminTools.PROXY.getClientPlayer().sendMessage(new StringTextComponent(str)));
        ctx.get().setPacketHandled(true);
    }
}
