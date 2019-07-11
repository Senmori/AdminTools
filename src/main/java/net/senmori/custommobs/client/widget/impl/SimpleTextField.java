package net.senmori.custommobs.client.widget.impl;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.Widget;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.senmori.custommobs.client.widget.AbstractLabel;
import net.senmori.custommobs.client.widget.AbstractTextFieldWidget;
import net.senmori.custommobs.lib.input.KeyInput;

import javax.annotation.Nullable;
import java.awt.Color;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Due to some javafx-like property management, setting a value to null will not erase that value unless otherwise
 * stated (i.e. setting {@link #setSuggestionText(String, boolean)} to null and replacing the default}.
 *
 * This is a simple text field that is highly customizable without having to extend the class and implement your
 * custom functionality.
 *
 */
//TODO: Custom renderer via consumer?
@OnlyIn( Dist.CLIENT )
public class SimpleTextField extends AbstractTextFieldWidget {

    public SimpleTextField(int x, int y) {
        super( x, y );
    }

    /**
     * Get the x coordinate of this widget.
     *
     * @return the x coordinate of this widget.
     */
    @Override
    public int getX() {
        return super.getX();
    }

    /**
     * Get the y coordinate of this widget.
     *
     * @return the y coordinate of this widget
     */
    @Override
    public int getY() {
        return super.getY();
    }

    /**
     * Get the width of this widget
     *
     * @return the widget of this widget
     */
    @Override
    public int getWidth() {
        return super.getWidth();
    }

    /**
     * Get the height of this widget
     *
     * @return the height of this widget
     */
    @Override
    public int getHeight() {
        return super.getHeight();
    }

    /**
     * Add a new label to this text field.
     *
     * @param text the text of the label
     * @param position the {@link AbstractLabel.Position} of the label
     * @return the new {@link AbstractLabel}
     */
    public <T extends AbstractLabel> T addLabel(String text, T.Position position) {
        Label label = new Label(this.x, this.y);
        label.setText( text );
        label.setPosition( position );
        label.setParent( this );
        label.calculateLayout();
        this.label = label;
        return (T) label;
    }

    /**
     * Add a new label to this text field.
     *
     * @param label the label to add
     * @return the {@link AbstractLabel}.
     */
    public <T extends AbstractLabel> T addLabel(T label) {
        label.setParent( this );
        label.calculateLayout();
        this.label = label;
        return label;
    }

    /**
     * Remove the current label that is attached to this text field.
     */
    public void removeLabel() {
        this.label = null;
    }

    /**
     * @return the current {@link AbstractLabel} attached to this text field. Will return null if no label attached.
     */
    @Nullable
    public AbstractLabel getLabel() {
        return this.label;
    }

    /**
     * Set the consumer that will be called when the text changes in the text field.
     *
     * @param onTextChangeConsumer the new consumer
     */
    public void setOnTextChangeConsumer(Consumer<String> onTextChangeConsumer) {
        this.onTextChangeProperty.set(onTextChangeConsumer);
    }

    /**
     * Set the text formatter that will be applied directly before rendering the text in the text field.
     *
     * The BiFunction's parameter are, in order, the text to be formatted and the position of the cursor.
     *
     * @param formatter the new text formatter
     */
    public void setTextFormatter(BiFunction<String, Integer, String> formatter) {
        this.textFormatterProperty.set( formatter );
    }

    /**
     * Set the suggestion text formatter that will be applied directly before rendering the suggestion text.
     *
     * Because the suggestion text does not rely on the cursor, the parameters for the BiFunction are, in order,
     * the suggestion text and the length of the suggestion text.
     *
     * @param formatter the new suggestion text formatter
     */
    public void setSuggestionTextFormatter(BiFunction<String, Integer, String> formatter) {
        this.suggestionTextFormatProperty.set( formatter );
    }

    /**
     * Set the consumer that will be called when the text field is periodically updated via
     * {@link net.senmori.custommobs.client.gui.IUpdatable#tick()}.
     *
     * @param consumer the on tick consumer
     */
    public void setTickConsumer(Consumer<Widget> consumer) {
        this.onTickConsumerProperty.set( consumer );
    }

    /**
     * Set the text validator that will be used when setting the text. This is not the key input validator.
     * This is called when {@link #setText(String)} is called, or when a new key is appended to the end
     * of the existing text. The new text will then be passed through this validator to ensure it is still
     * a valid string.
     *
     * @param validator the new text validator
     */
    public void setTextValidator(Predicate<String> validator) {
        this.textValidatorProperty.set( validator );
    }

    /**
     * Set whether the text field should remove the suggestion text when focus
     * is gained on this widget.
     * By default, this is true.
     *
     * @param remove true to remove the suggestion on focus.
     */
    public void removeSuggestionOnFocus(boolean remove) {
        this.clearSuggestionTextOnFocusProperty.set( remove );
    }

    /**
     * Set whether to restore the suggestion text when focus is lost.
     * By default, this is true.
     *
     * @param restore true to restore the suggestion text on focus
     */
    public void restoreSuggestionOnLostFocus(boolean restore) {
        this.restoreSuggestionTextOnFocusLostProperty.set( restore );
    }

    /**
     * Set the cursor color for this text field.
     * Uses {@link Color}
     *
     * @param color the new {@link Color}
     */
    public void setCursorColor(Color color) {
        this.cursorColorProperty.set( color );
    }

    /**
     * Set the max length of the text in the text field.
     * When the max length is reached, no more text will accepted.
     *
     * @param length the maximum length of text
     */
    public void setMaxStringLength(int length) {
        this.maxStringLengthProperty.set( length );
    }

    /**
     * Set the text color of this text field. This is not the suggestion text color.
     *
     * @param color the new text {@link Color}
     */
    public void setTextColor(Color color) {
        this.textColorProperty.set( color );
    }

    /**
     * Set the text color validator that will be called directly before the text color is chosen for rendering.
     * Unlike every other predicate in this widget, this predicate's value is ignored. Instead,
     * this predicate should be used to set the text color depending on the string itself.
     *
     * @param textColorValidator the new text color validator
     */
    public void setTextColorValidator(Predicate<String> textColorValidator) {
        this.textColorValidationProperty.set( textColorValidator );
    }

    /**
     * Set the key input validator. This validator is called when a user presses a key while focused
     * on this widget. While this may report characters, this is primarily used to detect keybindings and
     * special combinations of those (i.e. Cut/Paste/Select All/etc.).
     * <br>
     * For detecting which key is pressed use {@link #setCharacterInputValidator(Predicate)}
     *
     * @param validator the new key input validator
     *
     * @see KeyInput
     */
    public void setKeyInputValidator(Predicate<KeyInput> validator) {
        this.keyInputProperty.set( validator );
    }

    /**
     * This character input validator is called when a user pressed a character (i.e. {@link Character}) while
     * focused on this widget.
     *
     * This validator allows users to determine which characters they want within their text field.
     *
     * @param predicate the character input validator
     */
    public void setCharacterInputValidator(Predicate<Character> predicate) {
        this.charInputValidationProperty.set( predicate );
    }

    /**
     * Set the entire text of the text field.
     *
     * @param text the new text string
     */
    @Override
    public void setText(String text) {
        super.setText( text );
    }

    /**
     * Get the currently selected text. This could be null, or empty string depending on where the cursor is.
     *
     * @return the currently selected text
     */
    @Nullable
    @Override
    public String getSelectedText() {
        return super.getSelectedText();
    }

    /**
     * Get the current text of the text field. This can be null if {@link #setText(String)} with a null string.
     *
     * @return the current text
     */
    @Nullable
    @Override
    public String getText() {
        return super.getText();
    }

    /**
     * Set the suggestion text that is displayed when this widget does not have focus.
     * To remove the suggestion text, use null or empty string.
     * <br>
     * To set the default suggestion text (i.e. the only that will be displayed even if you set the text to null)
     * use {@link #setSuggestionText(String, boolean)}.
     * <br>
     * This is the method that will be used the most, as {@link #setSuggestionText(String, boolean)} should only
     * be used once to set the default suggestion text.
     *
     * @param text the new text.
     *
     * @see #setSuggestionText(String, boolean)
     */
    @Override
    public void setSuggestionText(@Nullable String text) {
        super.setSuggestionText( text );
    }

    /**
     * Set the new suggestion text as well as the new default text. This enables the user to define the new
     * default value that will be rendered even if the suggestion text is set to null (as it is when the widget
     * gains focus).
     * By default, {@code replaceDefault} is false.
     *
     * @param text the new text
     * @param replaceDefault true to replace the default text with the text provided
     */
    @Override
    public void setSuggestionText(@Nullable String text, boolean replaceDefault) {
        super.setSuggestionText( text, replaceDefault );
    }

    /**
     * Set the suggestion text color.
     *
     * @param color the new suggestion text color.
     */
    public void setSuggestionTextColor(Color color) {
        this.suggestionColorProperty.set( color );
    }

    /**
     * Set the text box border color.
     *
     * @param color the new {@link Color}
     */
    public void setTextBoxBorderColor(Color color) {
        this.defaultTextBoxBackgroundColor.set( color );
    }

    /**
     * Set the text box's background color. This is the color directly behind the text.
     *
     * @param color the new {@link Color}
     */
    public void setTextBoxBGColor(Color color) {
        this.defaultTextBoxForegroundColor.set( color );
    }

    /**
     * A widget is enabled if it able to receive input.
     *
     * @return true if this widget is enabled.
     */
    public boolean isEnabled() {
        return super.isEnabled();
    }

    /**
     * Check if this widget is visible.
     *
     * @return true if the widget is visible.
     */
    public boolean isVisible() {
        return super.isVisible();
    }

    /**
     * Check if this widget currently has focus.
     *
     * @return true if the widget has focus.
     */
    @Override
    public boolean isFocused() {
        return super.isFocused();
    }

    /**
     * Check if the widget can receive input.
     * This is true if the widget is enabled, visible, and currently focused.
     *
     * @return true if the widget can receive input.
     *
     * @see #isVisible()
     * @see #isEnabled()
     * @see #isFocused()
     */
    @Override
    public boolean canReceiveInput() {
        return super.canReceiveInput();
    }

    /* ##########################
     *          GETTERS
     * ##########################
     */

    @Override
    public FontRenderer getFontRenderer() {
        return super.getFontRenderer();
    }

    @Override
    public Consumer<String> getOnTextChangeConsumer() {
        return super.getOnTextChangeConsumer();
    }

    @Override
    public BiFunction<String, Integer, String> getTextFormatter() {
        return super.getTextFormatter();
    }

    @Override
    public BiFunction<String, Integer, String> getSuggestionTextFormatter() {
        return super.getSuggestionTextFormatter();
    }

    @Override
    public Consumer<Widget> getOnTickConsumer() {
        return super.getOnTickConsumer();
    }

    @Override
    public Predicate<String> getTextValidator() {
        return super.getTextValidator();
    }

    @Override
    public boolean doRemoveSuggestionOnFocus() {
        return super.doRemoveSuggestionOnFocus();
    }

    @Override
    public boolean doRestoreSuggestionOnFocus() {
        return super.doRestoreSuggestionOnFocus();
    }

    @Override
    public Color getCursorColor() {
        return super.getCursorColor();
    }

    @Override
    public int getMaxStringLength() {
        return super.getMaxStringLength();
    }

    @Override
    public Color getSuggestionTextColor() {
        return super.getSuggestionTextColor();
    }

    @Override
    public Predicate<String> getTextColorValidator() {
        return super.getTextColorValidator();
    }

    @Override
    public Predicate<KeyInput> getKeyInputValidator() {
        return super.getKeyInputValidator();
    }

    @Override
    public Color getTextBoxBorderColor() {
        return super.getTextBoxBorderColor();
    }

    @Override
    public Color getTextBoxBGColor() {
        return super.getTextBoxBGColor();
    }
}
