package gory_moon.moarsigns.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;

public class ModelMoarSign extends ModelBase {

    public ModelRenderer board = new ModelRenderer(this, 0, 0);
    public ModelRenderer stick = new ModelRenderer(this, 0, 14);

    public ModelMoarSign() {
        board.addBox(-12.0F, -14.0F, -1.0F, 24, 12, 2, 0.0F);
        stick.addBox(-1.0F, -2.0F, -1.0F, 2, 14, 2, 0.0F);
    }

    public void render()
    {
        this.board.render(0.0625F);
        this.stick.render(0.0625F);
    }
}
