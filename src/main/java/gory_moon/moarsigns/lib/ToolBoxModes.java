package gory_moon.moarsigns.lib;

public enum ToolBoxModes {
    EDIT_MODE,
    ROTATE_MODE,
    MOVE_MODE,
    COPY_MODE,
    EXCHANGE_MODE;


    private int id;

    ToolBoxModes() {
        this.id = ordinal();
    }

    public int getID() {
        return id;
    }

    public boolean equals(int testID) {
        return this.id == testID;
    }
}
