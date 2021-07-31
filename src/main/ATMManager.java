package main;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import pckg_1.AdminUser;
import pckg_1.ClientUser;
import pckg_1.User;
import pckg_2.ClientTransact;
import pckg_2.TransactionRecord;

public class ATMManager {

    private static final ArrayList<User> allUsers = new ArrayList<>();
    private static final ArrayList<ClientUser> clientUsers = new ArrayList<>();
    private static final ArrayList<TransactionRecord> TransactRecords = new ArrayList<>();

    private static final File exLog = new File("ExceptionLog.txt");

    public static void main(String[] args) {

        System.out.println("\nWelcome to use this ATM manager program!\n"
                + "\nIO&Rumtime exception messages will be logged in the file located by the path "
                + "\"" + exLog.getAbsolutePath() + "\"\n");

        PrintWriter exceptionLog = null;
        Console kbd0 = System.console();
        try {
            if (!exLog.exists()) {
                exLog.createNewFile();
            }
            exceptionLog = new PrintWriter(exLog);
        } catch (IOException ex0) {
            System.out.println("An error occured while configurating the exception logging file!\n"
                    + ex0.getMessage()
                    + "\nContinue without logging exceptions!");
            kbd0.readLine("Press Enter to continue ...");
        }

        boolean mainCycle = true;
        int clientCounter = 0;
        try {
            allUsers.add(new AdminUser("Admin", "0001", "012345"));
            allUsers.add(new ClientUser("TestClientA", "1001", "123456", 1000));
            allUsers.add(new ClientUser("TestClientB", "1002", "234567", 200));
            clientUsers.add(new ClientUser("TestClientA", "1001", "123456", 1000));
            clientUsers.add(new ClientUser("TestClientB", "1002", "234567", 200));
            clientCounter = 1002;

            System.out.println("1 administrator account and 2 client accounts have been configured for testing.");
        } catch (Exception ex1) {
            System.out.println("An error occured while configuring testing accounts, and has led to program termination!\n"
                    + "The program need to be restarted for testing functional integrity.\n");
            ex1.printStackTrace(System.err);
            System.out.println();
            ex1.printStackTrace(exceptionLog);
            exceptionLog.print("\r\n");
            exceptionLog.flush();
            mainCycle = false;
        }

        while (mainCycle == true) {
            String input1 = "";
            Console kbd1 = System.console();
            input1 = kbd1.readLine("\n"
                    + "+--------------------+\n"
                    + "|Press Enter to start|\n"
                    + "|Type exit to quit   |\n"
                    + "+--------------------+\n");
            input1 = input1.trim();
            if (input1.equals("exit")) {
                break;
            } else if (input1.equals("")) {
                int inputTimes = 0;
                boolean usrValidity = false;
                do {
                    inputTimes += 1;
                    if (inputTimes > 3) {
                        Console kbd2 = System.console();
                        kbd2.readLine("Oops, your authentication failed 3 times!\n"
                                + "The ATM manager will redirect you to the START menu.\n"
                                + "\nPress Enter to continue ...");
                        break;
                    }
                    Console kbd3 = System.console();
                    String input3 = "";
                    input3 = kbd3.readLine("Please type in your ID ...\n");
                    input3 = input3.trim();
                    Console kbd4 = System.console();
                    String input4 = "";
                    input4 = kbd4.readLine("Please type in your PIN code ...\n");
                    input4 = input4.trim();

                    Console kbd5 = System.console();
                    String input5 = "";
                    input5 = kbd5.readLine("\nPress Enter directly to proceed user authentication (up to 3 times) ...\n"
                            + "Or type any character(s) like letter, number, space or punctuation to go back to the START menu ...\n");
                    if (input5.equals("")) {

                        int i = 0; // comparison match counter
                        for (User usr : allUsers) {
                            i = 0;
                            if (usr.getID().equals(input3)) {
                                i += 1;
                                if (usr.getPIN().equals(input4)) {
                                    i += 1;
                                } else {
                                    break;
                                }
                            }
                            if (i == 2) {
                                usrValidity = true;
                                break;
                            }
                        }
                        if (input3.equals("0001") && (input4.equals("012345"))) {
                            i += 1;
                        }

                        switch (i) {
                            case 0: // no user id matched
                                System.out.println("Sorry, the user ID you typed does not exist!\n"
                                        + "You need to retype your user ID and PIN code later.\n");
                                break;

                            case 1: // user id matched, but PIN not matched
                                System.out.println("Sorry, the PIN code which you typed doesn't match the user ID which you typed!\n"
                                        + "You need to retype your user ID and PIN code later.\n");
                                break;

                            case 2:  // correct id and PIN for client user
                                System.out.println("Client user passed authentication!\n");

                                boolean clientMenu = true;
                                do {
                                    Console kbd6 = System.console();
                                    String input6 = "";
                                    input6 = kbd6.readLine("\n"
                                            + "Type an integer to make a selection from the client transaction menu ...\n"
                                            + "+--------------------------------------------------+\n"
                                            + "| 1--> withdraw                                    |\n"
                                            + "| 2--> deposit                                     |\n"
                                            + "| 3--> transfer                                    |\n"
                                            + "| 4--> check balance                               |\n"
                                            + "| 5--> list transactions                           |\n"
                                            + "| 6--> finish                                      |\n"
                                            + "| Any other input will lead to redisplay this menu |\n"
                                            + "+--------------------------------------------------+\n"
                                    );
                                    input6 = input6.trim();

                                    switch (input6) {
                                        case "1":          // start withdraw
                                            Console kbd7 = System.console();
                                            String input7 = "";
                                            input7 = kbd7.readLine("\nPlease type a postive integer as the amount you expect to withdraw ...\n"
                                                    + "+--------------------------------------------------+\n"
                                                    + "| Amount                                           |\n"
                                                    + "+--------------------------------------------------+\n"
                                            );
                                            input7 = input7.trim();
                                            try {
                                                int wthdrAmount = Integer.parseInt(input7);
                                                if (wthdrAmount <= 0) {
                                                    Console kbd8 = System.console();
                                                    String input8 = "";
                                                    kbd8.readLine("\nError: withdraw amount can not be <=0 .\n"
                                                            + "The ATM manager will redirect you to the client process menu.\n"
                                                            + "Press Enter to continue ...");
                                                    break;
                                                } else {
                                                    int remaining1;
                                                    for (ClientUser clientUsr : clientUsers) {
                                                        if (clientUsr.getID().equals(input3)) {
                                                            remaining1 = clientUsr.getBalance();
                                                            remaining1 = ClientTransact.withdraw(remaining1, wthdrAmount);
                                                            System.out.println("\nSuccessful withdrawal!\n");
                                                            System.out.println("The balance of the account with ID " + input3 + " is " + remaining1);
                                                            clientUsr.setBalance(remaining1);
                                                            Console kbd37 = System.console();
                                                            kbd37.readLine("Press Enter to continue ...\n");

                                                            Date transactTime1 = new Date();
                                                            SimpleDateFormat fmt = new SimpleDateFormat("yyy/MM/dd HH:mm:ss");
                                                            String time1 = fmt.format(transactTime1);
                                                            String ID1 = input3;
                                                            String type1 = "W";
                                                            int transactAmount1 = wthdrAmount;
                                                            TransactRecords.add(new TransactionRecord(time1, ID1, type1, transactAmount1));

                                                            break;
                                                        }
                                                    }
                                                }
                                            } catch (Exception ex2) {
                                                System.out.println("\nFailed to withdraw money!");
                                                ex2.printStackTrace(System.err);
                                                System.out.println();
                                                ex2.printStackTrace(exceptionLog);
                                                exceptionLog.print("\r\n");
                                                exceptionLog.flush();
                                                Console kbd38 = System.console();
                                                kbd38.readLine("Press Enter to continue ...");
                                            }
                                            break;

                                        case "2":        // start deposit
                                            Console kbd9 = System.console();
                                            String input9 = "";
                                            input9 = kbd9.readLine("\nPlease type an postive integer as the amount you expect to deposit ...\n"
                                                    + "+--------------------------------------------------+\n"
                                                    + "| Amount                                           |\n"
                                                    + "+--------------------------------------------------+\n"
                                            );
                                            input9 = input9.trim();
                                            try {
                                                int depAmount = Integer.parseInt(input9);
                                                if (depAmount <= 0) {
                                                    Console kbd10 = System.console();
                                                    String input10 = "";
                                                    kbd10.readLine("\nError: deposit amount can not be <=0 .\n"
                                                            + "The ATM manager will redirect you to the client process menu.\n"
                                                            + "Press Enter to continue ...");
                                                    break;
                                                } else {
                                                    int remaining2;
                                                    for (ClientUser clientUsr : clientUsers) {
                                                        if (clientUsr.getID().equals(input3)) {
                                                            remaining2 = clientUsr.getBalance();
                                                            remaining2 = ClientTransact.deposit(remaining2, depAmount);
                                                            System.out.println("\nSuccessfully deposited!\n"
                                                                    + "The balance of the account with ID " + clientUsr.getID() + " is " + remaining2);
                                                            clientUsr.setBalance(remaining2);
                                                            Console kbd35 = System.console();
                                                            kbd35.readLine("Press Enter to continue ...");

                                                            Date transactTime2 = new Date();
                                                            SimpleDateFormat fmt = new SimpleDateFormat("yyy/MM/dd HH:mm:ss");
                                                            String time2 = fmt.format(transactTime2);
                                                            String ID2 = input3;
                                                            String type2 = "D";
                                                            int transactAmount2 = depAmount;
                                                            TransactRecords.add(new TransactionRecord(time2, ID2, type2, transactAmount2));

                                                            break;
                                                        }
                                                    }
                                                }
                                            } catch (Exception ex3) {
                                                System.out.println("\nFailed to deposit money!");
                                                ex3.printStackTrace(System.err);
                                                System.out.println();
                                                ex3.printStackTrace(exceptionLog);
                                                exceptionLog.print("\r\n");
                                                exceptionLog.flush();
                                            }
                                            break;

                                        case "3":         // start transfer
                                            boolean retypeAmount = false;
                                            int tranAmount = 0;
                                            do {
                                                Console kbd11 = System.console();
                                                String input11 = "";
                                                input11 = kbd11.readLine("\nPlease type an postive integer as the amount you expect to transfer ...\n"
                                                        + "+--------------------------------------------------+\n"
                                                        + "| Amount                                           |\n"
                                                        + "+--------------------------------------------------+\n"
                                                );
                                                input11 = input11.trim();
                                                try {
                                                    tranAmount = Integer.parseInt(input11);
                                                    if (tranAmount <= 0) {
                                                        retypeAmount = true;
                                                        System.out.println("\nError: transfer amount can not be <=0 .\n");
                                                        Console kbd12 = System.console();
                                                        String input12 = "";
                                                        kbd12.readLine("You need correct your transfer amount!\n"
                                                                + "Press Enter to continue ...");
                                                    } else {
                                                        retypeAmount = false;
                                                    }
                                                } catch (Exception ex4) {
                                                    System.out.println();
                                                    ex4.printStackTrace(System.err);
                                                    System.out.println();
                                                    ex4.printStackTrace(exceptionLog);
                                                    exceptionLog.print("\r\n");
                                                    exceptionLog.flush();
                                                    retypeAmount = true;
                                                }

                                            } while (retypeAmount);

                                            boolean retypePayeeID = false;
                                            do {
                                                Console kbd13 = System.console();
                                                String input13 = "";
                                                input13 = kbd13.readLine("\n"
                                                        + "You may type \"exit\" to cancel the attempt of current transaction ...\n"
                                                        + "Or type the recipient's user ID to proceed this money tranfer ...\n"
                                                        + "+--------------------------------------------------+\n"
                                                        + "| Payee ID                                         |\n"
                                                        + "+--------------------------------------------------+\n"
                                                );
                                                input13 = input13.trim();
                                                int j = 0;
                                                if (input13.equals("exit")) {
                                                    break;
                                                } else {
                                                    for (ClientUser clientUsr : clientUsers) {
                                                        j = 0;
                                                        if (clientUsr.getID().equals(input13)) {
                                                            if (input13.equals(input3)) {
                                                                j += 1;
                                                            } else {
                                                                j += 2;
                                                            }
                                                            break;
                                                        }
                                                    }
                                                }

                                                switch (j) {
                                                    case 0:
                                                        retypePayeeID = true;
                                                        System.out.println("\nError: the typed payee ID doesn't exist in the client list.\n");
                                                        Console kbd14 = System.console();
                                                        String input14 = "";
                                                        kbd14.readLine("You need retype a new payee ID.\n"
                                                                + "Press Enter to continue ...");

                                                        break;

                                                    case 1:
                                                        retypePayeeID = true;
                                                        System.out.println("\nError: the typed payee ID is same as the payer ID.\n");
                                                        Console kbd15 = System.console();
                                                        String input15 = "";
                                                        kbd15.readLine("You need retype a new payee ID.\n"
                                                                + "Press Enter to continue ...");
                                                        break;

                                                    case 2:
                                                        retypePayeeID = false;

                                                        int remaining3;
                                                        boolean transOK = false;
                                                        for (ClientUser clientUsr : clientUsers) {
                                                            if (clientUsr.getID().equals(input3)) {
                                                                try {
                                                                    remaining3 = clientUsr.getBalance();
                                                                    remaining3 = ClientTransact.withdraw(remaining3, tranAmount);
                                                                    clientUsr.setBalance(remaining3);
                                                                    System.out.println("\nThe transfer has been successfully completed!\n\n"
                                                                            + "Account ID: " + clientUsr.getID() + "\n"
                                                                            + "+--------------------------------------------------+\n"
                                                                            + "| Current balance is " + String.format("%-30s", clientUsr.getBalance()) + "|\n"
                                                                            + "+--------------------------------------------------+\n");
                                                                    transOK = true;
                                                                    Console kbd32 = System.console();
                                                                    kbd32.readLine("Press Enter to continue ...");

                                                                    Date transactTime3 = new Date();
                                                                    SimpleDateFormat fmt = new SimpleDateFormat("yyy/MM/dd HH:mm:ss");
                                                                    String time3 = fmt.format(transactTime3);
                                                                    String ID3 = input3;
                                                                    String type3 = "T";
                                                                    int transactAmount3 = tranAmount;
                                                                    TransactRecords.add(new TransactionRecord(time3, ID3, type3, transactAmount3));

                                                                } catch (Exception ex5) {
                                                                    System.out.println();
                                                                    ex5.printStackTrace(System.err);
                                                                    System.out.println();
                                                                    ex5.printStackTrace(exceptionLog);
                                                                    exceptionLog.print("\r\n");
                                                                    exceptionLog.flush();
                                                                    transOK = false;
                                                                }

                                                                break;
                                                            }
                                                        }

                                                        if (transOK) {
                                                            int remaining4;
                                                            for (ClientUser clientUsr : clientUsers) {
                                                                if (clientUsr.getID().equals(input13)) {
                                                                    try {
                                                                        remaining4 = clientUsr.getBalance();
                                                                        remaining4 = ClientTransact.deposit(remaining4, tranAmount);
                                                                        clientUsr.setBalance(remaining4);

                                                                        Date transactTime4 = new Date();
                                                                        SimpleDateFormat fmt = new SimpleDateFormat("yyy/MM/dd HH:mm:ss");
                                                                        String time4 = fmt.format(transactTime4);
                                                                        String ID4 = input13;
                                                                        String type4 = "R";
                                                                        int transactAmount4 = tranAmount;
                                                                        TransactRecords.add(new TransactionRecord(time4, ID4, type4, transactAmount4));
                                                                    } catch (Exception ex6) {
                                                                        System.out.println();
                                                                        ex6.printStackTrace(System.err);
                                                                        System.out.println();
                                                                        ex6.printStackTrace(exceptionLog);
                                                                        exceptionLog.print("\r\n");
                                                                        exceptionLog.flush();
                                                                    }
                                                                    break;
                                                                }
                                                            }

                                                        } else {
                                                            System.out.println("The transfer can't be completed because of insuffcient balance!");
                                                            Console kbd36 = System.console();
                                                            kbd36.readLine("Press Enter to continue ...\n");
                                                        }
                                                }
                                            } while (retypePayeeID);

                                            break;

                                        case "4":    // check balance
                                            for (ClientUser clientUsr : clientUsers) {
                                                if (clientUsr.getID().equals(input3)) {
                                                    System.out.println("\nAccount ID: " + clientUsr.getID() + "\n"
                                                            + "+--------------------------------------------------+\n"
                                                            + "| Current balance is " + String.format("%-30s", clientUsr.getBalance()) + "|\n"
                                                            + "+--------------------------------------------------+\n");
                                                    break;
                                                }
                                            }
                                            Console kbd33 = System.console();
                                            kbd33.readLine("Press Enter to continue ...");
                                            break;

                                        case "5":   //list transaction records
                                            System.out.println("\nThe transaction records of the account with ID " + input3 + "\n"
                                                    + "+--------------------------------------------------+\n"
                                                    + "| Date                   | Type | Amount           |\n"
                                                    + "+--------------------------------------------------+"
                                            );
                                            for (TransactionRecord Record : TransactRecords) {
                                                if (Record.getID().equals(input3)) {
                                                    System.out.println("| " + String.format("%-23s", Record.getTime())
                                                            + "| " + String.format("%-5s", Record.getType())
                                                            + "| " + String.format("%-17s", Record.getAmount())
                                                            + "|");
                                                }
                                            }
                                            System.out.println("+--------------------------------------------------+\n");
                                            Console kbd34 = System.console();
                                            kbd34.readLine("Press Enter to continue ...");
                                            break;

                                        case "6":   // finish and go back to START menu
                                            clientMenu = false;

                                            break;

                                        default:
                                            break;

                                    }

                                } while (clientMenu);

                                break;

                            case 3: // correct id and PIN for admin 
                                System.out.println("Admin user passed authentication!");

                                boolean adminMenu = true;
                                do {
                                    Console kbd16 = System.console();
                                    String input16 = "";
                                    input16 = kbd16.readLine("\n"
                                            + "Type an integer to make a selection from the administrator menu ...\n"
                                            + "+--------------------------------------------------+\n"
                                            + "| 1--> add account                                 |\n"
                                            + "| 2--> delete account                              |\n"
                                            + "| 3--> list accounts                               |\n"
                                            + "| 4--> dump info to a file                         |\n"
                                            + "| 5--> finish                                      |\n"
                                            + "| Any other input will lead to redisplay this menu |\n"
                                            + "+--------------------------------------------------+\n"
                                    );
                                    input16 = input16.trim();

                                    switch (input16) {
                                        case "1":   // add client account
                                            Console kbd17 = System.console();
                                            String input17 = "";
                                            input17 = kbd17.readLine("\nPlease type username for the client account you expect to add ...\n"
                                                    + "+--------------------------------------------------+\n");

                                            Console kbd18 = System.console();
                                            String input18 = "";
                                            input18 = kbd18.readLine("\nPlease type passcode for the client account you expect to add ...\n"
                                                    + "+--------------------------------------------------+\n");

                                            clientCounter++;
                                            String newClientID = String.valueOf(clientCounter);
                                            try {
                                                allUsers.add(new ClientUser(input17, newClientID, input18, 0));
                                                clientUsers.add(new ClientUser(input17, newClientID, input18, 0));
                                                System.out.println("\nSuccessfully added the expected client account.\n"
                                                        + "\nThe information of the new client account\n"
                                                        + "+--------------------------------------------------+\n"
                                                        + "Account ID: " + newClientID + "\n"
                                                        + "Username: " + input17 + "\n"
                                                        + "Passcode: " + input18 + "\n"
                                                        + "Balance: " + 0 + "\n\n");
                                                Console kbd19 = System.console();
                                                String input19 = "";
                                                input19 = kbd19.readLine("Press Enter to go back to the administrator menu ...\n");

                                            } catch (Exception ex7) {
                                                System.out.println("\nFailed to add the expected new client account!\n");
                                                ex7.printStackTrace(System.err);
                                                System.out.println();
                                                ex7.printStackTrace(exceptionLog);
                                                exceptionLog.print("\r\n");
                                                exceptionLog.flush();
                                                clientCounter--;
                                                Console kbd40 = System.console();
                                                kbd40.readLine("Press Enter to continue ...");
                                            }

                                            break;

                                        case "2":   // delete client account
                                            Console kbd20 = System.console();
                                            String input20 = "";
                                            input20 = kbd20.readLine("\nPlease type the ID of the client account you expect to delete ...\n");
                                            input20 = input20.trim();

                                            if (input20.equals("0001")) {
                                                System.out.println("\nSorry, ID 0001 belongs to the administrator account which can't be deleted!");
                                                Console kbd29 = System.console();
                                                kbd29.readLine("Press Enter to continue ...");
                                            } else {
                                                boolean foundClientID = false;
                                                for (User usr : allUsers) {
                                                    if (usr.getID().equals(input20)) {
                                                        foundClientID = true;
                                                        int m = allUsers.indexOf(usr);
                                                        allUsers.remove(m);
                                                        break;
                                                    }
                                                }
                                                for (ClientUser clientUsr : clientUsers) {
                                                    if (clientUsr.getID().equals(input20)) {
                                                        int n = clientUsers.indexOf(clientUsr);
                                                        clientUsers.remove(n);
                                                        Console kbd21 = System.console();
                                                        String input21 = "";
                                                        input21 = kbd21.readLine("\nSuccessfully deleted the client account of ID " + input20 + " .\n"
                                                                + "Press Enter to continue ...");
                                                        break;
                                                    }
                                                }

                                                if (!foundClientID) {
                                                    System.out.println("\nSorry, the ID you typed doesn't exist in the client list!");
                                                    Console kbd39 = System.console();
                                                    kbd39.readLine("Press Enter to continue ...\n");
                                                }

                                            }

                                            break;

                                        case "3":   // list client account
                                            System.out.println("\n\nThe information of administrator user\n"
                                                    + "+--------------------------------------------------+\n"
                                                    + "| ID      | Name              | Balance            |\n"
                                                    + "+--------------------------------------------------+\n"
                                                    + "| 0001    | Admin             |                    |\n"
                                                    + "+--------------------------------------------------+\n");
                                            System.out.println("The information of all client users\n"
                                                    + "+--------------------------------------------------+\n"
                                                    + "| ID      | Name              | Balance            |\n"
                                                    + "+--------------------------------------------------+\n");
                                            for (ClientUser clientUsr : clientUsers) {
                                                System.out.println("| " + String.format("%-8s", clientUsr.getID())
                                                        + "| " + String.format("%-18s", clientUsr.getName())
                                                        + "| " + String.format("%-19s", clientUsr.getBalance())
                                                        + "|");
                                            }
                                            System.out.println("+--------------------------------------------------+\n");
                                            Console kbd28 = System.console();
                                            kbd28.readLine("Press Enter to continue ...");

                                            break;

                                        case "4":   // dump information into a file                                            
                                            String input22 = "";   // start to dump accounts information
                                            Console kbd22 = System.console();
                                            input22 = kbd22.readLine("\nPress Enter directly to start dumping accounts information into txt file ...\n"
                                                    + "Or type any character(s) like letter, number, space or punctuation to skip this step ...\n");

                                            if (input22.equals("")) {
                                                boolean input23isOK = false;
                                                int input23Times = 0;
                                                String input23 = "";
                                                do {
                                                    input23Times++;
                                                    if (input23Times > 3) {
                                                        System.out.println("\nOops, invalid file name typing exceeded 3 time!\n"
                                                                + "The step of dumping accounts information has been skipped.\n"
                                                                + "You can try it again later.\n");
                                                        break;
                                                    }
                                                    Console kbd23 = System.console();
                                                    input23 = kbd23.readLine("\nInput a name including .txt suffix to create a file for dumping accounts information ...\n");
                                                    input23 = input23.trim();
                                                    if (input23.matches("[a-zA-Z0-9]{1,}([-_\\s]*[a-zA-Z0-9]{1,})*\\.txt")) {
                                                        input23isOK = true;
                                                    } else {
                                                        System.out.println("\nSorry, the previously typed file name is not appropriate to use!\n"
                                                                + "You need to retype a new file name including .txt suffix.");
                                                    }
                                                } while (!input23isOK);

                                                if (input23Times < 4) {
                                                    File accountsInfo = new File(input23);
                                                    PrintWriter dumpAccountsInfo = null;
                                                    Console kbd24 = System.console();
                                                    try {
                                                        if (!accountsInfo.exists()) {
                                                            accountsInfo.createNewFile();
                                                        }
                                                        dumpAccountsInfo = new PrintWriter(accountsInfo);
                                                    } catch (IOException ex8) {
                                                        System.out.println("An error occured while configuring the dump file for accounts information!");
                                                        ex8.printStackTrace(System.err);
                                                        System.out.println("\nContinue without dump file for accounts information!");
                                                        ex8.printStackTrace(exceptionLog);
                                                        exceptionLog.print("\r\n");
                                                        exceptionLog.flush();
                                                        kbd24.readLine("Press Enter to continue ...");
                                                    }

                                                    String accountsList = ""
                                                            + "The account information of all users\r\n"
                                                            + "+--------------------------------------------------+\r\n"
                                                            + "| ID      | Name              | Balance            |\r\n"
                                                            + "+--------------------------------------------------+\r\n"
                                                            + "| 0001    | Admin             |                    |\r\n";
                                                    for (ClientUser clientUsr : clientUsers) {
                                                        accountsList += "| " + String.format("%-8s", clientUsr.getID())
                                                                + "| " + String.format("%-18s", clientUsr.getName())
                                                                + "| " + String.format("%-19s", clientUsr.getBalance())
                                                                + "|\r\n";
                                                    }
                                                    accountsList += "+--------------------------------------------------+\r\n\r\n";

                                                    dumpAccountsInfo.print(accountsList);
                                                    dumpAccountsInfo.flush();
                                                    dumpAccountsInfo.close();
                                                    System.out.println("\nThe account information of all users has been dumped to the file located by the path "
                                                            + "\"" + accountsInfo.getAbsolutePath() + "\"");
                                                    Console kbd30 = System.console();
                                                    kbd30.readLine("Press Enter to continue ...\n");
                                                }
                                            }

                                            String input25 = "";   // start to dump transaction information
                                            Console kbd25 = System.console();
                                            input25 = kbd25.readLine("\nPress Enter directly to start dumping transactions information into txt file ...\n"
                                                    + "Or type any character(s) like letter, number, space or punctuation to skip this step ...\n");

                                            if (input25.equals("")) {
                                                boolean input26isOK = false;
                                                int input26Times = 0;
                                                String input26 = "";
                                                do {
                                                    input26Times++;
                                                    if (input26Times > 3) {
                                                        System.out.println("\nOops, invalid file name typing exceeded 3 time!\n"
                                                                + "The step of dumping transactions information has been skipped.\n"
                                                                + "You can try it again later.\n");
                                                        break;
                                                    }
                                                    Console kbd26 = System.console();
                                                    input26 = kbd26.readLine("\nType a name including .txt suffix to create a file for dumping transactions information ...\n");
                                                    input26 = input26.trim();
                                                    if (input26.matches("[a-zA-Z0-9]{1,}([-_\\s]*[a-zA-Z0-9]{1,})*\\.txt")) {
                                                        input26isOK = true;
                                                    } else {
                                                        System.out.println("\nSorry, the previously typed file name is not appropriate to use!\n"
                                                                + "You need to retype a new file name including .txt suffix.");
                                                    }
                                                } while (!input26isOK);

                                                if (input26Times < 4) {
                                                    File transactInfo = new File(input26);
                                                    PrintWriter dumpTransactInfo = null;
                                                    Console kbd27 = System.console();
                                                    try {
                                                        if (!transactInfo.exists()) {
                                                            transactInfo.createNewFile();
                                                        }
                                                        dumpTransactInfo = new PrintWriter(transactInfo);
                                                    } catch (IOException ex9) {
                                                        System.out.println("An error occured while configuring the dump file for transactions information!");
                                                        ex9.printStackTrace(System.err);
                                                        System.out.println("\nContinue without dump file for transactions information!");
                                                        ex9.printStackTrace(exceptionLog);
                                                        exceptionLog.print("\r\n");
                                                        exceptionLog.flush();
                                                        kbd27.readLine("Press Enter to continue ...");
                                                    }

                                                    String transactList = ""
                                                            + "The transaction records of all client accounts\r\n"
                                                            + "+-----------------------------------------------------------+\r\n"
                                                            + "| Date                   | ID     | Type | Amount           |\r\n"
                                                            + "+-----------------------------------------------------------+\r\n";
                                                    for (TransactionRecord Record : TransactRecords) {
                                                        transactList += "| " + String.format("%-23s", Record.getTime())
                                                                + "| " + String.format("%-8s", Record.getID())
                                                                + "| " + String.format("%-5s", Record.getType())
                                                                + "| " + String.format("%-16s", Record.getAmount())
                                                                + "|\r\n";

                                                    }
                                                    transactList += "+-----------------------------------------------------------+\r\n\r\n";

                                                    dumpTransactInfo.print(transactList);
                                                    dumpTransactInfo.flush();
                                                    dumpTransactInfo.close();
                                                    System.out.println("\nThe transaction information of all clients has been dumped to the file located by the path "
                                                            + "\"" + transactInfo.getAbsolutePath() + "\"");
                                                    Console kbd31 = System.console();
                                                    kbd31.readLine("Press Enter to continue ...\n");
                                                }
                                            }

                                            break;

                                        case "5":   // finish and go back to START menu
                                            adminMenu = false;
                                            break;

                                        default:
                                            break;

                                    }

                                } while (adminMenu);

                                break;
                        }

                    } else {
                        break;  // break verifying user ID & PIN matching 
                    }

                } while (!usrValidity);

                continue;

            } else {
                System.out.println("Invalid input!\nThe ATM manager has gone back to the START menu.");
                continue;
            }

        }

        exceptionLog.close();

    }

    private static void clearScreen() throws IOException, InterruptedException {
        String os = System.getProperty("os.name");
        if (os.toLowerCase().contains("windows")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } else {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }

}
