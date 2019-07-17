package net.senmori.custommobs.client.textures;

import net.minecraft.util.ResourceLocation;
import net.senmori.custommobs.lib.texture.ITextureGroupFactory;
import net.senmori.custommobs.lib.texture.TextureGroup;
import net.senmori.custommobs.lib.texture.TextureGroupLayout;

public final class TextureGroups {
    private static final ResourceLocation ICON_LOCATION = new ResourceLocation( "textures/gui/icons.png" );
    private static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation( "textures/gui/widgets.png" );

    /** All icons contained within the 'textures/gui/icons.png' sprite sheet. */
    public static final TextureGroup ICONS = ITextureGroupFactory.create( ICON_LOCATION, "GUI Icons" ).build();

    /** All textures contained within the 'textures/gui/widgets.png' texture sheet. */
    public static final TextureGroup WIDGETS = ITextureGroupFactory.create( WIDGETS_LOCATION, "Widgets" ).build();

    /**
     *  All textures within the 'textures/gui/icons.png' sprite sheet.
     */
    public static class Icons {
        /** The ping icons that show up when TAB is pressed in-game. */
        public static final TextureGroup TAB_PING = ITextureGroupFactory.create( ICONS, "Server Ping Icons (Tab)" )
                .startAt( 0, 16 )
                .defaultDimensions( 10, 8 )
                .layout( TextureGroupLayout.VERTICAL )
                .overrideTextureSettings()
                .build();
        /** The heart icons which represent the player's health */
        public static final TextureGroup HEARTS = ITextureGroupFactory.create( ICONS, "Hearts" )
                .startAt( 16, 0 )
                .defaultDimensions( 9, 9 )
                .layout( TextureGroupLayout.HORIZONTAL )
                .overrideTextureSettings()
                .build();
        /** The armor icons that are rendered when a player has armor equipped. */
        public static final TextureGroup ARMOR = ITextureGroupFactory.create( ICONS, "Armor" )
                .startAt( 16, 9 )
                .defaultDimensions( 9, 9 )
                .layout( TextureGroupLayout.HORIZONTAL )
                .overrideTextureSettings()
                .build();
    }


    /**
     * All textures that are buttons are contained within this holder class.
     */
    public static class Buttons {
        /** All button textures. */
        public static final TextureGroup BUTTONS = ITextureGroupFactory.create( WIDGETS, "Buttons" )
                .startAt( 0, 46 )
                .defaultDimensions( 200, 20 )
                .layout( TextureGroupLayout.VERTICAL )
                .overrideTextureSettings()
                .build();
        /** These textures are not contained in the 'textures/gui/widgets.png' but within 'texture/gui/checkbox.png' */
        public static final TextureGroup CHECKBOX = ITextureGroupFactory.create( new ResourceLocation("textures/gui/checkbox.png"), "Checkbox" )
                .startAt( 0, 0 )
                .defaultDimensions( 20, 20 )
                .layout( TextureGroupLayout.VERTICAL )
                .overrideTextureSettings()
                .build();
    }

}
