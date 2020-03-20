package net.senmori.admintools.client.gui.widget;

import net.senmori.admintools.client.textures.Button;
import net.senmori.admintools.lib.properties.defaults.DefaultObjectProperty;
import net.senmori.admintools.lib.properties.primitive.BooleanProperty;
import net.senmori.admintools.lib.texture.ITexture;

public abstract class ToggleableButton extends AbstractButton {

    private final BooleanProperty toggledProperty = new BooleanProperty( this, "toggled", false );
    private final DefaultObjectProperty<ITexture> toggledTexture = new DefaultObjectProperty<>( this, "toggled texture", Button.NORMAL.getTexture() );

    public ToggleableButton(int xIn, int yIn) {
        super( xIn, yIn );
    }

    public boolean isToggled() {
        return toggledProperty.get();
    }

    public void setToggled(boolean value) {
        this.toggledProperty.set( value );
    }

    public void setToggledTexture(ITexture texture) {
        this.toggledTexture.set( texture );
    }

    public ITexture getToggledTexture() {
        return toggledTexture.get();
    }

    @Override
    public void onPress() {
        this.setToggled( !isToggled() );
        super.onPress();
    }

    @Override
    protected ITexture getTextureForRender() {
        ITexture texture = super.getTextureForRender();
        if ( isToggled() ) {
            texture = getToggledTexture();
        }
        super.updateDimensions( texture );
        return texture;
    }
}
