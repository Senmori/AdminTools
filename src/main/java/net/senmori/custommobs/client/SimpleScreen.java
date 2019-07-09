package net.senmori.custommobs.client;

import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.senmori.custommobs.client.gui.IUpdatable;
import net.senmori.custommobs.client.widget.AbstractLabel;
import net.senmori.custommobs.client.widget.impl.Label;
import net.senmori.custommobs.client.widget.impl.SimpleTextField;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nonnull;
import java.awt.Color;

public class SimpleScreen extends Screen {

    public SimpleScreen() {
        super( new StringTextComponent( "Simple Screen" ) );
    }

    @Override
    protected void init() {
        super.init();
        int height = getMinecraft().fontRenderer.FONT_HEIGHT + 3;

        SimpleTextField textField = new SimpleTextField( 83, 80 );
        textField.setDimensions( 150, height );
        textField.setSuggestionText( "Set a custom entity name", true);
        //textField.setSuggestionTextColor( new Color( 127, 219, 255 ) );
        textField.setTextColorValidator( str -> {
            if (str.length() < 10) {
                textField.setTextColor( new Color( 220, 20, 0) );
            } else {
                textField.setTextColor( null );
            }
            return true;
        } );
        textField.setCharacterInputValidator( key -> !Character.isDigit( key ) );
        Label label = textField.addLabel( "Custom Name ", AbstractLabel.Position.LEFT );
        label.setSpacing( 2 );
        addButton( textField );

        for (IGuiEventListener widget : children()) {
            if (widget instanceof AbstractWidget) {
                ( ( AbstractWidget ) widget ).printDebug();
            }
        }
    }

    @Override
    @Nonnull
    protected <T extends Widget> T addButton(@Nonnull T widget) {
        if (widget instanceof AbstractWidget) {
            ((AbstractWidget)widget).setScreen( this );
        }
        return super.addButton( widget );
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render( mouseX, mouseY, partialTicks );
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {

        int focusedIndex = 0;
        AbstractWidget<?> widget = null;
        for ( IGuiEventListener child : children()) {
            if (child instanceof AbstractWidget  && ((AbstractWidget)child).isFocused()) {
                widget = ((AbstractWidget)child);
                focusedIndex = children().indexOf( child );
                break;
            }
        }
        if (widget != null) {
            if ( keyCode == GLFW.GLFW_KEY_TAB ) {
                return shiftOnTab( focusedIndex, widget, Screen.hasShiftDown() );
            }
        }
        return super.keyPressed( keyCode, scanCode, modifiers );
    }

    private AbstractWidget<?> findWidgetAt(int index) {
        IGuiEventListener widget = children().get( index );
        if (widget instanceof AbstractWidget) {
            return ((AbstractWidget)widget);
        }
        return null;
    }

    private boolean shiftOnTab(int current, AbstractWidget currentFocus, boolean hasShiftDown) {
        if (hasShiftDown) {
            // shift + tab goes backwards
            if (current <= 0) {
                current = children().size() - 1;
            } else {
                current = MathHelper.clamp( current - 1, 0, children.size() - 1 );
            }
        } else { // go forward
            if (current + 1 >= children().size()) {
                current = 0;
            } else {
                current = MathHelper.clamp( current + 1, 0, children.size() - 1);
            }
        }
        AbstractWidget<?> next = findWidgetAt( current );
        if (next != null) {
            currentFocus.setFocused( false );
            currentFocus.setEnabled( false );
            next.setEnabled( true );
            next.setFocused( true );
            return true;
        }
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        children().stream().filter( child -> child instanceof IUpdatable ).forEach( child -> ( ( IUpdatable ) child ).tick() );
    }
}
