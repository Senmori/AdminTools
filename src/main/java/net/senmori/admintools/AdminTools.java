package net.senmori.admintools;

import net.minecraft.client.Minecraft;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.senmori.admintools.client.SimpleScreen;
import net.senmori.admintools.client.config.ClientConfig;
import net.senmori.admintools.setup.ClientProxy;
import net.senmori.admintools.setup.IProxy;
import net.senmori.admintools.setup.ServerProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.Color;

@Mod( AdminTools.MODID )
public class AdminTools {
    private static AdminTools INSTANCE;
    public static Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "admintools";

    public static IProxy PROXY = DistExecutor.runForDist( () -> ClientProxy::new, () -> ServerProxy::new );

    private final Color DEBUG_COLOR = new Color(0, 210, 0, 40);
    public AdminTools() {
        AdminTools.INSTANCE = this;
        ClientConfig.init();

        MinecraftForge.EVENT_BUS.addListener( this::setup );
        MinecraftForge.EVENT_BUS.addListener( this::onServerStart );
    }

    public static AdminTools getInstance() {
        return INSTANCE;
    }

    public Color getDebugColor() {
        return DEBUG_COLOR;
    }

    public Logger getLogger() {
        return LOGGER;
    }

    @SubscribeEvent
    public void setup(final FMLCommonSetupEvent event) {
        PROXY.init();
    }

    @SubscribeEvent
    public void onServerStart(FMLServerStartingEvent event) {
        event.getCommandDispatcher().register( Commands.literal("cmgui")
                .requires( source -> source.getEntity() instanceof PlayerEntity )
                .executes( context -> {
                    Minecraft.getInstance().displayGuiScreen( new SimpleScreen() );
                    return 0;
                } ));
    }
}
