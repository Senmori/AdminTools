package net.senmori.custommobs.lib.util;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

import java.awt.Color;

/**
 * This is a utility class that has been copied from AbstractGui so I can make changes to parameter names.
 * Mojang wrote this class. I just changed the parameter names to make it easier to use.
 */
public class RenderUtil {

    public static void drawTextureSprite(int startX, int startY, int width, int height, ResourceLocation texture) {
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureMap().getSprite( texture );
        drawTextureSprite( startX, startY, width, height, sprite );
    }

    public static void drawTextureSprite(int startX, int startY, int width, int height, TextureAtlasSprite sprite) {
        drawTextureSprite( startX, startY, 0, width, height, sprite);
    }

    public static void drawTextureSprite(int startX, int startY, int zLayer, int width, int height, TextureAtlasSprite sprite) {
        innerDraw( startX, startX + width, startY, startY + height, zLayer, sprite.getMinU(), sprite.getMaxU(), sprite.getMinV(), sprite.getMaxV() );
    }

    public static void blit(int startX, int startY, float textureX, float textureY, int width, int height) {
        AbstractGui.blit(startX, startY, textureX, textureY, width, height, 256, 256);
    }

    public static void innerDraw(int startX, int totalWidth, int startY, int totalHeight, int zLayer, float minTexU, float maxTexU, float texMinV, float texMaxV) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin( 7, DefaultVertexFormats.POSITION_TEX );
        bufferBuilder.pos((double)startX, (double)totalHeight, (double)zLayer).tex((double)minTexU, (double)texMaxV).endVertex();
        bufferBuilder.pos((double)totalWidth, (double)totalHeight, (double)zLayer).tex((double)maxTexU, (double)texMaxV).endVertex();
        bufferBuilder.pos((double)totalWidth, (double)startY, (double)zLayer).tex((double)maxTexU, (double)texMinV).endVertex();
        bufferBuilder.pos((double)startX, (double)startY, (double)zLayer).tex((double)minTexU, (double)texMinV).endVertex();
        tessellator.draw();
    }

    protected void hLine(int startX, int endX, int length, Color color) {
        if (endX < startX) {
            int i = startX;
            startX = endX;
            endX = i;
        }

        fill(startX, length, endX + 1, length + 1, color);
    }

    protected void vLine(int startX, int endY, int length, Color color) {
        if (length < endY) {
            int i = endY;
            endY = length;
            length = i;
        }

        fill(startX, endY + 1, startX + 1, length, color);
    }

    public static void fill(int startX, int startY, int endX, int endY, Color color) {
        if (startX < endX) {
            int i = startX;
            startX = endX;
            endX = i;
        }

        if (startY < endY) {
            int j = startY;
            startY = endY;
            endY = j;
        }

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos((double)startX, (double)endY, 0.0D).endVertex();
        bufferbuilder.pos((double)endX, (double)endY, 0.0D).endVertex();
        bufferbuilder.pos((double)endX, (double)startY, 0.0D).endVertex();
        bufferbuilder.pos((double)startX, (double)startY, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture();
        GlStateManager.disableBlend();
    }
}
