package net.senmori.admintools.lib.input;

import net.senmori.admintools.lib.util.Keyboard;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

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

    @Override
    public String toString() {
        return "KeyInput: " + getName() + " - (S: " + scanCode + ",K: " + keyCode + ", " + inputModifier.toString();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof KeyInput)) {
            return false;
        }
        KeyInput other = (KeyInput)obj;
        return keyCode == other.keyCode && scanCode == other.scanCode &&
                getInputModifier().getModifiers() == other.getInputModifier().getModifiers();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(keyCode, scanCode, inputModifier.getModifiers());
    }

    public enum Action {
        PRESS,
        RELEASE,
        CHAR;
    }

    public enum Type
    {
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
        MODIFIER {
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
                return false;
            }
        };
        public abstract boolean accepts(int codePoint, int modifiers);

        public static Type find(int codePoint, int modifiers) {
            return Stream.of(KeyInput.Type.values())
                    .filter(value -> value.accepts(codePoint,modifiers))
                    .findFirst()
                    .orElse(Type.UNKNOWN);
        }
    }
}
