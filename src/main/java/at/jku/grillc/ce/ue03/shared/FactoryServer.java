package at.jku.grillc.ce.ue03.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FactoryServer extends Remote {
    String toUpperCase(String str, FactoryClient client) throws RemoteException;

    String registerClient(Integer machineNr, FactoryClient clientToRegister) throws RemoteException;
}
