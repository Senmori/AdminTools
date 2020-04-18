package net.senmori.admintools.config.api;

import net.senmori.admintools.config.value.DoubleValue;
import net.senmori.admintools.config.value.IntValue;
import net.senmori.admintools.config.value.LongValue;

import java.util.List;

import static net.senmori.admintools.util.ConfigUtil.split;

/**
 * Represents the definitions for an acceptable range of numbers.
 */
public interface RangeDefinitions
{
    IntValue defineInRange(List<String> path, int defaultValue, int min, int max);

    LongValue defineInRange(List<String> path, long defaultValue, long min, long max);

    DoubleValue defineInRange(List<String> path, double defaultValue, double min, double max);

    default IntValue defineInRange(String path, int defaultValue, int min, int max)
    {
        return defineInRange(split(path), defaultValue, min, max);
    }

    default IntValue define(String path, int value)
    {
        return defineInRange(split(path), value, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    default LongValue defineInRange(String path, long defaultValue, long min, long max)
    {
        return defineInRange(split(path), defaultValue, min, max);
    }

    default LongValue define(String path, long value)
    {
        return defineInRange(split(path), value, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    default DoubleValue defineInRange(String path, double defaultValue, double min, double max)
    {
        return defineInRange(split(path), defaultValue, min, max);
    }

    default DoubleValue define(String path, double value)
    {
        return defineInRange(split(path), value, Double.MIN_VALUE, Double.MAX_VALUE);
    }
}
