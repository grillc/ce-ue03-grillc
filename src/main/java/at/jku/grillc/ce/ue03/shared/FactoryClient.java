package at.jku.grillc.ce.ue03.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FactoryClient extends Remote {
    void update(String result) throws RemoteException;
}
