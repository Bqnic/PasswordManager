import java.util.ArrayList;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {

        User userDatabase = new User();
        ArrayList<Passwords> Manager = new ArrayList<>();

        Scanner sc = new Scanner(System.in);

        int arrSize = 0;

        //Loading passwords, loading the passwords for manager object goes with for loop because it is an array of objects.

        arrSize = userDatabase.loadUsers();

        for (int i = 0; i < arrSize; i++){
           Passwords passwords = new Passwords();
           passwords.loadPasswords(i);
           Manager.add(passwords);
        }

        //Basic interface for a user, will loop until user decides to quit.

        while (true) {
            System.out.println("1. Login\n2. Register\n3. Quit");
            int choice = sc.nextInt();

            if (choice == 1) {
                int userID = userDatabase.Login();

                //userID will be -1 if something went wrong with login, so the value must be non-negative to access user interface.

                if (userID >= 0){
                    userID = userDatabase.UserInterface(userID);

                    if (userID >= 0)
                        Manager.get(userID).Choose();
                }
            }

            else if (choice == 2) {
                int userID = userDatabase.Register();

                if (userID >= 0){
                   Manager.add(new Passwords());
                   arrSize++;
                }
            }

            //When user decides to quit, passwords for User class and passwords for Passwords class save before system.exit

            else if (choice == 3) {
                System.out.print("Have a nice day!");
                userDatabase.saveUsers();

                for (int i = 0; i < arrSize; i++){
                    Manager.get(i).savePasswords(i);
                }

                System.exit(0);
            }

            else System.out.println("Incorrect input.");
        }
    }
}