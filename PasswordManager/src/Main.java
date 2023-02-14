import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {

        User userDatabase = new User();
        Passwords [] Manager = new Passwords[20];

        Scanner sc = new Scanner(System.in);

        int arrSize = 0;

        arrSize = userDatabase.loadUsers();

        for (int i = 0; i < arrSize; i++){
            Manager[i] = new Passwords();
            Manager[i].loadPasswords(i);
        }

        while (true) {
            System.out.println("1. Login\n2. Register\n3. Quit");
            int choice = sc.nextInt();

            if (choice == 1) {
                int userID = userDatabase.Login();

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