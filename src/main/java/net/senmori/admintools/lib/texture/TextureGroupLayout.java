package net.senmori.admintools.lib.texture;

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
            int index = texture.getTextureIndex();
            int startX = group.isOverrideTextureSettings() ? group.getStartX() : texture.getX();
            int width = group.isOverrideTextureSettings() ? group.getDefaultWidth() : texture.getWidth();
            for (int i = 1; index > 1 && i < index; i++) {
                startX += width;
            }
            texture.setX( startX );
            texture.setY( group.isOverrideTextureSettings() ? group.getStartY() : texture.getY() );
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
            int index = texture.getTextureIndex();
            int startY = group.isOverrideTextureSettings() ? group.getStartY() : texture.getY();
            int height = group.isOverrideTextureSettings() ? group.getDefaultHeight() : texture.getHeight();
            for (int i = 1; index > 1 && i < index; i++) {
                startY += height;
            }
            texture.setY( startY );
            texture.setX( group.isOverrideTextureSettings() ? group.getStartX() : texture.getX() );
            //CustomMobs.getInstance().getLogger().info( "LAYOUT: " + texture.toString() );
            return true;
        }
        return false;
    });

    boolean adjust(Texture texture, TextureGroup group);
}
