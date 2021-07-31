package pckg_1;

public class ClientUser extends User {

    private String name;
    private int balance;

    public ClientUser(String name, String ID, String PIN, int balance) throws Exception {
        super(ID, PIN);

        if (name.matches("[a-zA-Z]{2,}(\\s[a-zA-Z]{2,})*") || name.matches("[a-zA-Z]{2,}(\\.[a-zA-Z]{2,})*")
                || name.matches("[a-zA-Z]{2,}(-[a-zA-Z]{2,})*") || name.matches("[a-zA-Z]{2,}(_[a-zA-Z]{2,})*")) {
            this.name = name;
        } else {
            throw new Exception("Invalid client name");
        }

        if (balance >= 0) {
            this.balance = balance;
        } else {
            throw new Exception("Invalid balance");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if (name.matches("[a-zA-Z]{2,}(\\s[a-zA-Z]{2,})*") || name.matches("[a-zA-Z]{2,}(\\.[a-zA-Z]{2,})*")
                || name.matches("[a-zA-Z]{2,}(-[a-zA-Z]{2,})*") || name.matches("[a-zA-Z]{2,}(_[a-zA-Z]{2,})*")) {
            this.name = name;
        } else {
            throw new Exception("Invalid client name");
        }
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) throws Exception {
        if (balance >= 0) {
            this.balance = balance;
        } else {
            throw new Exception("Invalid balance");
        }
    }

}
