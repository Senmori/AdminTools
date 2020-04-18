package net.senmori.admintools.config.value;

import com.electronwill.nightconfig.core.Config;
import com.google.common.collect.Lists;

import java.awt.Color;
import java.util.List;
import java.util.function.Supplier;

public class ColorValue extends ConfigValue<Color>
{
    private final ListValue<Integer> configList;

    public ColorValue(Config config, List<String> path, ListValue<Integer> configList, Supplier<Color> defaultSupplier)
    {
        super(config, path, defaultSupplier);
        this.configList = configList;
    }

    @Override
    public void set(Color value)
    {
        configList.set(Lists.newLinkedList(Lists.newArrayList(value.getRed(), value.getGreen(), value.getBlue(), value.getAlpha())));
    }

    @Override
    protected Color getRaw(Config config, List<String> path, Supplier<Color> defaultSupplier)
    {
        List<Integer> list = configList.get();
        int red = getColorValue(list, 0, defaultSupplier.get().getRed());
        int green = getColorValue(list, 1, defaultSupplier.get().getGreen());
        int blue = getColorValue(list, 2, defaultSupplier.get().getBlue());
        int alpha = getColorValue(list, 3, defaultSupplier.get().getAlpha());
        return new Color(red, green, blue, alpha);
    }

    private int getColorValue(List<Integer> list, int index, int defaultValue)
    {
        return list.size() >= index + 1 ? list.get(index) : defaultValue;
    }
}
