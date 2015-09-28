package gory_moon.moarsigns.client.interfaces.sign.buttons;

public abstract class GuiButtonToggleable extends GuiButton {

    private boolean state;

    public GuiButtonToggleable(int x, int y, int srcX) {
        super(x, y, srcX);
    }

    public GuiButtonToggleable(int x, int y, int w, int h, int srcX) {
        super(x, y, w, h, srcX);
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
