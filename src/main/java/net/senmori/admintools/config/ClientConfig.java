package net.senmori.admintools.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.senmori.admintools.AdminTools;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.Color;

public class ClientConfig {

    public ForgeConfigSpec.BooleanValue DEBUG_MODE;
    public ForgeConfigSpec.ConfigValue<Integer> DEBUG_COLOR_INT;
    public Color DEBUG_COLOR;

    public ForgeConfigSpec.ConfigValue<Integer> MAX_BUTTON_LENGTH;
    public ForgeConfigSpec.ConfigValue<Integer> DEFAULT_WIDGET_WIDTH;
    public ForgeConfigSpec.ConfigValue<Integer> DEFAULT_WIDGET_HEIGHT;

    ClientConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("UI Settings")
                .push( "UX" );
        MAX_BUTTON_LENGTH = builder.comment("The maximum length (in pixels) a button may be. This effects the rendering of the button texture.")
                .define( "max_button_length", 200 );
        DEFAULT_WIDGET_WIDTH = builder.comment( "The default width of widgets." )
                .define( "default_widget_width", 200 );
        DEFAULT_WIDGET_HEIGHT = builder.comment( "The default height of widgets." )
                .define( "default_widget_height", 20 );


        builder.pop();

        /*
         * ALWAYS LAST
         */
        builder.comment("Client only settings. These should only be modified by experienced users.")
                .push( "Debug" );
        DEBUG_MODE = builder.comment( "Enable debug mode. Don't enable this." )
                .define( "debug", false );
        DEBUG_COLOR_INT = builder.comment( "Debug color" )
                .define( "color", AdminTools.getInstance().getDebugColor().getRGB() );
        builder.pop();
    }

    public Color getDebugColor() {
        if (DEBUG_COLOR == null) {
            DEBUG_COLOR = DEBUG_COLOR_INT != null ? new Color( DEBUG_COLOR_INT.get() ) : AdminTools.getInstance().getDebugColor();
        }
        return DEBUG_COLOR;
    }


    public static ForgeConfigSpec CLIENT_SPEC;
    public static ClientConfig CONFIG;

    public static void init() {
        final Pair<ClientConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure( ClientConfig::new );
        CLIENT_SPEC = pair.getRight();
        CONFIG = pair.getLeft();
        ModLoadingContext.get().registerConfig( ModConfig.Type.CLIENT, ClientConfig.CLIENT_SPEC );
    }
}
