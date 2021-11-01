package at.jku.grillc.ce.ue03.shared;

import at.jku.grillc.ce.ue03.client.RunClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class InteractionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RunClient.class);

    public static final String clientWelcome = "\nWillkommen am neuen Terminal!" +
            "\nWählen Sie eine Maschine:" +
            "\n" +
            "\n0 - Drehmaschine Mazák" +
            "\n1 - Fräsmaschine MCFV1060" +
            "\n2 - Fräsmaschine G. Master" +
            "\n3 - Fräsmaschine Huron" +
            "\n4 - Fräsmaschine DMG DMF260" +
            "\nIhre Wahl: ";

    public static final String clientRunningMenu = "\nStatus ändern:" +
            "\n0 - Status auf IN PRODUKTION setzen"+
            "\n1 - Status auf IM LEERLAUF setzen" +
            "\n2 - Status auf IN WARTUNG setzen" +
            "\n3 - Status auf AUSGESCHALTEN setzen" +
            "\n\n9 - Server herunterfahren";

    public static Integer integerLogger(String messageString) {
        final Scanner in = new Scanner(System.in);
        Integer line = null;
        while (line == null) {
            try {
                LOGGER.info(messageString);
                line = in.nextInt();
            } catch (InputMismatchException ex) {
                LOGGER.info("\n --- Unültige Eingabe. Bitte eine der o.g. Optionen wählen ---");
            }
            in.nextLine();
        }
        return line;
    }

    public static String stringLogger(String messageString) {
        final Scanner in = new Scanner(System.in);
        LOGGER.info(messageString);
        String line = in.nextLine();
        return line;
    }

    public static void logStatus(FactoryClient client) {
        try {
            LOGGER.info("Dies ist Maschine " + client.getMachineNr() + " - " + client.getMachine());
            LOGGER.info("Aktueller Status: " + client.getMachineStatus());
            LOGGER.info("Letzter/aktueller Auftrag: " + client.getOrderNr());
        } catch (RemoteException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
    }

}
