package net.senmori.admintools.config.builder;

import net.senmori.admintools.lib.util.ValueRange;

import java.util.stream.Stream;

public class BuilderContext
{
    private StringBuilder comment = new StringBuilder();
    private String langKey;
    private ValueRange<?> range;
    private boolean worldRestart = false;
    private Class<?> clazz;

    protected BuilderContext()
    {
    }

    public void setComment(String... value)
    {
        Stream.of(value).forEach(s -> comment.append(s).append("\n"));
    }

    public boolean hasComment()
    {
        return this.comment.length() > 0;
    }

    public String[] getComment()
    {
        return this.comment.toString().split("\\n");
    }

    public String buildComment()
    {
        return comment.toString();
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
