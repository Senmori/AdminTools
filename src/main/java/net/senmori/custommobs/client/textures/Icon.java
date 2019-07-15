package net.senmori.custommobs.client.textures;

import net.senmori.custommobs.lib.texture.ITexture;
import net.senmori.custommobs.lib.texture.TextureGroup;

import java.util.Locale;

public enum Icon {
    /** Tab list ping textures */
    PING_FULL( TextureGroups.Icons.TAB_PING, 1),
    PING_4(TextureGroups.Icons.TAB_PING, 2),
    PING_3(TextureGroups.Icons.TAB_PING, 3),
    PING_2(TextureGroups.Icons.TAB_PING, 4),
    PING_1(TextureGroups.Icons.TAB_PING, 5),
    PING_NONE(TextureGroups.Icons.TAB_PING, 6),
    /** Heart textures */
    HEART_OUTLINE_1_BLACK(TextureGroups.Icons.HEARTS, 1),
    HEART_OUTLINE_2_WHITE(TextureGroups.Icons.HEARTS, 2),
    HEART_OUTLINE_3_RED(TextureGroups.Icons.HEARTS, 3),
    HEART_OUTLINE_4_WHITE(TextureGroups.Icons.HEARTS, 4),
    HEART_NORMAL_FULL(TextureGroups.Icons.HEARTS, 5),
    HEART_NORMAL_HALF(TextureGroups.Icons.HEARTS, 6),
    HEART_NORMAL_FULL_HURT(TextureGroups.Icons.HEARTS, 7),
    HEART_NORMAL_HALF_HURT(TextureGroups.Icons.HEARTS, 8),
    HEART_POISONED_FULL(TextureGroups.Icons.HEARTS, 9),
    HEART_POISONED_HALF(TextureGroups.Icons.HEARTS, 10),
    HEART_POISONED_FULL_HURT(TextureGroups.Icons.HEARTS, 11),
    HEART_POISONED_HALF_HURT(TextureGroups.Icons.HEARTS, 12),
    HEART_WITHER_FULL(TextureGroups.Icons.HEARTS, 13),
    HEART_WITHER_HALF(TextureGroups.Icons.HEARTS, 14),
    HEART_WITHER_FULL_HURT(TextureGroups.Icons.HEARTS, 15),
    HEART_WITHER_HALF_HURT(TextureGroups.Icons.HEARTS, 16),
    HEART_ABSORPTION_FULL(TextureGroups.Icons.HEARTS, 17),
    HEART_ABSORPTION_HALF(TextureGroups.Icons.HEARTS, 18),
    /** Armor icons */
    ARMOR_EMPTY(TextureGroups.Icons.ARMOR, 1),
    ARMOR_HALF(TextureGroups.Icons.ARMOR, 2),
    ARMOR_FULL(TextureGroups.Icons.ARMOR, 3),
    ARMOR_FULL_2(TextureGroups.Icons.ARMOR, 4),
    ;

    private final TextureGroup group;
    Icon(TextureGroup group, int index) {
        this.group = group;
        group.addTexture( getName() ).setTextureIndex( index );
    }

    public String getName() {
        return name().toLowerCase( Locale.ENGLISH );
    }

    public ITexture getTexture() {
        return group.getOrAddTexture( getName() );
    }
}
