package net.senmori.admintools.client.gui.widget;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
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
import net.minecraftforge.common.MinecraftForge;
import net.senmori.admintools.client.gui.AbstractWidget;
import net.senmori.admintools.client.gui.util.TextFormatter;
import net.senmori.admintools.client.gui.widget.api.KeyPressAction;
import net.senmori.admintools.client.gui.widget.api.Updatable;
import net.senmori.admintools.lib.input.KeyInput;
import net.senmori.admintools.lib.properties.color.ColorProperty;
import net.senmori.admintools.lib.properties.consumer.ConsumerProperty;
import net.senmori.admintools.lib.properties.predicate.PredicateProperty;
import net.senmori.admintools.lib.properties.primitive.BooleanProperty;
import net.senmori.admintools.lib.properties.primitive.IntegerProperty;
import net.senmori.admintools.lib.properties.primitive.ObjectProperty;
import net.senmori.admintools.lib.properties.primitive.StringProperty;
import net.senmori.admintools.lib.util.Keyboard;
import net.senmori.admintools.lib.util.RenderUtil;
import net.senmori.admintools.util.KeyboardUtil;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.awt.Color;
import java.util.function.Consumer;
import java.util.function.Predicate;

@OnlyIn( Dist.CLIENT )
public abstract class AbstractTextFieldWidget extends AbstractWidget<AbstractTextFieldWidget> implements Updatable {
    // Internal
    private int cursorCounter;
    private boolean hasShiftDown;
    private int cursorPosition;
    private int selectionEnd;
    protected boolean canLoseFocus = true;
    private int lineScrollOffset;

    protected final ColorProperty textBoxBorderColor = ColorProperty.of( "textBoxBorderColor", new Color( 160, 160, 160 ) );
    protected final ColorProperty textBoxForegroundColor = ColorProperty.of( "text box foreground", new Color( 0, 0, 0 ) );

    protected final BooleanProperty clearSuggestionTextOnFocus = new BooleanProperty( "remove suggestion text on focus", true );
    protected final BooleanProperty restoreSuggestionTextOnFocusLost = new BooleanProperty( "restore suggestion text", true );
    protected final BooleanProperty enableBackgroundDrawing = new BooleanProperty( "enable background drawing", true );
    protected final PredicateProperty<Character> charInputValidation = PredicateProperty.of( "key input validation", SharedConstants::isAllowedCharacter );
    protected final PredicateProperty<String> textColorValidation = PredicateProperty.of( "text color validation", s -> true );
    protected final IntegerProperty maxStringLength = new IntegerProperty( "max string length", 32 );
    protected final ColorProperty textColor = ColorProperty.of( "text color", new Color( 224, 224, 224 ) );
    protected final ColorProperty suggestionColor = ColorProperty.of( "suggestion color", new Color( 128, 128, 128 ) );
    protected final ColorProperty cursorColor = ColorProperty.of( "cursor color", new Color( 208, 208, 208 ) );
    protected final StringProperty textProperty = new StringProperty( "text", "" );
    protected final PredicateProperty<String> textValidator = PredicateProperty.of( "text validator", s -> true );
    protected final PredicateProperty<KeyInput> keyInput = PredicateProperty.of( "key input property", input -> true );
    protected final ObjectProperty<TextFormatter> textFormatter = new ObjectProperty<>(new TextFormatter());
    protected final ObjectProperty<TextFormatter> suggestionTextFormat = new ObjectProperty<>(new TextFormatter("suggestion text formatter", (str, num) -> str ));
    protected final ConsumerProperty<String> onTextChange = ConsumerProperty.of( "text change consumer" );
    protected final ConsumerProperty<Widget> onTickConsumer = ConsumerProperty.of( "tick consumer" );

    // Non-final variables (so players can change the default suggestion text)
    protected StringProperty suggestionText = new StringProperty( "suggestion text", "" );
    protected AbstractLabel label;

    public AbstractTextFieldWidget(int x, int y) {
        super( x, y );
        maxStringLength.addListener( (listener) -> {
            adjustTextLength(getText(), (int)listener.getValue());
        } );

        addKeyPressAction(Keyboard::isSelectAll, (i, w) -> this.selectAll());
        addKeyPressAction(Keyboard::isCopy,(i, w) -> setClipboardString(this.getSelectedText()));
        addKeyPressAction(input -> Keyboard.isPaste(input) && this.isEnabled(), (i, w) -> this.writeText(getClipboardString()));
        addKeyPressAction(Keyboard::isCut, (i, w) -> this.cut(this.getSelectedText()));
        addKeyPressAction(KeyboardUtil.inputMatches(GLFW.GLFW_KEY_BACKSPACE).and(KeyboardUtil.isControlPressed()), (i, w) -> this.deleteWords(-1));
        addKeyPressAction(KeyboardUtil.inputMatches(GLFW.GLFW_KEY_BACKSPACE).and(KeyboardUtil.isControlPressed().negate()), (i, w) -> this.deleteFromCursor(-1));
        addKeyPressAction(KeyboardUtil.inputMatches(GLFW.GLFW_KEY_DELETE).and(KeyboardUtil.isControlPressed()), (i, w) -> this.deleteWords(1));
        addKeyPressAction(KeyboardUtil.inputMatches(GLFW.GLFW_KEY_DELETE).and(KeyboardUtil.isControlPressed().negate()), (i, w) -> this.deleteFromCursor(1));
        addKeyPressAction(KeyboardUtil.inputMatches(GLFW.GLFW_KEY_RIGHT).and(KeyboardUtil.isControlPressed()), (i, w) -> {
            this.setCursorPosition(this.getNthWordFromCursor(1));
        } );
        addKeyPressAction(KeyboardUtil.inputMatches(GLFW.GLFW_KEY_RIGHT).and(KeyboardUtil.isControlPressed().negate()), (i, w) -> this.moveCursorBy(1));
        addKeyPressAction(KeyboardUtil.inputMatches(GLFW.GLFW_KEY_LEFT).and(KeyboardUtil.isControlPressed()), (i, w) -> {
            this.setCursorPosition(this.getNthWordFromCursor(-1));
        } );
        addKeyPressAction(KeyboardUtil.inputMatches(GLFW.GLFW_KEY_LEFT).and(KeyboardUtil.isControlPressed().negate()), (i, w) -> this.moveCursorBy(-1));
        addSingleKeyPressAction(GLFW.GLFW_KEY_HOME, (i, w) -> this.setCursorPositionStart());
        addSingleKeyPressAction(GLFW.GLFW_KEY_END, (i, w) -> this.setCursorPositionEnd());
    }

    private void selectAll() {
        this.setCursorPositionEnd();
        this.setSelectionPos(0);
    }

    private void setClipboardString(String text) {
        Minecraft.getInstance().keyboardListener.setClipboardString(text);
    }

    private String getClipboardString() {
        return Minecraft.getInstance().keyboardListener.getClipboardString();
    }

    private void cut(String text) {
        setClipboardString(text);
        if (this.isEnabled()) {
            this.writeText("" );
        }
    }

    public Consumer<String> getOnTextChangeConsumer() {
        return onTextChange.get();
    }

    public TextFormatter getTextFormatter() {
        return this.textFormatter.get();
    }

    public TextFormatter getSuggestionTextFormatter() {
        return this.suggestionTextFormat.get();
    }

    public Consumer<Widget> getOnTickConsumer() {
        return this.onTickConsumer.get();
    }

    public Predicate<String> getTextValidator() {
        return this.textValidator.get();
    }

    public boolean shouldRemoveSuggestionOnFocus() {
        return this.clearSuggestionTextOnFocus.get();
    }

    public boolean shouldRestoreSuggestionOnFocus() {
        return this.restoreSuggestionTextOnFocusLost.get();
    }

    public Color getCursorColor() {
        return this.cursorColor.get();
    }

    public int getMaxStringLength() {
        return this.maxStringLength.get();
    }

    public Color getSuggestionTextColor() {
        return this.suggestionColor.get();
    }

    public Color getTextColor() {
        return this.textColor.get();
    }

    public Predicate<String> getTextColorValidator() {
        return this.textColorValidation.get();
    }

    public Predicate<KeyInput> getKeyInputValidator() {
        return this.keyInput.get();
    }

    public Predicate<Character> getCharacterInputValidator() {
        return this.charInputValidation.get();
    }

    public Color getTextBoxBorderColor() {
        return this.textBoxBorderColor.get();
    }

    public Color getTextBoxBGColor() {
        return this.textBoxForegroundColor.get();
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
        if ( narrationProperty.get().isEmpty() && label != null ) {
            return I18n.format( "gui.narrate.editBox", label.getText(), getText() );
        }
        return narrationProperty.get().isEmpty() ? super.getNarrationMessage() : I18n.format( "gui.narrate.editBox", narrationProperty.get(), getText() );
    }

    private void adjustTextLength(String text, int maxLength) {
        if (text.length() > maxLength) {
            this.textProperty.set(text.substring(0, maxLength));
        }
    }

    /**
     * Sets the entire new text string.
     *
     * @param text the new text string
     */
    public void setText(String text) {
        if ( this.getTextValidator().test( text ) ) {
            int maxStringLength = this.maxStringLength.get();
            if ( text.length() > maxStringLength ) {
                adjustTextLength(text, maxStringLength);
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
        this.suggestionText.set(text);
    }

    public void setDefaultSuggestionText(String text) {
        this.suggestionText = new StringProperty( "suggestion text", text == null ? "" : text );
    }

    @Nullable
    public String getSuggestionText() {
        return this.suggestionText.get();
    }

    @Override
    protected void onFocusChange(boolean currentFocus, boolean newFocus) {
        super.onFocusChange( currentFocus, newFocus );
        if ( !this.isFocused() & newFocus && shouldRemoveSuggestionOnFocus() ) {
            setSuggestionText( "" );
        } else if ( this.isFocused() && !newFocus && shouldRestoreSuggestionOnFocus() ) {
            setSuggestionText( this.suggestionText.getDefault() );
        }
    }

    protected boolean doEnableBackgroundDrawing() {
        return this.enableBackgroundDrawing.get();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if ( !this.isVisible() ) {
            return false;
        }
        boolean clicked = in( mouseX, mouseY );
        if ( this.canLoseFocus ) {
            this.changeFocus( clicked );
        }

        if ( this.isFocused() && clicked && Keyboard.isKeyCode(mouseButton, GLFW.GLFW_MOUSE_BUTTON_LEFT) ) {
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
            KeyPressAction entry = findActionForKeyPress(input);
            if (entry != null) {
                entry.accept(input, this);
                return true;
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
        int start = Math.min(this.cursorPosition, this.selectionEnd);
        int end = Math.max(this.cursorPosition, this.selectionEnd);
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
        int min = Math.min(this.cursorPosition, this.selectionEnd);
        int max = Math.max(this.cursorPosition, this.selectionEnd);
        int len = this.maxStringLength.get() - this.getText().length() - ( min - max );
        if ( !this.getText().isEmpty() ) {
            newMessage += this.getText().substring( 0, min );
        }

        int tmp;
        if ( len < filtered.length() ) {
            newMessage += filtered.substring( 0, len );
            tmp = len;
        } else {
            newMessage += filtered;
            tmp = filtered.length();
        }

        if ( !this.getText().isEmpty() && max < this.getText().length() ) {
            newMessage += this.getText().substring( max );
        }

        if ( this.getTextValidator().test( newMessage ) ) {
            this.textProperty.set( newMessage );
            this.cursorPosition( min + tmp );
            this.setSelectionPos( this.cursorPosition );
            this.onTextChange( getText() );
        }
    }

    public void delete(int num) {
        if (!this.isEnabled()) return;
        if ( Keyboard.hasControlDown() ) {
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
                    RenderUtil.fill( k1, yPos - 1, k1 + 1, yPos + 1 + 9, getCursorColor() );
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
        RenderSystem.color4f( 0.0F, 0.0F, 255.0F, 255.0F );
        RenderSystem.disableTexture();
        RenderSystem.enableColorLogicOp();
        RenderSystem.logicOp( GlStateManager.LogicOp.OR_REVERSE );
        bufferbuilder.begin( 7, DefaultVertexFormats.POSITION );
        bufferbuilder.pos( ( double ) startX, ( double ) endY, 0.0D ).endVertex();
        bufferbuilder.pos( ( double ) endX, ( double ) endY, 0.0D ).endVertex();
        bufferbuilder.pos( ( double ) endX, ( double ) startY, 0.0D ).endVertex();
        bufferbuilder.pos( ( double ) startX, ( double ) startY, 0.0D ).endVertex();
        tessellator.draw();
        RenderSystem.disableColorLogicOp();
        RenderSystem.enableTexture();
    }

    @Override
    public void printDebug() {
        super.printDebug();
        if ( label != null ) {
            label.printDebug();
        }
    }
}
