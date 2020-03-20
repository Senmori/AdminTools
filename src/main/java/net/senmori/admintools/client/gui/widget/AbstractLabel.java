package net.senmori.admintools.client.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.senmori.admintools.client.gui.AbstractWidget;
import net.senmori.admintools.config.ClientConfig;
import net.senmori.admintools.client.gui.widget.api.IAttachable;
import net.senmori.admintools.client.gui.widget.api.IUpdatable;
import net.senmori.admintools.client.gui.widget.impl.Label;
import net.senmori.admintools.lib.properties.color.DefaultColorProperty;
import net.senmori.admintools.lib.properties.consumer.DefaultConsumerProperty;
import net.senmori.admintools.lib.properties.defaults.DefaultIntegerProperty;
import net.senmori.admintools.lib.properties.defaults.DefaultObjectProperty;
import net.senmori.admintools.lib.properties.defaults.DefaultStringProperty;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.Color;
import java.util.function.Consumer;

/**
 *  A label is a gui element that displays text within some given bounds.
 *
 *  For easy instantiation, use {@link Label}.
 */
public abstract class AbstractLabel extends AbstractWidget<AbstractLabel> implements IUpdatable, IAttachable {

    private final DefaultObjectProperty<Widget> attachedWidgetProperty = new DefaultObjectProperty<>( this, "parent widget", this );
    private final DefaultColorProperty textColorProperty = new DefaultColorProperty( this, "text color", new Color( 208, 208, 208 ) );
    private final DefaultIntegerProperty maxTextLengthProperty = new DefaultIntegerProperty( this, "max text length", 32 );
    private final DefaultStringProperty labelTextProperty = new DefaultStringProperty( this, "label text", "" );
    private final DefaultIntegerProperty labelSpacingProperty = new DefaultIntegerProperty( this, "label spacing", 1 );
    private final DefaultObjectProperty<Position> labelPositionProperty = new DefaultObjectProperty<>( this, "label position", Position.SELF );
    private final DefaultConsumerProperty<Widget> tickConsumer = new DefaultConsumerProperty<>( this, "tick consumer" );

    private final DefaultIntegerProperty textSpacing = new DefaultIntegerProperty( this, "text spacing", 2 );
    private final DefaultIntegerProperty textPositionX = new DefaultIntegerProperty( this, "text spacing X", getX() + getSpacing() );
    private final DefaultIntegerProperty textPositionY = new DefaultIntegerProperty( this, "text spacing Y", getY() + (getHeight() / 2));

    private boolean dirty = false;

    public AbstractLabel(int xIn, int yIn) {
        super( xIn, yIn );
    }

    public void setText(String text) {
        this.labelTextProperty.set( text );
        markDirty();
    }

    public String getText() {
        return this.labelTextProperty.get();
    }

    public void setSpacing(int spacing) {
        this.labelSpacingProperty.set( spacing );
        markDirty();
    }

    public int getSpacing() {
        return this.labelSpacingProperty.get();
    }

    public void setPosition(AbstractLabel.Position position) {
        this.labelPositionProperty.set( position );
        markDirty();
    }

    public AbstractLabel.Position getPosition() {
        return this.labelPositionProperty.get();
    }

    public void setMaxTextLength(int length) {
        this.maxTextLengthProperty.set( length );
        markDirty();
    }

    public int getMaxTextLength() {
        return this.maxTextLengthProperty.get();
    }

    public void setParent(Widget widget) {
        this.attachedWidgetProperty.set( widget );
        markDirty();
    }

    public Widget getParent() {
        return this.attachedWidgetProperty.get();
    }

    public Color getTextColor() {
        return this.textColorProperty.get();
    }

    public void setTextColor(Color color) {
        this.textColorProperty.set( color );
    }

    public Consumer<Widget> getTickConsumer() {
        return tickConsumer.get();
    }

    public void onTick(Consumer<Widget> consumer) {
        this.tickConsumer.set( consumer );
    }

    public void calculateLayout() {
        this.getPosition().calculate( getParent(), this, getSpacing() );

        int space;
        int stringX = getX() + (textSpacing.get() / 2); //
        if (getFontRenderer().getStringWidth( getText() ) == getWidth()) {
            stringX = getX();
        }
        textPositionX.set( stringX );
        space = getHeight() / 2;
        int stringY = getY() + (space - (space / 2)) + 1;
        textPositionY.set( stringY );
    }

    protected void markDirty() {
        this.dirty = true;
    }

    protected void markClean() {
        this.dirty = false;
    }

    protected boolean isDirty() {
        return this.dirty;
    }

    @Override
    public void tick() {
        getTickConsumer().accept( this );
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render( mouseX, mouseY, partialTicks );
        if ( isDirty() ) {
            calculateLayout();
            markClean();
        }
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float partialTicks) {
        debugOutline( this );
        getFontRenderer().drawStringWithShadow( getText(), textPositionX.get(), textPositionY.get(), getTextColor().getRGB() );
    }

    @Override
    public void printDebug() {
        if (ClientConfig.CONFIG.DEBUG_MODE.get()) {
            calculateLayout();
            super.printDebug();
        }
    }

    @Nullable
    @Override
    public Widget getAttachedWidget() {
        return attachedWidgetProperty.get();
    }

    @Override
    public boolean isAttachedTo(Widget widget) {
        return getAttachedWidget() == widget;
    }

    @Nonnull
    @Override
    public Widget getWidget() {
        return this;
    }

    /**
     * Represents how a label can be positioned in relation to another widget.
     */
    public interface Position {
        /**
         * Represents a position that the gui designer determines.
         */
        Position CUSTOM = (parent, label, spacing) -> {};
        /**
         * Represents a stand-alone label without any attachments to a widget.
         */
        Position SELF = (parent, label, spacing) -> {
            FontRenderer fontRenderer = label.getFontRenderer();
            spacing = ( spacing >= 0 ? spacing : label.getSpacing() );
            String labelText = label.getText();
            int textLength = fontRenderer.getStringWidth( labelText ) + spacing;

            if ( textLength > label.getMaxTextLength() ) { // trim label text if it's too long
                labelText = fontRenderer.trimStringToWidth( labelText, label.getMaxTextLength(), true );
                label.setText( labelText );
            }

            label.setWidth( fontRenderer.getStringWidth( label.getText() ) + spacing );
            label.setHeight( fontRenderer.getWordWrappedHeight( label.getText(), label.getMaxTextLength() ) + spacing );
        };

        /**
         * Positions the given label to the left of the given widget using the given spacing.
         * This aligns the label along the y-axis of the parent widget, while adjusting the
         * label's x-position, not the parent's x position.
         * Therefore, if there is not enough room on screen then the label's text will be cut.
         */
        Position LEFT = (parent, label, spacing) -> {
            FontRenderer font = label.getFontRenderer();
            spacing = ( spacing >= 0 ? spacing : label.getSpacing() );
            String labelText = label.getText();
            int textLength = font.getStringWidth( labelText );

            if ( ( parent.x - ( textLength + spacing ) >= 0 ) ) {
                // label text fits
                label.setX( parent.x - ( textLength + spacing ) );
            } else {
                // text doesn't completely fit, trim it until it does from the end of the string first
                int over = Math.abs( parent.x - ( textLength + spacing ) );
                labelText = font.trimStringToWidth( labelText, textLength - over );
                label.setMaxTextLength( labelText.length() - over );
                label.setText( labelText );

                label.setX( parent.x - (textLength + spacing));
            }
            int parentHeight = parent.y + parent.getHeight();
            label.setWidth( textLength );
            label.setY( ( parentHeight - ( ( parentHeight - parent.y ) ) ) );
            label.setHeight( parent.getHeight() );
        };

        /**
         * Positions the given label to the right of the given widget using the given spacing.
         * This aligns the label along the y-axis of the parent widget, while adjusting the
         * label's x-position, not the parent's x position.
         * Therefore, if there is not enough room on screen then the label's text will be cut.
         */
        Position RIGHT = (parent, label, spacing) -> {
            Screen screen = parent instanceof AbstractWidget ? ((AbstractWidget)parent).getScreen() : Minecraft.getInstance().currentScreen;
            int screenWidth;
            if (screen == null) {
                screenWidth = Minecraft.getInstance().getMainWindow().getScaledWidth();
            } else {
                screenWidth = screen.width;
            }
            FontRenderer font = label.getFontRenderer();
            spacing = ( spacing >= 0 ? spacing : label.getSpacing() );
            String labelText = label.getText();
            int textLength = font.getStringWidth( labelText );

            int parentWidth = (parent.x + parent.getWidth());
            if (parentWidth + (textLength + spacing) <= screenWidth) {
                label.setX( parentWidth + spacing );
            } else {
                int remove = Math.abs( screenWidth - (parent.x + (textLength + spacing)) );
                labelText = font.trimStringToWidth( labelText, textLength - remove );
                label.setMaxTextLength( labelText.length() - remove );
                label.setText(labelText);

                label.setX( parentWidth + spacing);
            }

            label.setWidth( textLength );
            int parentHeight = parent.y + parent.getHeight();
            label.setY( parentHeight - ( (parentHeight - parent.y)));
            label.setHeight( parent.getHeight());
        };

        /**
         * Calculate this label's new position in relation to the given widget.
         *
         * @param parent the widget this label is attached to
         */
        void calculate(Widget parent, AbstractLabel label, int spacing);
    }
}
