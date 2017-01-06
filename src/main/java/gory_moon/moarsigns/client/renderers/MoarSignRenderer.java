package gory_moon.moarsigns.client.renderers;

import gory_moon.moarsigns.blocks.ModBlocks;
import gory_moon.moarsigns.client.models.ModelMoarSign;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class MoarSignRenderer extends TileEntitySpecialRenderer<TileEntityMoarSign> {

    private ModelMoarSign modelMoarSign;
    private ResourceLocation tempTexture = new ResourceLocation("textures/entity/sign.png");

    public MoarSignRenderer() {
        this.modelMoarSign = new ModelMoarSign();
    }

    @Override
    public void renderTileEntityAt(TileEntityMoarSign te, double x, double y, double z, float partialTicks, int destroyStage) {
        ResourceLocation texture = te.getResourceLocation();

        Block block = te.getBlockType();
        GlStateManager.pushMatrix();
        float f = 0.6666667F;
        float f1;

        if (!te.showInGui && (block == ModBlocks.SIGN_STANDING_WOOD || block == ModBlocks.SIGN_STANDING_METAL)) {
            GlStateManager.translate((float) x + 0.5F, (float) y + 0.75F * f, (float) z + 0.5F);
            float f2 = (float) (te.getBlockMetadata() * 360) / 16.0F;
            GlStateManager.rotate(-f2, 0.0F, 1.0F, 0.0F);
            this.modelMoarSign.stick.showModel = true;
        } else {
            int i = te.getBlockMetadata();

            int side = !te.showInGui ? i & 7 : 2;

            f1 = 0.0F;

            boolean flatSign = !te.showInGui && ((i & 8) >> 3) == 1;
            boolean groundSign = false;

            if (flatSign) {
                groundSign = (i & 1) == 1;

                if (groundSign) {
                    int rotation = (i & 6) >> 1;
                    f1 = 0F;

                    if (rotation == 1)
                        f1 = 90F;
                    else if (rotation == 2)
                        f1 = 180F;
                    else if (rotation == 3)
                        f1 = -90F;
                } else {
                    int rotation = (i & 6) >> 1;
                    f1 = 180F;

                    if (rotation == 1)
                        f1 = -90F;
                    else if (rotation == 2)
                        f1 = 0F;
                    else if (rotation == 3)
                        f1 = 90F;
                }
            } else {
                if (side == 2) {
                    f1 = 180.0F;
                }

                if (side == 4) {
                    f1 = 90.0F;
                }

                if (side == 5) {
                    f1 = -90.0F;
                }

            }

            GlStateManager.translate((float) x + 0.5F, (float) y + 0.75F * f, (float) z + 0.5F);
            GlStateManager.rotate(-f1, 0.0F, 1.0F, 0.0F);
            if (flatSign && !groundSign)
                GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            if (flatSign && groundSign) {
                GlStateManager.rotate(270.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
            }
            GlStateManager.translate(0.0F, -0.3125F, -0.4375F);
            this.modelMoarSign.stick.showModel = false;
        }

        if (destroyStage >= 0) {
            bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 2.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        } else {
            if (texture != null)
                bindTexture(texture);
            else
                bindTexture(tempTexture);
        }

        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        GlStateManager.scale(f, -f, -f);
        modelMoarSign.render();
        GlStateManager.popMatrix();

        FontRenderer fontRenderer = getFontRenderer();

        int[] sizes = te.rowSizes;
        boolean[] rows = te.visibleRows;
        int[] offset = te.rowLocations;

        if (destroyStage < 0) {
            for (int row = 0; row < rows.length; row++) {
                if (te.signText[row] != null) {
                    if (!rows[row])
                        continue;

                    float size = sizes[row];
                    GlStateManager.pushMatrix();
                    f1 = 0.016666668F * f + (size / 1000F);
                    GlStateManager.translate(size > 0 ? 0.01F : 0.0F, 0.5F * f - ((float) 0.02 * size) - (size < 2 ? 0 : size < 7 ? 0.01F : size < 11 ? 0.02F : size < 16 ? 0.03F : size < 20 ? 0.035F : 0.037F), 0.07F * f);
                    GlStateManager.scale(f1, -f1, f1);
                    GL11.glNormal3f(0.0F, 0.0F, -1.0F * f1);
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    GlStateManager.depthMask(false);

                    ITextComponent ichatcomponent = te.signText[row];
                    List<ITextComponent> list = GuiUtilRenderComponents.splitText(ichatcomponent, 90, fontRenderer, false, true);
                    String s = list != null && list.size() > 0 ? ((ITextComponent) list.get(0)).getFormattedText() : "";

                    int maxLength = Utils.getMaxLength((int) size) - Utils.toPixelWidth(fontRenderer, Utils.getStyleOffset(s, te.shadowRows[row]));
                    s = fontRenderer.trimStringToWidth(s, Math.min(maxLength, fontRenderer.getStringWidth(s)));

                    GlStateManager.disableLighting();

                    fontRenderer.drawString(s, -fontRenderer.getStringWidth(s) / 2, (-te.signText.length * 5) + offset[row] - 2, 0, te.shadowRows[row]);

                    GlStateManager.enableLighting();

                    GlStateManager.depthMask(true);
                    GlStateManager.popMatrix();
                }
            }
        }


        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();

        if (destroyStage >= 0) {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }
}
