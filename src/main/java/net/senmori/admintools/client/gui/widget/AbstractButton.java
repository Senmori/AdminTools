package net.senmori.admintools.client.gui.widget;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.senmori.admintools.client.gui.AbstractWidget;
import net.senmori.admintools.client.gui.widget.api.Attachable;
import net.senmori.admintools.client.gui.widget.api.Pressable;
import net.senmori.admintools.client.gui.widget.api.Updatable;
import net.senmori.admintools.client.textures.Button;
import net.senmori.admintools.config.ForgeClientConfig;
import net.senmori.admintools.lib.properties.color.ColorProperty;
import net.senmori.admintools.lib.properties.consumer.ConsumerProperty;
import net.senmori.admintools.lib.properties.primitive.BooleanProperty;
import net.senmori.admintools.lib.properties.primitive.ObjectProperty;
import net.senmori.admintools.lib.properties.primitive.StringProperty;
import net.senmori.admintools.lib.texture.Texture;
import org.lwjgl.glfw.GLFW;

import java.awt.Color;
import java.util.function.Consumer;

@OnlyIn( Dist.CLIENT )
public abstract class AbstractButton extends AbstractWidget<AbstractButton> implements Pressable, Updatable {
    // textures
    private final ObjectProperty<Texture> defaultTextureProperty = new ObjectProperty<>( "default texture", Button.NORMAL.getTexture() );
    private final ObjectProperty<Texture> disabledTextureProperty = new ObjectProperty<>( " disabled texture", Button.DISABLED.getTexture() );
    private final ObjectProperty<Texture> hoverTextureProperty = new ObjectProperty<>( "hover texture", Button.HOVER.getTexture() );
    // button text
    private final StringProperty textProperty = new StringProperty( "button text", "" );
    // colors - taken from Widget#getFGColor - unpacked and converted into a nice RGB format
    private final ColorProperty enabledColor = ColorProperty.of( "enabled color", new Color( 224, 224, 224 ) );
    private final ColorProperty disabledColor = ColorProperty.of( "disabled color", new Color( 160, 160, 160 ) );
    private final ColorProperty hoverColor = ColorProperty.of( "hover color", new Color( 255, 255, 160 ) );
    // consumers
    private final ConsumerProperty<Widget> tickConsumer = ConsumerProperty.of( "tick consumer" );
    private final ConsumerProperty<Widget> hoverProperty = ConsumerProperty.of( "hover consumer" );
    private final ConsumerProperty<Widget> clickProperty = ConsumerProperty.of( "click consumer" );

    // other
    private final BooleanProperty textureDefinesDimensions = new BooleanProperty( "dimension definition", true );
    private final ObjectProperty<Attachable> attachedWidget = new ObjectProperty<>( "attached widget", null );

    public AbstractButton(int xIn, int yIn) {
        super( xIn, yIn );

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
        if ( narrationProperty.get().isEmpty() && !getText().isEmpty() ) {
            return I18n.format( "gui.narrate.button", getText() ); // better than nothing I suppose
        }
        return this.narrationProperty.get().isEmpty() ? "" : I18n.format( "gui.narrate.button", narrationProperty.get() );
    }

    @Override
    public void onPress() {
        getClickProperty().accept( this );
    }


    @Override
    public void tick() {
        getTickConsumer().accept( this );
        if ( isHovered() ) {
            getHoverConsumer().accept( this );
        }
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
                    && keyCode != GLFW.GLFW_KEY_KP_ENTER /* 335 */ ) {
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
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        onPress();
        return super.mouseClicked( mouseX, mouseY, button );
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer fontrenderer = getFontRenderer();
        renderButtonTexture( getTextureForRender() );
        this.renderBg( minecraft, mouseX, mouseX );
        int x = this.x + this.width / 2;
        int y = this.y + ( this.height - 8 ) / 2;
        Color color = getTextColorForRender();
        this.drawCenteredString( fontrenderer, this.getText(), x, y, color.getRGB() );
    }

    protected Color getTextColorForRender() {
        if ( !isEnabled() ) {
            return disabledColor.get();
        } else if ( isHovered() ) {
            return hoverColor.get();
        }
        return enabledColor.get();
    }

    protected Texture getTextureForRender() {
        Texture texture = getDefaultTexture();
        if ( !isEnabled() ) {
            texture = getDisabledTexture();
        } else if ( isHovered() ) {
            texture = getHoverTexture();
        }
        if ( texture != null ) {
            updateDimensions( texture );
        }
        return texture;
    }

    protected void updateDimensions(Texture texture) {
        if ( doTexturesDefineDimensions() && texture != null ) {
            if ( texture.getWidth() != getWidth() ) {
                setWidth( texture.getWidth() );
            }
            if ( texture.getHeight() != getHeight() ) {
                setHeight( texture.getHeight() );
            }
        }
        if ( texture != null ) {
            texture.getGroup().adjustLayout();
        }
    }

    /**
     * Buttons are rendered by this method
     */
    protected void renderButtonTexture(Texture texture) {
        Minecraft.getInstance().getTextureManager().bindTexture( texture.getLocation() );
        RenderSystem.color4f( 1.0F, 1.0F, 1.0F, this.alpha );
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate( GlStateManager.SourceFactor.SRC_ALPHA.param, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.param, GlStateManager.SourceFactor.ONE.param, GlStateManager.DestFactor.ZERO.param );
        RenderSystem.blendFunc( GlStateManager.SourceFactor.SRC_ALPHA.param, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA.param );
        int textureX = texture.getX();
        int textureY = texture.getY();
        int middle = this.width / 2;
        this.blit( getX(), getY(), textureX, textureY, middle, getHeight() ); // left half
        this.blit( getX() + middle, getY(), ForgeClientConfig.get().MAX_BUTTON_LENGTH.get() - middle, textureY, middle, getHeight() ); // right half
    }

    public Color getEnabledColor() {
        return enabledColor.get();
    }

    public void setEnabledTextColor(Color color) {
        this.enabledColor.set( color );
    }

    public Color getDisabledColor() {
        return disabledColor.get();
    }

    public void setDisabledTextColor(Color color) {
        this.disabledColor.set( color );
    }

    public Color getHoverColor() {
        return hoverColor.get();
    }

    public void setHoverTextColor(Color color) {
        this.hoverColor.set( color );
    }

    public void setText(String text) {
        this.textProperty.set( text );
    }

    public String getText() {
        return this.textProperty.get();
    }

    public Texture getDefaultTexture() {
        return this.defaultTextureProperty.get();
    }

    public void setDefaultTexture(Texture texture) {
        this.defaultTextureProperty.set( texture );
        getDefaultTexture().getGroup().adjustLayout();
    }

    public Texture getDisabledTexture() {
        return disabledTextureProperty.get();
    }

    public void setDisabledTexture(Texture texture) {
        this.disabledTextureProperty.set( texture );
        disabledTextureProperty.get().getGroup().adjustLayout();
    }

    public Texture getHoverTexture() {
        return this.hoverTextureProperty.get();
    }

    public void setHoverTexture(Texture texture) {
        this.hoverTextureProperty.set( texture );
        hoverTextureProperty.get().getGroup().adjustLayout();
    }

    public void onPress(Consumer<Widget> consumer) {
        hoverProperty.set( consumer );
    }

    public Consumer<Widget> getHoverConsumer() {
        return hoverProperty.get();
    }

    public Consumer<Widget> getTickConsumer() {
        return tickConsumer.get();
    }

    public void onTick(Consumer<Widget> consumer) {
        this.tickConsumer.set( consumer );
    }

    public Consumer<Widget> getClickProperty() {
        return clickProperty.get();
    }

    public void onClick(Consumer<Widget> consumer) {
        this.clickProperty.set( consumer );
    }

    public boolean doTexturesDefineDimensions() {
        return textureDefinesDimensions.get();
    }
}
