package at.jku.grillc.ce.ue03.client;

import at.jku.grillc.ce.ue03.shared.MachineStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.jku.grillc.ce.ue03.shared.FactoryClient;
import at.jku.grillc.ce.ue03.shared.FactoryServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static at.jku.grillc.ce.ue03.server.RunServer.REGISTRY_PORT;
import static at.jku.grillc.ce.ue03.server.RunServer.SERVER;

public class RMIClient implements FactoryClient {
    private static int machineNr;
    private static final Logger LOGGER = LoggerFactory.getLogger(RMIClient.class);
    private final FactoryServer server;

    public RMIClient() throws RemoteException, NotBoundException {
        UnicastRemoteObject.exportObject(this, 0);
        final Registry registry = LocateRegistry.getRegistry("localhost", REGISTRY_PORT);
        server = (FactoryServer) registry.lookup(SERVER);

    }

    public String logClientInToServer(Integer machineNr) throws RemoteException {
        this.machineNr = machineNr;
        String result = server.registerClient(machineNr,  this);
        return result;
    }

    public String checkOrderNr(String orderNr) throws RemoteException {
        return server.checkOrderNr(orderNr);
    }

    public String updateStatus(int machineStatus, String orderNr) throws RemoteException {
        return server.updateStatus(machineNr, machineStatus, orderNr);
    }

    public void update(final String result) {
        LOGGER.info("Broadcasted > {}", result);
    }

    public String getOrderNr() throws RemoteException {
        return server.getOrderNr(machineNr);
    }

    public int getMachineNr() throws RemoteException {
        return machineNr;
    }

    public String getMachineStatus() throws RemoteException {
        return server.getMachineStatus(machineNr);
    }

    public String getMachine() throws RemoteException {
        return server.getMachine(machineNr);
    }

    public void shutdownServer() throws RemoteException {
        server.shutdownServer();
    }


}
