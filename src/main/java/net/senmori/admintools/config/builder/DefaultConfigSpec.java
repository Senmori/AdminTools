package net.senmori.admintools.config.builder;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.EnumGetMethod;
import com.google.common.collect.Lists;
import com.google.common.collect.ObjectArrays;
import net.senmori.admintools.config.api.DefaultConfigDefinitions;
import net.senmori.admintools.config.spec.ValueSpec;
import net.senmori.admintools.config.value.BooleanValue;
import net.senmori.admintools.config.value.ConfigValue;
import net.senmori.admintools.config.value.DoubleValue;
import net.senmori.admintools.config.value.EnumValue;
import net.senmori.admintools.config.value.IntValue;
import net.senmori.admintools.config.value.ListValue;
import net.senmori.admintools.config.value.LongValue;
import net.senmori.admintools.lib.util.ValueRange;
import net.senmori.admintools.util.ConfigUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class DefaultConfigSpec implements DefaultConfigDefinitions
{
    protected BuilderContext context = new BuilderContext();
    protected Map<List<String>, String> levelComments = new HashMap<>();
    protected List<String> currentPath = new ArrayList<>();
    protected Config config;

    protected DefaultConfigSpec(Config config)
    {
        this.config = config;
    }

    public Config getConfig()
    {
        return config;
    }

    /**
     * The root define method. This is where everything should end up eventually.
     */
    protected <T> ConfigValue<T> _define(List<String> path, ValueSpec value, Supplier<T> defaultSupplier)
    {
        if ( !currentPath.isEmpty() ) {
            List<String> tmp = new ArrayList<>(currentPath.size() + path.size());
            tmp.addAll(currentPath);
            tmp.addAll(path);
            path = tmp;
        }
        getConfig().set(path, value);
        context = new BuilderContext();
        return new ConfigValue<>(getConfig(), path, defaultSupplier);
    }

    @Override
    public <V extends Enum<V>> EnumValue<V> defineEnum(List<String> path, Supplier<V> defaultValue, EnumGetMethod converter, Predicate<Object> validator, Class<V> clazz)
    {
        context.setClazz(clazz);
        EnumSet<V> allowedValues = EnumSet.allOf(clazz);
        Predicate<Object> validObject = obj -> {
            if ( obj instanceof Enum ) {
                return allowedValues.contains(obj);
            }
            try {
                return allowedValues.contains(converter.get(obj, clazz));
            } catch ( IllegalArgumentException | ClassCastException e ) {
                return false;
            }
        };
        String allEnumNames = allowedValues.stream().map(Enum::name).collect(Collectors.joining(", "));
        context.setComment(ObjectArrays.concat(context.getComment(), "Allowed Values: " + allEnumNames));
        ValueSpec valueSpec = new ValueSpec(defaultValue, validObject.and(validator), context);
        List<String> updatedPath = _define(path, valueSpec, defaultValue).getPath();
        return new EnumValue<>(getConfig(), updatedPath, defaultValue, converter, clazz);
    }

    @Override
    public <T> ListValue<T> defineList(List<String> path, Supplier<List<T>> defaultSupplier, Predicate<Object> elementValidator, Function<Object, T> elementConverter)
    {
        context.setClazz(List.class);
        Predicate<Object> configValidator = x -> x instanceof List && (( List<?> ) x).stream().allMatch(elementValidator);
        ValueSpec spec = new ValueSpec(defaultSupplier, configValidator, context)
        {
            @Override
            public Object correct(Object value)
            {
                if ( !(value instanceof List) || (( List<?> ) value).isEmpty() ) {
                    return getDefault();
                }
                List<?> rawList = Lists.newArrayList(( List<?> ) value);
                rawList.removeIf(elementValidator.negate());
                if ( rawList.isEmpty() ) {
                    return getDefault();
                }
                return rawList.stream().map(elementConverter).collect(Collectors.toList());
            }
        };
        _define(path, spec, defaultSupplier);
        return new ListValue<>(getConfig(), path, defaultSupplier, elementConverter);
    }

    @Override
    public <T> ConfigValue<T> defineInList(List<String> path, Supplier<T> defaultValue, Collection<? extends T> acceptableValues)
    {
        return define(path, defaultValue, acceptableValues::contains);
    }

    @Override
    public <T> ConfigValue<T> define(List<String> path, Supplier<T> defaultSupplier, Predicate<Object> validator)
    {
        return _define(path, new ValueSpec(defaultSupplier, validator, context), defaultSupplier);
    }

    @Override
    public BooleanValue define(List<String> path, Supplier<Boolean> defaultValue)
    {
        ValueSpec spec = new ValueSpec(defaultValue, ConfigUtil::isBoolean, context);
        _define(path, spec, defaultValue);
        return new BooleanValue(getConfig(), path, defaultValue);
    }

    private <V extends Comparable<V>> void setupRangeContext(ValueRange<V> range, V value, V min, V max)
    {
        context.setRange(range);
        context.setComment(ObjectArrays.concat(context.getComment(), "Range: " + range.toString()));
        if ( min.compareTo(max) > 0 ) {
            throw new IllegalArgumentException("Range minimum must be less than maximum.");
        }
        if ( !range.test(value) ) {
            throw new IllegalArgumentException("Invalid default value for range. Expecting " + range.toString() + ". Received " + value);
        }
    }

    @Override
    public IntValue defineInRange(List<String> path, int defaultValue, int min, int max)
    {
        Supplier<Integer> supplier = () -> defaultValue;
        ValueRange<Integer> range = new ValueRange<>(Integer.class, min, max);
        setupRangeContext(range, defaultValue, min, max);
        Predicate<Object> validator = o -> o instanceof Number && range.test((( Number ) o).intValue());
        ValueSpec spec = new ValueSpec(supplier, validator, context);
        _define(path, spec, supplier);
        return new IntValue(getConfig(), path, supplier);
    }

    @Override
    public LongValue defineInRange(List<String> path, long defaultValue, long min, long max)
    {
        Supplier<Long> supplier = () -> defaultValue;
        ValueRange<Long> range = new ValueRange<>(Long.class, min, max);
        setupRangeContext(range, defaultValue, min, max);
        Predicate<Object> validator = o -> o instanceof Number && range.test((( Number ) o).longValue());
        ValueSpec spec = new ValueSpec(supplier, validator, context);
        _define(path, spec, supplier);
        return new LongValue(getConfig(), path, supplier);
    }

    @Override
    public DoubleValue defineInRange(List<String> path, double defaultValue, double min, double max)
    {
        Supplier<Double> supplier = () -> defaultValue;
        ValueRange<Double> range = new ValueRange<>(Double.class, min, max);
        setupRangeContext(range, defaultValue, min, max);
        Predicate<Object> validator = o -> o instanceof Number && range.test((( Number ) o).doubleValue());
        ValueSpec spec = new ValueSpec(supplier, validator, context);
        _define(path, spec, supplier);
        return new DoubleValue(getConfig(), path, supplier);
    }
}
