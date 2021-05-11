package au.edu.sydney.pac.erp.feaa;

import au.edu.sydney.pac.erp.auth.AuthModule;
import au.edu.sydney.pac.erp.client.Client;
import au.edu.sydney.pac.erp.client.ClientList;
import au.edu.sydney.pac.erp.email.EmailService;
import au.edu.sydney.pac.erp.fax.FaxService;
import au.edu.sydney.pac.erp.reporting.ReportFacade;
import au.edu.sydney.pac.erp.print.PrintService;

import java.util.List;

/**
 * The main access view for users of the FEAA module.
 */
public interface FEAAFacade {
    void setClientProvider(ClientList var1);

    void setAuthProvider(AuthModule var1);

    void setEmailProvider(EmailService var1);

    void setFaxProvider(FaxService var1);

    void setReportingProvider(ReportFacade var1);

    void setPrintProvider(PrintService var1);

    Client addClient(String var1, String var2, String var3);

    void assignClient(int var1, String var2);

    List viewAllClients();

    List getAllClients();

    int addAccount(Integer var1, int var2, String var3, int var4, int var5, String var6, String var7);

    List getAccounts();

    List getAccounts(int var1);

    String getAccountName(int var1);

    int getAccountBalance(int var1);

    int getAccountIncomings(int var1);

    int getAccountOutgoings(int var1);

    void setAccountIncomings(int var1, int var2);

    void setAccountOutgoings(int var1, int var2);

    void setReportPreferences(int var1, boolean var2, boolean var3, boolean var4);

    int makeReport(int var1);

    int getTotalLifetimeCommission(int var1);

    boolean login(String var1, String var2);

    void logout();
}
