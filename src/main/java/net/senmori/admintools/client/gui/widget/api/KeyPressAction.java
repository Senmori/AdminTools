package net.senmori.admintools.client.gui.widget.api;

import net.minecraft.client.gui.widget.Widget;
import net.senmori.admintools.lib.input.KeyInput;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

public class KeyPressAction implements Predicate<KeyInput>, BiConsumer<KeyInput, Widget>
{
    private final Predicate<KeyInput> predicate;
    private final BiConsumer<KeyInput, Widget> consumer;
    public KeyPressAction(Predicate<KeyInput> predicate, BiConsumer<KeyInput, Widget> consumer) {
        this.predicate = predicate;
        this.consumer = consumer;
    }

    public static KeyPressAction of(Predicate<KeyInput> input, BiConsumer<KeyInput, Widget> consumer) {
        return new KeyPressAction(input, consumer);
    }

    @Override
    public void accept(KeyInput keyInput, Widget widget)
    {
        consumer.accept(keyInput, widget);
    }

    @Override
    public boolean test(KeyInput keyInput)
    {
        return predicate.test(keyInput);
    }
}
