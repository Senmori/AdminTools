package net.senmori.admintools.util;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.List;

public class ConfigUtil
{
    public static final Joiner LINE_JOINER = Joiner.on( "\n" );
    public static final Joiner DOT_JOINER = Joiner.on( "." );
    public static final Splitter DOT_SPLITTER = Splitter.on( "." );

    public static List<String> split(String path) {
        return Lists.newArrayList( DOT_SPLITTER.split( path ) );
    }

    public static boolean isBoolean(Object value) {
        if (value instanceof String) {
            return ((String)value).equalsIgnoreCase("true") || ((String)value).equalsIgnoreCase("false");
        }
        return value instanceof Boolean;
    }
}
