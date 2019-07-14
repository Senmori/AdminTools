package net.senmori.custommobs.client.gui.widget.impl;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.senmori.custommobs.CustomMobs;
import net.senmori.custommobs.client.config.ClientConfig;
import net.senmori.custommobs.client.gui.AbstractWidget;
import net.senmori.custommobs.lib.properties.defaults.DefaultIntegerProperty;
import net.senmori.custommobs.lib.properties.defaults.DefaultObjectProperty;
import net.senmori.custommobs.lib.texture.ITexture;
import net.senmori.custommobs.lib.texture.IconTexture;
import net.senmori.custommobs.lib.texture.TextureGroups;
import net.senmori.custommobs.lib.util.RenderUtil;

@OnlyIn( Dist.CLIENT )
public class ImageButton extends AbstractWidget<ImageButton> {
    private final DefaultObjectProperty<ResourceLocation> textureProperty = new DefaultObjectProperty<>( this, "texture", null );
    private final DefaultIntegerProperty textureStartXProperty = new DefaultIntegerProperty( this, " texture start X", 0 );
    private final DefaultIntegerProperty textureStartYProperty = new DefaultIntegerProperty( this, " texture start Y", 0 );
    private ResourceLocation resourceLocation;
    private int xTexStart;
    private int yTexStart;
    private int yDiffText;
    private int fieldOne;
    private int fieldTwo;

    public ImageButton(int xIn, int yIn) {
        super( xIn, yIn );
    }

    public ImageButton(int xIn, int yIn, int width,
                       int height, int yDiffText, int field_one, int field_two) {
        super(xIn, yIn);
        setDimensions( width, height );
        this.fieldOne = 256;
        this.fieldTwo = 256;
        this.yDiffText = yDiffText;
    }

    public void setFields(int one, int two) {
        this.fieldOne = one;
        this.fieldTwo = two;
    }

   public void setYDiff(int yDiff) {
        this.yDiffText = yDiff;
   }

   public void setTexture(ITexture texture) {
        this.texture = texture;
        texture.getGroup().adjustLayout();
   }
   private ITexture texture;
    public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {

        for (IconTexture icon : IconTexture.values()) {
            texture = IconTexture.HEART_ABSORPTION_FULL.getTexture();
            ResourceLocation location = texture.getLocation();
            int width = texture.getWidth();
            int height = texture.getHeight();
            Minecraft.getInstance().getTextureManager().bindTexture( location );
            GlStateManager.disableDepthTest();
            AbstractGui.blit(this.x, this.y, (float)texture.getX(), (float)texture.getY(), width, height, 256, 256);

            //blit(this.x, this.y, (float)this.xTexStart, (float)i, this.width, this.height, this.fieldOne, this.fieldTwo );
            GlStateManager.enableDepthTest();
        }
        int i = this.yTexStart;
        if (this.isHovered()) {
            i += this.yDiffText;
        }

    }
}
