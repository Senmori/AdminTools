package net.senmori.admintools.events;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.senmori.admintools.AdminTools;
import net.senmori.admintools.commands.ModCommands;
import net.senmori.admintools.config.ClientConfig;
import net.senmori.admintools.network.Networking;

public class ForgeEventHandlers
{

    public ForgeEventHandlers()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
        MinecraftForge.EVENT_BUS.addListener(this::serverLoad);
    }

    public void init(final FMLCommonSetupEvent event)
    {
        Networking.registerMessages();
    }

    public void serverLoad(FMLServerStartingEvent event)
    {
        ModCommands.register(event.getCommandDispatcher());
        AdminTools.get().getLogger().warn("*** DEBUG_COLOR: " + ClientConfig.get().DEBUG_COLOR.get().toString() + " ***");
    }
}
