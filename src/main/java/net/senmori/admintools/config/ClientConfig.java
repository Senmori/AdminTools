package net.senmori.admintools.config;

import net.minecraftforge.common.util.LazyOptional;
import net.senmori.admintools.config.builder.ConfigSpec;
import net.senmori.admintools.config.value.BooleanValue;
import net.senmori.admintools.config.value.IntValue;

import java.awt.Color;

public class ClientConfig
{
    private final Color DEF_DEBUG_COLOR = new Color(0, 210, 0, 40);

    public BooleanValue DEBUG_MODE;
    private IntValue DEBUG_COLOR_INT;
    private LazyOptional<Color> DEBUG_COLOR = LazyOptional.of(this::getDebugColor);

    public IntValue MAX_BUTTON_LENGTH;
    public IntValue DEFAULT_WIDGET_WIDTH;
    public IntValue DEFAULT_WIDGET_HEIGHT;

    protected ClientConfig(ConfigSpec spec)
    {
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

            /*
             * ALWAYS LAST
             */
            spec.comment("Advanced settings. These should only be modified by experienced users.")
                    .push("Debug");
            DEBUG_MODE = spec.comment("Enable debug mode. Don't enable this.")
                    .define("debug", false);
            DEBUG_COLOR_INT = spec.comment("Debug color")
                    .define("color", getDebugColor().getRGB());
            spec.pop();
    }

    public Color getDebugColor()
    {
        if ( DEBUG_COLOR == null ) {
            Integer rgbValue = DEBUG_COLOR_INT != null && DEBUG_COLOR_INT.get() != null ? DEBUG_COLOR_INT.get() : DEF_DEBUG_COLOR.getRGB();
            DEBUG_COLOR = LazyOptional.of(() -> new Color(rgbValue));
        }
        return DEBUG_COLOR.orElse(DEF_DEBUG_COLOR);
    }
}
