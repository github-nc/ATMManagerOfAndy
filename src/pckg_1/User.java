package pckg_1;

public class User {

    private String ID;
    private String PIN;

    public User(String ID, String PIN) throws Exception {
        this.ID = ID;
        this.PIN = PIN;

        if (ID.matches("[0-9]{4}")) {
            this.ID = ID;
        } else {
            throw new Exception("Invalid user ID");
        }

        if (PIN.matches("[0-9]{6}")) {
            this.PIN = PIN;
        } else {
            throw new Exception("Invalid user passcode");
        }
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) throws Exception {

        if (ID.matches("[0-9]{4}")) {
            this.ID = ID;
        } else {
            throw new Exception("Invalid user ID");
        }
    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) throws Exception {
        if (PIN.matches("[0-9]{6}")) {
            this.PIN = PIN;
        } else {
            throw new Exception("Invalid user passcode");
        }
    }

}
