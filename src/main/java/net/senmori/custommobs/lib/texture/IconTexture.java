package net.senmori.custommobs.lib.texture;

import net.senmori.custommobs.CustomMobs;
import net.senmori.custommobs.client.config.ClientConfig;

import java.util.Locale;

public enum IconTexture {
    /** Tab list ping textures */
    PING_FULL(TextureGroups.Icons.TAB_PING),
    PING_4(TextureGroups.Icons.TAB_PING),
    PING_3(TextureGroups.Icons.TAB_PING),
    PING_2(TextureGroups.Icons.TAB_PING),
    PING_1(TextureGroups.Icons.TAB_PING),
    NO_PING(TextureGroups.Icons.TAB_PING),
    /** Heart textures */
    HEART_OUTLINE_1_BLACK(TextureGroups.Icons.HEARTS),
    HEART_OUTLINE_2_WHITE(TextureGroups.Icons.HEARTS),
    HEART_OUTLINE_3_RED(TextureGroups.Icons.HEARTS),
    HEART_OUTLINE_4_WHITE(TextureGroups.Icons.HEARTS),
    HEART_NORMAL_FULL(TextureGroups.Icons.HEARTS),
    HEART_NORMAL_HALF(TextureGroups.Icons.HEARTS),
    HEART_NORMAL_FULL_HURT(TextureGroups.Icons.HEARTS),
    HEART_NORMAL_HALF_HURT(TextureGroups.Icons.HEARTS),
    HEART_POISONED_FULL(TextureGroups.Icons.HEARTS),
    HEART_POISONED_HALF(TextureGroups.Icons.HEARTS),
    HEART_POISONED_FULL_HURT(TextureGroups.Icons.HEARTS),
    HEART_POISONED_HALF_HURT(TextureGroups.Icons.HEARTS),
    HEART_WITHER_FULL(TextureGroups.Icons.HEARTS),
    HEART_WITHER_HALF(TextureGroups.Icons.HEARTS),
    HEART_WITHER_FULL_HURT(TextureGroups.Icons.HEARTS),
    HEART_WITHER_HALF_HURT(TextureGroups.Icons.HEARTS),
    HEART_ABSORPTION_FULL(TextureGroups.Icons.HEARTS),
    HEART_ABSORPTION_HALF(TextureGroups.Icons.HEARTS),;

    ;

    private final TextureGroup group;
    IconTexture(TextureGroup group) {
        this.group = group;
        group.addTexture( getName() );
    }

    public String getName() {
        return name().toLowerCase( Locale.ENGLISH );
    }

    public ITexture getTexture() {
        return group.getOrAddTexture( getName() );
    }


    static {
        for (IconTexture icon : values()) {
            icon.group.addTexture( icon.getName() );
            if ( ClientConfig.CONFIG.DEBUG_MODE.get() ) {
                CustomMobs.getInstance().getLogger().info( "Registered " + icon.getName() + " to group " + icon.group.getName() );
                CustomMobs.getInstance().getLogger().info( icon.getTexture().toString() );
            }
        }
    }
}
