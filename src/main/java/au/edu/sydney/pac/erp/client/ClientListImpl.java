package au.edu.sydney.pac.erp.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ClientListImpl implements ClientList {
    private List<Client> clients = new ArrayList<>();

    public ClientListImpl() {
    }

    public Client addClient(int id, String firstName, String lastName, String phoneNumber) throws IllegalStateException,IllegalArgumentException{
        if (id < 1) {
            throw new IllegalArgumentException("ID must be greater than zero");
        } else if (this.hasID(id) == true) {
            throw new IllegalStateException("Duplicate ID found");
        } else if (firstName != null && firstName.isEmpty() == false) {
            if (lastName != null && lastName.isEmpty() == false) {
                if (phoneNumber != null && phoneNumber.isEmpty() == false) {
                    Client newClient = new ClientImpl(id, firstName, lastName, phoneNumber);
                    this.clients.add(newClient);
                    return newClient;
                } else {
                    throw new IllegalArgumentException("Phone number may not be null or empty");
                }
            } else {
                throw new IllegalArgumentException("Last name may not be null or empty");
            }
        } else {
            throw new IllegalArgumentException("First name may not be null or empty");
        }
    }

    public void clear() {
        this.clients.clear();
    }

    public List<Client> findAll() {
        List<Client> immutableList = Collections.unmodifiableList(this.clients);
        return immutableList;
    }

    public List<Client> findAll(boolean toAssign) {
        List<Client> res = new ArrayList<Client>();

        for (Client client : this.clients) {
            if (toAssign == client.isAssigned()) {
                res.add(client);
            }
        }

        return res;
    }

    public List<Client> findAll(String... departmentCodes) {
        List<Client> res = new ArrayList<>();
        if (departmentCodes != null) {
            for (String department : departmentCodes) {
                if (department != null) {

                    for (Client client : this.clients) {
                        if (department.equals(client.getDepartmentCode())) {
                            res.add(client);
                        }
                    }
                }
            }

        }
        return res;
    }

    public Client findOne(int id) throws IllegalArgumentException{
        if (id < 1) {
            throw new IllegalArgumentException("ID must be greater than zero");
        } else {
            Client res = null;

            for (Client client : this.clients) {
                if (id == client.getID()) {
                    res = client;
                }
            }

            return res;
        }
    }

    public boolean remove(int id) throws IllegalArgumentException{

        if (id < 1) {
            throw new IllegalArgumentException("ID must be greater than zero");
        } else {
            Client res = null;

            for (Client client : this.clients) {
                if (id == client.getID()) {
                    res = client;
                }
            }

            if (res != null) {
                this.clients.remove(res);
            }

            return res != null;
        }
    }

    private boolean hasID(int id) {

        Client client;

        Iterator<Client> temp = this.clients.iterator();

        do {
            if (temp.hasNext() == false) {
                return false;
            }

            client = temp.next();

        } while(client.getID() != id);

        return true;
    }
}
