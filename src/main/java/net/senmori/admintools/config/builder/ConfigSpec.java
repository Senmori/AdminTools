package net.senmori.admintools.config.builder;

import com.electronwill.nightconfig.core.Config;

import java.util.ArrayList;
import java.util.List;

import static net.senmori.admintools.util.ConfigUtil.split;

public class ConfigSpec extends DefaultConfigSpec
{
    public ConfigSpec(Config config)
    {
        super(config);
    }

    public ConfigSpec comment(String comment)
    {
        context.setComment(comment);
        return this;
    }

    public ConfigSpec comment(String... comment)
    {
        context.setComment(comment);
        return this;
    }

    public ConfigSpec translation(String translationKey)
    {
        context.setTranslationKey(translationKey);
        return this;
    }

    public ConfigSpec worldRestart()
    {
        context.worldRestart();
        return this;
    }

    public ConfigSpec push(String path)
    {
        return push(split(path));
    }

    public ConfigSpec push(List<String> path)
    {
        currentPath.addAll(path);
        if ( context.hasComment() ) {

            levelComments.put(new ArrayList<>(currentPath), context.buildComment());
            context.setComment(); // Set to empty
        }
        context.ensureEmpty();
        return this;
    }

    public ConfigSpec pop()
    {
        return pop(1);
    }

    public ConfigSpec pop(int count)
    {
        if ( count > currentPath.size() )
            throw new IllegalArgumentException("Attempted to pop " + count + " elements when we only had: " + currentPath);
        for ( int x = 0; x < count; x++ )
            currentPath.remove(currentPath.size() - 1);
        return this;
    }
}
