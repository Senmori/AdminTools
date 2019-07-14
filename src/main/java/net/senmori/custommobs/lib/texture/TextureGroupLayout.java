package net.senmori.custommobs.lib.texture;

import java.util.List;
import java.util.Objects;

public interface TextureGroupLayout {
    /**
     * There is no layout for this texture group. Texture positions are defined by the textures themselves.
     */
    TextureGroupLayout NONE = (texture, group) -> true;
    /**
     * Textures are laid out in a horizontal fashion within this group.
     */
    TextureGroupLayout HORIZONTAL = ((texture, group) -> {
        if (texture == null || !Objects.equals(texture.getGroup(), group)) {
            return false;
        }
        if (group.getTextures().contains( texture )) {
            int index = group.getNextIndex();
            int startX = group.isOverrideTextureSettings() ? (index * group.getDefaultWidth()) + group.getDefaultWidth() : (index * texture.getWidth()) + texture.getWidth();
            texture.setX( startX );
            texture.setY( group.isOverrideTextureSettings() ? group.getStartY() : texture.getY() );
            texture.setTextureIndex( index );
            return true;
        }
        return false;
    });

    /**
     * Textures are laid out in a vertical fashion within this group.
     */
    TextureGroupLayout VERTICAL = ((texture, group) -> {
        if (texture == null || !Objects.equals(texture.getGroup(), group)) {
            return false;
        }
        if (group.getTextures().contains( texture )) {
            int index = group.getNextIndex();
            texture.setX( group.isOverrideTextureSettings() ? group.getStartX() : texture.getX() );
            int startY = group.isOverrideTextureSettings() ? (index * group.getDefaultHeight()) + group.getDefaultHeight() : (index * texture.getHeight()) + texture.getHeight();
            texture.setY( startY );
            texture.setTextureIndex( index );
            return true;
        }
        return false;
    });

    boolean adjust(ITexture texture, TextureGroup group);
}
