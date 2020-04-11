package net.senmori.admintools.lib.input;

import net.senmori.admintools.lib.util.Keyboard;
import org.lwjgl.glfw.GLFW;

import java.util.stream.Stream;

public final class MouseInput
{

    private int button, action;
    private double mouseX, mouseY;
    private double scrollDelta;

    private MouseInput.Button buttonType = Button.UNKNOWN;
    private Type actionType = Type.UNKNOWN;
    private Action mouseInputAction = Action.UNKNOWN;
    private InputModifier inputModifier;

    public static MouseInput hover(double mouseX, double mouseY)
    {
        return MouseInput.mouseInput(Action.MOVE, mouseX, mouseY, -1, Type.UNKNOWN.getRawAction(), Keyboard.buildCurrentModifiers());
    }

    /**
     * Normal left/right/middle click constructor
     */
    private MouseInput(Action mouseInputActionn, double mouseX, double mouseY, int button, int action, int modifiers)
    {
        this.button = button;
        this.action = action;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.buttonType = Button.find(button);
        this.actionType = Type.find(action);
        this.inputModifier = new InputModifier(modifiers);
        this.mouseInputAction = mouseInputActionn;
    }

    public static MouseInput mouseInput(Action mouseInputAction, double mouseX, double mouseY, int button, int action, int modifier)
    {
        return new MouseInput(mouseInputAction, mouseX, mouseY, button, action, modifier);
    }

    private MouseInput(Action mouseInputAction, double mouseX, double mouseY, int button, double dragX, double dragY, int modifier)
    {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.button = button;
        this.action = GLFW.GLFW_PRESS; // Because a drag is a constant press with movement
        this.buttonType = Button.find(button);
        this.actionType = Type.PRESS;
        this.mouseInputAction = mouseInputAction;
        this.inputModifier = new InputModifier(modifier); // no modifiers
    }

    public static MouseInput drag(double mouseX, double mouseY, int button, double dragX, double dragY, int modifier)
    {
        return new MouseInput(Action.DRAG, mouseX, mouseY, button, dragX, dragY, modifier);
    }

    private MouseInput(Action mouseInputAction, double mouseX, double mouseY, double scrollDelta, int modifiers)
    {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.scrollDelta = scrollDelta;
        this.inputModifier = new InputModifier(modifiers);
        this.mouseInputAction = mouseInputAction;
    }

    public static MouseInput scroll(double x, double y, double scrollDeta, int modifiers)
    {
        return new MouseInput(Action.SCROLL, x, y, scrollDeta, modifiers);
    }

    public double getMouseX()
    {
        return mouseX;
    }

    public double getMouseY()
    {
        return mouseY;
    }

    public double getScrollDelta()
    {
        return scrollDelta;
    }

    public Action getMouseInputAction()
    {
        return mouseInputAction;
    }

    public int getButton()
    {
        return button;
    }

    public int getAction()
    {
        return action;
    }

    public Button getButtonType()
    {
        return buttonType;
    }

    public Type getActionType()
    {
        return actionType;
    }

    public InputModifier getInputModifier()
    {
        return inputModifier;
    }

    public enum Action
    {
        CLICK,
        DRAG,
        SCROLL,
        MOVE,
        UNKNOWN;
    }

    public enum Type
    {
        PRESS(GLFW.GLFW_PRESS) {
            @Override
            public boolean accepts(int action)
            {
                return action == getRawAction();
            }
        },
        RELEASE(GLFW.GLFW_RELEASE) {
            @Override
            public boolean accepts(int action)
            {
                return action == getRawAction();
            }
        },
        UNKNOWN(GLFW.GLFW_KEY_UNKNOWN) {
            @Override
            public boolean accepts(int action)
            {
                return true;
            }
        };

        public abstract boolean accepts(int action);

        private final int rawAction;

        Type(int rawAction)
        {
            this.rawAction = rawAction;
        }

        public int getRawAction()
        {
            return rawAction;
        }


        public static Type find(int action)
        {
            return Stream.of(Type.values())
                    .filter(v -> v.accepts(action))
                    .findFirst()
                    .orElse(UNKNOWN);
        }
    }

    public enum Button
    {
        LEFT {
            @Override
            public boolean accepts(int button)
            {
                return button == GLFW.GLFW_MOUSE_BUTTON_LEFT;
            }
        },
        RIGHT {
            @Override
            public boolean accepts(int button)
            {
                return button == GLFW.GLFW_MOUSE_BUTTON_RIGHT;
            }
        },
        MIDDLE {
            @Override
            public boolean accepts(int button)
            {
                return button == GLFW.GLFW_MOUSE_BUTTON_MIDDLE;
            }
        },
        UNKNOWN {
            @Override
            public boolean accepts(int button)
            {
                return true;
            }
        };

        public abstract boolean accepts(int button);


        public static Button find(int button)
        {
            return Stream.of(Button.values())
                    .filter(v -> v.accepts(button))
                    .findFirst()
                    .orElse(UNKNOWN);
        }
    }
}
