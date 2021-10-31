package at.jku.grillc.ce.ue03.client;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(RMIClient.class);
    private final FactoryServer server;

    public RMIClient() throws RemoteException, NotBoundException {
        UnicastRemoteObject.exportObject(this, 0);
        final Registry registry = LocateRegistry.getRegistry("localhost", REGISTRY_PORT);
        server = (FactoryServer) registry.lookup(SERVER);

    }

    public String logClientInToServer(Integer machineNr) throws RemoteException {
        String result = server.registerClient(machineNr,  this);
        return result;
    }




    public String toUpperCase(final String argument) {
        try {
            final String result = server.toUpperCase(argument, this);
            return result;
        } catch (final RemoteException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            throw new RuntimeException("Could not contact server");
        }
    }

    @Override
    public void update(final String result) {
        LOGGER.info("Broadcasted > {}", result);
    }
}
