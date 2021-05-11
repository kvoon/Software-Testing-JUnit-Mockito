package au.edu.sydney.pac.erp.client;

public class ClientImpl implements Client {
        private final int id;
        private final String firstName;
        private final String lastName;
        private final String phoneNumber;
        private String departmentCode;
        private boolean isAssigned = false;

        public ClientImpl(int id, String firstName, String lastName, String phoneNumber) throws IllegalArgumentException {
            if (id < 1) {
                throw new IllegalArgumentException("ID must be greater than 0");
            } else if (firstName != null && firstName.isEmpty() == false) {
                if (lastName != null && !lastName.isEmpty()) {
                    if (phoneNumber != null && phoneNumber.isEmpty() == false) {
                        this.id = id;
                        this.firstName = firstName;
                        this.lastName = lastName;
                        this.phoneNumber = phoneNumber;
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

        public void assignDepartment(String departmentCode) throws IllegalArgumentException, IllegalStateException {
            if (this.isAssigned) {
                throw new IllegalStateException("Already assigned.");
            } else if (departmentCode != null && departmentCode.isEmpty() == false) {
                this.isAssigned = true;
                this.departmentCode = departmentCode;
            } else {
                throw new IllegalArgumentException("Department code may not be null or empty");
            }
        }

        public int getID() {
        return this.id;
    }

        public String getFirstName() {
        return this.firstName;
    }

        public String getLastName() {
        return this.lastName;
    }

        public String getPhoneNumber() {
        return this.phoneNumber;
    }

        public boolean isAssigned() {
            return this.isAssigned;
        }

        public String getDepartmentCode() {
            return this.departmentCode;
        }

    }

