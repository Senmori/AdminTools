package net.senmori.admintools.tmp;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import net.senmori.admintools.AdminTools;
import net.senmori.admintools.asset.assets.JarFileAsset;
import net.senmori.admintools.asset.assets.LocalFileAsset;
import net.senmori.admintools.config.builder.ConfigBuilder;
import net.senmori.admintools.config.builder.ConfigSpec;
import net.senmori.admintools.config.value.BooleanValue;
import net.senmori.admintools.config.value.IntValue;

import java.awt.Color;
import java.io.File;
import java.util.Arrays;

public class ClientConfig {

    private static ClientConfig INSTANCE;

    private Config config;
    private ConfigSpec spec;
    public BooleanValue DEBUG_MODE;
    public IntValue DEBUG_COLOR_INT;
    public Color DEBUG_COLOR;

    public IntValue MAX_BUTTON_LENGTH;
    public IntValue DEFAULT_WIDGET_WIDTH;
    public IntValue DEFAULT_WIDGET_HEIGHT;

    ClientConfig(ConfigSpec spec) {
        this.config = spec.getConfig();
        this.spec = spec;
        spec.comment("UI Settings")
                .push( "UX" );
        MAX_BUTTON_LENGTH = spec
                .comment("The maximum length (in pixels) a button may be. This effects the rendering of the button texture.")
                .defineInRange( "max_button_length", 200, 0, 200 );
        DEFAULT_WIDGET_WIDTH = spec
                .comment( "The default width of widgets." )
                .define( "default_widget_width", 200 );
        DEFAULT_WIDGET_HEIGHT = spec
                .comment( "The default height of widgets." )
                .define( "default_widget_height", 20 );


        spec.pop();

        /*
         * ALWAYS LAST
         */
        spec.comment("Advanced settings. These should only be modified by experienced users.")
                .push( "Debug" );
        DEBUG_MODE = spec.comment( "Enable debug mode. Don't enable this." )
                .define( "debug", false );
        DEBUG_COLOR_INT = spec.comment( "Debug color" )
                .define( "color", AdminTools.get().getDebugColor().getRGB() );
        spec.pop();
    }

    public Color getDebugColor() {
        if (DEBUG_COLOR == null) {
            DEBUG_COLOR = DEBUG_COLOR_INT != null ? new Color( DEBUG_COLOR_INT.get() ) : AdminTools.get().getDebugColor();
        }
        return DEBUG_COLOR;
    }

    public static void init() {
        CommentedConfig config = ConfigBuilder.newConfig("admintools-client");
        INSTANCE = new ClientConfig(new ConfigSpec(config));
        AdminTools.get().getLogger().info(Arrays.toString(config.valueMap().values().toArray()));
    }

    public static ClientConfig get() {
        return INSTANCE;
    }
}
