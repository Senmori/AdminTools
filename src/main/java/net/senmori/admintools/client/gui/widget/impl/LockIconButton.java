package net.senmori.admintools.client.gui.widget.impl;

import net.senmori.admintools.client.gui.widget.ToggleableButton;
import net.senmori.admintools.client.textures.Button;
import net.senmori.admintools.lib.properties.primitive.ObjectProperty;
import net.senmori.admintools.lib.texture.Texture;

import java.util.function.Supplier;

public class LockIconButton extends ToggleableButton {

    private final ObjectProperty<Texture> unlockedNormal = new ObjectProperty<>( "unlocked normal", Button.CB_UNLOCKED_NORMAL.getTexture() );
    private final ObjectProperty<Texture> unlockedHover = new ObjectProperty<>( "unlocked hover", Button.CB_UNLOCKED_HOVER.getTexture() );
    private final ObjectProperty<Texture> unlockedDisabled = new ObjectProperty<>( "unlocked disabled", Button.CB_UNLOCKED_DISABLED.getTexture() );

    private final ObjectProperty<Texture> lockedNormal = new ObjectProperty<>( "locked normal", Button.CB_LOCKED_NORMAL.getTexture() );
    private final ObjectProperty<Texture> lockedHover = new ObjectProperty<>( "locked hover", Button.CB_LOCKED_HOVER.getTexture() );
    private final ObjectProperty<Texture> lockedDisabled = new ObjectProperty<>( "locked disabled", Button.CB_LOCKED_DISABLED.getTexture() );

    private final Supplier<Texture> normalTextureProcedure = () -> isLocked() ? getLockedTexture() : getUnlockedTexture();
    private final Supplier<Texture> disabledTextureProcedure = () -> isLocked() ? getLockedDisabledTexture() : getUnlockedDisabledTexture();
    private final Supplier<Texture> hoverTextureProcedure = () -> isLocked() ? getLockedHoverTexture() : getUnlockedHoverTexture();

    public LockIconButton(int xIn, int yIn) {
        super( xIn, yIn );
        setDefaultTexture( Button.CB_UNLOCKED_NORMAL.getTexture() );
        setDisabledTexture( Button.CB_UNLOCKED_DISABLED.getTexture() );
        setHoverTexture( Button.CB_UNLOCKED_NORMAL.getTexture() );
        setToggledTexture( Button.CB_LOCKED_NORMAL.getTexture() );
    }

    public boolean isLocked() {
        return super.isToggled();
    }

    public void setLocked(boolean locked) {
        super.setToggled( locked );
    }

    @Override
    protected Texture getTextureForRender() {
        if ( !isEnabled() ) {
            return disabledTextureProcedure.get();
        }
        if ( isHovered() ) {
            return hoverTextureProcedure.get();
        }
        Texture texture = normalTextureProcedure.get();
        super.updateDimensions( texture );
        return texture;
    }


    public Texture getUnlockedTexture() {
        return unlockedNormal.get();
    }

    public void setUnlockedTexture(Texture texture) {
        unlockedNormal.set( texture );
    }

    public Texture getUnlockedDisabledTexture() {
        return this.unlockedDisabled.get();
    }

    public void setUnlockedDisabled(Texture texture) {
        this.unlockedDisabled.set( texture );
    }

    public Texture getUnlockedHoverTexture() {
        return this.unlockedHover.get();
    }

    public void setUnlockedHover(Texture texture) {
        this.unlockedHover.set( texture );
    }

    public Texture getLockedTexture() {
        return this.lockedNormal.get();
    }

    public void setLockedTexture(Texture texture) {
        this.lockedNormal.set( texture );
    }

    public Texture getLockedDisabledTexture() {
        return lockedDisabled.get();
    }

    public void setLockedDisabledTexture(Texture texture) {
        this.lockedDisabled.set( texture );
    }

    public Texture getLockedHoverTexture() {
        return this.lockedHover.get();
    }

    public void setLockedHoverTexture(Texture texture) {
        this.lockedHover.set( texture );
    }
}
