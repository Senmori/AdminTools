package net.senmori.custommobs.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.senmori.custommobs.CustomMobs;
import net.senmori.custommobs.client.config.ClientConfig;
import net.senmori.custommobs.lib.input.KeyInput;
import net.senmori.custommobs.lib.input.MouseInput;
import net.senmori.custommobs.lib.properties.consumer.DefaultBiConsumerProperty;
import net.senmori.custommobs.lib.properties.consumer.DefaultConsumerProperty;
import net.senmori.custommobs.lib.properties.defaults.DefaultObjectProperty;
import net.senmori.custommobs.lib.properties.defaults.DefaultStringProperty;
import net.senmori.custommobs.lib.properties.predicate.DefaultPredicateProperty;
import net.senmori.custommobs.lib.properties.simple.BooleanProperty;
import net.senmori.custommobs.lib.sound.CustomSound;
import net.senmori.custommobs.lib.util.Keyboard;
import net.senmori.custommobs.lib.sound.SoundUtil;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.awt.Color;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

@OnlyIn( Dist.CLIENT )
public abstract class AbstractWidget<T extends Widget> extends Widget {
    public static final int DEFAULT_WIDTH = 200;
    public static final int DEFAULT_HEIGHT = 20;

    protected Screen screen;
    protected Widget parent;

    protected final BooleanProperty enabledProperty = new BooleanProperty( this, "enabled", this.active );
    protected final BooleanProperty visibleProperty = new BooleanProperty( this, "visible", this.visible );
    protected final BooleanProperty focusedProperty = new BooleanProperty( this, "focused", this.focused );
    protected final DefaultObjectProperty<FontRenderer> fontRendererProperty = new DefaultObjectProperty<>( this, "font renderer", Minecraft.getInstance().fontRenderer );

    // Misc.
    protected final DefaultStringProperty narrationProperty = new DefaultStringProperty( this, "narration message", "" );
    protected final DefaultObjectProperty<CustomSound> soundProperty = new DefaultObjectProperty<>( this, "sound", new CustomSound(SoundEvents.UI_BUTTON_CLICK) );

    // Consumers
    protected final DefaultConsumerProperty<Boolean> focusConsumerProperty = new DefaultConsumerProperty<>( this, "focus consumer");

    public AbstractWidget(int xIn, int yIn) {
        super( xIn, yIn, "" );
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
        enabledProperty.addListener( (listener) -> this.active = ( boolean ) listener.getValue() );
        visibleProperty.addListener( (listener) -> this.visible = (boolean) listener.getValue() );
        focusedProperty.addListener( (listener) -> this.focused = (boolean) listener.getValue() );
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Nullable
    public Screen getScreen() {
        return screen;
    }

    public void setParent(Widget widget) {
        this.parent = widget;
    }

    @Nullable
    public Widget getParent() {
        return this.parent;
    }

    public T setDimensions(int width, int height) {
        setWidth( width );
        setHeight( height );
        return ( T ) this;
    }

    @Override
    public void setWidth(int width) {
        super.setWidth( width );
    }

    @Override
    public void setHeight(int height) {
        super.setHeight( height );
    }

    /**
     * Set the narration message.
     *
     * @param message the new narration message.
     * @return the widget
     */
    public T setNarrationMessage(String message) {
        this.narrationProperty.set( message );
        return ( T ) this;
    }

    /**
     * The narration message is a feature introduced in 1.12. It reads out text in the chat and can be activated
     * by pressing 'Ctrl + B'.
     *
     * @return the message that will be played by the narrator.
     */
    @Override
    public String getNarrationMessage() {
        return narrationProperty.get().isEmpty() ? super.getNarrationMessage() : I18n.format( "gui.narrate.button", this.narrationProperty.get() );
    }

    /**
     * Set the {@link SoundEvent} that will be played when the widget is pressed (not released!).
     *
     * @param sound the sound
     * @return this widget
     * @see SoundHandler#play(ISound)
     * @see SimpleSound#master(SoundEvent, float)
     */
    public T setOnClickSound(SoundEvent sound) {
        return setOnClickSound( sound, 1.0F );
    }

    /**
     * Set the {@link SoundEvent} that will be called when the widget is pressed (not released!).
     *
     * @param sound the sound to play
     * @param pitch the pitch
     * @return this widget
     * @see SoundHandler#play(ISound)
     * @see SimpleSound#master(SoundEvent, float)
     */
    public T setOnClickSound(SoundEvent sound, float pitch) {
        this.soundProperty.set( SoundUtil.get().createSound( sound ) );
        this.soundProperty.get().setPitch( pitch );
        return ( T ) this;
    }

    /**
     * Get the {@link CustomSound} data class that contains all information related to playing sounds.
     * This includes, the sound ({@link SoundEvent}), the {@link SoundCategory}, volume, and pitch.
     *
     * @return the {@link CustomSound}
     */
    public CustomSound getSound() {
        return this.soundProperty.get();
    }

    public T setFontRenderer(FontRenderer fontRenderer) {
        this.fontRendererProperty.set( fontRenderer );
        return (T) this;
    }

    public FontRenderer getFontRenderer() {
        return this.fontRendererProperty.get();
    }

    /**
     * Check if the given x/y coordinates are within the bounds of this widget.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if the coordinates are within the bounds of this widget
     */
    public boolean in(int x, int y) {
        return x >= this.x && x < ( this.x + this.width ) && y >= this.y && y <= ( this.y + this.height );
    }

    /**
     * Check if the given x/y coordinates are within the bounds of this widget.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if the coordinates are within the bounds of this widget
     */
    public boolean in(double x, double y) {
        return x >= ( double ) this.x && x < ( double ) ( this.x + this.width ) && y >= ( double ) this.y && y <= ( double ) ( this.y + this.height );
    }

    public void onFocus(Consumer<Boolean> focusConsumer) {
        this.focusConsumerProperty.set( focusConsumer );
    }

    protected void onFocusChange(boolean currentFocus, boolean newFocus) {
        this.focusConsumerProperty.get().accept( newFocus );
    }

    @Override
    protected boolean isValidClickButton(int mouseButton) {
        return mouseButton == GLFW.GLFW_MOUSE_BUTTON_LEFT; // GLFW.GLFW_MOUSE_BUTTON_LEFT == 0
    }

    public boolean validateMouseClick(double mouseX, double mouseY, int button) {
        return isValidClickButton( button );
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if ( this.isEnabled() && this.isVisible() ) {
            if ( this.validateMouseClick( mouseX, mouseY, button ) && this.clicked(mouseX, mouseY) ) {
                    this.playDownSound( Minecraft.getInstance().getSoundHandler() );
                    this.onClick( mouseX, mouseY );
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if ( validateMouseClick( mouseX, mouseY, button ) ) {
            this.onRelease( mouseX, mouseY );
            return true;
        }
        return false;
    }


    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int mouseButton, double dragX, double dragY) {
        if ( isValidClickButton( mouseButton ) ) {
            this.onDrag( mouseX, mouseY, dragX, dragY );
            return true;
        }
        return false;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render( mouseX, mouseY, partialTicks );
    }

    @Override
    public void playDownSound(SoundHandler soundHandler) {
        getSound().playSound( soundHandler);
    }

    @Override
    protected boolean clicked(double x, double y) {
        return this.isEnabled() && this.isVisible() && in( x, y );
    }

    @Override
    public boolean isMouseOver(double x, double y) {
        return this.isEnabled() && this.isVisible() && in( x, y );
    }

    @Override
    public void setFocused(boolean focus) {
        changeFocus( focus );
    }

    public boolean changeFocus(boolean focus) {
        if ( this.isEnabled() && this.isVisible() ) {
            if ( this.focused != focus ) {
                this.onFocusChange( this.focused, focus );
                this.focused = !focused;
            }
        }
        return false;
    }

    /**
     * @return true if the widget is visible, focused and enabled.
     */
    public boolean canReceiveInput() {
        return isVisible() && isFocused() && isEnabled();
    }

    /**
     * @return true if the widget is currently visible
     */
    public boolean isVisible() {
        return this.visibleProperty.get();
    }

    public void setVisible(boolean visible) {
        this.visibleProperty.set( visible );
    }

    /**
     * @return true if the widget is enabled and accepting input
     */
    public boolean isEnabled() {
        return this.enabledProperty.get();
    }

    public void setEnabled(boolean enabled) {
        this.enabledProperty.set( enabled );
    }

    protected void fill(int startX, int startY, int endX, int endY, Color color) {
        fill( startX, startY, endX, endY, color.getRGB() );
    }

    protected void debugOutline(Widget widget) {
        if (ClientConfig.CONFIG.DEBUG_MODE.get()) {
            fill( widget.x, widget.y, widget.x + widget.getWidth(), widget.y + widget.getHeight(), ClientConfig.CONFIG.getDebugColor() );
        }
    }

    public void printDebug() {
        if (ClientConfig.CONFIG.DEBUG_MODE.get()) {
            String xy = " [X=" + getX() + ",Y=" + getY() + "]";
            String wh = " + [W=" + getWidth() + ",H=" + getHeight() + "]";
            String totals = " == [" + (getX() + getWidth()) + ',' + (getY() + getHeight()) + "]";
            CustomMobs.LOGGER.info( getClass().getSimpleName() + xy + wh + totals );
        }
    }
}
