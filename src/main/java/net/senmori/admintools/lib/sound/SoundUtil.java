package net.senmori.admintools.lib.sound;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class SoundUtil {
    private static final SoundUtil INSTANCE = new SoundUtil();
    private static final Pattern DOT_PATTERN = Pattern.compile( "\\." );

    private Map<ResourceLocation, SoundCategory> soundCache = new HashMap<>();
    private Map<String, SoundCategory> SOUND_CATEGORIES = Arrays.stream( SoundCategory.values() ).collect( Collectors.toMap( SoundCategory::getName, Function.identity() ) );

    private SoundUtil() {
    }

    public static SoundUtil get() {
        return INSTANCE;
    }

    /**
     * Get the {@link SoundCategory} associated with a given {@link net.minecraft.util.SoundEvent}.
     *
     * @param soundName the sound name
     * @return the sound category, or {@code SoundCategory#MASTER} if none could be found.
     */
    public SoundCategory getSoundCategory(ResourceLocation soundName) {
        if ( soundCache.containsKey( soundName ) ) {
            return soundCache.getOrDefault( soundName, SoundCategory.MASTER );
        }
        String[] split = soundName.getPath().split( DOT_PATTERN.pattern() );
        if ( split.length >= 1 ) {
            SoundCategory category = SOUND_CATEGORIES.getOrDefault( split[ 0 ].toLowerCase(), SoundCategory.MASTER );
            soundCache.putIfAbsent( soundName, category );
            return category;
        }
        return SoundCategory.MASTER;
    }

    public CustomSound createSound(SoundEvent sound) {
        return new CustomSound( sound );
    }
}
