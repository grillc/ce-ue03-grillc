package at.jku.grillc.ce.ue03.shared;

import static at.jku.grillc.ce.ue03.shared.MachineStatus.AUSGESCHALTEN;

public class MachineClient {
    private FactoryClient client;
    private int machineNr;
    private int machineStatus;
    private String orderNr;

    public MachineClient(FactoryClient client, int machineNr) {
        this.client = client;
        this.machineNr = machineNr;
        this.machineStatus = 3;
        this.orderNr = "0";
    }

    public FactoryClient getClient() {
        return client;
    }

    public int getMachineNr() {
        return machineNr;
    }

    public String getOrderNr() { return orderNr; }

    public void setOrderNr(String orderNr) { this.orderNr = orderNr; }

    public String getMachineStatus() {
        return MachineStatus.fromValue(machineStatus).toString();
    }

    public void setMachineStatus(int machineStatus) {
        this.machineStatus = machineStatus;
    }
}