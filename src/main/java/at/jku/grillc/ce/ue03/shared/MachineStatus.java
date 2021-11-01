package at.jku.grillc.ce.ue03.shared;

public enum MachineStatus {
    PRODUKTION(0),
    LEERLAUF(1),
    WARTUNG(2),
    AUSGESCHALTEN(3);

    private int value;

    MachineStatus(int value) { this.value = value; }

    public int getValue() {
        return value;
    }

    public static MachineStatus fromValue(int value) {
        return MachineStatus.values()[value];

    }
}
