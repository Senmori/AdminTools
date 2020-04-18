package net.senmori.admintools;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.Config;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.loading.FMLPaths;
import net.senmori.admintools.events.ForgeEventHandlers;
import net.senmori.admintools.setup.ClientProxy;
import net.senmori.admintools.setup.IProxy;
import net.senmori.admintools.setup.ServerProxy;
import net.senmori.admintools.util.Directory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.Color;
import java.io.File;
import java.nio.file.Path;

@Mod( AdminTools.MODID )
public class AdminTools implements Project
{
    private static AdminTools INSTANCE;
    private static Logger LOGGER = LogManager.getLogger();

    public static final String MODID = "admintools";

    public static IProxy PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    private Directory WORKING_DIRECTORY;
    private Directory CONFIG_DIRECTORY;

    private final ForgeEventHandlers eventHandlers;

    public AdminTools()
    {
        AdminTools.INSTANCE = this;
        eventHandlers = new ForgeEventHandlers();
        loadSettings();
    }

    public static AdminTools get()
    {
        return INSTANCE;
    }

    public ResourceLocation newResourceLocation(String path) {
        return new ResourceLocation(MODID, path);
    }

    public Logger getLogger()
    {
        return LOGGER;
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
            String file = FMLPaths.MODSDIR.get().toAbsolutePath().toString();
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
        PROXY.init();
        return true;
    }
}
