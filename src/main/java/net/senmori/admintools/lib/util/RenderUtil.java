package net.senmori.admintools.lib.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.senmori.admintools.lib.texture.Texture;

import java.awt.Color;

/**
 * This is a utility class that has been copied from AbstractGui so I can make changes to parameter names.
 * Mojang wrote this class. I just changed the parameter names to make it easier to use.
 */
public class RenderUtil {


    public static void drawTexture(int screenX, int screenY, Texture texture) {
        AbstractGui.blit( screenX, screenY, ( float ) texture.getX(), ( float ) texture.getY(), texture.getWidth(), texture.getHeight(), 256, 256 );
    }

    public static void drawOutline(Widget widget, Color color, boolean renderMiddle) {
        int x = widget.x;
        int y = widget.y;
        int width = widget.getWidth();
        int height = widget.getHeight();

        int lineWidth = 1;
        // top
        fill( x, y, ( x + width ), y + lineWidth, color );
        // bottom
        fill( x, ( y + height ), ( x + width ), ( y + height ) - lineWidth, color );
        // left
        fill( x, y, x + lineWidth, y + height, color );
        // right
        fill( ( x + width ), y, ( x + width ) - lineWidth, ( y + height ), color );

        if ( renderMiddle ) {
            int middleX = x + (width / 2 ) - 1;
            int middleY = y + (height / 2);
            fill(middleX, y, middleX + lineWidth, (y + height), color); // vertical divider
            fill(x, middleY, x + width, middleY + lineWidth, color); // horizontal divider
        }
    }

    public static void fill(int startX, int startY, int endX, int endY, Color color) {
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

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.defaultBlendFunc();
        RenderSystem.color4f( color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() );
        bufferbuilder.begin( 7, DefaultVertexFormats.POSITION );
        bufferbuilder.pos( ( double ) startX, ( double ) endY, 0.0D ).endVertex();
        bufferbuilder.pos( ( double ) endX, ( double ) endY, 0.0D ).endVertex();
        bufferbuilder.pos( ( double ) endX, ( double ) startY, 0.0D ).endVertex();
        bufferbuilder.pos( ( double ) startX, ( double ) startY, 0.0D ).endVertex();
        tessellator.draw();
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }
}
