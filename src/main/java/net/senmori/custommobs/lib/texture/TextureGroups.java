package net.senmori.custommobs.lib.texture;

import net.minecraft.util.ResourceLocation;

public abstract class TextureGroups {

    public static final TextureGroup ICONS = ITextureGroupFactory.create( new ResourceLocation( "textures/gui/icons.png" ), "GUI Icons" )
            .startAt( 0, 0 ).build();
    public static final TextureGroup WIDGETS = ITextureGroupFactory.create( new ResourceLocation( "textures/gui/widgets.png" ), "Widgets" )
            .startAt( 0, 0 ).build();
    public static class Icons {
        public static final TextureGroup TAB_PING = ITextureGroupFactory.create( ICONS,  "Tab Ping icons")
                .startAt( 0,16 )
                .defaultDimensions( 10, 8 )
                .layout( TextureGroupLayout.VERTICAL )
                .overrideTextureSettings()
                .build();
        public static final TextureGroup HEARTS = ITextureGroupFactory.create( ICONS, "Hearts" )
                .startAt( 16, 0 )
                .defaultDimensions( 8, 8 )
                .layout( TextureGroupLayout.HORIZONTAL )
                .overrideTextureSettings()
                .build();
        public static final TextureGroup ARMOR = ITextureGroupFactory.create(ICONS, "Armor")
                .startAt( 16, 9 )
                .defaultDimensions( 9, 9 )
                .layout( TextureGroupLayout.HORIZONTAL )
                .overrideTextureSettings()
                .build();
    }

    public static class Buttons {
        public static final TextureGroup BUTTONS = ITextureGroupFactory.create( WIDGETS, "Buttons" )
                .startAt( 0, 46 )
                .defaultDimensions( 199, 19 )
                .layout( TextureGroupLayout.VERTICAL )
                .overrideTextureSettings()
                .build();
    }

}
