package net.senmori.admintools.client.gui.widget.api;

import net.minecraft.client.gui.widget.Widget;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Represents any object that can be attached to a given {@link Widget} and rendered along side it.
 * This object is responsible for it's own rendering and positioning.
 */
public interface IAttachable {


    /**
     * @return the {@link Widget} this object is attached to.
     */
    @Nullable
    Widget getAttachedWidget();

    /**
     * Check if this attachable object is attached to the given widget.
     *
     * @param widget the widget to check
     * @return true if this attachable is attached to the given widget
     */
    boolean isAttachedTo(Widget widget);

    /**
     * Get the {@link Widget} that is implementing this interface
     *
     * @return the widget
     */
    @Nonnull
    Widget getWidget();
}
