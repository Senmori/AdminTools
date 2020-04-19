package net.senmori.admintools.config.spec;

import net.senmori.admintools.lib.util.ValueRange;
import net.senmori.admintools.util.ConfigUtil;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

public class BuilderContext
{
    private @Nonnull String[] comment = new String[0];
    private String langKey;
    private ValueRange<?> range;
    private boolean worldRestart = false;
    private Class<?> clazz;

    protected BuilderContext()
    {
    }

    public void setComment(String... value)
    {
        this.comment = value;
    }

    public boolean hasComment()
    {
        return this.comment.length > 0;
    }

    public String[] getComment()
    {
        return this.comment;
    }

    public String buildComment()
    {
        return ConfigUtil.LINE_JOINER.join(comment);
    }

    public void setTranslationKey(String value)
    {
        this.langKey = value;
    }

    public String getTranslationKey()
    {
        return this.langKey;
    }

    public <V extends Comparable<? super V>> void setRange(ValueRange<V> value)
    {
        this.range = value;
        this.setClazz(value.getClazz());
    }

    @SuppressWarnings( "unchecked" )
    public <V extends Comparable<? super V>> ValueRange<V> getRange()
    {
        return ( ValueRange<V> ) this.range;
    }

    public void worldRestart()
    {
        this.worldRestart = true;
    }

    public boolean needsWorldRestart()
    {
        return this.worldRestart;
    }

    public void setClazz(Class<?> clazz)
    {
        this.clazz = clazz;
    }

    public Class<?> getClazz()
    {
        return this.clazz;
    }

    public void ensureEmpty()
    {
        validate(hasComment(), "Non-empty comment when empty expected");
        validate(langKey, "Non-null translation key when null expected");
        validate(range, "Non-null range when null expected");
        validate(worldRestart, "Dangling world restart value set to true");
    }

    private void validate(Object value, String message)
    {
        if ( value != null )
            throw new IllegalStateException(message);
    }

    private void validate(boolean value, String message)
    {
        if ( value )
            throw new IllegalStateException(message);
    }
}
