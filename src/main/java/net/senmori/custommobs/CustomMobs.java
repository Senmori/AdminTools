package net.senmori.custommobs;

import net.minecraft.client.Minecraft;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.senmori.custommobs.client.config.ClientConfig;
import net.senmori.custommobs.client.SimpleScreen;
import net.senmori.custommobs.commands.CommandManager;
import net.senmori.custommobs.commands.command.GUICommand;
import net.senmori.custommobs.config.ServerConfig;
import net.senmori.custommobs.lib.properties.read.ReadOnlyObjectProperty;
import net.senmori.custommobs.setup.ClientProxy;
import net.senmori.custommobs.setup.IProxy;
import net.senmori.custommobs.setup.ServerProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.Color;
import java.lang.management.ManagementFactory;

@Mod( CustomMobs.MODID )
public class CustomMobs {
    private static CustomMobs INSTANCE;
    public static Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "custommobs";

    public static IProxy PROXY = DistExecutor.runForDist( () -> ClientProxy::new, () -> ServerProxy::new );

    private final Color DEBUG_COLOR = new Color(0, 210, 0, 40);
    public CustomMobs() {
        CustomMobs.INSTANCE = this;
        ClientConfig.init();

        MinecraftForge.EVENT_BUS.addListener( this::setup );
        MinecraftForge.EVENT_BUS.addListener( this::onServerStart );
    }

    public static CustomMobs getInstance() {
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
        CommandManager.register( new GUICommand() );
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
