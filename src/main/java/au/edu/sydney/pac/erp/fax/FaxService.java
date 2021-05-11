package au.edu.sydney.pac.erp.fax;


import au.edu.sydney.pac.erp.auth.AuthToken;

public interface FaxService {

    /**
     * In PAC-land phone numbers and fax numbers are the sme thing.<br>
     *
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * <br>
     *
     * @param token The authentication token to verify this operation with. May not be null.
     * @param phone The contact phone number to fax to. May not be null or empty. Must contain only numbers and +() characters.
     * @param data The data to be faxed. May not be null or empty.
     * @throws SecurityException If the authentication token is null or fails verification.
     * @throws IllegalArgumentException If any of the other preconditions are violated.
     */
    void faxReport(AuthToken token, String phone, String data) throws SecurityException, IllegalArgumentException;
}
