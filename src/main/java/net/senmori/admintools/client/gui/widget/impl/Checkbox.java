package net.senmori.admintools.client.gui.widget.impl;

import net.senmori.admintools.client.gui.widget.ToggleableButton;
import net.senmori.admintools.client.textures.Button;

public class Checkbox extends ToggleableButton {
    public Checkbox(int xIn, int yIn) {
        super( xIn, yIn );
        setDefaultTexture( Button.CHECKBOX.getTexture() );
        setDisabledTexture( Button.CHECKBOX.getTexture() );
        setToggledTexture( Button.CHECKBOX_FILLED.getTexture() );
        setHoverTexture( Button.CHECKBOX.getTexture() );
    }
}
