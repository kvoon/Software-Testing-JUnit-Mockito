package au.edu.sydney.pac.erp.print;

import au.edu.sydney.pac.erp.auth.AuthToken;

public interface PrintService {
    void printReport(AuthToken var1, String var2);
}
