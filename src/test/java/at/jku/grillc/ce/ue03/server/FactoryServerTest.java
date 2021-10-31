package at.jku.grillc.ce.ue03.server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import at.jku.grillc.ce.ue03.shared.FactoryServer;

import java.rmi.RemoteException;

class FactoryServerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(FactoryServerTest.class);
    private FactoryServer server;

    @BeforeEach
    void init() {
        try {
            this.server = new ServerImpl();
        } catch (final RemoteException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
    }

    @Test
    void testUppercase() {
        final String test = "abcd";
        final String expectedResult = "ABCD";

        String result = "";
        try {
            result = server.toUpperCase(test, null);
        } catch (final RemoteException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }
        assertEquals(expectedResult, result);
    }
}
