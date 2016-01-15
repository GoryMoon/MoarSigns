package gory_moon.moarsigns.client.interfaces;

import cpw.mods.fml.client.FMLClientHandler;
import gory_moon.moarsigns.api.SignInfo;
import gory_moon.moarsigns.api.SignRegistry;
import gory_moon.moarsigns.blocks.Blocks;
import gory_moon.moarsigns.client.interfaces.containers.slots.SlotPreview;
import gory_moon.moarsigns.items.ItemMoarSign;
import gory_moon.moarsigns.items.ModItems;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import gory_moon.moarsigns.util.Colors;
import gory_moon.moarsigns.util.Localization;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuiPreview extends GuiContainer {

    private static final ResourceLocation texture = new ResourceLocation("moarsigns", "textures/gui/sign_preview.png");
    private static InventoryBasic inventory;
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
        ModItems.sign.getSubItemStacks(containerPreview.itemList);
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
        if (sign == null)
            fontRendererObj.drawSplitString(Localization.GUI.PREVIEW.CLICK_SIGN.translate(), 120, 10, 70, Colors.LIGHTGRAY.getRGB());
        if (x >= guiLeft + 130 && y >= guiTop + 25 && x < guiLeft + 210 && y < guiTop + 120) {
            String s = Localization.GUI.PREVIEW.DRAG.translate();
            drawHoveringText(Arrays.asList(s.split("\n")), x - guiLeft, y - guiTop, fontRendererObj);
        }
        super.drawGuiContainerForegroundLayer(x, y);
    }

    private boolean needsScrollBars() {
        return ((ContainerPreview) inventorySlots).needsScrollBars();
    }

    protected void handleMouseClick(Slot slot, int slotId, int button, int flag) {
        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();

            sign = new TileEntityMoarSign();
            String texture = ItemMoarSign.getTextureFromNBTFull(stack.getTagCompound());
            SignInfo info = SignRegistry.get(texture);

            sign.setWorldObj(FMLClientHandler.instance().getWorldClient());
            sign.isMetal = info.isMetal;
            sign.blockType = info.isMetal ? Blocks.signStandingMetal : Blocks.signStandingWood;
            sign.signText = new String[]{"", (char) 167 + "nThis is some", (char) 167 + "4example " + (char) 167 + "ltext", ""};
            sign.setResourceLocation(texture);
        }
    }


    public void mouseClickMove(int x, int y, int button, long timeSinceClicked) {
        if (isDraging) {
            if (button == 0) {
                int x1 = x - guiLeft;
                int y1 = y - guiTop;

                yaw = yaw + x1 - scrollX;
                roll = roll + y1 - scrollY;
                scrollX = x1;
                scrollY = y1;
            }
        }
        super.mouseClickMove(x, y, button, timeSinceClicked);
    }

    public void mouseClicked(int x, int y, int button) {
        if (button == 0) {
            if (x >= guiLeft + 130 && y >= guiTop + 25 && x < guiLeft + 210 && y < guiTop + 120) {
                int x1 = x - guiLeft;
                int y1 = y - guiTop;

                scrollX = x1;
                scrollY = y1;
                isSpinning = false;
                isDraging = true;
            }
        }
        super.mouseClicked(x, y, button);
    }

    public void mouseMovedOrUp(int x, int y, int button) {
        if (isDraging && button == 0) {
            isSpinning = true;
            isDraging = false;
        }
        super.mouseMovedOrUp(x, y, button);
    }

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        int i = Mouse.getEventDWheel();

        if (i != 0 && this.needsScrollBars()) {
            int j = ((ContainerPreview) this.inventorySlots).itemList.size() / 5 - 6;

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

    static class ContainerPreview extends Container {
        public List itemList = new ArrayList();

        public ContainerPreview() {
            if (inventory == null) inventory = new InventoryBasic("Preview Inventory", true, 35);

            for (int y = 0; y < 7; y++) {
                for (int x = 0; x < 5; x++) {
                    addSlotToContainer(new SlotPreview(inventory, x + y * 5, 8 + 18 * x, 8 + y * 18));
                }
            }

            this.scrollTo(0.0F);
        }

        public boolean canInteractWith(EntityPlayer player) {
            return true;
        }

        /**
         * Updates the gui slots ItemStack's based on scroll position.
         */
        public void scrollTo(float pos) {
            int i = this.itemList.size() / 5 - 6;
            int j = (int) ((double) (pos * (float) i) + 0.5D);

            if (j < 0) {
                j = 0;
            }


            for (int k = 0; k < 7; ++k) {
                for (int l = 0; l < 5; ++l) {
                    int i1 = l + (k + j) * 5;


                    if (i1 >= 0 && i1 < this.itemList.size()) {
                        inventory.setInventorySlotContents(l + k * 5, (ItemStack) this.itemList.get(i1));
                    } else {
                        inventory.setInventorySlotContents(l + k * 5, (ItemStack) null);
                    }
                }
            }
        }

        public boolean needsScrollBars() {
            return this.itemList.size() > 35;
        }

        protected void retrySlotClick(int p_75133_1_, int p_75133_2_, boolean p_75133_3_, EntityPlayer p_75133_4_) {
        }

        @Override
        public ItemStack slotClick(int par1, int par2, int par3, EntityPlayer player) {
            return null;
        }

        public ItemStack transferStackInSlot(EntityPlayer player, int slotId) {
            return null;
        }

        public boolean func_94530_a(ItemStack stack, Slot slot) {
            return false;
        }

        public boolean canDragIntoSlot(Slot slot) {
            return false;
        }
    }
}
