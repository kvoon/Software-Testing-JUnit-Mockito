package au.edu.sydney.pac.erp.feaa;

import au.edu.sydney.pac.erp.auth.AuthModule;
import au.edu.sydney.pac.erp.auth.AuthToken;
import au.edu.sydney.pac.erp.client.Client;
import au.edu.sydney.pac.erp.client.ClientList;

import au.edu.sydney.pac.erp.email.EmailService;
import au.edu.sydney.pac.erp.fax.FaxService;
import au.edu.sydney.pac.erp.print.PrintService;
import au.edu.sydney.pac.erp.reporting.ReportFacade;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class FEAAFacadeImplTest {

    FEAAFacade feaaFacade;

    ClientList clientList;

    Client mockedClient;

    AuthToken mockedAuthToken;

    AuthModule mockedAuthProvider;

    ReportFacade mockedReportProvider;

    PrintService mockedPrintProvider;

    FaxService mockedFaxServiceProvider;

    EmailService mockedEmailServiceProvider;

    @Before
    public void setup() {
        this.feaaFacade = new FEAAFacadeImpl();
        this.mockedClient = mock(Client.class);
        this.clientList = mock(ClientList.class);
        this.mockedAuthProvider = mock(AuthModule.class);
        this.mockedAuthToken = mock(AuthToken.class);
        this.mockedEmailServiceProvider = mock(EmailService.class);
        this.mockedFaxServiceProvider = mock(FaxService.class);
        this.mockedReportProvider = mock(ReportFacade.class);
        this.mockedPrintProvider = mock(PrintService.class);
        when(mockedAuthProvider.login("Terry Gilliam","hunter2")).thenReturn(mockedAuthToken);
        when(mockedAuthProvider.authenticate(mockedAuthToken)).thenReturn(true);
        feaaFacade.setClientProvider(clientList);
        feaaFacade.setAuthProvider(mockedAuthProvider);
        feaaFacade.setReportingProvider(mockedReportProvider);
        feaaFacade.setPrintProvider(mockedPrintProvider);
        feaaFacade.setFaxProvider(mockedFaxServiceProvider);
        feaaFacade.setEmailProvider(mockedEmailServiceProvider);
        feaaFacade.login("Terry Gilliam","hunter2");
    }

    /**
     * Test Login/Logout Section
     */

    @Test
    public void testLoginAfterLogOut(){
        feaaFacade.logout();
        assertFalse(feaaFacade.login("Ken Lee","helloWorld"));
        assertTrue(feaaFacade.login("Terry Gilliam","hunter2"));
    }

    @Test(expected = IllegalStateException.class)
    public void loginWhenAlreadyLoggedIn(){
        feaaFacade.login("Terry Gilliam","hunter2");
    }

    @Test(expected = IllegalStateException.class)
    public void testLoginNotAuth(){
        when(mockedAuthProvider.authenticate(mockedAuthToken)).thenReturn(false);
        feaaFacade.login("Terry Gilliam","hunter2");
    }

    @Test(expected = IllegalStateException.class)
    public void testLogInNullAuth(){
        feaaFacade.logout();
        feaaFacade.setAuthProvider(null);
        feaaFacade.login("Terry Gilliam","hunter2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void InvalidInputLogin(){
        feaaFacade.logout();
        feaaFacade.login(null,null);
        feaaFacade.login("","");
    }

    @Test(expected = IllegalStateException.class)
    public void testLogOutNullAuth(){
        feaaFacade.setAuthProvider(null);
        feaaFacade.logout();
    }

    @Test(expected = IllegalStateException.class)
    public void testDoubleLogOut(){
        feaaFacade.logout();
        feaaFacade.logout();
    }

    /**
     * Test Login/Logout Section
     */

    @Test
    public void testClientProvider(){
        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        assertNotNull(clientList.findOne(1));
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        assertNotNull(feaaFacade.getAccounts(1));
        assertEquals(1,feaaFacade.getAccounts(1).size());
        feaaFacade.setClientProvider(clientList);
        assertTrue(feaaFacade.getAccounts(1).isEmpty());

    }

    //--------------------------------------------------------------------------------------

    /**
     * addClient() Tests - START
     */

    @Test(expected = IllegalArgumentException.class)
    public void testFNameNull() {
        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);
        feaaFacade.addClient(null, "James", "+6012345678");
        verify(clientList,atLeastOnce()).addClient(anyInt(),anyString(),anyString(),anyString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFNameEmpty() {
        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);
        feaaFacade.addClient("", "James", "+6012345678");
        verify(clientList,atLeastOnce()).addClient(anyInt(),anyString(),anyString(),anyString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLNameNull() {
        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);
        feaaFacade.addClient("Lebron", null, "+6012345678");
        verify(clientList,atLeastOnce()).addClient(anyInt(),anyString(),anyString(),anyString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLNameEmpty(){
        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);
        feaaFacade.addClient("Lebron", "", "+6012345678");
        verify(clientList,atLeastOnce()).addClient(anyInt(),anyString(),anyString(),anyString());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testPhoneNumberNull() {
        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);
        feaaFacade.addClient("LeBron", "James", null);
        verify(clientList,atLeastOnce()).addClient(anyInt(),anyString(),anyString(),anyString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPhoneNumberEmpty() {
        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);
        feaaFacade.addClient("Lebron", "James", "");
        verify(clientList,atLeastOnce()).addClient(anyInt(),anyString(),anyString(),anyString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPhoneFormFormat() {
        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);
        feaaFacade.addClient("Lebron", "James", "@@@@@");
        verify(clientList,atLeastOnce()).addClient(anyInt(),anyString(),anyString(),anyString());

    }

    @Test(expected = IllegalStateException.class)
    public void testNullClientProviderAddClient() {
        feaaFacade.setClientProvider(null);
        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);
        feaaFacade.addClient("Lebron", "James", "+6012345678");
        verify(clientList,atLeastOnce()).addClient(anyInt(),anyString(),anyString(),anyString());
    }

    @Test(expected = IllegalStateException.class)
    public void testNullAuthProviderAddClient(){
        feaaFacade.setAuthProvider(null);
        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);
        feaaFacade.addClient("Lebron", "James", "+6012345678");
        verify(clientList,atLeastOnce()).addClient(anyInt(),anyString(),anyString(),anyString());
    }

   @Test
    public void testAddClient() {

       when(mockedClient.getFirstName()).thenReturn("Lebron");
       when(mockedClient.getLastName()).thenReturn("James");
       when(mockedClient.getPhoneNumber()).thenReturn("+6012345678");

       when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);

       Client result = feaaFacade.addClient("Lebron", "James", "+6012345678");

       assertEquals(mockedClient.getFirstName(), result.getFirstName());
       assertEquals(mockedClient.getLastName(), result.getLastName());
       assertEquals(mockedClient.getPhoneNumber(), result.getPhoneNumber());

       assertNotNull(result);

       verify(clientList,atLeastOnce()).addClient(anyInt(),anyString(),anyString(),anyString());

   }

    @Test(expected = SecurityException.class)
    public void testAddClientNotLoggedIn() {
        feaaFacade.logout();
        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);
        feaaFacade.addClient("Lebron", "James", "+6012345678");
        verify(clientList,atLeastOnce()).addClient(anyInt(),anyString(),anyString(),anyString());
    }

    @Test(expected = SecurityException.class)
    public void testAddClientInvalidLogInAuthenticationFail() {
        when(mockedAuthProvider.authenticate(mockedAuthToken)).thenReturn(false);
        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);
        feaaFacade.addClient("Lebron", "James", "+6012345678");
        verify(clientList,atLeastOnce()).addClient(anyInt(),anyString(),anyString(),anyString());
    }

    /**
     * addClient() Tests - END
     */
    //--------------------------------------------------------------------------------------

    @Test
    public void testEmptyInitClientList() {
        assertEquals(0, feaaFacade.getAllClients().size());
        assertTrue(feaaFacade.getAllClients().isEmpty());
    }

    /**
     * assignClient() Tests - START
     */


    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAssignClientID() {
        feaaFacade.assignClient(0, "DOMESTIC");
        feaaFacade.assignClient(-1, "DOMESTIC");
        verify(clientList,atLeastOnce()).addClient(anyInt(),anyString(),anyString(),anyString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAssignClientDepartmentCode() {
        feaaFacade.assignClient(1, "");
        feaaFacade.assignClient(1, null);
        feaaFacade.assignClient(1, "Bikini Bottom");
        feaaFacade.assignClient(1, "   ");
        verify(clientList,atLeastOnce()).addClient(anyInt(),anyString(),anyString(),anyString());
    }

    @Test(expected = SecurityException.class)
    public void testNotLoggedInAssignClient(){
        feaaFacade.logout();
        feaaFacade.assignClient(1,"DOMESTIC");
        verify(clientList,atLeastOnce()).addClient(anyInt(),anyString(),anyString(),anyString());
    }

    @Test(expected = SecurityException.class)
    public void testFailedAuthenticationAssignClient(){
        when(mockedAuthProvider.authenticate(mockedAuthToken)).thenReturn(false);
        feaaFacade.assignClient(1,"DOMESTIC");
        verify(clientList,atLeastOnce()).addClient(anyInt(),anyString(),anyString(),anyString());
    }

    @Test
    public void testAssignClientA() {

        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        when(mockedClient.isAssigned()).thenReturn(false);
        assertFalse(clientList.findOne(1) == null);
        assertEquals(false, clientList.findOne(1).isAssigned());

        feaaFacade.assignClient(1,"DOMESTIC");
        feaaFacade.assignClient(1,"INTERNATIONAL");
        feaaFacade.assignClient(1,"LARGE ACCOUNTS");

        verify(mockedClient,times(3)).assignDepartment(anyString());
        verify(clientList,atLeastOnce()).findOne(anyInt());
    }

    @Test
    public void testAssignClientB() {

        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        when(mockedClient.isAssigned()).thenReturn(false);
        assertFalse(clientList.findOne(1) == null);
        assertEquals(false, clientList.findOne(1).isAssigned());

        feaaFacade.assignClient(1,"DOM");
        feaaFacade.assignClient(1,"INT");
        feaaFacade.assignClient(1,"LRG");

        verify(mockedClient,times(3)).assignDepartment(anyString());
        verify(clientList,atLeastOnce()).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testAlreadyAssignedClient(){
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
       when(mockedClient.isAssigned()).thenReturn(true);
       assertNotNull(clientList.findOne(1));
       feaaFacade.assignClient(1,"DOMESTIC");
       verify(mockedClient,times(1)).assignDepartment(anyString());
        verify(clientList,atLeastOnce()).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testNotMatchingUnassignedClient(){
        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        when(mockedClient.isAssigned()).thenReturn(false);
        assertNotNull(clientList.findOne(1));
        feaaFacade.assignClient(4,"DOMESTIC");
        verify(mockedClient,times(1)).assignDepartment(anyString());
        verify(clientList,atLeastOnce()).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testNullClientProviderAssignClient() {
        feaaFacade.setClientProvider(null);
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        when(mockedClient.isAssigned()).thenReturn(false);
        assertNotNull(clientList.findOne(1));
        feaaFacade.assignClient(1, "INTERNATIONAL");
        verify(mockedClient,times(1)).assignDepartment(anyString());
        verify(clientList,atLeastOnce()).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testNullAuthProviderAssignAssignClient(){
        feaaFacade.setAuthProvider(null);
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        when(mockedClient.isAssigned()).thenReturn(false);
        assertNotNull(clientList.findOne(1));
        feaaFacade.assignClient(1, "INTERNATIONAL");
        verify(mockedClient,times(1)).assignDepartment(anyString());
        verify(clientList,atLeastOnce()).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testInvalidClientIDOnUnassigned() {
        feaaFacade.assignClient(4, "INTERNATIONAL");
        verify(mockedClient,times(1)).assignDepartment(anyString());
        verify(clientList,atLeastOnce()).findOne(anyInt());
    }

    /**
     * assignClient() tests - END
     */

    //--------------------------------------------------------------------------------------

    /**
     * viewAllClients() tests - START
     */

    @Test(expected = SecurityException.class)
    public void testNotLoggedInViewAllClients(){
        feaaFacade.logout();
        feaaFacade.viewAllClients();
    }

    @Test(expected = SecurityException.class)
    public void testNotAuthenticatedForViewAllClients(){
        when(mockedAuthProvider.authenticate(mockedAuthToken)).thenReturn(false);
        feaaFacade.viewAllClients();

    }

    @Test(expected = IllegalStateException.class)
    public void testNotAuthenticatedViewAllClients(){
        feaaFacade.setAuthProvider(null);
        feaaFacade.viewAllClients();
    }

    @Test
    public void testViewAllClientNullClientProvider(){
        feaaFacade.setClientProvider(null);
        assertNotNull(feaaFacade.viewAllClients());
        assertTrue(feaaFacade.viewAllClients().isEmpty());
    }

    @Test
    public void testViewAllClientsEmptyList() {
        assertNotNull(feaaFacade.viewAllClients());
        assertTrue(feaaFacade.viewAllClients().isEmpty());
    }

    @Test
    public void testViewAllClients() {
        List<Client> tempList = new ArrayList<>();

        when(mockedClient.getFirstName()).thenReturn("Lebron");
        when(mockedClient.getLastName()).thenReturn("James");
        when(mockedClient.getPhoneNumber()).thenReturn("+6012345678");

       when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);

        feaaFacade.addClient("Lebron", "James", "+6012345678");
        tempList.add(mockedClient);
        when(clientList.findAll()).thenReturn(tempList);

        assertNotNull(feaaFacade.viewAllClients());
        assertEquals(1,feaaFacade.viewAllClients().size());
        assertEquals("James, Lebron", feaaFacade.viewAllClients().get(0));
        assertFalse(feaaFacade.viewAllClients().isEmpty());
        verify(clientList,atLeastOnce()).findAll();
    }

    /**
     * viewAllClients tests - END
     */

    //--------------------------------------------------------------------------------------

    /**
     * getAllClients tests - START
     */
    @Test(expected = IllegalStateException.class)
    public void testGetAllClientsFromNullClientProvider() {
        feaaFacade.setClientProvider(null);
        feaaFacade.getAllClients();
    }

    @Test(expected = IllegalStateException.class)
    public void testGetAllClientsFromNullAuth() {
        feaaFacade.setAuthProvider(null);
        feaaFacade.getAllClients();
    }

    @Test(expected = SecurityException.class)
    public void testNotLoggedInForGetAllClients(){
        feaaFacade.logout();
        feaaFacade.getAllClients();
    }
    @Test(expected = SecurityException.class)
    public void testNotAuthenticatedForGetAllClients(){
        when(mockedAuthProvider.authenticate(mockedAuthToken)).thenReturn(false);
        feaaFacade.getAllClients();
    }

    @Test
    public void testGetAllClients() {
        List<Client> tempList = new ArrayList<>();
        tempList.add(mockedClient);
        when(clientList.findAll()).thenReturn(tempList);
        assertNotNull(feaaFacade.getAllClients());
        assertEquals(1,feaaFacade.getAllClients().size());
        verify(clientList,atLeastOnce()).findAll();
    }

    @Test
    public void testGetAllClientsA(){
        List<Client> tempList = new ArrayList<>();

        when(mockedClient.getID()).thenReturn(1);
        when(mockedClient.getFirstName()).thenReturn("Lebron");
        when(mockedClient.getLastName()).thenReturn("James");
        when(mockedClient.getPhoneNumber()).thenReturn("+6012345678");

        tempList.add(mockedClient);
        when(clientList.findAll()).thenReturn(tempList);

        List<Client> list  = feaaFacade.getAllClients();

        assertEquals("Lebron",list.get(0).getFirstName());
        assertEquals("James",list.get(0).getLastName());
        assertEquals("+6012345678",list.get(0).getPhoneNumber());
        assertEquals(1,list.get(0).getID());

        assertNotNull(feaaFacade.getAllClients());
        assertEquals(1,feaaFacade.getAllClients().size());
        assertFalse(feaaFacade.getAllClients().isEmpty());

        verify(clientList,atLeastOnce()).findAll();

    }

    /**
     * getAllClients tests - END
     */
    //--------------------------------------------------------------------------------------

    /**
     * addAccount tests - START
     */
    @Test(expected = IllegalStateException.class)
    public void testNullClientProviderAddAccount() {
        feaaFacade.setClientProvider(null);
        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);
        feaaFacade.addAccount(1, 1, "Lebron's Account", 230000000, 5000000,"+()1234567","bob@email.com");
        verify(clientList,times(1)).addClient(anyInt(),anyString(),anyString(),anyString());
    }

    @Test(expected = IllegalStateException.class)
    public void testNullAuthProviderAddAccount() {
        feaaFacade.setAuthProvider(null);
        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);
        feaaFacade.addAccount(1, 1, "Lebron's Account", 230000000, 5000000,"+()1234567","bob@email.com");
        verify(clientList,times(1)).addClient(anyInt(),anyString(),anyString(),anyString());
    }

    @Test(expected = IllegalStateException.class)
    public void testUnMatchingClientIDAddAccount() {
        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);
        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        assertNotNull(clientList.findOne(1));
        feaaFacade.addAccount(1, 2, "Lebron's Account", 230000000, 5000000,"+()1234567","bob@email.com");
        verify(clientList,times(1)).addClient(anyInt(),anyString(),anyString(),anyString());
    }

    @Test(expected = SecurityException.class)
    public void testNotLoggedInAddAccount(){
        feaaFacade.logout();
        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        assertNotNull(clientList.findOne(1));
        feaaFacade.addAccount(1, 1, "Lebron's Account", 230000000, 5000000,"+()1234567","bob@email.com");
        verify(clientList,times(1)).addClient(anyInt(),anyString(),anyString(),anyString());
    }

    @Test(expected = SecurityException.class)
    public void testUnAuthAddAccount(){
        when(mockedAuthProvider.authenticate(mockedAuthToken)).thenReturn(false);
        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        assertNotNull(clientList.findOne(1));
        feaaFacade.addAccount(1, 1, "Lebron's Account", 230000000, 5000000,"+()1234567","bob@email.com");
        verify(clientList,times(1)).addClient(anyInt(),anyString(),anyString(),anyString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidClientIDAddAccountA() {
        feaaFacade.addAccount(1, -1, "Bob", 230000000, 5000000,"+()123456","bob@email.com");
        verify(clientList,times(1)).addClient(anyInt(),anyString(),anyString(),anyString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidClientIDAddAccountB() {
        feaaFacade.addAccount(1, 0, "Bob's Account", 230000000, 5000000,"+()123456","bob@email.com");
        verify(clientList,times(1)).addClient(anyInt(),anyString(),anyString(),anyString());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAccountIDAddAccount() {
        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);
        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        assertNotNull(clientList.findOne(1));
        feaaFacade.addAccount(0, 1, "Bob's Account", 240000000, 8000000,"+()123456","bob@email.com");
        verify(clientList,times(1)).addClient(anyInt(),anyString(),anyString(),anyString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAccountIDAddAccountC() {
        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);
        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        assertNotNull(clientList.findOne(1));
        feaaFacade.addAccount(-1, 1, "Bob's Account", 240000000, 8000000,"+()123456","bob@email.com");
        verify(clientList,times(1)).addClient(anyInt(),anyString(),anyString(),anyString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAccountNameAddAccountA() {
        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);
        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        assertNotNull(clientList.findOne(1));
        feaaFacade.addAccount(1, 1, "", 240000000, 8000000,"+()123456","bob@email.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidAccountNameAddAccountB() {
        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);
        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        assertNotNull(clientList.findOne(1));
        feaaFacade.addAccount(1, 1, null, 240000000, 8000000,"+()123456","bob@email.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInitialInComingsAddAccount() {
        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);
        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        assertNotNull(clientList.findOne(1));
        feaaFacade.addAccount(1, 1, "Lebron's Account", -1, 5000000,"+()1234567","bob@email.com");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidInitialOutGoingsAddAccount() {
        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);
        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        assertNotNull(clientList.findOne(1));
        feaaFacade.addAccount(1, 1, "Lebron's Account", 100, -5,"+()1234567","bob@email.com");
        verify(clientList,times(1)).findOne(anyInt());
    }
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPhoneNumberAddAccountA() {
        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);
        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        assertNotNull(clientList.findOne(1));
        feaaFacade.addAccount(1, 1, "Lebron's Account", 100, 5,"","bob@email.com");
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPhoneNumberAddAccountB() {
        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);
        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        assertNotNull(clientList.findOne(1));
        feaaFacade.addAccount(1, 1, "Bob's Account", 100, 5,"Bob's Number","bob@email.com");
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDuplicateAccountIDAddAccount(){
        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);
        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        when(clientList.findOne(eq(2))).thenReturn(mockedClient);
        assertNotNull(clientList.findOne(1));
        assertNotNull(clientList.findOne(2));
        feaaFacade.addAccount(1, 1, "Bob's Account", 100, 5,"+()1234567","bob@email.com");
        feaaFacade.addAccount(1, 2, "Bob's Account", 100, 5,"+()1234567","bob@email.com");
        verify(clientList,times(2)).findOne(anyInt());
    }

    @Test
    public void testNullAccountIDAddAccount() {
        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);
        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        int result = feaaFacade.addAccount(null, 1, "Lebron's Account", 230000000, 5000000,"+()1234567","bob@email.com");
        assertTrue(result > 0);
        verify(clientList,times(1)).findOne(anyInt());
    }


    @Test
    public void testAddAccount() {
        List<Client> tempList = new ArrayList<>();
        tempList.add(mockedClient);
        when(clientList.findAll()).thenReturn(tempList);
        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);
        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        assertNotNull(feaaFacade.getAllClients());
        int result = feaaFacade.addAccount(1, 1, "Bob's Account", 100, 5,"+()1234567","bob@email.com");
        assertEquals(1, result);
        verify(clientList,times(1)).findOne(anyInt());
    }

    /**
     * addAccount() tests - END
     */

    //--------------------------------------------------------------------------------------

    /**
     * getAccount(NO PARAM) tests - START
     */

    @Test
    public void testEmptyGetAccounts() {
        assertNotNull(feaaFacade.getAccounts());
    }

    /**
     * Not mentioned in API.
     */

    @Test(expected = IllegalStateException.class)
    public void testNullAuthProviderGetAccounts() {
        feaaFacade.setAuthProvider(null);
        feaaFacade.getAccounts();
    }

    @Test(expected = SecurityException.class)
    public void testNotLoggedInGetAccounts(){
        feaaFacade.logout();
        feaaFacade.getAccounts();
    }

    @Test(expected = SecurityException.class)
    public void testNotAuthGetAccounts(){
        when(mockedAuthProvider.authenticate(mockedAuthToken)).thenReturn(false);
        feaaFacade.getAccounts();
    }

    @Test
    public void testGetAccounts(){
      // when(clientList.addClient(1,"Bob","Dylan","12345678")).thenReturn(mockedClient);
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob's Account",123,456,"12345678","bob@email.com");
        assertEquals("1: Bob's Account",feaaFacade.getAccounts().get(0));
        assertNotNull(feaaFacade.getAccounts());
        assertEquals("1: Bob's Account",feaaFacade.getAccounts().get(0));
        assertEquals(1,feaaFacade.getAccounts().size());
        verify(clientList,times(1)).findOne(anyInt());
    }

    /**
     * getAccount(NO PARAM) tests - END
     */

    //--------------------------------------------------------------------------------------

    /**
     * getAccount(int clientID) tests - START
     */

    @Test(expected = SecurityException.class)
    public void testNotLoginGetAccountsWithID(){
        feaaFacade.logout();
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.getAccounts(1);
    }

    @Test(expected = SecurityException.class)
    public void testNotAuthGetAccountsWithID(){
        when(mockedAuthProvider.authenticate(mockedAuthToken)).thenReturn(false);
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.getAccounts(1);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testNullClientProviderGetAccountsWithID(){
       feaaFacade.setClientProvider(null);
       when(clientList.findOne(anyInt())).thenReturn(mockedClient);
       feaaFacade.getAccounts(1);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testNullAuthProviderGetAccountsWithID(){
        feaaFacade.setAuthProvider(null);
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.getAccounts(1);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testUnMatchingAccountGetAccountsWithID(){
        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        assertNotNull(clientList.findOne(1));
        feaaFacade.getAccounts(2);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalArgumentException.class)
   public void testInvalidClientIDGetAccounts() {
        feaaFacade.getAccounts(-1);
        feaaFacade.getAccounts(0);
   }

    @Test
    public void testGetAccountsByID() {
       when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        assertNotNull(clientList.findOne(1));
        assertNotNull(feaaFacade.getAccounts(1));
        assertEquals(1,feaaFacade.getAccounts(1).size());
        assertEquals(1,feaaFacade.getAccounts(1).get(0));
        verify(clientList,atLeastOnce()).findOne(eq(1));

    }

    @Test
    public void testGetAccountsByIDEmpty() {
        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        assertNotNull(clientList.findOne(1));
        assertNotNull(feaaFacade.getAccounts(1));
        assertEquals(0,feaaFacade.getAccounts(1).size());
        assertTrue(feaaFacade.getAccounts(1).isEmpty());
        verify(clientList,atLeastOnce()).findOne(eq(1));
    }

    /**
     * getAccount(int clientID) tests - END
     */

    //--------------------------------------------------------------------------------------

    /**
     * getAccountName() tests - START
     */

    @Test(expected = SecurityException.class)
    public void notLoggedInGetAccountName() {
        feaaFacade.logout();
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.getAccountName(1);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = SecurityException.class)
    public void notAuthGetAccountName(){
        when(mockedAuthProvider.authenticate(mockedAuthToken)).thenReturn(false);
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.getAccountName(1);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void nullClientProviderGetAccountName(){
        feaaFacade.setClientProvider(null);
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.getAccountName(1);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void nullAuthProviderGetAccountName(){
        feaaFacade.setAuthProvider(null);
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.getAccountName(1);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void unMatchingIDGetAccountName(){
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.getAccountName(2);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidIDGetAccountName(){
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.getAccountName(0);
        feaaFacade.getAccountName(-1);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test
    public void testGetAccountName(){
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        String result = feaaFacade.getAccountName(1);
        assertEquals("Bob",result);
        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(clientList,times(1)).findOne(anyInt());
    }

    /**
     * getAccountName() tests - END
     */

    //--------------------------------------------------------------------------------------

    /**
     * getAccountBalance() tests - START
     */

    @Test(expected = SecurityException.class)
    public void testNotLoggedInGetAccountBalance(){
        feaaFacade.logout();
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.getAccountBalance(1);
        verify(clientList,times(1)).findOne(anyInt());

    }

    @Test(expected = SecurityException.class)
    public void testNotAuthGetAccountBalance(){
        when(mockedAuthProvider.authenticate(mockedAuthToken)).thenReturn(false);
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.getAccountBalance(1);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testNullClientProviderGetAccountBalance(){
        feaaFacade.setClientProvider(null);
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.getAccountBalance(1);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testNullAuthProviderGetAccountBalance(){
        feaaFacade.setAuthProvider(null);
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.getAccountBalance(1);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testUnMatchingIDGetAccountBalance(){
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.getAccountBalance(2);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidIDGetAccountBalance(){
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.getAccountBalance(0);
        feaaFacade.getAccountBalance(-1);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test
    public void testGetAccountBalance(){
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        int result = feaaFacade.getAccountBalance(1);
        assertEquals(feaaFacade.getAccountIncomings(1)-feaaFacade.getAccountOutgoings(1),result);
        verify(clientList,times(1)).findOne(anyInt());
    }

    /**
     * getAccountBalance() test - END
     */

    //--------------------------------------------------------------------------------------

    @Test(expected = SecurityException.class)
    public void testNotLoggedInGetAccountInComings(){
        feaaFacade.logout();
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.getAccountIncomings(1);
        verify(clientList,times(1)).findOne(anyInt());

    }

    @Test(expected = SecurityException.class)
    public void testNotAuthInGetAccountInComings(){
        when(mockedAuthProvider.authenticate(mockedAuthToken)).thenReturn(false);
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.getAccountIncomings(1);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testNullClientProviderGetAccountInComings(){
        feaaFacade.setClientProvider(null);
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.getAccountIncomings(1);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testNullAuthProviderGetAccountInComings(){
        feaaFacade.setAuthProvider(null);
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.getAccountIncomings(1);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testUnMatchingClientIDGetAccountInComings(){
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.getAccountIncomings(2);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidClientIDGetAccountInComings(){
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.getAccountIncomings(-1);
        feaaFacade.getAccountIncomings(0);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test
    public void testGetAccountIncomings(){
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        assertEquals(123,feaaFacade.getAccountIncomings(1));
        verify(clientList,times(1)).findOne(anyInt());
    }

    /**
     * getAccountIncomings() test - END
     */

    //--------------------------------------------------------------------------------------

    /**
     * getAccountOutgoings() test - START
     */

    @Test(expected = SecurityException.class)
    public void notLoggedInGetAccountOutgoings(){
        feaaFacade.logout();
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.getAccountOutgoings(1);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = SecurityException.class)
    public void notAuthGetAccountIncomings(){
        when(mockedAuthProvider.authenticate(mockedAuthToken)).thenReturn(false);
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.getAccountOutgoings(1);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testNullClientProviderOnGetOutGoings() {
        feaaFacade.setClientProvider(null);
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.getAccountOutgoings(1);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testNullAuthProviderOnGetOutgoings(){
        feaaFacade.setAuthProvider(null);
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.getAccountOutgoings(1);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testUnMatchingAccountIDOnGetOutGoings() {
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.getAccountOutgoings(5);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidEntryOnGetOutgoings() {
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.getAccountOutgoings(0);
        feaaFacade.getAccountOutgoings(-1);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test
    public void testGetAccountOutgoings() {
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        assertEquals(456, feaaFacade.getAccountOutgoings(1));
        verify(clientList,times(1)).findOne(anyInt());
    }

    /**
     * getAccountOutgoings() tests - END
     */

    //-----------------------------------------------

    /**
     * setAccountIncomings() tests - START
     */

    @Test
    public void testSetIncomingsEffectsOnOutcomings(){
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.setAccountIncomings(1,100);
        assertEquals(100,feaaFacade.getAccountIncomings(1));
        assertNotEquals(100,feaaFacade.getAccountOutgoings(1));
        assertNotSame(feaaFacade.getAccountIncomings(1), feaaFacade.getAccountOutgoings(1));
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = SecurityException.class)
    public void testNotLoggedInSetAccountIncomings(){
        feaaFacade.logout();
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.setAccountIncomings(1,100);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = SecurityException.class)
    public void testNotAuthSetAccountIncomings(){
        when(mockedAuthProvider.authenticate(mockedAuthToken)).thenReturn(false);
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.setAccountIncomings(1,100);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testNullClientProviderOnSetIncomings(){
        feaaFacade.setClientProvider(null);
        feaaFacade.setAccountIncomings(1,500);
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.setAccountIncomings(1,100);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testNullAuthProviderOnSetIncomings(){
        feaaFacade.setAuthProvider(null);
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.setAccountIncomings(1,100);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testUnMatchingClientIDOnSetIncomings(){
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.setAccountIncomings(2,100);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidIDOnSetIncomings(){
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.setAccountIncomings(0,100);
        feaaFacade.setAccountIncomings(-1,100);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidIncomingsOnSetIncomings(){
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.setAccountIncomings(1,-100);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test
    public void testSetIncomings(){
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.setAccountIncomings(1,100);
        assertEquals(100,feaaFacade.getAccountIncomings(1));
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test
    public void testSetIncomingsZero(){
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.setAccountIncomings(1,0);
        assertEquals(0,feaaFacade.getAccountIncomings(1));
        verify(clientList,times(1)).findOne(anyInt());
    }

    /**
     * setIncomings tests - END
     */

    //---------------------------------------------------

    @Test(expected = SecurityException.class)
    public void testNotLoggedInSetOutgoings(){
        feaaFacade.logout();
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.setAccountOutgoings(1,100);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = SecurityException.class)
    public void testNotAuthInSetOutgoings(){
        when(mockedAuthProvider.authenticate(mockedAuthToken)).thenReturn(false);
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.setAccountOutgoings(1,100);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testNullClientProviderSetOutgoings(){
        feaaFacade.setClientProvider(null);
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.setAccountOutgoings(1,100);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testNullAuthProviderSetOutgoings(){
        feaaFacade.setAuthProvider(null);
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.setAccountOutgoings(1,100);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testUnMatchingIDSetOutgoings(){
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.setAccountOutgoings(2,100);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidIDSetOutgoings(){
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.setAccountOutgoings(0,100);
        feaaFacade.setAccountOutgoings(-1,100);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidOutgoingsSetOutgoings(){
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.setAccountOutgoings(1,-100);
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test
    public void testSetOutgoings(){
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.setAccountOutgoings(1,100);
        assertEquals(100,feaaFacade.getAccountOutgoings(1));
        verify(clientList,times(1)).findOne(anyInt());
    }

    @Test
    public void testSetOutgoingsOnZero(){
        when(clientList.findOne(anyInt())).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");
        feaaFacade.setAccountOutgoings(1,0);
        assertEquals(0,feaaFacade.getAccountOutgoings(1));
        verify(clientList,times(1)).findOne(anyInt());
    }

    /**
     * setAccountOutgoings() test - END
     */

    //------------------------------

    /**
     * setReportPreference() and makeReport() tests START
     */

    @Test(expected = SecurityException.class)
    public void testNotLoggedInReport(){
        feaaFacade.logout();

        when(mockedClient.getID()).thenReturn(1);
        when(mockedClient.getPhoneNumber()).thenReturn("12345678");
        when(mockedClient.getFirstName()).thenReturn("Bob");
        when(mockedClient.getLastName()).thenReturn("Dylan");

        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");

        BigDecimal bigD = BigDecimal.valueOf((double)Math.abs(feaaFacade.getAccountBalance(1)) * 0.21745254754D);
        int res = bigD.intValue();

        feaaFacade.setReportPreferences(1,true,true,true);

        when(mockedReportProvider.makeReport(mockedAuthToken,1,"Bob","Dylan",feaaFacade.getAccountBalance(1))).thenReturn(bigD);

        assertEquals(res ,feaaFacade.makeReport(1));
    }

    @Test(expected = SecurityException.class)
    public void testNotAuthInReport(){
        when(mockedAuthProvider.authenticate(mockedAuthToken)).thenReturn(false);
        when(mockedClient.getID()).thenReturn(1);
        when(mockedClient.getPhoneNumber()).thenReturn("12345678");
        when(mockedClient.getFirstName()).thenReturn("Bob");
        when(mockedClient.getLastName()).thenReturn("Dylan");


        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");

        BigDecimal bigD = BigDecimal.valueOf((double)Math.abs(feaaFacade.getAccountBalance(1)) * 0.21745254754D);

        feaaFacade.setReportPreferences(1,true,true,true);

        when(mockedReportProvider.makeReport(mockedAuthToken,1,"Bob","Dylan",feaaFacade.getAccountBalance(1))).thenReturn(bigD);
        feaaFacade.makeReport(2);

    }

    @Test(expected = IllegalStateException.class)
    public void testNullAuthProviderReport(){
        feaaFacade.setAuthProvider(null);
        when(mockedClient.getID()).thenReturn(1);
        when(mockedClient.getPhoneNumber()).thenReturn("12345678");
        when(mockedClient.getFirstName()).thenReturn("Bob");
        when(mockedClient.getLastName()).thenReturn("Dylan");

        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");

        BigDecimal bigD = BigDecimal.valueOf((double)Math.abs(feaaFacade.getAccountBalance(1)) * 0.21745254754D);
        int res = bigD.intValue();

        feaaFacade.setReportPreferences(1,true,true,true);

        when(mockedReportProvider.makeReport(mockedAuthToken,1,"Bob","Dylan",feaaFacade.getAccountBalance(1))).thenReturn(bigD);

        assertEquals(res ,feaaFacade.makeReport(1));
        verify(clientList,atLeastOnce()).findOne(anyInt());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidIDSetReportPref(){
        feaaFacade.setReportPreferences(0,true,true,true);
        feaaFacade.setReportPreferences(-1,true,true,true);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAllFalseSetReport(){
        when(mockedClient.getID()).thenReturn(1);
        when(mockedClient.getPhoneNumber()).thenReturn("12345678");
        when(mockedClient.getFirstName()).thenReturn("Bob");
        when(mockedClient.getLastName()).thenReturn("Dylan");

        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");

        BigDecimal bigD = BigDecimal.valueOf((double)Math.abs(feaaFacade.getAccountBalance(1)) * 0.21745254754D);
        int res = bigD.intValue();

        feaaFacade.setReportPreferences(1,false,false,false);

        when(mockedReportProvider.makeReport(mockedAuthToken,1,"Bob","Dylan",feaaFacade.getAccountBalance(1))).thenReturn(bigD);

        assertEquals(res ,feaaFacade.makeReport(1));


}
    @Test(expected = IllegalStateException.class)
    public void testNotMatchingAccountIDReport(){
        when(mockedClient.getID()).thenReturn(1);
        when(mockedClient.getPhoneNumber()).thenReturn("12345678");
        when(mockedClient.getFirstName()).thenReturn("Bob");
        when(mockedClient.getLastName()).thenReturn("Dylan");

        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);

        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");

        BigDecimal bigD = BigDecimal.valueOf((double)Math.abs(feaaFacade.getAccountBalance(1)) * 0.21745254754D);
        int res = bigD.intValue();

        feaaFacade.setReportPreferences(2,true,true,true);

        when(mockedReportProvider.makeReport(mockedAuthToken,1,"Bob","Dylan",feaaFacade.getAccountBalance(1))).thenReturn(bigD);

        assertEquals(res ,feaaFacade.makeReport(1));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotMatchingAccountIDSetReportPrefAndMakeReport(){
        when(mockedClient.getID()).thenReturn(1);
        when(mockedClient.getPhoneNumber()).thenReturn("12345678");
        when(mockedClient.getFirstName()).thenReturn("Bob");
        when(mockedClient.getLastName()).thenReturn("Dylan");

        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);

        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");

        BigDecimal bigD = BigDecimal.valueOf((double)Math.abs(feaaFacade.getAccountBalance(1)) * 0.21745254754D);


        feaaFacade.setReportPreferences(1,true,true,true);

        when(mockedReportProvider.makeReport(mockedAuthToken,1,"Bob","Dylan",feaaFacade.getAccountBalance(1))).thenReturn(bigD);

        feaaFacade.makeReport(2);
    }

    @Test
    public void testSetReportPreferencesAndMakeReport(){
        when(mockedClient.getID()).thenReturn(1);
        when(mockedClient.getPhoneNumber()).thenReturn("12345678");
        when(mockedClient.getFirstName()).thenReturn("Bob");
        when(mockedClient.getLastName()).thenReturn("Dylan");

        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");

        BigDecimal bigD = BigDecimal.valueOf((double)Math.abs(feaaFacade.getAccountBalance(1)) * 0.21745254754D);
        int res = bigD.intValue();

        feaaFacade.setReportPreferences(1,true,true,true);

        when(mockedReportProvider.makeReport(mockedAuthToken,1,"Bob","Dylan",feaaFacade.getAccountBalance(1))).thenReturn(bigD);

        assertEquals(res ,feaaFacade.makeReport(1));
        verify(clientList,atLeastOnce()).findOne(anyInt());

    }


    @Test(expected = IllegalStateException.class)
    public void testClientProviderNullMakeReport(){
        feaaFacade.setClientProvider(null);
        when(mockedClient.getID()).thenReturn(1);
        when(mockedClient.getPhoneNumber()).thenReturn("12345678");
        when(mockedClient.getFirstName()).thenReturn("Bob");
        when(mockedClient.getLastName()).thenReturn("Dylan");

        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);

        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");

        BigDecimal bigD = BigDecimal.valueOf((double)Math.abs(feaaFacade.getAccountBalance(1)) * 0.21745254754D);
        int res = bigD.intValue();

        feaaFacade.setReportPreferences(1,true,true,true);

        when(mockedReportProvider.makeReport(mockedAuthToken,1,"Bob","Dylan",feaaFacade.getAccountBalance(1))).thenReturn(bigD);

        assertEquals(res ,feaaFacade.makeReport(1));
        verify(clientList,atLeastOnce()).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testEmailProviderNullMakeReport(){
        feaaFacade.setEmailProvider(null);
        when(mockedClient.getID()).thenReturn(1);
        when(mockedClient.getPhoneNumber()).thenReturn("12345678");
        when(mockedClient.getFirstName()).thenReturn("Bob");
        when(mockedClient.getLastName()).thenReturn("Dylan");

        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);

        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");

        BigDecimal bigD = BigDecimal.valueOf((double)Math.abs(feaaFacade.getAccountBalance(1)) * 0.21745254754D);
        int res = bigD.intValue();

        feaaFacade.setReportPreferences(1,true,true,true);

        when(mockedReportProvider.makeReport(mockedAuthToken,1,"Bob","Dylan",feaaFacade.getAccountBalance(1))).thenReturn(bigD);

        assertEquals(res ,feaaFacade.makeReport(1));

        verify(clientList,atLeastOnce()).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testFaxProviderNullMakeReport(){
        feaaFacade.setFaxProvider(null);
        when(mockedClient.getID()).thenReturn(1);
        when(mockedClient.getPhoneNumber()).thenReturn("12345678");
        when(mockedClient.getFirstName()).thenReturn("Bob");
        when(mockedClient.getLastName()).thenReturn("Dylan");

        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);

        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");

        BigDecimal bigD = BigDecimal.valueOf((double)Math.abs(feaaFacade.getAccountBalance(1)) * 0.21745254754D);
        int res = bigD.intValue();

        feaaFacade.setReportPreferences(1,true,true,true);

        when(mockedReportProvider.makeReport(mockedAuthToken,1,"Bob","Dylan",feaaFacade.getAccountBalance(1))).thenReturn(bigD);

        assertEquals(res ,feaaFacade.makeReport(1));
        verify(clientList,atLeastOnce()).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testPrintProvidersNullMakeReport(){
        feaaFacade.setPrintProvider(null);
        when(mockedClient.getID()).thenReturn(1);
        when(mockedClient.getPhoneNumber()).thenReturn("12345678");
        when(mockedClient.getFirstName()).thenReturn("Bob");
        when(mockedClient.getLastName()).thenReturn("Dylan");

        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);

        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");

        BigDecimal bigD = BigDecimal.valueOf((double)Math.abs(feaaFacade.getAccountBalance(1)) * 0.21745254754D);
        int res = bigD.intValue();

        feaaFacade.setReportPreferences(1,true,true,true);

        when(mockedReportProvider.makeReport(mockedAuthToken,1,"Bob","Dylan",feaaFacade.getAccountBalance(1))).thenReturn(bigD);

        assertEquals(res ,feaaFacade.makeReport(1));
        verify(clientList,atLeastOnce()).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testReportProviderNullMakeReport(){
        feaaFacade.setReportingProvider(null);
        when(mockedClient.getID()).thenReturn(1);
        when(mockedClient.getPhoneNumber()).thenReturn("12345678");
        when(mockedClient.getFirstName()).thenReturn("Bob");
        when(mockedClient.getLastName()).thenReturn("Dylan");

        when(clientList.addClient(anyInt(),anyString(),anyString(),anyString())).thenReturn(mockedClient);

        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        feaaFacade.addAccount(1,1,"Bob",123,456,"12345678","bob@email.com");

        BigDecimal bigD = BigDecimal.valueOf((double)Math.abs(feaaFacade.getAccountBalance(1)) * 0.21745254754D);
        int res = bigD.intValue();

        feaaFacade.setReportPreferences(1,true,true,true);

        when(mockedReportProvider.makeReport(mockedAuthToken,1,"Bob","Dylan",feaaFacade.getAccountBalance(1))).thenReturn(bigD);

        assertEquals(res ,feaaFacade.makeReport(1));
        verify(clientList,atLeastOnce()).findOne(anyInt());
    }

    /**
     * setReportPreference() and makeReport() tests END
     */

    @Test
    public void testGetTotalLifeTimeCom() {

        when(mockedClient.getID()).thenReturn(1);
        when(mockedClient.getPhoneNumber()).thenReturn("12345678");
        when(mockedClient.getFirstName()).thenReturn("Bob");
        when(mockedClient.getLastName()).thenReturn("Dylan");

        when(clientList.addClient(anyInt(), anyString(), anyString(), anyString())).thenReturn(mockedClient);

        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        feaaFacade.addAccount(1, 1, "Bob", 123, 456, "12345678", "bob@email.com");

        BigDecimal bigD = BigDecimal.valueOf((double) Math.abs(feaaFacade.getAccountBalance(1)) * 0.21745254754D);
        int res = bigD.intValue();

        feaaFacade.setReportPreferences(1, true, true, true);

        when(mockedReportProvider.makeReport(mockedAuthToken, 1, "Bob", "Dylan", feaaFacade.getAccountBalance(1))).thenReturn(bigD);
        when(mockedReportProvider.getLifetimeCommission(mockedAuthToken, 1)).thenReturn(bigD);

        assertEquals(res, feaaFacade.getTotalLifetimeCommission(1));
        assertEquals(res, feaaFacade.makeReport(1));
        verify(clientList,atLeastOnce()).findOne(anyInt());
    }


    @Test(expected = SecurityException.class)
    public void testNotLoggedInGetTotalLifetimeCom(){
        feaaFacade.logout();
        when(mockedClient.getID()).thenReturn(1);
        when(mockedClient.getPhoneNumber()).thenReturn("12345678");
        when(mockedClient.getFirstName()).thenReturn("Bob");
        when(mockedClient.getLastName()).thenReturn("Dylan");

        when(clientList.addClient(anyInt(), anyString(), anyString(), anyString())).thenReturn(mockedClient);

        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        feaaFacade.addAccount(1, 1, "Bob", 123, 456, "12345678", "bob@email.com");

        BigDecimal bigD = BigDecimal.valueOf((double) Math.abs(feaaFacade.getAccountBalance(1)) * 0.21745254754D);
        int res = bigD.intValue();

        feaaFacade.setReportPreferences(1, true, true, true);

        when(mockedReportProvider.makeReport(mockedAuthToken, 1, "Bob", "Dylan", feaaFacade.getAccountBalance(1))).thenReturn(bigD);
        when(mockedReportProvider.getLifetimeCommission(mockedAuthToken, 1)).thenReturn(bigD);

        assertEquals(res, feaaFacade.getTotalLifetimeCommission(1));
        assertEquals(res, feaaFacade.makeReport(1));
        verify(clientList,atLeastOnce()).findOne(anyInt());

    }

    @Test(expected = SecurityException.class)
    public void testNotAuthGetTotalLifetimeCom(){
        when(mockedAuthProvider.authenticate(mockedAuthToken)).thenReturn(false);
        when(mockedClient.getID()).thenReturn(1);
        when(mockedClient.getPhoneNumber()).thenReturn("12345678");
        when(mockedClient.getFirstName()).thenReturn("Bob");
        when(mockedClient.getLastName()).thenReturn("Dylan");

        when(clientList.addClient(anyInt(), anyString(), anyString(), anyString())).thenReturn(mockedClient);

        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        feaaFacade.addAccount(1, 1, "Bob", 123, 456, "12345678", "bob@email.com");

        BigDecimal bigD = BigDecimal.valueOf((double) Math.abs(feaaFacade.getAccountBalance(1)) * 0.21745254754D);
        int res = bigD.intValue();

        feaaFacade.setReportPreferences(1, true, true, true);

        when(mockedReportProvider.makeReport(mockedAuthToken, 1, "Bob", "Dylan", feaaFacade.getAccountBalance(1))).thenReturn(bigD);
        when(mockedReportProvider.getLifetimeCommission(mockedAuthToken, 1)).thenReturn(bigD);

        assertEquals(res, feaaFacade.getTotalLifetimeCommission(1));
        assertEquals(res, feaaFacade.makeReport(1));
        verify(clientList,atLeastOnce()).findOne(anyInt());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidParamGetTotalLifetimeCom(){
        when(mockedClient.getID()).thenReturn(1);
        when(mockedClient.getPhoneNumber()).thenReturn("12345678");
        when(mockedClient.getFirstName()).thenReturn("Bob");
        when(mockedClient.getLastName()).thenReturn("Dylan");

        when(clientList.addClient(anyInt(), anyString(), anyString(), anyString())).thenReturn(mockedClient);

        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        feaaFacade.addAccount(1, 1, "Bob", 123, 456, "12345678", "bob@email.com");

        BigDecimal bigD = BigDecimal.valueOf((double) Math.abs(feaaFacade.getAccountBalance(1)) * 0.21745254754D);
        int res = bigD.intValue();

        feaaFacade.setReportPreferences(1, true, true, true);

        when(mockedReportProvider.makeReport(mockedAuthToken, 1, "Bob", "Dylan", feaaFacade.getAccountBalance(1))).thenReturn(bigD);
        when(mockedReportProvider.getLifetimeCommission(mockedAuthToken, 1)).thenReturn(bigD);
        assertEquals(res, feaaFacade.makeReport(1));
        feaaFacade.getTotalLifetimeCommission(2);
        verify(clientList,atLeastOnce()).findOne(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void testNullAuthProviderGetTotalLifetimeCom(){
        feaaFacade.setAuthProvider(null);
        when(mockedClient.getID()).thenReturn(1);
        when(mockedClient.getPhoneNumber()).thenReturn("12345678");
        when(mockedClient.getFirstName()).thenReturn("Bob");
        when(mockedClient.getLastName()).thenReturn("Dylan");

        when(clientList.addClient(anyInt(), anyString(), anyString(), anyString())).thenReturn(mockedClient);

        when(clientList.findOne(eq(1))).thenReturn(mockedClient);
        feaaFacade.addAccount(1, 1, "Bob", 123, 456, "12345678", "bob@email.com");

        BigDecimal bigD = BigDecimal.valueOf((double) Math.abs(feaaFacade.getAccountBalance(1)) * 0.21745254754D);
        int res = bigD.intValue();

        feaaFacade.setReportPreferences(1, true, true, true);

        when(mockedReportProvider.makeReport(mockedAuthToken, 1, "Bob", "Dylan", feaaFacade.getAccountBalance(1))).thenReturn(bigD);
        when(mockedReportProvider.getLifetimeCommission(mockedAuthToken, 1)).thenReturn(bigD);
        assertEquals(res, feaaFacade.makeReport(1));
        verify(clientList,atLeastOnce()).findOne(anyInt());
    }


}








