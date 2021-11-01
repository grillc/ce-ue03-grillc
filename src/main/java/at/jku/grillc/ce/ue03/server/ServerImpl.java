package at.jku.grillc.ce.ue03.server;

import at.jku.grillc.ce.ue03.client.RMIClient;
import at.jku.grillc.ce.ue03.shared.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ServerImpl implements FactoryServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerImpl.class);
    private static List<MachineClient> registeredMachines = new ArrayList<>();

    public ServerImpl() throws RemoteException {
        UnicastRemoteObject.exportObject(this, 0);
    }

    private void updateClients(String result, FactoryClient dontBroadcastToMe) {
        for (MachineClient mc : registeredMachines) {
            if (mc.getClient() == dontBroadcastToMe)
                continue;
            try {
                mc.getClient().update(result);
            } catch (final RemoteException e) {
                LOGGER.error(e.getLocalizedMessage(), e);
            }
        }
    }

    public String registerClient(Integer machineNr, FactoryClient clientToRegister) {
        final String result;
        if (machineNr < 0 || machineNr > Machine.values().length) {
            return "\nMaschinennummer  " + machineNr + " ist nicht Teil der Anlage. Bitte erneute Wahl.";
        }
        for (MachineClient mc : registeredMachines) {
            if (mc.getMachineNr() == machineNr)
                return "\nMaschine " + machineNr + " " + Machine.fromValue(machineNr) + " ist bereits registriert. \n Registrierung fehlgeschlagen. Bitte erneute Wahl";
        }
        registeredMachines.add(new MachineClient(clientToRegister, machineNr));
        result = "\nErfolg: Neuer Client als Maschine " + machineNr + " - " + Machine.fromValue(machineNr) + " registriert.";
        updateClients(result, clientToRegister);

        return result;
    }

    public String checkOrderNr(String orderNr) {
        // check format
        if (!orderNr.matches("[A-Z]{3}-[0-9]{5}"))
            return "\nFalsches Format. 3 Großbuchstaben; minus; 5 Ziffern.\nBeispiel: JKU-00001";
        // check usage
        for (MachineClient mc : registeredMachines) {
            if (orderNr.equals(mc.getOrderNr()))
                return "\nDieser Auftrag ist bereits in Bearbeitung. Bitte neu wählen";
        }
        return "OK";
    }

    public String updateStatus(int machineNr, int machineStatus, String orderNr) {
        if (machineStatus < 0 || machineStatus > MachineStatus.values().length)
            return "Statuscode " + machineStatus + " ist nicht verfügbar. ";

        for (MachineClient mc : registeredMachines) {
            if (machineNr == mc.getMachineNr()) {
                mc.setMachineStatus(machineStatus);
                mc.setOrderNr(orderNr);
                String result = "\nMaschine " + machineNr + " - " + Machine.fromValue(machineNr) + " hat jetzt den Status " + MachineStatus.fromValue(machineStatus).toString() + ", Auftragsnummer " + orderNr;
                updateClients(result, mc.getClient());
                return result;
            }
        }
        return "updateStatus nicht erfolgreich";
    }

    public String getMachineStatus(int machineNr) {
        for (MachineClient mc : registeredMachines) {
            if(mc.getMachineNr() == machineNr) {
                return mc.getMachineStatus().toString();
            }
        }
        return "Keine Maschine gefunden";
    }

    public String getOrderNr(int machineNr) {
        for (MachineClient mc : registeredMachines) {
            if(mc.getMachineNr() == machineNr) {
                return mc.getOrderNr();
            }
        }
        return "Keine Maschine gefunden";
    }

    public String getMachine(int machineNr) throws RemoteException {
        for (MachineClient mc : registeredMachines) {
            if(mc.getMachineNr() == machineNr) {
                return Machine.fromValue(machineNr).toString();
            }
        }
        return "Keine Maschine gefunden";
    }

    public void shutdownServer() throws RemoteException {
        updateClients("\nDer Server wird heruntergefahren"+
                "\nUm das System wieder hochzufahren, bitte alle Clients beenden."+
                "\nNach dem Wiederhochfahren des Servers können die Clients wieder gestartet werden.", null);
        System.exit(1);
    }


}
