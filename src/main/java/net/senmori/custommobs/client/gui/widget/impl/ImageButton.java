package net.senmori.custommobs.client.gui.widget.impl;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.senmori.custommobs.client.gui.AbstractWidget;
import net.senmori.custommobs.lib.properties.defaults.DefaultObjectProperty;
import net.senmori.custommobs.lib.texture.ITexture;
import net.senmori.custommobs.lib.util.RenderUtil;

@OnlyIn( Dist.CLIENT )
public class ImageButton extends AbstractWidget<ImageButton> {
    private final DefaultObjectProperty<ITexture> textureProperty = new DefaultObjectProperty<>( this, "texture", null );

    public ImageButton(int xIn, int yIn) {
        super( xIn, yIn );
    }

   public void setTexture(ITexture texture) {
        textureProperty.set( texture );
        texture.getGroup().adjustLayout();
   }

    public void renderButton(int mouseX, int mouseY, float partialTicks) {

        ITexture texture = textureProperty.get();
        if (texture == null || texture.getLocation() == null) return;
        //CustomMobs.getInstance().getLogger().info( texture.toString() );
        Minecraft.getInstance().getTextureManager().bindTexture( texture.getLocation() );
        GlStateManager.disableDepthTest();
        RenderUtil.drawTexture( this.x, this.y, texture );
        GlStateManager.enableDepthTest();
    }
}
