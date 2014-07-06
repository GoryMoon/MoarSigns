package gory_moon.moarsigns.lib;

public enum PacketIDs {
    MAIN_INFO_PACKET,
    SIGN_TEXT_PACKET,
    OPEN_GUI_PACKET,
    NONE;


    public int getID() {
        return ordinal();
    }

    public static PacketIDs getID(byte b) {
        for (PacketIDs id : values()) {
            if (id.getID() == b) {
                return id;
            }
        }
        return NONE;
    }
}
