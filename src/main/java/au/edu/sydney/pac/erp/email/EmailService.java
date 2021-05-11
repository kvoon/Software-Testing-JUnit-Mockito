package au.edu.sydney.pac.erp.email;


import au.edu.sydney.pac.erp.auth.AuthToken;

public interface EmailService {

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * <br>
     *
     * @param token The authentication token to verify this operation with. May not be null.
     * @param email The contact email address to send to. May not be null or empty. Must contain at least one @ character.
     * @param data The data to be emailed. May not be null or empty.
     * @throws SecurityException If the authentication token is null or fails verification.
     * @throws IllegalArgumentException if any of the other preconditions are violated.
     */
    void printReport(AuthToken token, String email, String data) throws SecurityException, IllegalArgumentException;
}
