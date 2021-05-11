package au.edu.sydney.pac.erp.auth;

public interface AuthModule {
    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * <br>
     *
     * @param userName The username to validate. May not be null or empty.
     * @param password The password to validate. May not be null or empty.
     * @throws IllegalArgumentException If any of the preconditions are violated.
     * @return A valid AuthToken if the username and password validate correctly, otherwise null.
     */
    AuthToken login(String userName, String password) throws IllegalArgumentException;

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * <br>
     *
     * @param token The token to logout. May not be null.
     * @throws IllegalArgumentException If any of the preconditions are violated.
     */
    void logout(AuthToken token) throws IllegalArgumentException;

    /**
     * <b>Preconditions:</b><br>
     * <br>
     * <b>Postconditions:</b><br>
     * <br>
     *
     * @param token The token to authenticate. May not be null.
     * @return True if the given token is valid, otherwise false.
     * @throws IllegalArgumentException If any of the preconditions are violated.
     */
    boolean authenticate(AuthToken token) throws IllegalArgumentException;
}
