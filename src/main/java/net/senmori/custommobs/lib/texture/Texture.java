package net.senmori.custommobs.lib.texture;

import net.minecraft.util.ResourceLocation;

/**
 * Texture represents any possible icon that can be displayed in a gui.
 * For example, player hearts are icons.
 */
public class Texture implements ITexture {

    private final TextureGroup group;
    private final String name;

    private int x = -1;
    private int y = -1;
    private int width = -1;
    private int height = -1;
    private int textureIndex = 1;

    public Texture(TextureGroup group, String name) {
        this.group = group;
        this.name = name;
    }

    @Override
    public TextureGroup getGroup() {
        return group;
    }

    @Override
    public ResourceLocation getLocation() {
        return group.getLocation();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return group.isOverrideTextureSettings() ? group.getDefaultWidth() : (this.width < 0 ? group.getDefaultWidth() : this.width);
    }

    @Override
    public int getHeight() {
        return group.isOverrideTextureSettings() ? group.getDefaultHeight() : (this.height < 0 ? group.getDefaultHeight() : this.height);
    }

    @Override
    public int getTextureIndex() {
        return textureIndex;
    }

    @Override
    public void setTextureIndex(int index) {
        this.textureIndex = index;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Texture [" + name + "][" + textureIndex + "] : " + "X/Y[" + getX() + "," + getY() +"] ~ W/H[" + getWidth() + "," + getHeight() + "] from \'" + getGroup().getLocation().toString() + "\'";
    }

    public static class Builder {

        private final String name;
        private TextureGroup group;
        private int x,y;
        private int width, height;

        protected Builder(String name) {
            this.name = name;
        }

        public Builder group(TextureGroup group) {
            this.group = group;
            return this;
        }

        public Builder at(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Builder dimensions(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public ITexture build() {
            return new Texture( group, name );
        }
    }
}
