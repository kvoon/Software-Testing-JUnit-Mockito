package au.edu.sydney.pac.erp.client;

/**
 * This class represents a client of PAC
 */
public interface Client {

    /**
     * Assigns this client to a given department<br><br>
     * <b>Preconditions:</b><br>
     * The client may not have already been assigned to a department<br>
     * <b>Postconditions:</b><br>
     * The client will be assigned to the given department<br>
     *
     * @param departmentCode The department code to assign. May not be empty or null.
     * @throws IllegalStateException If this client has already been assigned to a department.
     * @throws IllegalArgumentException If any argument requirements are breached
     */
    void assignDepartment(String departmentCode) throws IllegalStateException, IllegalArgumentException;

    /**
     * Simple accessor for client assignment state.<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * None<br>
     *
     * @return True if the client has been assigned to a department, otherwise false.
     */
    boolean isAssigned();

    /**
     * Simple accessor for client assigned department<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * None<br>
     *
     * @return The department code the client has been assigned to. Will be null if unassigned.
     */
    String getDepartmentCode();

    /**
     * Simple accessor for client id<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * None<br>
     *
     * @return The client ID. Will not be negative or zero.
     */
    int getID();

    /**
     * Simple accessor for client first name<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * None<br>
     *
     * @return The client first name. Will not be null or empty.
     */
    String getFirstName();

    /**
     * Simple accessor for client last name<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * None<br>
     *
     * @return The client last name. Will not be null or empty.
     */
    String getLastName();

    /**
     * Simple accessor for client phone number<br><br>
     * <b>Preconditions:</b><br>
     * None<br>
     * <b>Postconditions:</b><br>
     * None<br>
     *
     * @return The client phone number. Will not be null or empty.
     */
    String getPhoneNumber();
}
