import java.util.Scanner;
public class Main {

    public static void main(String[] args) {

        User userDatabase = new User();
        Passwords [] Manager = new Passwords[20];

        Scanner sc = new Scanner(System.in);

        int arrSize = 0;

        //Loading passwords, loading the passwords for manager object goes with for loop because it is an array of objects.

        arrSize = userDatabase.loadUsers();

        for (int i = 0; i < arrSize; i++){
            Manager[i] = new Passwords();
            Manager[i].loadPasswords(i);
        }

        //Basic interface for a user, will loop until user decides to quit.

        while (true) {
            System.out.println("1. Login\n2. Register\n3. Quit");
            int choice = sc.nextInt();

            if (choice == 1) {
                int userID = userDatabase.Login();

                //userID will be -1 if something went wrong with login, so the value must be non-negative to access passwords

                if (userID >= 0){
                    Manager[userID].Choose();
                }
            }

            else if (choice == 2) {
                int userID = userDatabase.Register();

                if (userID >= 0){
                    Manager[userID] = new Passwords();
                    arrSize++;
                }
            }

            //When user decides to quit, passwords for User class and passwords for Passwords class save before system.exit

            else if (choice == 3) {
                System.out.print("Have a nice day!");
                userDatabase.saveUsers();

                for (int i = 0; i < arrSize; i++){
                    Manager[i].savePasswords(i);
                }

                System.exit(0);
            }

            else System.out.println("Incorrect input.");
        }
    }
}