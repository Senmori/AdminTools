package net.senmori.admintools.config.value;

import com.electronwill.nightconfig.core.Config;
import net.senmori.admintools.config.spec.ConfigSpec;
import org.apache.commons.lang3.math.NumberUtils;

import java.awt.Color;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ColorValue extends ConfigValue<Color>
{
    public ColorValue(ConfigSpec spec, List<String> path, Supplier<Color> defaultSupplier)
    {
        super(spec, path, defaultSupplier);
    }

    @Override
    protected Color getRaw(Config config, List<String> path, Supplier<Color> defaultSupplier)
    {
        List<?> rawList = config.get(getPath());
        List<Integer> list = rawList.stream()
                .map(Object::toString)
                .filter(NumberUtils::isParsable)
                .limit(4)
                .map(NumberUtils::createInteger)
                .collect(Collectors.toList());
        int red = getColorValue(list, 0);
        int green = getColorValue(list, 1);
        int blue = getColorValue(list, 2);
        int alpha = getColorValue(list, 3);
        return new Color(red, green, blue, alpha);
    }

    private int getColorValue(List<Integer> list, int index)
    {
        return list.size() >= index + 1 ? list.get(index) : 0;
    }
}
