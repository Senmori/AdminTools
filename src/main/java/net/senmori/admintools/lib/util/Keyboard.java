package net.senmori.admintools.lib.util;

import net.minecraft.client.Minecraft;
import net.senmori.admintools.lib.input.InputModifier;
import net.senmori.admintools.lib.input.KeyInput;
import org.lwjgl.glfw.GLFW;

import java.nio.DoubleBuffer;
import java.util.function.Supplier;

public class Keyboard {
    public static final Supplier<Long> MC_WINDOW_HANDLE = () -> Minecraft.getInstance().getMainWindow().getHandle();

    private static DoubleBuffer MOUSE_X_BUFFER = DoubleBuffer.allocate( 1 );
    private static DoubleBuffer MOUSE_Y_BUFFER = DoubleBuffer.allocate( 1 );

    public static boolean isKeyDown(int key) {
        return doesKeyMatchGLFW( key, GLFW.GLFW_PRESS );
    }

    public static boolean isKeyReleased(int key) {
        return doesKeyMatchGLFW( key, GLFW.GLFW_RELEASE );
    }

    private static boolean doesKeyMatchGLFW(int key, int glfwKeyStatus) {
        return GLFW.glfwGetKey( MC_WINDOW_HANDLE.get(), key ) == glfwKeyStatus;
    }

    public static boolean isKeyCode(int keyCode, int GLFWKeycode) {
        return keyCode == GLFWKeycode;
    }

    public static boolean isKeyCode(KeyInput input, int GLFWKeycode) {
        return isKeyCode(input.getKeyCode(), GLFWKeycode);
    }

    public static String getKeyName(int keyCode, int scanCode) {
        return GLFW.glfwGetKeyName( keyCode, scanCode );
    }

    public static boolean hasShiftDown() {
        return Keyboard.isKeyDown( GLFW.GLFW_KEY_LEFT_SHIFT ) || Keyboard.isKeyDown( GLFW.GLFW_KEY_RIGHT_SHIFT );
    }
    public static boolean hasAltDown() {
        return Keyboard.isKeyDown( GLFW.GLFW_KEY_LEFT_ALT ) || Keyboard.isKeyDown( GLFW.GLFW_KEY_RIGHT_ALT );
    }
    public static boolean hasControlDown() {
        return Keyboard.isKeyDown( GLFW.GLFW_KEY_LEFT_CONTROL ) || Keyboard.isKeyDown( GLFW.GLFW_KEY_RIGHT_CONTROL );
    }

    public static boolean isCut(KeyInput input) {
        InputModifier mods = input.getInputModifier();
        return input.getKeyCode() == GLFW.GLFW_KEY_X && mods.isControlPressed() && !mods.isShiftPressed() && !mods.isAltPressed();
    }

    public static boolean isPaste(KeyInput input) {
        InputModifier mods = input.getInputModifier();
        return input.getKeyCode() == GLFW.GLFW_KEY_V && mods.isControlPressed() && !mods.isShiftPressed() && !mods.isAltPressed();
    }

    public static boolean isCopy(KeyInput input) {
        InputModifier mods = input.getInputModifier();
        return input.getKeyCode() == GLFW.GLFW_KEY_C && mods.isControlPressed() && !mods.isShiftPressed() && !mods.isAltPressed();
    }

    public static boolean isSelectAll(KeyInput input) {
        InputModifier mods = input.getInputModifier();
        return input.getKeyCode() == GLFW.GLFW_KEY_A && mods.isControlPressed() && !mods.isShiftPressed() && !mods.isAltPressed();
    }

    public static int buildCurrentModifiers() {
        int mod = 0;
        if (isKeyDown( GLFW.GLFW_KEY_LEFT_SHIFT ) || isKeyDown( GLFW.GLFW_KEY_RIGHT_SHIFT )) mod |= GLFW.GLFW_MOD_SHIFT;
        if (isKeyDown( GLFW.GLFW_KEY_LEFT_CONTROL ) || isKeyDown( GLFW.GLFW_KEY_RIGHT_CONTROL )) mod |= GLFW.GLFW_MOD_CONTROL;
        if (isKeyDown( GLFW.GLFW_KEY_LEFT_ALT ) || isKeyDown( GLFW.GLFW_KEY_RIGHT_ALT )) mod |= GLFW.GLFW_MOD_ALT;
        if (isKeyDown( GLFW.GLFW_KEY_LEFT_SUPER ) || isKeyDown( GLFW.GLFW_KEY_RIGHT_SUPER )) mod |= GLFW.GLFW_MOD_SUPER;
        if (isKeyDown( GLFW.GLFW_KEY_CAPS_LOCK)) mod |= GLFW.GLFW_MOD_CAPS_LOCK;
        if (isKeyDown( GLFW.GLFW_KEY_NUM_LOCK )) mod |= GLFW.GLFW_MOD_NUM_LOCK;
        return mod;
    }

    private static void allocateMouseBuffers() {
        MOUSE_X_BUFFER = DoubleBuffer.allocate( 1 );
        MOUSE_Y_BUFFER = DoubleBuffer.allocate( 1 );
    }

    public static double getMouseX() {
        allocateMouseBuffers();
        GLFW.glfwGetCursorPos( MC_WINDOW_HANDLE.get(), MOUSE_X_BUFFER, MOUSE_Y_BUFFER );
        return MOUSE_X_BUFFER.hasArray() ? MOUSE_X_BUFFER.get() : -1.0D;
    }

    public static double getMouseY() {
        allocateMouseBuffers();
        GLFW.glfwGetCursorPos( MC_WINDOW_HANDLE.get(), MOUSE_X_BUFFER, MOUSE_Y_BUFFER );
        return MOUSE_Y_BUFFER.hasArray() ? MOUSE_Y_BUFFER.get() : -1.0D;
    }

    public static double getMouseWheel() {
        return 0.0D;
    }
}
