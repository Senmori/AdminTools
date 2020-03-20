package net.senmori.admintools.lib.texture;

import net.minecraft.util.ResourceLocation;

/**
 * A texture is a rectangular area of pixels found within a source file.
 * Multiple types of textures can be within a single source file.
 * For example, inventory images contain all icons needed to be displayed on screen. This includes
 * any icons such as the recipe book icon.
 */
public interface Texture {

    /**
     * Create a new {@link GuiTexture.Builder} instance to help create textures.
     *
     * @param name
     * @return
     */
    public static GuiTexture.Builder create(String name) {
        return new GuiTexture.Builder(name);
    }

    /**
     * The {@link ResourceLocation} is the location where the texture can be found.
     *
     * @return the {@link ResourceLocation} of the file where this texture is located.
     */
    ResourceLocation getLocation();

    /**
     * The name of the texture is a user-friendly way to describe the texture.
     *
     * @return the user-friendly description of the texture
     */
    String getName();

    /**
     * The x-coordinate of the texture on the {@link #getLocation} file.
     * These coordinates are in pixels.
     *
     * @return the x-coordinate of the texture
     */
    int getX();

    /**
     * The y-coordinate of the texture on the {@link ResourceLocation} file.
     * These coordinates are in pixels.
     *
     * @return the y-coordinate of the texture
     */
    int getY();

    /**
     * The width of the texture, in pixels.
     *
     * @return the width of the texture
     */
    int getWidth();

    /**
     * The height of the texture, in pixels.
     *
     * @return the height of the texture
     */
    int getHeight();

    int getTextureIndex();

    void setTextureIndex(int index);

    void setX(int x);

    void setY(int y);

    void setWidth(int width);

    void setHeight(int height);

    /**
     * Get the {@link TextureGroup} this texture belongs to.
     *
     * @return the TextureGroup this texture belongs to, or null if it belongs to no group.
     */
    TextureGroup getGroup();
}
