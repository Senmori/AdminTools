package net.senmori.admintools.config.value;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.Config;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ListValue<T> extends ConfigValue<List<T>>
{
    private final Function<Object, T> converter;

    public ListValue(CommentedConfig config, List<String> path, Supplier<List<T>> defaultSupplier, Function<Object, T> elementConverter)
    {
        super(config, path, defaultSupplier);
        this.converter = elementConverter;
    }

    @Override
    protected List<T> getRaw(CommentedConfig config, List<String> path, Supplier<List<T>> defaultSupplier)
    {
        List<?> result = config.getOrElse(path, defaultSupplier);
        return result.stream().map(converter).collect(Collectors.toCollection(LinkedList::new));
    }
}
