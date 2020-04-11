package net.senmori.admintools;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.loading.FMLPaths;
import net.senmori.admintools.asset.assets.JarFileAsset;
import net.senmori.admintools.asset.assets.LocalFileAsset;
import net.senmori.admintools.client.SimpleScreen;
import net.senmori.admintools.config.builder.ConfigBuilder;
import net.senmori.admintools.config.spec.ProjectConfigSpec;
import net.senmori.admintools.setup.ClientProxy;
import net.senmori.admintools.setup.IProxy;
import net.senmori.admintools.setup.ServerProxy;
import net.senmori.admintools.tmp.ClientConfig;
import net.senmori.admintools.util.Directory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.Color;
import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;

@Mod( AdminTools.MODID )
public class AdminTools implements Project
{
    private static AdminTools INSTANCE;
    public static Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "admintools";

    public static IProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    private final Color DEBUG_COLOR = new Color(0, 210, 0, 40);
    private Directory WORKING_DIRECTORY;
    private Directory CONFIG_DIRECTORY;

    private CommentedConfig config;

    public AdminTools()
    {
        AdminTools.INSTANCE = this;

        MinecraftForge.EVENT_BUS.addListener(this::setup);
        MinecraftForge.EVENT_BUS.addListener(this::onServerStart);
    }

    public static AdminTools get()
    {
        return INSTANCE;
    }

    public Color getDebugColor()
    {
        return DEBUG_COLOR;
    }

    public Logger getLogger()
    {
        return LOGGER;
    }

    @SubscribeEvent
    public void setup(final FMLCommonSetupEvent event)
    {
        PROXY.init();
        loadSettings();
    }

    @SubscribeEvent
    public void onServerStart(FMLServerStartingEvent event)
    {
        event.getCommandDispatcher().register(Commands.literal("cmgui")
                .requires(source -> source.getEntity() instanceof PlayerEntity)
                .executes(context -> {
                    Minecraft.getInstance().displayGuiScreen(new SimpleScreen());
                    return 0;
                }));
    }

    @Override
    public String getName()
    {
        return MODID;
    }

    @Override
    public ClassLoader getProjectClassLoader()
    {
        return getClass().getClassLoader();
    }

    @Override
    public Directory getWorkingDirectory()
    {
        if ( WORKING_DIRECTORY == null ) {
            String file = getProjectClassLoader().getResource(".").getPath();
            WORKING_DIRECTORY = new Directory(file, "");
        }
        return WORKING_DIRECTORY;
    }

    @Override
    public Directory getConfigDirectory()
    {
        if (CONFIG_DIRECTORY == null) {
            Path configPath = FMLPaths.CONFIGDIR.get();
            File dir = new File(configPath.toUri());
            CONFIG_DIRECTORY = new Directory(dir.getParent(), dir.getPath());
        }
        return CONFIG_DIRECTORY;
    }

    @Override
    public boolean loadSettings()
    {
        return true;
    }

    @Override
    public Config getConfig()
    {
        return config;
    }
}
