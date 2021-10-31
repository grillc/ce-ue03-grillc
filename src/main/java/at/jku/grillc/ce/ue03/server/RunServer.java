package at.jku.grillc.ce.ue03.server;

import at.jku.grillc.ce.ue03.shared.FactoryServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RunServer {
    public static final int REGISTRY_PORT = 21099;
    private static final Logger LOGGER = LoggerFactory.getLogger(RunServer.class);
    public static final String SERVER = "Server_01";

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        final FactoryServer server = new ServerImpl();
        final Registry registry = LocateRegistry.createRegistry(REGISTRY_PORT);
        registry.bind(SERVER, server);
        LOGGER.info("Server started");
    }
}
