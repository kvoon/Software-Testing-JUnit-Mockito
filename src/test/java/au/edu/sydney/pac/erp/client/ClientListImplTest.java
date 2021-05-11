package au.edu.sydney.pac.erp.client;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class ClientListImplTest {

    ClientList clientList;

    @Before
    public void setup() {

        clientList = new ClientListImpl();

    }

    @Test(expected = IllegalStateException.class)
    public void testDuplicateID() {
        clientList.addClient(1, "Jack", "Sparrow", "12345678");
        clientList.addClient(1, "Johnny", "Depp", "12345678");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEntryA() {
        clientList.addClient(0, "", "", "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEntryB() {
        clientList.addClient(-1, null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidIDEntry() {
        clientList.addClient(0, "Jack", "Sparrow", "12345678");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidFirstNameEntryNull() {
        clientList.addClient(1, null, "Sparrow", "12345678");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidFirstNameEntryEmpty() {
        clientList.addClient(1, "", "Sparrow", "12345678");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidLastNameEntryNull() {
        clientList.addClient(1, "Jack", null, "12345678");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidLastNameEntryEmpty() {
        clientList.addClient(1, "Jack", "", "12345678");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPhoneNumberNull() {
        clientList.addClient(1, "Jack", "Sparrow", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPhoneNumberEmpty() {
        clientList.addClient(1, "Jack", "Sparrow", "");
    }

    @Test
    public void testAddOneClient() {
        clientList.addClient(1, "Tony", "Stark", "12345678");
        assertEquals(1, clientList.findOne(1).getID());
        assertEquals("Tony", clientList.findOne(1).getFirstName());
        assertEquals("Stark", clientList.findOne(1).getLastName());
        assertEquals("12345678", clientList.findOne(1).getPhoneNumber());
    }

    @Test
    public void testAddOneClientSize() {
        clientList.addClient(1, "Tony", "Stark", "12345678");
        assertEquals(1, clientList.findAll().size(),0);
    }

    @Test
    public void testClear() {
        clientList.addClient(1, "Tony", "Stark", "12345678");
        clientList.addClient(2, "Bruce", "Banner", "12345678");
        clientList.addClient(3, "Steve", "Rogers", "12345678");
        clientList.clear();
        assertEquals(0, clientList.findAll().size());
    }

    @Test
    public void testFindAll() {
        clientList.addClient(1, "Tony", "Stark", "12345678");
        clientList.addClient(2, "Bruce", "Banner", "12345678");
        clientList.addClient(3, "Steve", "Rogers", "12345678");
        assertNotNull(clientList.findAll());
        assertEquals(1, clientList.findAll().get(0).getID());
        assertEquals(2, clientList.findAll().get(1).getID());
        assertEquals(3, clientList.findAll().get(2).getID());
    }

    @Test
    public void testFindAllEmpty() {
        assertEquals(0, clientList.findAll().size());
        assertTrue(clientList.findAll().isEmpty());
    }

    @Test
    public void testFindAllAssignedA() {
        clientList.addClient(1, "Tony", "Stark", "12345678");
        assertEquals(0, clientList.findAll(true).size());
        assertTrue(clientList.findAll(true).isEmpty());
        assertEquals(1, clientList.findAll(false).size());
        assertFalse(clientList.findAll(false).isEmpty());
    }

    @Test
    public void testFindAllAssignedB() {
        clientList.addClient(1, "Tony", "Stark", "12345678");
        clientList.findOne(1).assignDepartment("Avengers");
        assertEquals(1, clientList.findAll(true).size());
        assertEquals(0, clientList.findAll(false).size());
    }

    @Test
    public void testFindAllAssignedC() {
        clientList.addClient(1, "Tony", "Stark", "12345678");
        clientList.addClient(2, "Bruce", "Banner", "12345678");
        clientList.addClient(3, "Nick", "Fury", "12345678");
        clientList.findOne(1).assignDepartment("Avengers");
        clientList.findOne(2).assignDepartment("Avengers");
        assertEquals(2, clientList.findAll(true).size());
        assertEquals(1, clientList.findAll(false).size());
    }

    @Test
    public void testFindAllAssignedDepartmentEmpty() {
        clientList.addClient(1, "Tony", "Stark", "12345678");
        clientList.addClient(2, "Bruce", "Banner", "12345678");
        clientList.findOne(1).assignDepartment("Avengers");
        clientList.findOne(2).assignDepartment("Avengers");
        assertEquals(0, clientList.findAll("Hydra", "Shield").size());
        assertTrue(clientList.findAll("Hydra", "Shield").isEmpty());
        assertNotNull(clientList.findAll("Hydra", "Shield"));
    }

    @Test
    public void testFindAllAssignedDepartment() {
        clientList.addClient(1, "Tony", "Stark", "12345678");
        clientList.addClient(2, "Bruce", "Banner", "12345678");
        clientList.addClient(3, "Nick", "Fury", "12345678");
        clientList.findOne(1).assignDepartment("Avengers");
        clientList.findOne(2).assignDepartment("Avengers");
        clientList.findOne(3).assignDepartment("Shield");
        assertEquals(3, clientList.findAll("Avengers", "Shield").size());
    }

    @Test
    public void testFindAllNoParamVsFindAllFalse(){
        clientList.addClient(1, "Tony", "Stark", "12345678");
        clientList.addClient(2, "Bruce", "Banner", "12345678");
        clientList.addClient(3, "Nick", "Fury", "12345678");
        assertNotSame(clientList.findAll(), clientList.findAll(false));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindOneInvalidIDA() {
        clientList.findOne(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindOneInvalidIDB() {
        clientList.findOne(-1);
    }

    @Test
    public void testFindOne() {
        clientList.addClient(1, "Tony", "Stark", "12345678");
        assertEquals(1, clientList.findOne(1).getID());
        assertEquals("Tony", clientList.findOne(1).getFirstName());
        assertEquals("Stark", clientList.findOne(1).getLastName());
        assertEquals("12345678", clientList.findOne(1).getPhoneNumber());
    }

    @Test
    public void testRemoveOne() {
        clientList.addClient(1, "Tony", "Stark", "12345678");
        clientList.remove(1);
        assertEquals(0, clientList.findAll().size());
    }

    @Test
    public void testRemoveOneTrue() {
        clientList.addClient(1, "Tony", "Stark", "12345678");
        assertTrue(clientList.remove(1));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveInvalidIDA() {
        clientList.remove(-1);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveInvalidIDB() {
        clientList.remove(0);

    }

    @Test
    public void testFindAllNull() {
        assertNotNull(clientList.findAll(null));
        assertEquals(0, clientList.findAll().size());

    }

    @Test
    public void testFindAllNoParamNull(){
        assertNotNull(clientList.findAll());

    }

    @Test
    public void testFindAllEmptyList(){
        assertNotNull(clientList.findAll(true));
    }

    @Test
    public void testFindAllNoParam(){
        clientList.addClient(1,"Kobe", "Bryant", "12345678");
        clientList.findAll().get(0).assignDepartment("1");
        clientList.addClient(2,"Lebon","James","12345678");
        assertEquals(1,clientList.findAll(true).size());


    }

}

