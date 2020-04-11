package net.senmori.admintools.client.gui.widget;

import net.senmori.admintools.client.textures.Button;
import net.senmori.admintools.lib.properties.primitive.BooleanProperty;
import net.senmori.admintools.lib.properties.primitive.ObjectProperty;
import net.senmori.admintools.lib.texture.Texture;

public abstract class ToggleableButton extends AbstractButton {

    private final BooleanProperty toggledProperty = new BooleanProperty( "toggled", false );
    private final ObjectProperty<Texture> toggledTexture = new ObjectProperty<>( "toggled texture", Button.NORMAL.getTexture() );

    public ToggleableButton(int xIn, int yIn) {
        super( xIn, yIn );
    }

    public boolean isToggled() {
        return toggledProperty.get();
    }

    public void setToggled(boolean value) {
        this.toggledProperty.set( value );
    }

    public void setToggledTexture(Texture texture) {
        this.toggledTexture.set( texture );
    }

    public Texture getToggledTexture() {
        return toggledTexture.get();
    }

    @Override
    public void onPress() {
        this.setToggled( !isToggled() );
        super.onPress();
    }

    @Override
    protected Texture getTextureForRender() {
        Texture texture = super.getTextureForRender();
        if ( isToggled() ) {
            texture = getToggledTexture();
        }
        super.updateDimensions( texture );
        return texture;
    }
}
