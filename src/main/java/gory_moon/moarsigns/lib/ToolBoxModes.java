package gory_moon.moarsigns.lib;

public enum ToolBoxModes {
    EDIT_MODE(0),
    ROTATE_MODE(1),
    COPY_MODE(2),
    MOVE_MODE(3);


    private int id;

    ToolBoxModes(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public boolean equals(int testID) {
        return this.id == testID;
    }
}
