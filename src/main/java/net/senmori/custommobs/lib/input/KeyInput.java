package net.senmori.custommobs.lib.input;

import net.senmori.custommobs.lib.util.Keyboard;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;

public final class KeyInput {

    @Nullable private final String name;
    private final KeyInput.Type type;
    private final KeyInput.Action action;
    private final int keyCode;
    private final int scanCode;
    private final InputModifier inputModifier;

    private KeyInput(KeyInput.Action action, int keyCode, int scanCode, int modifiers) {
        this.action = action;
        this.keyCode = keyCode;
        this.scanCode = scanCode;
        this.name = Keyboard.getKeyName( keyCode, scanCode );
        this.type = KeyInput.Type.find( keyCode, modifiers );
        this.inputModifier = new InputModifier( modifiers );
    }

    public static KeyInput key(KeyInput.Action action, int keyCode, int scanCode, int modifiers) {
        return new KeyInput(action, keyCode, scanCode, modifiers);
    }

    private KeyInput(KeyInput.Action action, int keyCode, int scanCode, int modifiers, char key) {
        this.action = action;
        this.keyCode = keyCode;
        this.scanCode = scanCode;
        this.inputModifier = new InputModifier( modifiers );
        this.type = KeyInput.Type.find(keyCode, modifiers);
        this.name = String.valueOf( key );
    }

    public static KeyInput charKey(KeyInput.Action action, int keyCode, int scanCode, int modifiers, char key) {
        return new KeyInput(action, keyCode, scanCode, modifiers, key);
    }

    public KeyInput.Type getInputType() {
        return type;
    }

    public KeyInput.Action getAction() {
        return action;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public int getScanCode() {
        return scanCode;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public InputModifier getInputModifier() {
        return inputModifier;
    }

    public enum Action {
        PRESS,
        RELEASE,
        CHAR;
    }

    public enum Type {
        CHAR {
            @Override
            public boolean accepts(int keycode, int modifiers) {
                return Character.isAlphabetic( keycode );
            }
        },
        DIGIT {
            @Override
            public boolean accepts(int codePoint, int modifiers) {
                return Character.isDigit( codePoint );
            }
        },
        MOD {
            @Override
            public boolean accepts(int codePoint, int modifiers) {
                // check for each GLFW_MOD key
                return !( CHAR.accepts( codePoint, modifiers ) || DIGIT.accepts( codePoint, modifiers ) )
                        && ( is( modifiers, GLFW.GLFW_MOD_SHIFT )
                        || is( modifiers, GLFW.GLFW_MOD_CONTROL )
                        || is( modifiers, GLFW.GLFW_MOD_ALT )
                        || is( modifiers, GLFW.GLFW_MOD_SUPER )
                        || is( modifiers, GLFW.GLFW_MOD_CAPS_LOCK )
                        || is( modifiers, GLFW.GLFW_MOD_NUM_LOCK )
                );
            }

            private boolean is(int codePoint, int mod) {
                return ( codePoint & mod ) == mod;
            }
        },
        UNKNOWN {
            @Override
            public boolean accepts(int codePoint, int modifiers) {
                return true;
            }
        };

        public abstract boolean accepts(int codePoint, int modifiers);

        public static Type find(int codePoint, int modifiers) {
            if ( CHAR.accepts( codePoint, modifiers ) ) return CHAR;
            if ( DIGIT.accepts( codePoint, modifiers ) ) return DIGIT;
            if ( MOD.accepts( codePoint, modifiers ) ) return MOD;
            return UNKNOWN;
        }
    }
}
