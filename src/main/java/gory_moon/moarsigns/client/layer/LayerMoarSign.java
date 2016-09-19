package gory_moon.moarsigns.client.layer;

import gory_moon.moarsigns.client.models.ModelMoarSign;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Just playing around a bit, no permanent code
 */
public class LayerMoarSign implements LayerRenderer {

    private ModelMoarSign modelMoarSign;
    private ResourceLocation texture = new ResourceLocation("moarsigns:textures/signs/metal/diamond_sign.png");

    public LayerMoarSign() {
        modelMoarSign = new ModelMoarSign();
        modelMoarSign.stick.showModel = false;
    }

    @Override
    public void doRenderLayer(EntityLivingBase entityLivingbase, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entityLivingbase.getName().equals("Gory_Moon") && !entityLivingbase.isInvisible()) {
            float sneak = entityLivingbase.isSneaking()? 0.1F: 0.0F;
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 0.0F + sneak, 0.15F);
            float f = 0.3333334F;
            GlStateManager.scale(f, -f, -f);
            if (sneak > 0) {
                GlStateManager.rotate(30, 0, 0, 0);
                GlStateManager.translate(0.0F, 0.0F, 0.12F);
            }
            Minecraft.getMinecraft().renderEngine.bindTexture(texture);
            modelMoarSign.render();
            GlStateManager.popMatrix();

            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;


            GlStateManager.pushMatrix();
            int size = 5;
            float f1 = 0.016666668F * f + (size / 1000F);;
            //GlStateManager.translate(size > 0 ? 0.01F : 0.0F, 0.5F * f - ((float) 0.02 * size) - (size < 2 ? 0 : size < 7 ? 0.01F : size < 11 ? 0.02F : size < 16 ? 0.03F : size < 20 ? 0.035F : 0.037F), 0.07F * f);
            GlStateManager.translate(0.0F, 0.043F + sneak, 0.1709F);
            GlStateManager.scale(-f1, f1, f1);
            if (sneak > 0) {
                GlStateManager.rotate(30, 0, 0, 0);
                GlStateManager.translate(0.0F, 0.0F, 0.12F);
            }
            GL11.glNormal3f(0.0F, 0.0F, -1.0F * f1);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.depthMask(false);


            GlStateManager.disableLighting();

            String s = "§eMoar";
            String s1 = "§6Signs";
            fontRenderer.drawString(s, -fontRenderer.getStringWidth(s) / 2, 2, 0, true);
            fontRenderer.drawString(s1, -fontRenderer.getStringWidth(s1) / 2, 12, 0, true);

            GlStateManager.enableLighting();

            GlStateManager.depthMask(true);
            GlStateManager.popMatrix();
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
