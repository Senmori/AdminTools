package net.senmori.custommobs.client.gui.widget;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.senmori.custommobs.client.gui.AbstractWidget;
import net.senmori.custommobs.lib.input.KeyInput;
import net.senmori.custommobs.lib.properties.defaults.DefaultObjectProperty;
import net.senmori.custommobs.lib.properties.defaults.DefaultStringProperty;
import org.lwjgl.glfw.GLFW;

public abstract class AbstractButton extends AbstractWidget {

    private final DefaultObjectProperty<ResourceLocation> textureProperty = new DefaultObjectProperty<>( this, "texture", Widget.WIDGETS_LOCATION );
    private final DefaultStringProperty messageProperty = new DefaultStringProperty( this, "button text", "" );

    public AbstractButton(int xIn, int yIn) {
        super( xIn, yIn );
    }

    public void setText(String text) {
        this.messageProperty.set( text );
    }

    public String getText() {
        return this.messageProperty.get();
    }

    public void setTexture(ResourceLocation name) {
        this.textureProperty.set( name );
    }

    public ResourceLocation getTexture() {
        return this.textureProperty.get();
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
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if ( this.isEnabled() && this.isVisible() ) {
            if ( keyCode != GLFW.GLFW_KEY_ENTER /* 257 */
                    && keyCode != GLFW.GLFW_KEY_SPACE /* 32 */
                    && keyCode != GLFW.GLFW_KEY_KP_ENTER /* 335*/ ) {
                return false;
            } else {
                KeyInput input = KeyInput.key( KeyInput.Action.PRESS, keyCode, scanCode, modifiers );
                this.playDownSound( Minecraft.getInstance().getSoundHandler() );
                getKeyPressConsumer().accept( this, input );
                return true;
            }
        }
        return false;
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer fontrenderer = getFontRenderer();
        minecraft.getTextureManager().bindTexture(getTexture());
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, this.alpha);
        int i = this.getYImage(this.isHovered());
        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        this.blit(this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
        this.blit(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
        this.renderBg(minecraft, mouseX, mouseX);
        int j = getFGColor();

        this.drawCenteredString(fontrenderer, this.getText(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0F) << 24);

    }
}
