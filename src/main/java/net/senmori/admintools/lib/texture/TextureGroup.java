package net.senmori.admintools.lib.texture;

import com.google.common.collect.ImmutableList;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TextureGroup {
    private final String groupName;
    private final ResourceLocation location;
    private final Collection<ITexture> textures = new ArrayList<>();
    private final Collection<TextureGroup> textureGroups = new ArrayList<>();

    private final int startX;
    private final int startY;
    private final int defaultWidth, defaultHeight;
    private final int maxWidth, maxHeight;
    private final boolean overrideTextureSettings;
    private final TextureGroupLayout layout;
    private final AtomicInteger currentTextureIndex = new AtomicInteger( 1 );

    private TextureGroup(ResourceLocation file, String name, int startX,
                         int startY, int defaultWidth, int defaultHeight,
                         int maxWidth, int maxHeight, boolean overrideTextureSettings,
                         TextureGroupLayout layout) {
        this.groupName = name;
        this.location = file;
        this.startX = startX;
        this.startY = startY;
        this.defaultWidth = defaultWidth;
        this.defaultHeight = defaultHeight;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.overrideTextureSettings = overrideTextureSettings;
        this.layout = layout;
    }

    public void adjustLayout() {
        textures.forEach( tex -> layout.adjust( tex, this ) );
        textureGroups.forEach( TextureGroup::adjustLayout );
    }

    public ITexture addTexture(String name) {
        ITexture texture = ITexture.create( name ).group( this ).build();
        textures.add( texture );
        return texture;
    }

    public ITexture addTexture(ITexture texture) {
        if (texture.getGroup() == this) {
            textures.add( texture );
            return texture;
        }
        throw new UnsupportedOperationException( "Attempted to add texture with a group of \'" + texture.getGroup().getName() + "\' to \'" + this.getName() + "\'" );
    }

    protected int getNextIndex() {
        return currentTextureIndex.getAndIncrement();
    }

    public ITexture getOrAddTexture(String name) {
        ITexture texture = findTextureByName( name );
        if (texture != null) return texture;
        return addTexture( name );
    }

    public ITexture findTextureByName(String name) {
        ITexture texture = textures.stream().filter( tex -> tex.getName().equals( name ) ).findFirst().orElse( null );
        if (texture != null) {
            return texture;
        }
        for (TextureGroup group : textureGroups) {
            texture = group.findTextureByName( name );
            if (texture != null) {
                return texture;
            }
        }
        return null;
    }

    public ITexture findTextureByCoordinate(int x, int y) {
        for (ITexture texture : textures) {
            int startX = texture.getX();
            int startY = texture.getY();
            int endX = startX + texture.getWidth();
            int endY = startY + texture.getHeight();
            if (x >= startX && x < endX && y >= startY && y < endY) {
                return texture;
            }
        }
        for (TextureGroup group : textureGroups) {
            ITexture texture = group.findTextureByCoordinate( x, y );
            if (texture != null) {
                return null;
            }
        }
        return null;
    }

    public TextureGroup getGroupByName(String groupName) {
        return textureGroups.stream().filter( group -> group.getName().equals( groupName ) ).findFirst().orElse( null );
    }

    public TextureGroup getGroupByCoordinate(int x, int y) {
        ITexture texture = findTextureByCoordinate( x, y );
        return texture != null ? texture.getGroup() : this;
    }

    public ResourceLocation getLocation() {
        return location;
    }

    public String getName() {
        return groupName;
    }

    public List<ITexture> getTextures() {
        return ImmutableList.copyOf(textures);
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public int getDefaultWidth() {
        return defaultWidth;
    }

    public int getDefaultHeight() {
        return defaultHeight;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public boolean isOverrideTextureSettings() {
        return overrideTextureSettings;
    }

    public void addFromGroup(TextureGroup group) {
        group.getTextures().forEach( this::addTexture );
    }

    /**
     * This builder helps users create easy TextureGroups for easy organization of textures.
     */
    public static class Builder {
        private final ResourceLocation location;
        private final String name;

        private int startX = 0;
        private int startY = 0;
        private int defaultWidth = -1;
        private int defaultHeight = -1;
        private int maxWidth = -1;
        private int maxHeight = -1;
        private boolean overrideTextureSettings;
        private TextureGroupLayout layout = TextureGroupLayout.NONE;

        protected Builder(ResourceLocation location, String name) {
            this.location = location;
            this.name = name;
        }

        /**
         * The x and y coordinates that this texture group should start at.
         * By default, these are 0. Pertaining to the top-left corner of the sprite sheet.
         *
         * @param x the x-coordinate this group should start at
         * @param y the y-coordinate this group should start at
         * @return this builder
         */
        public Builder startAt(int x, int y) {
            this.startX = Math.max(0, x);
            this.startY = Math.max(0, y);
            return this;
        }

        /**
         * The default dimensions that should be assumed for any textures that do not specify
         * and width or height.
         * By default, these are -1. This means all textures within the TextureGroup must specify their
         * own width and height.
         *
         * @param width the default width
         * @param height the default height
         * @return this builder
         */
        public Builder defaultDimensions(int width, int height) {
            this.defaultWidth = Math.max(0, width);
            this.defaultHeight = Math.max(0, height);
            return this;
        }

        /**
         * Set the maximum allowed x and y coordinates for this texture group.
         * By default, these are -1. This means there are no checks in place to guarantee a texture's
         * x and y coordinates are within the sprite sheet.
         *
         * @param width the maximum width
         * @param height the maximum height
         * @return this builder
         */
        public Builder maxDimensions(int width, int height) {
            this.maxWidth = Math.max( 0, width );
            this.maxHeight = Math.max(0, height );
            return this;
        }

        /**
         * If set, this TextureGroup's settings will override any member Texture's settings.
         *
         * @return this builder
         */
        public Builder overrideTextureSettings() {
            this.overrideTextureSettings = true;
            return this;
        }

        /**
         * Set how the texture's within this group are laid out.
         * By default, this is {@link TextureGroupLayout#NONE}.
         *
         * @param layout the new {@link TextureGroupLayout}
         * @return this builder
         */
        public Builder layout(TextureGroupLayout layout) {
            this.layout = layout;
            return this;
        }

        /**
         * @return a new {@link TextureGroup}
         */
        public TextureGroup build() {
            return new TextureGroup(
                    location, name, startX,
                    startY, defaultWidth, defaultHeight,
                    maxWidth, maxHeight, overrideTextureSettings,
                    layout
            );
        }
    }
}
