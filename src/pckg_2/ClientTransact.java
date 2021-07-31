package pckg_2;

public class ClientTransact {

    public static int withdraw(int Bal, int Wthdr) throws Exception {

        if (Bal >= Wthdr) {
            Bal = Bal - Wthdr;
        } else {
            throw new Exception ("Process failed because of insuffcient balance!");
        }
        return Bal;
    }
    
    public static int deposit(int Bal, int Dep){
    Bal =  Bal + Dep;        
    return Bal;    
    }

}
