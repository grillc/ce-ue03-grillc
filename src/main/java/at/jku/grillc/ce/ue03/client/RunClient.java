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

        boolean registrationSuccess = registrationProcess(client);
        LOGGER.info("Registrierung erfolgreich: " + registrationSuccess);
        LOGGER.info("whatnow");
        return;
    }

    private static boolean registrationProcess(RMIClient client) {
        final Scanner in = new Scanner(System.in);
        while (true) {
            Integer line = 1000;
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
                LOGGER.info("\nUnültige Eingabe. Bitte eine der o.g. Optionen wählen");
            }
            in.nextLine();
            if (line == 9)
                return false;

            try {
                final String result = client.logClientInToServer(line);
                LOGGER.info("{}\n", result);
                if (result.contains("Erfolg:"))
                    return true;
            } catch (final Exception e) {
                LOGGER.error(e.getLocalizedMessage(), e);
            }

        }
    }
}
