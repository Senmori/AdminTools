package net.senmori.custommobs.client.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.senmori.custommobs.CustomMobs;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.Color;

public class ClientConfig {

    public ForgeConfigSpec.BooleanValue DEBUG_MODE;
    public ForgeConfigSpec.ConfigValue<Integer> DEBUG_COLOR_INT;
    public Color DEBUG_COLOR;

    ClientConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Client only settings. These should only be modified by experienced users.")
               .push( "Debug" );
        DEBUG_MODE = builder.comment( "Enable debug mode. Don't enable this." )
                .define( "debug", false );
        DEBUG_COLOR_INT = builder.comment( "Debug color" )
                .define( "color", CustomMobs.getInstance().getDebugColor().getRGB() );
        builder.pop();
    }

    public Color getDebugColor() {
        if (DEBUG_COLOR == null) {
            DEBUG_COLOR = DEBUG_COLOR_INT != null ? new Color( DEBUG_COLOR_INT.get() ) : CustomMobs.getInstance().getDebugColor();
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
