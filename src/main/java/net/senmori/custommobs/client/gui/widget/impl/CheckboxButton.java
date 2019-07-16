package net.senmori.custommobs.client.gui.widget.impl;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.widget.Widget;
import net.senmori.custommobs.client.gui.AbstractWidget;
import net.senmori.custommobs.client.gui.widget.AbstractLabel;
import net.senmori.custommobs.client.gui.widget.api.IPressable;
import net.senmori.custommobs.client.gui.widget.api.IUpdatable;
import net.senmori.custommobs.client.textures.Button;
import net.senmori.custommobs.lib.properties.consumer.DefaultConsumerProperty;
import net.senmori.custommobs.lib.properties.defaults.DefaultObjectProperty;
import net.senmori.custommobs.lib.properties.primitive.BooleanProperty;
import net.senmori.custommobs.lib.texture.ITexture;
import org.lwjgl.glfw.GLFW;

import java.util.function.Consumer;

public class CheckboxButton extends AbstractWidget<CheckboxButton> implements IPressable, IUpdatable {

    private final BooleanProperty pressedProperty = new BooleanProperty( this, "pressed", false );
    private final BooleanProperty textureDefinesDimensions = new BooleanProperty( this, "dimension definition", true );
    private final DefaultObjectProperty<ITexture> defaultTexture = new DefaultObjectProperty<>( this, "unchecked texture", Button.CHECKBOX.getTexture() );
    private final DefaultObjectProperty<ITexture> disabledTexture = new DefaultObjectProperty<>( this, "disabled texture", Button.CHECKBOX.getTexture() );
    private final DefaultObjectProperty<ITexture> checkedTexture = new DefaultObjectProperty<>( this, "checked texture", Button.CHECKBOX_FILLED.getTexture() );

    private final DefaultConsumerProperty<Widget> tickConsumer = new DefaultConsumerProperty<>( this, "tick consumer" );
    private final DefaultConsumerProperty<Widget> hoverProperty = new DefaultConsumerProperty<>( this, "hover consumer" );
    private final DefaultConsumerProperty<Widget> clickConsumer = new DefaultConsumerProperty<>( this, "click consumer" );

    private AbstractLabel label = null;

    public CheckboxButton(int xIn, int yIn) {
        super( xIn, yIn );
        setDimensions( getDefaultTexture() );
    }

    private void setDimensions(ITexture texture) {
        if (textureDefinesDimensions.get()) {
            setDimensions( texture.getWidth(), texture.getHeight() );
        }
    }

    @Override
    public void onPress() {
        setIsPressed( !isPressed() );
        getOnClickConsumer().accept( this );
    }

    @Override
    public void tick() {
        getTickConsumer().accept( this );
        if (isHovered()) {
            getHoverConsumer().accept( this );
        }
    }

    protected ITexture getTextureForRender() {
        ITexture texture = getDefaultTexture();
        if (!isEnabled()) {
            texture = getDisabledTexture();
        } else if (isPressed()) {
            texture = getCheckedTexture();
        }
        if (doTexturesDefineDimensions()) {
            if (texture.getWidth() != getWidth()) {
                setWidth( texture.getWidth() );
            }
            if (texture.getHeight() != getHeight()) {
                setHeight( texture.getHeight() );
            }
        }
        return texture;
    }

    /**
     * This method will not accept any input other than 'Enter', 'Space', or 'Keypad Enter'.
     *
     * @return true if one of the valid inputs are encountered.
     */
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if ( this.isEnabled() && this.isVisible() ) {
            if ( keyCode != GLFW.GLFW_KEY_ENTER /* 257 */
                    && keyCode != GLFW.GLFW_KEY_SPACE /* 32 */
                    && keyCode != GLFW.GLFW_KEY_KP_ENTER /* 335*/ ) {
                return false;
            } else {
                this.playDownSound( Minecraft.getInstance().getSoundHandler() );
                onPress();
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        onPress();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        if (getLabel() != null) {
            getLabel().calculateLayout();
            getLabel().render( mouseX, mouseY, partialTicks );
        }
        super.render( mouseX, mouseY, partialTicks );
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float partialTicks) {
        if (getLabel() != null) {
            getLabel().renderButton( mouseX, mouseY, partialTicks );
        }
        renderCheckbox( getTextureForRender() );
    }

    @Override
    public void printDebug() {
        super.printDebug();
        if (getLabel() != null) {
            getLabel().printDebug();
        }
    }

    protected void renderCheckbox(ITexture texture) {
        Minecraft.getInstance().getTextureManager().bindTexture(texture.getLocation());
        GlStateManager.enableDepthTest();
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, this.alpha);
        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        AbstractGui.blit(this.x, this.y, texture.getX(), texture.getY(), getWidth(), getHeight(), 32, 64);
    }

    public boolean isPressed() {
        return pressedProperty.get();
    }

    public void setIsPressed(boolean value) {
        this.pressedProperty.set( value );
    }

    public boolean doTexturesDefineDimensions() {
        return textureDefinesDimensions.get();
    }

    public void setTextureDefinesDimensions(boolean value) {
        this.textureDefinesDimensions.set( value );
    }

    public ITexture getDefaultTexture() {
        return defaultTexture.get();
    }

    public void setDefaultTexture(ITexture texture) {
        this.defaultTexture.set( texture );
    }

    public ITexture getCheckedTexture() {
        return checkedTexture.get();
    }

    public void setCheckTexture(ITexture texture) {
        this.checkedTexture.set( texture );
    }

    private ITexture getDisabledTexture() {
        return disabledTexture.get();
    }

    public void setDisabledTexture(ITexture texture) {
        this.disabledTexture.set( texture );
    }

    public Consumer<Widget> getTickConsumer() {
        return tickConsumer.get();
    }

    public void onTick(Consumer<Widget> consumer) {
        this.tickConsumer.set( consumer );
    }

    public Consumer<Widget> getHoverConsumer() {
        return hoverProperty.get();
    }

    public void onHover(Consumer<Widget> consumer) {
        this.hoverProperty.set( consumer );
    }

    public Consumer<Widget> getOnClickConsumer() {
        return clickConsumer.get();
    }

    public void onClick(Consumer<Widget> consumer) {
        this.clickConsumer.set( consumer );
    }

    /**
     * Add a new label to this text field.
     *
     * @param text the text of the label
     * @param position the {@link AbstractLabel.Position} of the label
     * @return the new {@link AbstractLabel}
     */
    public <T extends AbstractLabel> T addLabel(String text, T.Position position) {
        Label label = new Label(this.x, this.y);
        label.setText( text );
        label.setPosition( position );
        label.setParent( this );
        label.calculateLayout();
        this.label = label;
        return (T) label;
    }

    /**
     * Add a new label to this text field.
     *
     * @param label the label to add
     * @return the {@link AbstractLabel}.
     */
    public <T extends AbstractLabel> T addLabel(T label) {
        label.setParent( this );
        label.calculateLayout();
        this.label = label;
        return label;
    }

    private AbstractLabel getLabel() {
        return label;
    }
}
