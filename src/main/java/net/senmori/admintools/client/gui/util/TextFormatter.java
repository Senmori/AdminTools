package net.senmori.admintools.client.gui.util;

import net.senmori.admintools.lib.properties.function.BiFunctionProperty;

import java.util.function.BiFunction;

public class TextFormatter implements BiFunction<String, Integer, String>
{
    private final BiFunctionProperty<String, Integer, String> property;
    public TextFormatter() {
        this.property = BiFunctionProperty.of("text formatter", (str, num) -> str);
    }

    public TextFormatter(String name, BiFunction<String, Integer, String> formatter) {
        this.property = BiFunctionProperty.of(name, formatter);
    }

    @Override
    public String apply(String str, Integer num)
    {
        return this.property.get().apply(str, num);
    }
}
