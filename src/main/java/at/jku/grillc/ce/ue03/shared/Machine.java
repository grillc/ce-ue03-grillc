package at.jku.grillc.ce.ue03.shared;

public enum Machine {
    DREHMASCHINE_MAZAK(0),
    FRAESMASCHINE_MCFV1060(1),
    FRAESMASCHINE_G_MASTER(2),
    FRAESMASCHINE_HURON(3),
    FRAESMASCHINE_DMG_DMF260(4);

    private final int value;

    Machine(int value) { this.value = value; }

    public int getValue() {
        return value;
    }

    public static Machine fromValue(int value) {
        return Machine.values()[value];

    }

}
