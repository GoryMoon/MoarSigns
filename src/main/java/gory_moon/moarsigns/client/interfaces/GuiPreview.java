package gory_moon.moarsigns.client.interfaces;

import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.blocks.ModBlocks;
import gory_moon.moarsigns.client.interfaces.containers.ContainerPreview;
import gory_moon.moarsigns.items.ItemMoarSign;
import gory_moon.moarsigns.items.ModItems;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import gory_moon.moarsigns.util.Colors;
import gory_moon.moarsigns.util.Localization;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.Arrays;

public class GuiPreview extends GuiContainer {

    private static final ResourceLocation texture = new ResourceLocation("moarsigns", "textures/gui/sign_preview.png");
    private float currentScroll;
    private boolean isScrolling;
    private boolean wasClicking;
    private boolean firstDraw;

    private TileEntityMoarSign sign;
    private float roll;
    private float yaw;
    private int scrollX;
    private int scrollY;
    private boolean isDraging;
    private boolean isSpinning = true;
    private boolean rollDown;

    public GuiPreview() {
        super(new ContainerPreview());

        xSize = 226;
        ySize = 140;
        this.allowUserInput = true;
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.clear();

        ContainerPreview containerPreview = (ContainerPreview) inventorySlots;
        containerPreview.itemList.clear();
        ModItems.SIGN.getSubItemStacks(containerPreview.itemList);
    }

    @Override
    public void drawScreen(int x, int y, float renderPartialTicks) {
        if (!firstDraw) {
            this.currentScroll = 0.0F;
            ((ContainerPreview) inventorySlots).scrollTo(0.0F);
            firstDraw = true;
        }
        boolean buttonDown = Mouse.isButtonDown(0);

        if (!this.wasClicking && buttonDown && x >= guiLeft + 101 && y >= guiTop + 8 && x < guiLeft + 113 && y < guiTop + 132) {
            this.isScrolling = this.needsScrollBars();
        }

        if (!buttonDown) {
            this.isScrolling = false;
        }

        this.wasClicking = buttonDown;

        if (this.isScrolling) {

            this.currentScroll = ((float) (y - guiTop - 8) - 7.5F) / ((float) (117) - 15F);

            if (this.currentScroll < 0.0F) {
                this.currentScroll = 0.0F;
            }

            if (this.currentScroll > 1.0F) {
                this.currentScroll = 1.0F;
            }

            ((ContainerPreview) this.inventorySlots).scrollTo(this.currentScroll);
        }
        super.drawScreen(x, y, renderPartialTicks);

        if (x >= guiLeft + 115 && y >= guiTop + 5 && x < guiLeft + 220 && y < guiTop + 135) {
            String s = Localization.GUI.PREVIEW.DRAG.translate();
            drawHoveringText(Arrays.asList(s.split("\n")), x, y, fontRenderer);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float renderPartialTicks, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.drawTexturedModalRect(guiLeft + 101, guiTop + 8 + (int) ((float) (109) * this.currentScroll), 232 + (this.needsScrollBars() ? 0 : 12), 0, 12, 15);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        if (sign != null) {
            GL11.glPushMatrix();
            GL11.glTranslatef((float) guiLeft + 170F, (float) guiTop + 70F, 70.0F);

            float scale = 70;
            GL11.glScalef(-scale, scale, scale);

            GL11.glRotatef(180, 0, 0, 1);
            GL11.glRotatef(roll, 1, 0, 0);
            GL11.glRotatef(yaw, 0, 1, 0);

            TileEntityRendererDispatcher.instance.renderTileEntityAt(sign, -0.5D, -0.75D, -0.5D, 0.0F);
            GL11.glPopMatrix();

            if (isSpinning) {
                yaw += 0.1F;
                roll %= 360.0F;

                if (rollDown) {
                    roll -= 0.01F;
                    if (roll > 5) {
                        roll -= 0.05F;
                    } else {
                        roll -= 0.01F;
                    }
                    if (roll < -5) {
                        rollDown = false;
                    }
                } else {
                    roll += 0.01F;
                    if ((roll < 15)) {
                        roll += 0.05F;
                    } else {
                        roll += 0.01F;
                    }
                    if (roll > 22) {
                        rollDown = true;
                    }

                }
            }
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        super.drawGuiContainerForegroundLayer(x, y);
        if (sign == null)
            fontRenderer.drawSplitString(Localization.GUI.PREVIEW.CLICK_SIGN.translate(), 120, 10, 70, Colors.LIGHTGRAY.getRGB());
    }

    private boolean needsScrollBars() {
        return ((ContainerPreview) inventorySlots).needsScrollBars();
    }

    @Override
    protected void handleMouseClick(Slot slot, int slotId, int mouseButton, ClickType type) {
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();

            sign = new TileEntityMoarSign();
            String texture = ItemMoarSign.getTextureFromNBTFull(stack.getTagCompound());
            SignInfo info = SignRegistry.get(texture);

            sign.setWorld(FMLClientHandler.instance().getWorldClient());
            sign.isMetal = info.isMetal;
            sign.setBlockType(info.isMetal ? ModBlocks.SIGN_STANDING_METAL : ModBlocks.SIGN_STANDING_WOOD);
            ITextComponent[] components = new ITextComponent[]{null, new TextComponentString(Localization.GUI.PREVIEW.EXAMPLE_TEXT_1.translate()), new TextComponentString(Localization.GUI.PREVIEW.EXAMPLE_TEXT_2.translate()), null};
            System.arraycopy(components, 0, sign.signText, 0, sign.signText.length);
            sign.setResourceLocation(texture);
        }
    }


    @Override
    public void mouseClickMove(int x, int y, int button, long timeSinceClicked) {
        if (isDraging && button == 0) {
            int x1 = x - guiLeft;
            int y1 = y - guiTop;

            yaw = yaw + x1 - scrollX;
            roll = roll + y1 - scrollY;
            scrollX = x1;
            scrollY = y1;
        }
        super.mouseClickMove(x, y, button, timeSinceClicked);
    }

    @Override
    public void mouseClicked(int x, int y, int button) throws IOException {
        if (button == 0 && x >= guiLeft + 115 && y >= guiTop + 5 && x < guiLeft + 220 && y < guiTop + 135) {
            int x1 = x - guiLeft;
            int y1 = y - guiTop;

            scrollX = x1;
            scrollY = y1;
            isSpinning = false;
            isDraging = true;
        }
        super.mouseClicked(x, y, button);
    }

    @Override
    public void mouseReleased(int x, int y, int button) {
        if (isDraging && button == 0) {
            isSpinning = true;
            isDraging = false;
        }
        super.mouseReleased(x, y, button);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel();

        if (i != 0 && this.needsScrollBars()) {
            int j = (((ContainerPreview) this.inventorySlots).itemList.size() / 5) - 6;

            if (i > 0) {
                i = 1;
            }

            if (i < 0) {
                i = -1;
            }

            this.currentScroll = (float) ((double) this.currentScroll - (double) i / (double) j);

            if (this.currentScroll < 0.0F) {
                this.currentScroll = 0.0F;
            }

            if (this.currentScroll > 1.0F) {
                this.currentScroll = 1.0F;
            }

            ((ContainerPreview) this.inventorySlots).scrollTo(this.currentScroll);
        }
    }


}
