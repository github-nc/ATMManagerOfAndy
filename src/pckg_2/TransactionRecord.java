package pckg_2;

public class TransactionRecord {

    private String Time;
    private String ID;
    private String Type;
    private int Amount;

    public TransactionRecord(String Time, String ID, String Type, int Amount) throws Exception {
        this.Time = Time;
        this.ID = ID;
        this.Type = Type;
        if (Amount > 0) {
            this.Amount = Amount;
        } else {
            throw new Exception("Invalid record of transaction amount which should be a positive integer!\n");
        }
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String Time) {
        this.Time = Time;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int Amount) throws Exception {
        if (Amount > 0) {
            this.Amount = Amount;
        } else {
            throw new Exception("Invalid record of transaction amount which should be a positive integer!\n");
        }
    }

}
