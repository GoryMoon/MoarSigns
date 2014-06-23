package gory_moon.moarsigns.client.interfaces;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class GuiDebug extends GuiContainer {

    private boolean blockInWorld;
    private World world;
    private int x;
    private int y;
    private int z;
    private Block block;
    private IInventory inventory;
    private GuiRectangle infoArea;

    public GuiDebug(InventoryPlayer inventory, int ID, World world, int x, int y, int z, IInventory tempInv) {
        super(new ContainerDebug(inventory, ID, tempInv));
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.inventory = tempInv;
        blockInWorld = ID == 0;

        if (blockInWorld) {
            block = world.getBlock(x, y, z);
            infoArea = new GuiRectangle(8, 10, 160, 20);
        } else {
            infoArea = new GuiRectangle(31, 10, 137, 19);
        }

        xSize = 178;
        ySize = 116;
    }

    private static final ResourceLocation texture_item = new ResourceLocation("moarsigns", "textures/gui/debug_item.png");
    private static final ResourceLocation texture_world = new ResourceLocation("moarsigns", "textures/gui/debug_block.png");

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
                meta = world.getBlockMetadata(this.x, y, z);
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

            infoArea.drawString(this, par1, par2, GuiColor.YELLOW + "Unlocalized Name: " + Un + "\n" + GuiColor.LIGHTBLUE + "Meta: " + meta);

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

}
