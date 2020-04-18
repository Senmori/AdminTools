package net.senmori.admintools.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.senmori.admintools.AdminTools;

import java.util.concurrent.atomic.AtomicInteger;

public class Networking
{

    public static SimpleChannel INSTANCE;
    private static AtomicInteger ID = new AtomicInteger(0);

    public static int nextID() { return ID.getAndIncrement(); }

    public static void registerMessages() {
        ResourceLocation resourceLocation = AdminTools.get().newResourceLocation(AdminTools.MODID);
        INSTANCE = NetworkRegistry.newSimpleChannel(resourceLocation, () -> "1.0", s -> true, s -> true);
        INSTANCE.registerMessage(nextID(),
                PacketOpenGui.class,
                PacketOpenGui::toBytes,
                PacketOpenGui::new,
                PacketOpenGui::handle);
    }
}
