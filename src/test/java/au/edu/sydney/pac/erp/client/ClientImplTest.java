package au.edu.sydney.pac.erp.client;
import org.junit.Test;
import static org.junit.Assert.*;


public class ClientImplTest {

    @Test(expected = IllegalArgumentException.class)
    public void testIDLessThanOne(){

        Client client = new ClientImpl(0, "John", "Smith","121341515");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testIDNegativeA(){

        Client client = new ClientImpl(-1, "John", "Smith","121341515");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testIDNegativeB(){

        Client client = new ClientImpl(-1000, "John", "Smith","121341515");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testFirstNameNull(){
        Client client = new ClientImpl(1, null, "Smith","121341515");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFirstNameEmpty(){
        Client client = new ClientImpl(1, "", "Smith","121341515");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLastNameNull(){
        Client client = new ClientImpl(1, "John", null,"121341515");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLastNameEmpty(){
        Client client = new ClientImpl(1, "John", "","121341515");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPhoneNumberNull(){
        Client client = new ClientImpl(1, "John", "Smith",null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPhoneNumberEmpty(){
        Client client = new ClientImpl(1, "John", "Smith","");
    }

    @Test
    public void testInitClient(){
        Client client = new ClientImpl(1, "John", "Smith","12345678");
        assertNotNull(client);
        assertEquals(1,client.getID());
        assertEquals("John", client.getFirstName());
        assertEquals("Smith",client.getLastName());
        assertEquals("12345678", client.getPhoneNumber());
    }

    @Test
    public void testAssignDepartmentNotNull(){
        Client client = new ClientImpl(1, "John", "Smith","12345678");
        client.assignDepartment("Toilet Paper Division");
        assertNotNull(client.getDepartmentCode());
        assertNotEquals("",client.getDepartmentCode());

    }

    @Test
    public void testAssignDepartment(){
        Client client = new ClientImpl(1, "John", "Smith","12345678");
        client.assignDepartment("Toilet Paper Division");
        assertEquals("Toilet Paper Division", client.getDepartmentCode());

    }
    @Test(expected = IllegalStateException.class)
    public void testAlreadyAssignDepartment(){
        Client client = new ClientImpl(1, "John", "Smith","12345678");
        client.assignDepartment("Toilet Paper Division");
        client.assignDepartment("Toilet Paper Division");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullAssignedDepartment(){
        Client client = new ClientImpl(1, "John", "Smith","12345678");
        client.assignDepartment(null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyAssignDepartment(){
        Client client = new ClientImpl(1, "John", "Smith","12345678");
        client.assignDepartment("");

    }

    @Test
    public void testIsAssigned(){
        Client client = new ClientImpl(1, "John", "Smith","12345678");
        client.assignDepartment("Toilet Paper Division");
        assertTrue(client.isAssigned());
    }

    @Test
    public void testIsNotAssigned(){
        Client client = new ClientImpl(1, "John", "Smith","12345678");
        assertFalse(client.isAssigned());
    }

    @Test
    public void testGetDepartmentCodeNotAssigned(){
        Client client = new ClientImpl(1, "John", "Smith","12345678");
        assertNull(client.getDepartmentCode());
    }







}