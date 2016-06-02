package gory_moon.moarsigns.client.interfaces;

import com.google.common.collect.Lists;
import gory_moon.moarsigns.client.interfaces.containers.ContainerDebug;
import gory_moon.moarsigns.tileentites.TileEntityMoarSign;
import gory_moon.moarsigns.util.Colors;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuiDebug extends GuiContainer {

    private static final ResourceLocation texture_item = new ResourceLocation("moarsigns", "textures/gui/debug_item.png");
    private static final ResourceLocation texture_world = new ResourceLocation("moarsigns", "textures/gui/debug_block.png");
    private boolean blockInWorld;
    private World world;
    private BlockPos pos;
    private Block block;
    private IInventory inventory;
    private Rectangle infoArea;
    private String[] signText = new String[4];

    public GuiDebug(InventoryPlayer inventory, int ID, World world, BlockPos pos, IInventory tempInv, TileEntityMoarSign te) {
        super(new ContainerDebug(inventory, ID, tempInv));
        this.world = world;
        this.pos = pos;
        this.inventory = tempInv;
        blockInWorld = ID == 0;

        if (blockInWorld) {
            block = world.getBlockState(pos).getBlock();
            infoArea = new Rectangle(8, 10, 160, 20);
        } else {
            infoArea = new Rectangle(31, 10, 137, 19);
        }

        TileEntityMoarSign sign = (TileEntityMoarSign) world.getTileEntity(pos);
        if (sign != null && sign.signText != null) {
            for (int i = 0; i < sign.signText.length; i++) {
                signText[i] = sign.signText[i] + (((char) 167) + "r") + Colors.CYAN;
            }
        }

        xSize = 178;
        ySize = 116;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1, 1, 1, 1);

        Minecraft.getMinecraft().getTextureManager().bindTexture(blockInWorld ? texture_world : texture_item);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        int meta = 0;
        String Un = "";
        int x = 0;
        int y1 = 0, y2 = 0;

        if (blockInWorld) {
            if (block != null) {
                Un = block.getUnlocalizedName();
                x = 10;
                y1 = 12;
                y2 = 20;
            }
        } else {
            if (inventory.getStackInSlot(0) != null) {
                x = 33;
                y1 = 12;
                y2 = 20;
                ItemStack stack = inventory.getStackInSlot(0);
                Un = stack.getUnlocalizedName();
                meta = stack.getItemDamage();
            }
        }

        if (!Un.equals("")) {
            String un = Un;
            if (!blockInWorld && Un.length() >= 23) un = Un.substring(0, 20) + "...";
            else if (blockInWorld && Un.length() >= 15) un = Un.substring(0, 13) + "...";

            fontRendererObj.drawString("UN: " + un, x, y1, 0x404040);
            fontRendererObj.drawString("Meta: " + meta, x, y2, 0x404040);

            ArrayList<String> strings = Lists.newArrayList();
            for (int i: OreDictionary.getOreIDs(blockInWorld ? new ItemStack(block): inventory.getStackInSlot(0))) {
                strings.add(OreDictionary.getOreName(i));
            }

            infoArea.drawString(this, par1, par2, Colors.YELLOW + "Unlocalized Name: " + Un + "\n" + Colors.LIGHTBLUE + "Meta: " + meta + "\n" + Colors.LIME + "OreDict: " + Arrays.toString(strings.toArray(new String[strings.size()])) + "\n" + Colors.CYAN + Arrays.toString(signText) );

        }

    }

    public int getLeft() {
        return guiLeft;
    }

    public int getTop() {
        return guiTop;
    }

    public void drawHoverString(List<String> lst, int x, int y) {
        drawHoveringText(lst, x, y, fontRendererObj);
    }

    public class Rectangle {

        private int x;
        private int y;
        private int w;
        private int h;

        public Rectangle(int x, int y, int w, int h) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
        }

        public boolean inRect(GuiDebug gui, int mouseX, int mouseY) {
            mouseX -= gui.getLeft();
            mouseY -= gui.getTop();

            return x <= mouseX && mouseX <= x + w && y <= mouseY && mouseY <= y + h;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }

        @SuppressWarnings("unused")
        public void draw(GuiDebug gui, int srcX, int srcY) {
            gui.drawTexturedModalRect(gui.getLeft() + x, gui.getTop() + y, srcX, srcY, w, h);
        }

        public void drawString(GuiDebug gui, int mouseX, int mouseY, String str) {
            if (inRect(gui, mouseX, mouseY)) {
                gui.drawHoverString(Arrays.asList(str.split("\n")), mouseX - gui.getLeft(), mouseY - gui.getTop());
            }
        }
    }

}
