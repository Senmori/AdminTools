package net.senmori.admintools.config;

import com.electronwill.nightconfig.core.CommentedConfig;
import net.minecraftforge.common.util.LazyOptional;
import net.senmori.admintools.AdminTools;
import net.senmori.admintools.asset.assets.JarFileAsset;
import net.senmori.admintools.asset.assets.LocalFileAsset;
import net.senmori.admintools.config.spec.ConfigBuilder;
import net.senmori.admintools.config.spec.ConfigSpec;
import net.senmori.admintools.config.value.BooleanValue;
import net.senmori.admintools.config.value.ColorValue;
import net.senmori.admintools.config.value.IntValue;

import javax.annotation.Nonnull;
import java.awt.Color;
import java.util.Objects;

public class ClientConfig
{
    private static ClientConfig INSTANCE;

    private final CommentedConfig config;
    private final ConfigSpec spec;

    public final BooleanValue DEBUG_MODE;
    public final ColorValue DEBUG_COLOR;
    public final IntValue MAX_BUTTON_LENGTH;
    public final IntValue DEFAULT_WIDGET_WIDTH;
    public final IntValue DEFAULT_WIDGET_HEIGHT;

    ClientConfig(ConfigSpec spec)
    {
        this.config = ( CommentedConfig ) spec.getConfig();
        this.spec = spec;
        spec.comment("UI Settings")
                .push("UX");
        MAX_BUTTON_LENGTH = spec
                .comment("The maximum length (in pixels) a button may be. This effects the rendering of the button texture.")
                .define("max_button_length", 200);
        DEFAULT_WIDGET_WIDTH = spec
                .comment("The default width of widgets.")
                .define("default_widget_width", 200);
        DEFAULT_WIDGET_HEIGHT = spec
                .comment("The default height of widgets.")
                .define("default_widget_height", 20);


        spec.pop();

        spec.comment("Advanced settings. These should only be modified by experienced users.")
                .push("Debug");
        DEBUG_MODE = spec.comment("Enable debug mode. Don't enable this.")
                .defineObject("debug", false);
        DEBUG_COLOR = spec.comment("Debug color")
                .defineColor("color", new Color(0, 210, 0, 40));
        spec.pop();
    }

    public static ClientConfig get()
    {
        if ( INSTANCE == null ) {
            init();
        }
        return INSTANCE;
    }

    public CommentedConfig getConfig()
    {
        return config;
    }

    public ConfigSpec getSpec()
    {
        return spec;
    }

    public static void init()
    {
        if ( Objects.isNull(INSTANCE) ) {
            LocalFileAsset clientConfigFile = LocalFileAsset.of(AdminTools.get(), "admintools-client");
            JarFileAsset sourceConfigFile = JarFileAsset.of(AdminTools.get(), "admintools-client");
            CommentedConfig config = ConfigBuilder.newBuilder(clientConfigFile).source(sourceConfigFile).build();
            ConfigSpec spec = new ConfigSpec(config);
            spec.correct(config);
            INSTANCE = new ClientConfig(spec);
        }
    }
}
