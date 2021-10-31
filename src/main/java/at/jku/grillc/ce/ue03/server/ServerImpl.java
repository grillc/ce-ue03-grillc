package at.jku.grillc.ce.ue03.server;

import at.jku.grillc.ce.ue03.shared.FactoryServer;
import at.jku.grillc.ce.ue03.shared.Machine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.jku.grillc.ce.ue03.shared.FactoryClient;

import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerImpl implements FactoryServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ServerImpl.class);
    private final Map<Integer, FactoryClient> clientsForBroadcast;



    public ServerImpl() throws RemoteException {
        UnicastRemoteObject.exportObject(this, 0);
        clientsForBroadcast = new HashMap<>();
    }



    @Override
    public String toUpperCase(final String str, final FactoryClient client) {
        final String result = str.toUpperCase();
        // heavy calculation here
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }

        updateClients(result, client);

        return result;
    }

    private void updateClients(String result, FactoryClient dontBroadcastToMe) {
        for (Map.Entry<Integer, FactoryClient> entry : clientsForBroadcast.entrySet()) {
            FactoryClient client = entry.getValue();
            if (client.equals(dontBroadcastToMe))
                continue;
            try {
                client.update(result);
            } catch (final RemoteException e) {
                LOGGER.error(e.getLocalizedMessage(), e);
            }
        }
    }

    @Override
    public String registerClient(Integer machineNr, FactoryClient clientToRegister) {
        final String result;
        if (machineNr < 0 || machineNr > 4) {
            result = "\nMaschinennummer  " + machineNr + " ist nicht Teil der Anlage. Bitte erneute Wahl.";
        } else if (clientsForBroadcast.containsKey(machineNr)) {
            result = "\nMaschine " + machineNr + " " + Machine.fromValue(machineNr) + "ist bereits registriert. \n Registrierung fehlgeschlagen. Bitte erneute Wahl";
        } else {
            clientsForBroadcast.put(machineNr, clientToRegister);
            result = "\nErfolg: Neuer Client als Maschine " + machineNr + " - " + Machine.fromValue(machineNr) + " registriert.";
            updateClients(result, clientToRegister);
        }
        return result;
    }
}
