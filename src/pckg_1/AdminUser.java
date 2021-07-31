package pckg_1;

public class AdminUser extends User {

    private String name;

    public AdminUser(String name, String ID, String PIN) throws Exception {
        super(ID, PIN);

        if (name.matches("[a-zA-Z]{2,}(\\s[a-zA-Z]{2,})*") || name.matches("[a-zA-Z]{2,}(\\.[a-zA-Z]{2,})*")
                || name.matches("[a-zA-Z]{2,}(-[a-zA-Z]{2,})*") || name.matches("[a-zA-Z]{2,}(_[a-zA-Z]{2,})*")) {
            this.name = name;
        } else {
            throw new Exception("Invalid administrator name");
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
            throw new Exception("Invalid administrator name");
        }
    }

}
