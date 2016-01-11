package gory_moon.moarsigns.lib;

public enum ToolBoxModes {
    EDIT_MODE,
    ROTATE_MODE,
    MOVE_MODE,
    COPY_MODE,
    EXCHANGE_MODE,
    PREVIEW_MODE;

    public int getID() {
        return ordinal();
    }

    public boolean equals(int testID) {
        return getID() == testID;
    }
}
