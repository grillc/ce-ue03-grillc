package at.jku.grillc.ce.ue03.client;

import at.jku.grillc.ce.ue03.shared.InteractionHandler;
import at.jku.grillc.ce.ue03.shared.MachineStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RunClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(RunClient.class);

    public static void main(String[] args) throws RemoteException, NotBoundException {
        final RMIClient client = new RMIClient();
        registrationProcess(client);
        runningStatus(client);
    }

    private static void registrationProcess(RMIClient client) {
        boolean registrationPending = true;
        while (registrationPending) {
            int userChoice = InteractionHandler.integerLogger(InteractionHandler.clientWelcome);
            try {
                final String result = client.logClientInToServer(userChoice);
                LOGGER.info("{}\n", result);
                if (result.contains("Erfolg:"))
                    registrationPending = false;
            } catch (final Exception e) {
                LOGGER.error(e.getLocalizedMessage(), e);
            }
        }
    }

    private static void runningStatus(RMIClient client) {
        boolean running = true;
        String orderNr="0";
        String orderNrStatus = "checking...";
        while(running){
            try {

                // print status, get an integer as status change decisions
                InteractionHandler.logStatus(client);  // print status information
                int userChoice = InteractionHandler.integerLogger(InteractionHandler.clientRunningMenu);

                // getting order number if status should be production
                if (userChoice == 0) {
                    while (!orderNrStatus.equals("OK")) {
                        orderNr = InteractionHandler.stringLogger("\nBitte Auftragsnummer eingeben");
                        orderNrStatus = client.checkOrderNr(orderNr);
                        LOGGER.info(orderNrStatus);
                    }
                }

                // shut down server if chosen
                if (userChoice == 9) {
                    client.shutdownServer();
                }

                // execution of status update
                LOGGER.info(client.updateStatus(userChoice, orderNr));

            } catch (final Exception e) {
                    LOGGER.error(e.getLocalizedMessage(), e);
            }
        }
    }

}
