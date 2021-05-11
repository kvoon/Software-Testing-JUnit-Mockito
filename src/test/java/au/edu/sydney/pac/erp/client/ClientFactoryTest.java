package au.edu.sydney.pac.erp.client;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ClientFactoryTest {

    ClientFactory clientFactory;

    @Before
    public void setUp() {
        this.clientFactory = new ClientFactory();
    }

    @Test
    public void testMakeClient(){

        assertNotNull(clientFactory.makeClientList());
    }

}