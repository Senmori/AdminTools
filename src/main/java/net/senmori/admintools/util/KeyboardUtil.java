package net.senmori.admintools.util;

import net.senmori.admintools.lib.input.KeyInput;
import net.senmori.admintools.lib.util.Keyboard;

import java.util.function.Predicate;

public class KeyboardUtil
{
    public static Predicate<KeyInput> inputMatches(int keyCode) {
        return keyInput -> Keyboard.isKeyCode(keyInput, keyCode);
    }

    public static Predicate<KeyInput> isControlPressed() {
        return i -> i.getInputModifier().isControlPressed();
    }

    public static Predicate<KeyInput> isShiftPressed() {
        return i -> i.getInputModifier().isShiftPressed();
    }

    public static Predicate<KeyInput> isAltPressed() {
        return i -> i.getInputModifier().isAltPressed();
    }
}
