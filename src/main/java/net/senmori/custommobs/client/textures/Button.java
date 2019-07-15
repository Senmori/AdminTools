package net.senmori.custommobs.client.textures;

import net.senmori.custommobs.lib.texture.ITexture;
import net.senmori.custommobs.lib.texture.TextureGroup;

import java.util.Locale;

public enum Button {
    DISABLED( TextureGroups.Buttons.BUTTONS, 1),
    NORMAL(TextureGroups.Buttons.BUTTONS, 2),
    HOVER(TextureGroups.Buttons.BUTTONS, 3)
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

    public ITexture getTexture() {
        return group.getOrAddTexture( getName() );
    }
}
