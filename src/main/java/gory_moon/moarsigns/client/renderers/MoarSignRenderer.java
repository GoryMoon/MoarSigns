package gory_moon.moarsigns.client.renderers;

import gory_moon.moarsigns.blocks.Blocks;
import gory_moon.moarsigns.client.ModelMoarSign;
import gory_moon.moarsigns.lib.Info;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import gory_moon.moarsigns.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class MoarSignRenderer extends TileEntitySpecialRenderer {

    private ModelMoarSign modelMoarSign;
    private ResourceLocation texture;
    private ResourceLocation tempTexture = new ResourceLocation("textures/entity/sign.png");

    public MoarSignRenderer() {
        this.modelMoarSign = new ModelMoarSign();
    }

    public void renderTileEntityMoarSignAt(TileEntityMoarSign tileentity, double x, double y, double z, float partialTickTime) {
        texture = tileentity.getResourceLocation();

        Block block = tileentity.getBlockType();
        GL11.glPushMatrix();
        float f1 = 0.6666667F;
        float f2;

        if (!tileentity.showInGui && (block == Blocks.signStandingWood || block == Blocks.signStandingMetal)) {
            GL11.glTranslatef((float) x + 0.5F, (float) y + 0.75F * f1, (float) z + 0.5F);
            float f3 = (float) (tileentity.getBlockMetadata() * 360) / 16.0F;
            GL11.glRotatef(-f3, 0.0F, 1.0F, 0.0F);
            this.modelMoarSign.stick.showModel = true;
        } else {
            int i = tileentity.getBlockMetadata();

            int side = i & 7;

            f2 = 0.0F;

            boolean flatSign = !tileentity.showInGui && ((i & 8) >> 3) == 1;
            boolean groundSign = false;

            if (flatSign) {
                groundSign = (i & 1) == 1;

                if (groundSign) {
                    int rotation = (i & 6) >> 1;
                    f2 = 0F;

                    if (rotation == 1) f2 = 90F;
                    else if (rotation == 2) f2 = 180F;
                    else if (rotation == 3) f2 = -90F;
                } else {
                    int rotation = (i & 6) >> 1;
                    f2 = 180F;

                    if (rotation == 1) f2 = -90F;
                    else if (rotation == 2) f2 = 0F;
                    else if (rotation == 3) f2 = 90F;
                }
            } else {
                if (side == 2) {
                    f2 = 180.0F;
                }

                if (side == 4) {
                    f2 = 90.0F;
                }

                if (side == 5) {
                    f2 = -90.0F;
                }

            }

            GL11.glTranslatef((float) x + 0.5F, (float) y + 0.75F * f1, (float) z + 0.5F);
            GL11.glRotatef(-f2, 0.0F, 1.0F, 0.0F);
            if (flatSign && !groundSign) GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            if (flatSign && groundSign) {
                GL11.glRotatef(270.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
            }
            GL11.glTranslatef(0.0F, -0.3125F, -0.4375F);
            this.modelMoarSign.stick.showModel = false;
        }

        if (texture != null) bindTexture(texture);
        else bindTexture(tempTexture);
        GL11.glPushMatrix();
        GL11.glScalef(f1, -f1, -f1);
        modelMoarSign.render();
        GL11.glPopMatrix();
        FontRenderer fontRenderer = func_147498_b();
        float size = (float) tileentity.fontSize;

        f2 = 0.016666668F * f1 + (size / 1000F);
        GL11.glTranslatef(size > 0 ? 0.01F : 0.0F, 0.5F * f1 - ((float) 0.02 * size), 0.07F * f1);
        GL11.glScalef(f2, -f2, f2);
        GL11.glNormal3f(0.0F, 0.0F, -1.0F * f2);
        GL11.glDepthMask(false);

        int rows = Utils.getRows((int) size);
        int maxLength = Utils.getMaxLength((int) size);
        int offset = tileentity.textOffset;

        int[] row = Info.textPostion[((int) size)];
        int lastRow = row[0];

        if (row.length > 1 && offset > lastRow) {
            for (int i = 0; i < row.length; i++) {
                if (offset < row[i]) {
                    rows = i;
                    break;
                }
            }
        } else {
            rows = 1;
            offset = tileentity.textOffset < lastRow ? lastRow : tileentity.textOffset;
        }

        for (int j = 0; j < rows; ++j) {
            String s = fontRenderer.trimStringToWidth(tileentity.signText[j], Math.min(maxLength, fontRenderer.getStringWidth(tileentity.signText[j])));

            if (j == tileentity.lineBeingEdited)
                s = "> " + s + " <";

            fontRenderer.drawString(s, -fontRenderer.getStringWidth(s) / 2, (j * 10 - tileentity.signText.length * 5) - offset - (size > 12 ? 0 : size > 0 ? 1 : 2), 0);

        }

        GL11.glDepthMask(true);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }


    @Override
    public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float partialTickTime) {
        renderTileEntityMoarSignAt((TileEntityMoarSign) tileentity, x, y, z, partialTickTime);
    }
}
