package net.senmori.custommobs.client.gui.widget;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.senmori.custommobs.client.gui.AbstractWidget;
import net.senmori.custommobs.client.gui.widget.api.IUpdatable;
import net.senmori.custommobs.lib.input.KeyInput;
import net.senmori.custommobs.lib.properties.color.DefaultColorProperty;
import net.senmori.custommobs.lib.properties.consumer.DefaultConsumerProperty;
import net.senmori.custommobs.lib.properties.defaults.DefaultIntegerProperty;
import net.senmori.custommobs.lib.properties.defaults.DefaultObjectProperty;
import net.senmori.custommobs.lib.properties.defaults.DefaultStringProperty;
import net.senmori.custommobs.lib.properties.predicate.DefaultPredicateProperty;
import net.senmori.custommobs.lib.properties.simple.BooleanProperty;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.awt.Color;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

@OnlyIn( Dist.CLIENT )
public abstract class AbstractTextFieldWidget extends AbstractWidget<AbstractTextFieldWidget> implements IUpdatable {
    // Internal
    private int cursorCounter;
    private boolean hasShiftDown;
    private int cursorPosition;
    private int selectionEnd;
    protected boolean canLoseFocus = true;
    private int lineScrollOffset;

    protected final DefaultColorProperty textBoxBorderColorProperty = new DefaultColorProperty( this, "text box border", new Color( 160, 160, 160 ) );
    protected final DefaultColorProperty textBoxForegroundColorProperty = new DefaultColorProperty( this, "text box foreground", new Color( 0, 0, 0 ) );

    protected final BooleanProperty clearSuggestionTextOnFocusProperty = new BooleanProperty( this, "remove suggestion text on focus", true );
    protected final BooleanProperty restoreSuggestionTextOnFocusLostProperty = new BooleanProperty( this, "restore suggestion text", true );
    protected final BooleanProperty enableBackgroundDrawingProperty = new BooleanProperty( this, "enable background drawing", true );
    protected final DefaultPredicateProperty<Character> charInputValidationProperty = new DefaultPredicateProperty<>( this, "key input validation", SharedConstants::isAllowedCharacter );
    protected final DefaultPredicateProperty<String> textColorValidationProperty = new DefaultPredicateProperty<>( this, "text color validation", s -> true );
    protected final DefaultIntegerProperty maxStringLengthProperty = new DefaultIntegerProperty( this, "max string length", 32 );
    protected final DefaultColorProperty textColorProperty = new DefaultColorProperty( this, "text color", new Color( 224, 224, 224 ) );
    protected final DefaultColorProperty suggestionColorProperty = new DefaultColorProperty( this, "suggestion color", new Color( 128, 128, 128 ) );
    protected final DefaultColorProperty cursorColorProperty = new DefaultColorProperty( this, "cursor color", new Color( 208, 208, 208 ) );
    protected final DefaultStringProperty textProperty = new DefaultStringProperty( this, "text", "" );
    protected final DefaultPredicateProperty<String> textValidatorProperty = new DefaultPredicateProperty<>( this, "text validator", s -> true );
    protected final DefaultPredicateProperty<KeyInput> keyInputProperty = new DefaultPredicateProperty<>( this, "key input property", input -> true );
    protected final DefaultObjectProperty<BiFunction<String, Integer, String>> textFormatterProperty = new DefaultObjectProperty<>( this, "text formatter", (str, num) -> str );
    protected final DefaultObjectProperty<BiFunction<String, Integer, String>> suggestionTextFormatProperty = new DefaultObjectProperty<>( this, "suggestion text formatter", (str, num) -> str );
    protected final DefaultConsumerProperty<String> onTextChangeProperty = new DefaultConsumerProperty<>( this, "text change consumer" );
    protected final DefaultConsumerProperty<Widget> onTickConsumerProperty = new DefaultConsumerProperty<>( this, "tick consumer" );

    // Non-final variables (so players can change the default suggestion text)
    protected DefaultStringProperty suggestionTextProperty = new DefaultStringProperty( this, "suggestion text", null );
    protected AbstractLabel label;

    public AbstractTextFieldWidget(int x, int y) {
        super( x, y );
        maxStringLengthProperty.addListener( (listener) -> {
            if ( this.getText().length() > ( int ) listener.getValue() ) {
                this.textProperty.set( getText().substring( 0, ( int ) listener.getValue() ) );
                this.onTextChange( this.getText() );
            }
        } );
    }

    public Consumer<String> getOnTextChangeConsumer() {
        return onTextChangeProperty.get();
    }

    public BiFunction<String, Integer, String> getTextFormatter() {
        return this.textFormatterProperty.get();
    }

    public BiFunction<String, Integer, String> getSuggestionTextFormatter() {
        return this.suggestionTextFormatProperty.get();
    }

    public Consumer<Widget> getOnTickConsumer() {
        return this.onTickConsumerProperty.get();
    }

    public Predicate<String> getTextValidator() {
        return this.textValidatorProperty.get();
    }

    public boolean doRemoveSuggestionOnFocus() {
        return this.clearSuggestionTextOnFocusProperty.get();
    }

    public boolean doRestoreSuggestionOnFocus() {
        return this.restoreSuggestionTextOnFocusLostProperty.get();
    }

    public Color getCursorColor() {
        return this.cursorColorProperty.get();
    }

    public int getMaxStringLength() {
        return this.maxStringLengthProperty.get();
    }

    public Color getSuggestionTextColor() {
        return this.suggestionColorProperty.get();
    }

    public Color getTextColor() {
        return this.textColorProperty.get();
    }

    public Predicate<String> getTextColorValidator() {
        return this.textColorValidationProperty.get();
    }

    public Predicate<KeyInput> getKeyInputValidator() {
        return this.keyInputProperty.get();
    }

    public Predicate<Character> getCharacterInputValidator() {
        return this.charInputValidationProperty.get();
    }

    public Color getTextBoxBorderColor() {
        return this.textBoxBorderColorProperty.get();
    }

    public Color getTextBoxBGColor() {
        return this.textBoxForegroundColorProperty.get();
    }

    /**
     * Text field narration message output is '%s edit box: %s' where the first '%s' is the narration message
     * and the second '%s' is the contents of the text field.
     * <br>
     * If the narration message is empty, but there is a {@link AbstractLabel} attached, the label's text will be used.
     *
     * @return the narration message
     */
    @Override
    public String getNarrationMessage() {
        if (narrationProperty.get().isEmpty() && label != null) {
            return I18n.format( "gui.narrate.editBox", label.getText(), getText() );
        }
        return narrationProperty.get().isEmpty() ? super.getNarrationMessage() : I18n.format( "gui.narrate.editBox", narrationProperty.get(), getText() );
    }

    /**
     * Sets the entire new text string.
     *
     * @param text the new text string
     */
    public void setText(String text) {
        if ( this.getTextValidator().test( text ) ) {
            int maxStringLength = this.maxStringLengthProperty.get();
            if ( text.length() > maxStringLength ) {
                this.textProperty.set( text.substring( 0, maxStringLength ) );
            } else {
                this.textProperty.set( text );
            }

            this.setCursorPositionEnd();
            this.setSelectionPos( this.cursorPosition );
            this.onTextChange( this.textProperty.get() );
        }
    }

    protected void setCursorPositionStart() {
        this.setCursorPosition( 0 );
    }

    protected void setCursorPositionEnd() {
        this.setCursorPosition( this.textProperty.get().length() );
    }

    protected void setCursorPosition(int position) {
        cursorPosition( position );
        if ( !this.hasShiftDown ) {
            this.setSelectionPos( this.cursorPosition );
        }

        this.onTextChange( this.textProperty.get() );
    }

    protected void onTextChange(String text) {
        getOnTextChangeConsumer().accept( text );
        this.nextNarration = Util.milliTime() + 500L;
    }

    protected void cursorPosition(int newPosition) {
        this.cursorPosition = MathHelper.clamp( newPosition, 0, this.getText().length() );
    }

    protected void setSelectionPos(int position) {
        int i = this.getText().length();
        this.selectionEnd = MathHelper.clamp( position, 0, i );
        if ( this.getFontRenderer() != null ) {
            if ( this.lineScrollOffset > i ) {
                this.lineScrollOffset = i;
            }

            int j = this.getAdjustedWidth();
            String trimmed = this.getFontRenderer().trimStringToWidth( this.getText().substring( this.lineScrollOffset ), j );
            int k = trimmed.length() + this.lineScrollOffset;
            if ( this.selectionEnd == this.lineScrollOffset ) {
                this.lineScrollOffset -= this.getFontRenderer().trimStringToWidth( this.getText(), j, true ).length();
            }

            if ( this.selectionEnd > k ) {
                this.lineScrollOffset += this.selectionEnd - k;
            } else if ( this.selectionEnd <= this.lineScrollOffset ) {
                this.lineScrollOffset -= this.lineScrollOffset - this.selectionEnd;
            }

            this.lineScrollOffset = MathHelper.clamp( this.lineScrollOffset, 0, i );
        }
    }

    protected int getAdjustedWidth() {
        return this.doEnableBackgroundDrawing() ? this.getWidth() - 8 : this.width;
    }

    public void setSuggestionText(@Nullable String text) {
        setSuggestionText( text, false );
    }

    public void setSuggestionText(@Nullable String text, boolean replaceDefault) {
        if ( replaceDefault ) {
            setDefaultSuggestionText( text );
        }
        this.suggestionTextProperty.set( text );
    }

    private void setDefaultSuggestionText(String text) {
        this.suggestionTextProperty = new DefaultStringProperty( this, "suggestion text", text );
    }

    @Nullable
    public String getSuggestionText() {
        return this.suggestionTextProperty.get();
    }

    @Override
    protected void onFocusChange(boolean currentFocus, boolean newFocus) {
        super.onFocusChange( currentFocus, newFocus );
        if ( !this.isFocused() & newFocus && doRemoveSuggestionOnFocus() ) {
            setSuggestionText( "" );
        } else if ( this.isFocused() && !newFocus && doRestoreSuggestionOnFocus() ) {
            setSuggestionText( this.suggestionTextProperty.getDefaultValue() );
        }
    }

    protected boolean doEnableBackgroundDrawing() {
        return this.enableBackgroundDrawingProperty.get();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if ( !this.isVisible() ) {
            return false;
        }
        boolean clicked = in( mouseX, mouseY );
        if ( this.canLoseFocus ) {
            // change focus
            this.changeFocus( clicked );
        }

        if ( this.isFocused() && clicked && mouseButton == GLFW.GLFW_MOUSE_BUTTON_LEFT ) { // GLFW_MOUSE_BUTTON_LEFT = 0
            int i = MathHelper.floor( mouseX ) - this.x;
            if ( this.doEnableBackgroundDrawing() ) {
                i -= 4;
            }

            String trimmed = this.getFontRenderer().trimStringToWidth( this.getText().substring( this.lineScrollOffset ), this.getAdjustedWidth() );
            this.setCursorPosition( this.getFontRenderer().trimStringToWidth( trimmed, i ).length() + this.lineScrollOffset );
            return true;
        }
        return false;
    }

    protected boolean onKeyPress(KeyInput input) {
        return getKeyInputValidator().test( input );
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if ( !this.canReceiveInput() ) {
            return false;
        } else {
            KeyInput input = KeyInput.key( KeyInput.Action.PRESS, keyCode, scanCode, modifiers );
            if ( !this.onKeyPress( input ) ) return false;
            this.hasShiftDown = input.getInputModifier().isShiftPressed();
            if ( Screen.isSelectAll( keyCode ) ) {
                this.setCursorPositionEnd();
                this.setSelectionPos( 0 );
                return true;
            } else if ( Screen.isCopy( keyCode ) ) {
                Minecraft.getInstance().keyboardListener.setClipboardString( this.getSelectedText() );
                return true;
            } else if ( Screen.isPaste( keyCode ) ) {
                if ( this.isEnabled() ) {
                    this.writeText( Minecraft.getInstance().keyboardListener.getClipboardString() );
                }

                return true;
            } else if ( Screen.isCut( keyCode ) ) {
                Minecraft.getInstance().keyboardListener.setClipboardString( this.getSelectedText() );
                if ( this.isEnabled() ) {
                    this.writeText( "" );
                }
                return true;
            } else {
                switch ( keyCode ) {
                    case GLFW.GLFW_KEY_BACKSPACE: // 259
                        if ( this.isEnabled() ) {
                            this.delete( -1 );
                        }
                        return true;
                    case GLFW.GLFW_KEY_INSERT: // 260
                    case GLFW.GLFW_KEY_DOWN: // 264
                    case GLFW.GLFW_KEY_UP: // 265
                    case GLFW.GLFW_KEY_PAGE_UP: // 266
                    case GLFW.GLFW_KEY_PAGE_DOWN: // 267
                        return false;
                    case GLFW.GLFW_KEY_DELETE: // 261
                        if ( this.isEnabled() ) {
                            this.delete( 1 );
                        }
                        return true;
                    case GLFW.GLFW_KEY_RIGHT: // 262
                        if ( Screen.hasControlDown() ) {
                            this.setCursorPosition( this.getNthWordFromCursor( 1 ) );
                        } else {
                            this.moveCursorBy( 1 );
                        }

                        return true;
                    case GLFW.GLFW_KEY_LEFT: // 263
                        if ( Screen.hasControlDown() ) {
                            this.setCursorPosition( this.getNthWordFromCursor( -1 ) );
                        } else {
                            this.moveCursorBy( -1 );
                        }
                        return true;
                    case GLFW.GLFW_KEY_HOME: // 268
                        this.setCursorPositionStart();
                        return true;
                    case GLFW.GLFW_KEY_END: // 268
                        this.setCursorPositionEnd();
                        return true;
                }
            }
        }
        return super.keyPressed( keyCode, scanCode, modifiers );
    }

    @Override
    public void tick() {
        ++this.cursorCounter;
        getOnTickConsumer().accept( this );
    }

    @Override
    public boolean charTyped(char key, int keyCode) {
        if ( !canReceiveInput() ) return false;

        if ( this.getCharacterInputValidator().test( key ) ) {
            if ( isEnabled() ) {
                this.writeText( String.valueOf( key ) );
            }
            return true;
        }
        return false;
    }

    public String getSelectedText() {
        int start = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
        int end = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
        return this.getText().substring( start, end );
    }

    public String getText() {
        return textProperty.get();
    }

    /**
     * Usually used when typing in characters, but is also used when pasting in contents from the clipboard.
     *
     * @param text the new text
     */
    public void writeText(String text) {
        String newMessage = "";
        String filtered = SharedConstants.filterAllowedCharacters( text );
        int i = this.cursorPosition < this.selectionEnd ? this.cursorPosition : this.selectionEnd;
        int j = this.cursorPosition < this.selectionEnd ? this.selectionEnd : this.cursorPosition;
        int k = this.maxStringLengthProperty.get() - this.getText().length() - ( i - j );
        if ( !this.getText().isEmpty() ) {
            newMessage = newMessage + this.getText().substring( 0, i );
        }

        int tmp;
        if ( k < filtered.length() ) {
            newMessage = newMessage + filtered.substring( 0, k );
            tmp = k;
        } else {
            newMessage = newMessage + filtered;
            tmp = filtered.length();
        }

        if ( !this.getText().isEmpty() && j < this.getText().length() ) {
            newMessage = newMessage + this.getText().substring( j );
        }

        if ( this.getTextValidator().test( newMessage ) ) {
            this.textProperty.set( newMessage );
            this.cursorPosition( i + tmp );
            this.setSelectionPos( this.cursorPosition );
            this.onTextChange( getText() );
        }
    }

    public void delete(int num) {
        if ( Screen.hasControlDown() ) {
            this.deleteWords( num );
        } else {
            this.deleteFromCursor( num );
        }
    }

    public void deleteWords(int num) {
        if ( !this.getText().isEmpty() ) {
            if ( this.selectionEnd != this.cursorPosition ) {
                this.writeText( "" );
            } else {
                this.deleteFromCursor( this.getNthWordFromCursor( num ) - this.cursorPosition );
            }
        }
    }

    public void deleteFromCursor(int num) {
        if ( !this.getText().isEmpty() ) {
            if ( this.selectionEnd != this.cursorPosition ) {
                this.writeText( "" );
            } else {
                boolean flag = num < 0;
                int i = flag ? this.cursorPosition + num : this.cursorPosition;
                int j = flag ? this.cursorPosition : this.cursorPosition + num;
                String s = "";
                if ( i >= 0 ) {
                    s = this.getText().substring( 0, i );
                }

                if ( j < this.getText().length() ) {
                    s = s + this.getText().substring( j );
                }

                if ( this.getTextValidator().test( s ) ) {
                    this.textProperty.set( s );
                    if ( flag ) {
                        this.moveCursorBy( num );
                    }

                    this.onTextChange( this.getText() );
                }
            }
        }
    }


    public int getNthWordFromCursor(int numWords) {
        return getNthWordFromPos( numWords, this.cursorPosition );
    }

    private int getNthWordFromPos(int n, int pos) {
        return this.getNthWordFromPos( n, pos, true );
    }

    private int getNthWordFromPos(int n, int pos, boolean ignoreWhitespace) {
        int currentPos = pos;
        boolean isBackwards = n < 0;
        int j = Math.abs( n );

        for ( int k = 0; k < j; ++k ) {
            if ( !isBackwards ) {
                int length = this.getText().length();
                currentPos = this.getText().indexOf( 32, currentPos );
                if ( currentPos == -1 ) {
                    currentPos = length;
                } else {
                    while ( ignoreWhitespace && currentPos < length && this.getText().charAt( currentPos ) == ' ' ) {
                        ++currentPos;
                    }
                }
            } else {
                while ( ignoreWhitespace && currentPos > 0 && this.getText().charAt( currentPos - 1 ) == ' ' ) {
                    --currentPos;
                }

                while ( currentPos > 0 && this.getText().charAt( currentPos - 1 ) != ' ' ) {
                    --currentPos;
                }
            }
        }

        return currentPos;
    }

    public void moveCursorBy(int length) {
        this.setCursorPosition( this.cursorPosition + length );
    }

    private Color getTextColorForRender() {
        getTextColorValidator().test( getText() );
        return getTextColor();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        super.render( mouseX, mouseY, partialTicks );
        if ( label != null ) {
            label.render( mouseX, mouseY, partialTicks );
        }
    }

    protected void drawCompleteTextBox(Color bgColor, Color fgColor) {
        fill( this.x - 1, this.y - 1, this.x + this.width + 1, this.y + this.height + 1, bgColor );
        fill( this.x, this.y, this.x + this.width, this.y + this.height, fgColor );
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float partialTicks) {
        if ( this.isVisible() ) {
            if ( this.doEnableBackgroundDrawing() ) {
                drawCompleteTextBox( getTextBoxBorderColor(), getTextBoxBGColor() );
            }
            debugOutline( this );
            if ( label != null ) {
                label.renderButton( mouseX, mouseY, partialTicks );
            }

            Color textColor = getTextColorForRender();
            int cursorStart = this.cursorPosition - this.lineScrollOffset;
            int end = this.selectionEnd - this.lineScrollOffset;
            String trimmed = this.getFontRenderer().trimStringToWidth( this.getText().substring( this.lineScrollOffset ), this.getAdjustedWidth() );
            boolean withinTextBounds = cursorStart >= 0 && cursorStart <= trimmed.length();
            boolean doRenderCursor = this.isFocused() && ( this.cursorCounter / 8 ) % 2 == 0 && withinTextBounds;
            int xPos = this.doEnableBackgroundDrawing() ? this.x + 4 : this.x;
            int yPos = this.doEnableBackgroundDrawing() ? this.y + ( this.height - 8 ) / 2 : this.y;
            int j1 = xPos;
            if ( end > trimmed.length() ) {
                end = trimmed.length();
            }

            if ( !trimmed.isEmpty() ) {
                String sub = withinTextBounds ? trimmed.substring( 0, cursorStart ) : trimmed;
                j1 = this.getFontRenderer().drawStringWithShadow( sub, ( float ) xPos, ( float ) yPos, textColor.getRGB() );
            }

            boolean inTextBox = this.cursorPosition < this.getText().length() || this.getText().length() >= getMaxStringLength();
            int k1 = j1;
            if ( !withinTextBounds ) {
                k1 = cursorStart > 0 ? xPos + this.width : xPos;
            } else if ( inTextBox ) {
                k1 = j1 - 1;
                --j1;
            }

            if ( !trimmed.isEmpty() && withinTextBounds && cursorStart < trimmed.length() ) {
                String formatted = getTextFormatter().apply( trimmed.substring( cursorStart ), this.cursorPosition );
                this.getFontRenderer().drawStringWithShadow( formatted, ( float ) j1, ( float ) yPos, textColor.getRGB() );
            }

            if ( !inTextBox && this.getText().isEmpty() && this.getSuggestionText() != null && !this.getSuggestionText().isEmpty() ) {
//                if ( getSuggestionText() == null || getSuggestionText().isEmpty() ) {
//                    setSuggestionText( suggestionTextProperty.getDefaultValue(), true );
//                }
                String format = getSuggestionTextFormatter().apply( getSuggestionText(), getSuggestionText().length() );
                this.getFontRenderer().drawStringWithShadow( format, ( float ) ( k1 - 1 ), ( float ) yPos, getSuggestionTextColor().getRGB() );
            }

            if ( doRenderCursor ) {
                if ( inTextBox ) {
                    AbstractGui.fill( k1, yPos - 1, k1 + 1, yPos + 1 + 9, getCursorColor().getRGB() );
                } else {
                    this.getFontRenderer().drawStringWithShadow( "|", ( float ) k1, ( float ) yPos, getCursorColor().getRGB() );
                }
            }

            if ( end != cursorStart ) {
                int len = xPos + this.getFontRenderer().getStringWidth( trimmed.substring( 0, end ) );
                this.drawSelectionBox( k1, yPos - 1, len - 1, yPos + 1 + 9 );
            }
        }
    }

    private void drawSelectionBox(int startX, int startY, int endX, int endY) {
        if ( startX < endX ) {
            int i = startX;
            startX = endX;
            endX = i;
        }

        if ( startY < endY ) {
            int j = startY;
            startY = endY;
            endY = j;
        }

        if ( endX > ( this.x + width ) ) {
            endX = ( this.x + width );
        }

        if ( startX > ( this.x + this.width ) ) {
            startX = ( this.x + width );
        }

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.color4f( 0.0F, 0.0F, 255.0F, 255.0F );
        GlStateManager.disableTexture();
        GlStateManager.enableColorLogicOp();
        GlStateManager.logicOp( GlStateManager.LogicOp.OR_REVERSE );
        bufferbuilder.begin( 7, DefaultVertexFormats.POSITION );
        bufferbuilder.pos( ( double ) startX, ( double ) endY, 0.0D ).endVertex();
        bufferbuilder.pos( ( double ) endX, ( double ) endY, 0.0D ).endVertex();
        bufferbuilder.pos( ( double ) endX, ( double ) startY, 0.0D ).endVertex();
        bufferbuilder.pos( ( double ) startX, ( double ) startY, 0.0D ).endVertex();
        tessellator.draw();
        GlStateManager.disableColorLogicOp();
        GlStateManager.enableTexture();
    }

    @Override
    public void printDebug() {
        super.printDebug();
        if ( label != null ) {
            label.printDebug();
        }
    }
}
