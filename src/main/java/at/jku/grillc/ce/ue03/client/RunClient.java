package at.jku.grillc.ce.ue03.client;

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
        LOGGER.info("whatnow");
        return;
    }

    private static void registrationProcess(RMIClient client) {
        final Scanner in = new Scanner(System.in);
        boolean registrationSuccess = false;
        Integer line = null;
        while (!registrationSuccess) {
            while (line == null) {

                try {
                    LOGGER.info("\nWillkommen am neuen Terminal!" +
                            "\nWählen Sie eine Maschine:" +
                            "\n" +
                            "\n0 - Drehmaschine Mazák" +
                            "\n1 - Fräsmaschine MCFV1060" +
                            "\n2 - Fräsmaschine G. Master" +
                            "\n3 - Fräsmaschine Huron" +
                            "\n4 - Fräsmaschine DMG DMF260" +
                            "\nIhre Wahl: ");
                    line = in.nextInt();
                    LOGGER.info(Integer.toString(line));
                } catch (InputMismatchException ex) {
                                        LOGGER.info("\n --- Unültige Eingabe. Bitte eine der o.g. Optionen wählen ---");
                }
                in.nextLine();
            }
            try {
                final String result = client.logClientInToServer(line);
                LOGGER.info("{}\n", result);
                if (result.contains("Erfolg:"))
                    registrationSuccess = true;
                else {
                    line = null;
                }
            } catch (final Exception e) {
                LOGGER.error(e.getLocalizedMessage(), e);
            }
        }
    }
}
