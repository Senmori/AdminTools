package net.senmori.custommobs.client.gui.widget;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.MathHelper;
import net.senmori.custommobs.client.config.ClientConfig;
import net.senmori.custommobs.client.gui.AbstractWidget;
import net.senmori.custommobs.client.gui.widget.api.IPressable;
import net.senmori.custommobs.lib.properties.consumer.DefaultConsumerProperty;
import net.senmori.custommobs.lib.properties.defaults.DefaultObjectProperty;
import net.senmori.custommobs.lib.properties.defaults.DefaultStringProperty;
import net.senmori.custommobs.lib.texture.Button;
import net.senmori.custommobs.lib.texture.ITexture;
import org.lwjgl.glfw.GLFW;

import java.util.function.Consumer;

public abstract class AbstractButton extends AbstractWidget implements IPressable {

    private final DefaultObjectProperty<ITexture> normalTextureProperty = new DefaultObjectProperty<>( this, "texture", Button.NORMAL.getTexture() );
    private final DefaultObjectProperty<ITexture> disabledTextureProperty = new DefaultObjectProperty<>( this, "texture", Button.DISABLED.getTexture() );
    private final DefaultObjectProperty<ITexture> hoverTextureProperty = new DefaultObjectProperty<>( this, "texture", Button.HOVER.getTexture() );
    private final DefaultStringProperty messageProperty = new DefaultStringProperty( this, "button text", "" );
    private final DefaultConsumerProperty<Widget> hoverProperty = new DefaultConsumerProperty<>( this, "hover consumer" );

    public AbstractButton(int xIn, int yIn) {
        super( xIn, yIn );
    }

    public void setText(String text) {
        this.messageProperty.set( text );
    }

    public String getText() {
        return this.messageProperty.get();
    }

    public ITexture getNormalTexture() {
        return this.normalTextureProperty.get();
    }

    public void onPress(Consumer<Widget> consumer) {
        hoverProperty.set( consumer );
    }

    public Consumer<Widget> getHoverConsumer()  {
        return hoverProperty.get();
    }

    /**
     * The narration message for buttons is output at '%s button'.
     * By default, this will play '{@link #getNarrationMessage()} button'.
     * If {@link #getNarrationMessage()} is empty, this button will use {@link #getText()} instead.
     * If both are empty, then no message will be played.
     *
     * @return the narration message
     */
    @Override
    public String getNarrationMessage() {
        if (narrationProperty.get().isEmpty() && !getText().isEmpty()) {
            return I18n.format( "gui.narrate.button", getText() ); // better than nothing I suppose
        }
        return this.narrationProperty.get().isEmpty() ? "" : I18n.format( "gui.narrate.button", narrationProperty.get() );
    }

    @Override
    public void onPress() {
        getHoverConsumer().accept( this );
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
    public void renderButton(int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer fontrenderer = getFontRenderer();
        renderButtonTexture( getTextureForRender() );
        this.renderBg(minecraft, mouseX, mouseX);
        int j = getFGColor();
        this.drawCenteredString(fontrenderer, this.getText(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0F) << 24);
    }

    protected ITexture getTextureForRender() {
        getNormalTexture().getGroup().adjustLayout();
        if (!isEnabled()) {
            return disabledTextureProperty.get();
        } else if (isHovered()) {
            return hoverTextureProperty.get();
        }
        return normalTextureProperty.get();
    }

    /**
     * Buttons are rendered by
     */
    protected void renderButtonTexture(ITexture texture) {
        Minecraft.getInstance().getTextureManager().bindTexture(texture.getLocation());
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, this.alpha);
        int i = this.getYImage(this.isHovered());
        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        int textureX = texture.getX();
        int textureY = texture.getY();
        int middle = this.width / 2;
        this.blit(getX(), getY(), textureX, textureY, middle, getHeight()); // left half
        this.blit(getX() + middle, getY(), ClientConfig.CONFIG.MAX_BUTTON_LENGTH.get() - middle, textureY, middle, getHeight()); // right half
    }
}
