package at.jku.grillc.ce.ue03.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FactoryClient extends Remote {

    String logClientInToServer(Integer machineNr) throws RemoteException;

    String checkOrderNr(String orderNr) throws RemoteException;

    String updateStatus(int machineStatus, String orderNr) throws RemoteException;

    void update(String result) throws RemoteException;

    String getOrderNr() throws RemoteException;

    int getMachineNr() throws RemoteException;

    String getMachineStatus() throws RemoteException;

    String getMachine() throws RemoteException;

    void shutdownServer() throws RemoteException;
}
