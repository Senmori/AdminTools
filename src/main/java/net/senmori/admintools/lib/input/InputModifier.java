package net.senmori.admintools.lib.input;

import org.lwjgl.glfw.GLFW;

public final class InputModifier {

    private final int modifiers;

    public InputModifier(int modifier) {
        this.modifiers = modifier;
    }

    public int getModifiers() {
        return modifiers;
    }

    public boolean isModifierPresent(int modifier) {
        return ( modifiers & modifier ) == modifier;
    }

    public boolean isShiftPressed() {
        return isModifierPresent( GLFW.GLFW_MOD_SHIFT );
    }

    public boolean isControlPressed() {
        return isModifierPresent( GLFW.GLFW_MOD_CONTROL );
    }

    public boolean isAltPressed() {
        return isModifierPresent( GLFW.GLFW_MOD_ALT );
    }

    @Override
    public String toString()
    {
        return "M:[" + modifiers + "]-Shift:" + isShiftPressed() + ",Alt:" + isAltPressed() + ",Ctrl:" + isControlPressed();
    }
}
