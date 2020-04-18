package net.senmori.admintools.config.api;

import net.senmori.admintools.config.value.ColorValue;

import java.awt.Color;
import java.util.List;
import java.util.function.Supplier;

import static net.senmori.admintools.util.ConfigUtil.split;

public interface CustomDefinitions
{

    ColorValue defineColor(List<String> path, Supplier<Color> defaultSupplier);


    default ColorValue defineColor(String path, Color color)
    {
        return defineColor(split(path), () -> color);
    }

    default ColorValue defineColor(String path, int r, int g, int b)
    {
        return defineColor(split(path), () -> new Color(r, g, b));
    }

    default ColorValue defineColor(String path, int r, int g, int b, int a)
    {
        return defineColor(split(path), () -> new Color(r, g, b, a));
    }
}
