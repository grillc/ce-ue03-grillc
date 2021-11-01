package at.jku.grillc.ce.ue03.shared;

import at.jku.grillc.ce.ue03.client.RMIClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FactoryServer extends Remote {

    String registerClient(Integer machineNr, FactoryClient clientToRegister) throws RemoteException;

    String getMachineStatus(int machineNr) throws RemoteException;

    String checkOrderNr(String orderNr) throws RemoteException;

    String updateStatus(int machineNr, int machineStatus, String orderNr) throws RemoteException;

    String getOrderNr(int machineNr) throws RemoteException;

    String getMachine(int MachineNr) throws RemoteException;

    void shutdownServer() throws RemoteException;
}