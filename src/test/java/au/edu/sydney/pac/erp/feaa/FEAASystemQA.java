package au.edu.sydney.pac.erp.feaa;

import au.edu.sydney.pac.erp.client.ClientFactory;
import org.junit.Before;
import org.junit.Test;
import au.edu.sydney.pac.erp.auth.AuthModule;
import au.edu.sydney.pac.erp.client.Client;
import au.edu.sydney.pac.erp.client.ClientList;
import static org.junit.Assert.*;
import au.edu.sydney.pac.erp.fax.FaxService;
import au.edu.sydney.pac.erp.print.PrintService;
import au.edu.sydney.pac.erp.reporting.ReportFacade;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;


public class FEAASystemQA {

    FEAAFacade feaaFacade;

    ClientList clientList;

    ReportFacade reportProvider;

    PrintService printProvider;

    FaxService faxProvider;

    FEAATestFactory testFactory;

    AuthModule authProvider;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUp(){

        this.testFactory = new FEAATestFactory();
        this.feaaFacade = new FEAAFacadeImpl();

        this.clientList = testFactory.makeClientModule();
        
        this.authProvider = testFactory.makeAuthModule();

        this.faxProvider = testFactory.makeFaxService();

        this.printProvider = testFactory.makePrintService();

        this.reportProvider = testFactory.makeReportFacade(authProvider);

        feaaFacade.setClientProvider(clientList);

        feaaFacade.setAuthProvider(authProvider);

        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

    }

    @Test
    public void QASystemTest1(){

        feaaFacade.login("Terry Gilliam","hunter2");

        feaaFacade.addClient("Bob","Dylan","12345678");

        feaaFacade.assignClient(1,"INTERNATIONAL");

        List<Client> list =feaaFacade.getAllClients();

        assertEquals(1, list.size());

        assertTrue(list.get(0).isAssigned());

        assertEquals("INTERNATIONAL",list.get(0).getDepartmentCode());

        feaaFacade.logout();

    }

    @Test
    public void QASystemTest2(){

        feaaFacade.login("Terry Gilliam","hunter2");

        feaaFacade.addClient("Bob","Dylan","12345678");

        feaaFacade.addAccount(1,1,"Bob's Acc",200,100,"12345678","bob@emai.com");

        feaaFacade.setReportPreferences(1,false,true,true);

        feaaFacade.setReportingProvider(reportProvider);

        feaaFacade.setPrintProvider(printProvider);

        feaaFacade.setFaxProvider(faxProvider);

        int report = feaaFacade.makeReport(1);

        assertEquals(feaaFacade.getTotalLifetimeCommission(1),report );

        String str1 = "Printing that report!";
        String str2 =  "Faxing that report!";

        assertEquals(str1+str2, outContent.toString().replace("\n","").replace("\r",""));

        feaaFacade.logout();

    }
    
}