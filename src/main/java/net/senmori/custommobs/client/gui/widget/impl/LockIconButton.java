package net.senmori.custommobs.client.gui.widget.impl;

import net.senmori.custommobs.client.gui.widget.ToggleableButton;
import net.senmori.custommobs.client.textures.Button;
import net.senmori.custommobs.lib.api.Procedure;
import net.senmori.custommobs.lib.properties.defaults.DefaultObjectProperty;
import net.senmori.custommobs.lib.texture.ITexture;

public class LockIconButton extends ToggleableButton {

    private final DefaultObjectProperty<ITexture> unlockedNormal = new DefaultObjectProperty<>( this, "unlocked normal", Button.CB_UNLOCKED_NORMAL.getTexture() );
    private final DefaultObjectProperty<ITexture> unlockedHover = new DefaultObjectProperty<>( this, "unlocked normal", Button.CB_UNLOCKED_HOVER.getTexture() );
    private final DefaultObjectProperty<ITexture> unlockedDisabled = new DefaultObjectProperty<>( this, "unlocked normal", Button.CB_UNLOCKED_DISABLED.getTexture() );

    private final DefaultObjectProperty<ITexture> lockedNormal = new DefaultObjectProperty<>( this, "locked normal", Button.CB_LOCKED_NORMAL.getTexture() );
    private final DefaultObjectProperty<ITexture> lockedHover = new DefaultObjectProperty<>( this, "locked normal", Button.CB_LOCKED_HOVER.getTexture() );
    private final DefaultObjectProperty<ITexture> lockedDisabled = new DefaultObjectProperty<>( this, "locked normal", Button.CB_LOCKED_DISABLED.getTexture() );

    private final Procedure<ITexture> normalTextureProcedure = () -> isLocked() ? getLockedTexture() : getUnlockedTexture();
    private final Procedure<ITexture> disabledTextureProcedure = () -> isLocked() ?  getLockedDisabledTexture() : getUnlockedDisabledTexture();
    private final Procedure<ITexture> hoverTextureProcedure = () -> isLocked() ? getLockedHoverTexture() : getUnlockedHoverTexture();

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
    protected ITexture getTextureForRender() {
        if (!isEnabled()) {
            return disabledTextureProcedure.execute();
        }
        if (isHovered()) {
            return hoverTextureProcedure.execute();
        }
        ITexture texture = normalTextureProcedure.execute();
        super.updateDimensions( texture );
        return texture;
    }


    public ITexture getUnlockedTexture() {
        return unlockedNormal.get();
    }

    public void setUnlockedTexture(ITexture texture) {
        unlockedNormal.set( texture );
    }

    public ITexture getUnlockedDisabledTexture() {
        return this.unlockedDisabled.get();
    }

    public void setUnlockedDisabled(ITexture texture) {
        this.unlockedDisabled.set( texture );
    }

    public ITexture getUnlockedHoverTexture() {
        return this.unlockedHover.get();
    }

    public void setUnlockedHover(ITexture texture) {
        this.unlockedHover.set( texture );
    }

    public ITexture getLockedTexture() {
        return this.lockedNormal.get();
    }

    public void setLockedTexture(ITexture texture) {
        this.lockedNormal.set( texture );
    }

    public ITexture getLockedDisabledTexture() {
        return lockedDisabled.get();
    }

    public void setLockedDisabledTexture(ITexture texture) {
        this.lockedDisabled.set( texture );
    }

    public ITexture getLockedHoverTexture() {
        return this.lockedHover.get();
    }

    public void setLockedHoverTexture(ITexture texture) {
        this.lockedHover.set( texture );
    }
}
