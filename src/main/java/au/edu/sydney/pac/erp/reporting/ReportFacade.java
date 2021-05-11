package au.edu.sydney.pac.erp.reporting;


import au.edu.sydney.pac.erp.auth.AuthToken;
import java.math.BigDecimal;

public interface ReportFacade {
    BigDecimal makeReport(AuthToken var1, int var2, String var3, String var4, int var5);

    BigDecimal getLifetimeCommission(AuthToken var1, int var2);
}
