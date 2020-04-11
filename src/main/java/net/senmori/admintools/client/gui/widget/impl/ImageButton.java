package net.senmori.admintools.client.gui.widget.impl;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.senmori.admintools.client.gui.AbstractWidget;
import net.senmori.admintools.client.gui.widget.api.Pressable;
import net.senmori.admintools.lib.input.KeyInput;
import net.senmori.admintools.lib.properties.consumer.ConsumerProperty;
import net.senmori.admintools.lib.properties.primitive.ObjectProperty;
import net.senmori.admintools.lib.texture.Texture;
import net.senmori.admintools.lib.util.RenderUtil;
import net.senmori.admintools.util.KeyboardUtil;
import org.lwjgl.glfw.GLFW;

import java.util.function.Consumer;
import java.util.function.Predicate;

@OnlyIn( Dist.CLIENT )
public class ImageButton extends AbstractWidget<ImageButton> implements Pressable
{
    private final ObjectProperty<Texture> textureProperty = new ObjectProperty<>("texture", null);
    private final ObjectProperty<Texture> onHoverTexture = new ObjectProperty<>("on hover texture", null);
    private final ConsumerProperty<Widget> hoverConsumer = ConsumerProperty.of("hover consumer");
    private final ConsumerProperty<Widget> pressConsumer = ConsumerProperty.of("press consumer");

    private final Predicate<KeyInput> invalidKeyPress;

    public ImageButton(int xIn, int yIn)
    {
        super(xIn, yIn);
        invalidKeyPress = KeyboardUtil.inputMatches(GLFW.GLFW_KEY_ENTER)
                .or(KeyboardUtil.inputMatches(GLFW.GLFW_KEY_SPACE)
                        .or(KeyboardUtil.inputMatches(GLFW.GLFW_KEY_KP_ENTER)));
    }

    public void setTexture(Texture texture)
    {
        textureProperty.set(texture);
        if ( texture != null ) {
            texture.getGroup().adjustLayout();
            setDimensions(texture.getWidth(), texture.getHeight());
        }
    }

    public void setHoverTexture(Texture texture)
    {
        onHoverTexture.set(texture);
        if ( texture != null ) {
            texture.getGroup().adjustLayout();
        }
    }

    public Texture getTexture()
    {
        return isHovered() && getHoverTexture() != null ? getHoverTexture() : textureProperty.get();
    }

    public Texture getHoverTexture()
    {
        return onHoverTexture.get();
    }

    public void onHover(Consumer<Widget> consumer)
    {
        this.hoverConsumer.set(consumer);
    }

    public Consumer<Widget> getHoverConsumer()
    {
        return hoverConsumer.get();
    }

    public void onClick(Consumer<Widget> consumer)
    {
        this.pressConsumer.set(consumer);
    }

    public Consumer<Widget> getClickConsumer()
    {
        return this.pressConsumer.get();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        if ( this.isEnabled() && this.isVisible() ) {
            KeyInput input = KeyInput.key(KeyInput.Action.PRESS, keyCode, scanCode, modifiers);
            if ( invalidKeyPress.test(input) ) {
                return false;
            }
            this.playDownSound(Minecraft.getInstance().getSoundHandler());
            onPress();
            return true;
        }
        return false;
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float partialTicks)
    {
        Texture texture = getTexture();
        if ( texture == null || texture.getLocation() == null ) return;
        Minecraft.getInstance().getTextureManager().bindTexture(texture.getLocation());
        RenderSystem.disableDepthTest();
        RenderUtil.drawTexture(this.x, this.y, texture);
        RenderSystem.enableDepthTest();
    }

    @Override
    public void onPress()
    {
        getClickConsumer().accept(this);
    }
}
