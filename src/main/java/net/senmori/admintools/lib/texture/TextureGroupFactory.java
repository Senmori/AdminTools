package net.senmori.admintools.lib.texture;

import net.minecraft.util.ResourceLocation;

public interface TextureGroupFactory {
    public static TextureGroup.Builder create(ResourceLocation location, String name) {
        return new TextureGroup.Builder( location, name );
    }

    public static TextureGroup.Builder create(TextureGroup parent, String name) {
        return new TextureGroup.Builder( parent.getLocation(), name );
    }
}
