package net.senmori.admintools.client.textures;

import net.senmori.admintools.lib.texture.Texture;
import net.senmori.admintools.lib.texture.TextureGroup;

import java.util.Locale;

public enum Button {
    DISABLED( TextureGroups.Buttons.BUTTONS, 1),
    NORMAL(TextureGroups.Buttons.BUTTONS, 2),
    HOVER(TextureGroups.Buttons.BUTTONS, 3),
    // Checkbox
    CHECKBOX(TextureGroups.Buttons.CHECKBOX, 1),
    CHECKBOX_FILLED(TextureGroups.Buttons.CHECKBOX, 2),
    // Locked Icons
    CB_LOCKED_NORMAL(TextureGroups.Buttons.LOCKED_ICON, 1),
    CB_LOCKED_HOVER(TextureGroups.Buttons.LOCKED_ICON, 2),
    CB_LOCKED_DISABLED(TextureGroups.Buttons.LOCKED_ICON, 3),
    //  Unlocked Icons
    CB_UNLOCKED_NORMAL(TextureGroups.Buttons.UNLOCK_ICON, 1),
    CB_UNLOCKED_HOVER(TextureGroups.Buttons.UNLOCK_ICON, 2),
    CB_UNLOCKED_DISABLED(TextureGroups.Buttons.UNLOCK_ICON, 3)
    ;


    private final TextureGroup group;

    Button(TextureGroup group, int index) {
        this.group = group;
        group.addTexture( getName() ).setTextureIndex( index );
        group.adjustLayout();
    }

    public String getName() {
        return name().toLowerCase( Locale.ENGLISH );
    }

    public Texture getTexture() {
        return group.getOrAddTexture( getName() );
    }
}
