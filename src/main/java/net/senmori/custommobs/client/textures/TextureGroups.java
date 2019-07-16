package net.senmori.custommobs.client.textures;

import net.minecraft.util.ResourceLocation;
import net.senmori.custommobs.lib.texture.ITextureGroupFactory;
import net.senmori.custommobs.lib.texture.TextureGroup;
import net.senmori.custommobs.lib.texture.TextureGroupLayout;

public final class TextureGroups {
    private static final ResourceLocation ICON_LOCATION = new ResourceLocation( "textures/gui/icons.png" );
    private static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation( "textures/gui/widgets.png" );

    public static final TextureGroup ICONS = ITextureGroupFactory.create( ICON_LOCATION, "GUI Icons" ).build();
    public static final TextureGroup WIDGETS = ITextureGroupFactory.create( WIDGETS_LOCATION, "Widgets" ).build();

    public static class Icons {
        public static final TextureGroup TAB_PING = ITextureGroupFactory.create( ICONS, "Server Ping Icons (Tab)" )
                .startAt( 0, 16 )
                .defaultDimensions( 10, 8 )
                .layout( TextureGroupLayout.VERTICAL )
                .overrideTextureSettings()
                .build();
        public static final TextureGroup HEARTS = ITextureGroupFactory.create( ICONS, "Hearts" )
                .startAt( 16, 0 )
                .defaultDimensions( 9, 9 )
                .layout( TextureGroupLayout.HORIZONTAL )
                .overrideTextureSettings()
                .build();
        public static final TextureGroup ARMOR = ITextureGroupFactory.create( ICONS, "Armor" )
                .startAt( 16, 9 )
                .defaultDimensions( 9, 9 )
                .layout( TextureGroupLayout.HORIZONTAL )
                .overrideTextureSettings()
                .build();
    }

    public static class Buttons {
        public static final TextureGroup BUTTONS = ITextureGroupFactory.create( WIDGETS, "Buttons" )
                .startAt( 0, 46 )
                .defaultDimensions( 200, 20 )
                .layout( TextureGroupLayout.VERTICAL )
                .overrideTextureSettings()
                .build();
        public static final TextureGroup CHECKBOX = ITextureGroupFactory.create( new ResourceLocation("textures/gui/checkbox.png"), "Checkbox" )
                .startAt( 0, 0 )
                .defaultDimensions( 20, 20 )
                .layout( TextureGroupLayout.VERTICAL )
                .overrideTextureSettings()
                .build();
    }

}
