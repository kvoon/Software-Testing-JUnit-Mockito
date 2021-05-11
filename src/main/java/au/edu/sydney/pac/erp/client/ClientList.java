package au.edu.sydney.pac.erp.client;

import java.util.List;

/**
 * This class provides access to Client module services to a consumer. Creates, stores, and manages Clients.
 */
public interface ClientList {

    /**
     * Creates and adds a new client to the list.<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * An unassigned client with the given details will be added to the list.<br>
     *
     * @param id The id of the client to add. Must be positive, may not be zero. Must be unique in this list.
     * @param firstName The first name of the client to add. May not be null or empty.
     * @param lastName The last name of the client to add. May not be null or empty.
     * @param phoneNumber The phone number of the client to add. May not be null or empty.
     * @return The client that was created and added.
     * @throws IllegalStateException If a client with the provided ID already exists in this list.
     * @throws IllegalArgumentException If any argument requirements are breached
     */
    Client addClient(int id, String firstName, String lastName, String phoneNumber) throws IllegalStateException, IllegalArgumentException;

    /**
     * Empties the list.<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * The list will be empty.<br>
     */
    void clear();

    /**
     * Gets all clients in the list.<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * None<br>
     *
     * @return A list of all clients in the list. Will not be null. Will be empty if no clients exist.
     */
    List<Client> findAll();

    /**
     * Gets all clients matching the given assignment status in the list.<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * None<br>
     *
     * @param assigned The assignment status to match.
     * @return A list of all clients with a matching department assignment status. Will not be null. Will be empty if no clients match.
     */
    List<Client> findAll(boolean assigned);

    /**
     * Gets all clients matching the given department in the list.<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * None<br>
     *
     * @param departmentCodes The department code(s) to match.
     * @return A list of all clients assigned to the given departments. Will not be null. Will be empty if no clients match.
     */
    List<Client> findAll(String... departmentCodes);

    /**
     * Retrieves a specific client by id.<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * None<br>
     *
     * @param id The client id to retrieve. Must be positive, may not be zero.
     * @return The matching client, or null if no match found.
     * @throws IllegalArgumentException If any argument requirements are breached
     */
    Client findOne(int id) throws IllegalArgumentException;

    /**
     * Removes a specific client by id.<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * Any client matching the given ID will no longer exist in the list.<br>
     *
     * @param id The client id to remove. Must be positive, may not be zero.
     * @return True if a match was removed, otherwise false.
     * @throws IllegalArgumentException If any argument requirements are breached
     */
    boolean remove(int id) throws IllegalArgumentException;
}
