package net.senmori.admintools.client;

import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.senmori.admintools.AdminTools;
import net.senmori.admintools.client.gui.AbstractWidget;
import net.senmori.admintools.client.gui.widget.api.Updatable;
import net.senmori.admintools.client.gui.widget.impl.Button;
import net.senmori.admintools.client.gui.widget.impl.LockIconButton;
import net.senmori.admintools.lib.texture.Texture;
import net.senmori.admintools.lib.util.Keyboard;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nonnull;
import java.awt.Color;

@OnlyIn( Dist.CLIENT )
public class SimpleScreen extends Screen
{


    public SimpleScreen()
    {
        super(new StringTextComponent(""));
    }

    @Override
    protected void init() {
        Button button = new Button(120, 120);
        button.setText("Click Me!");
        button.setHoverTextColor(Color.CYAN);
        button.onPress(widget -> {
            AdminTools.PROXY.getClientPlayer().sendStatusMessage(new StringTextComponent("Hello!"), true);
        });
        //Checkbox checkbox = new Checkbox( 120, 120 );
//        TextField textField = new TextField(150, 150);
//        textField.setDefaultSuggestionText("Hello user!");
//        textField.setTextColor(new Color(125.0F, 125.0F, 125.0F, 0.1F));
//        textField.setCharacterInputValidator(Character::isAlphabetic);
//        textField.setTextBoxBorderColor(Color.GREEN);

        addButton( button );
        //addButton( checkbox );
//        addButton(textField);

//        for (IGuiEventListener widget : children()) {
//            if (widget instanceof AbstractWidget) {
//                ( ( AbstractWidget<?> ) widget ).printDebug();
//            }
//        }
    }

    @Override
    @Nonnull
    protected <T extends Widget> T addButton(@Nonnull T widget) {
        if (widget instanceof AbstractWidget ) {
            ((AbstractWidget<?>)widget).setScreen( this );
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
            if (child instanceof AbstractWidget  && ((AbstractWidget<?>)child).isFocused()) {
                widget = ((AbstractWidget<?>)child);
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
            return ((AbstractWidget<?>)widget);
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
        children.stream().filter(this::isUpdatable).map(child -> (Updatable)child).forEach(Updatable::tick);
    }

    private boolean isUpdatable(IGuiEventListener widget) {
        return widget instanceof Updatable;
    }
}
