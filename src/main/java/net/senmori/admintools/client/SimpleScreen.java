package net.senmori.admintools.client;

import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.senmori.admintools.client.gui.AbstractWidget;
import net.senmori.admintools.client.gui.widget.api.Updatable;
import net.senmori.admintools.client.gui.widget.impl.Checkbox;
import net.senmori.admintools.client.gui.widget.impl.LockIconButton;
import net.senmori.admintools.lib.util.Keyboard;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nonnull;

@OnlyIn( Dist.CLIENT )
public class SimpleScreen extends Screen {

    public SimpleScreen() {
        super( new StringTextComponent( "Simple Screen" ) );
    }

    @Override
    protected void init() {
        super.init();

        LockIconButton button = new LockIconButton( 80, 80 );
        Checkbox checkbox = new Checkbox( 120, 120 );

        addButton( button );
        addButton( checkbox );

        for (IGuiEventListener widget : children()) {
            if (widget instanceof AbstractWidget) {
                ( ( AbstractWidget ) widget ).printDebug();
            }
        }
    }

    @Override
    @Nonnull
    protected <T extends Widget> T addButton(@Nonnull T widget) {
        if (widget instanceof AbstractWidget ) {
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
                return nextTab( focusedIndex, widget, Keyboard.hasShiftDown() );
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

    private boolean nextTab(int current, AbstractWidget<?> currentFocus, boolean hasShiftDown) {
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
        if (next != null && next != currentFocus) {
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
        children().stream().filter( child -> child instanceof Updatable ).forEach( child -> ( ( Updatable ) child ).tick() );
    }
}
