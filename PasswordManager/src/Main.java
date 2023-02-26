import java.util.ArrayList;
public class Main {

    public static void main(String[] args) {

        User userDatabase = new User();
        ArrayList<Passwords> Manager = new ArrayList<>();

        GUI gui = new GUI();

        int arrSize = 0;

        //Loading passwords, loading the passwords for manager object goes with for loop because it is an array of objects.

        arrSize = userDatabase.loadUsers();

        for (int i = 0; i < arrSize; i++){
           Passwords passwords = new Passwords();
           passwords.loadPasswords(i);
           Manager.add(passwords);
        }
        while(true) {
            int choice = gui.StartingWindow();

            //Basic interface for user

            if (choice == 1) {
                int userID = userDatabase.Login();

                //userID will be -1 if something went wrong with login, so the value must be non-negative to access user interface.

                if (userID >= 0 && userID < 1000) {

                    //This loop is to stay in user interface

                    while (userID >= 0 && userID < 1000) {
                        userID = userDatabase.UserInterface(userID);

                        if (userID >= 0 && userID < 1000) {
                            Manager.get(userID).Choose();
                        }
                    }

                    if (userID >= 1000) {
                        //If userID is bigger than 1000 then that means that an account has been deleted.
                        //I need to reload users and passwords in order to have everything working.

                        userDatabase.saveUsers();

                        Manager.get(userID - 1000).deleteAllPasswords();

                        for (int i = 0; i < arrSize; i++)
                            Manager.get(i).savePasswords(i);

                        Manager.clear();

                        arrSize = userDatabase.loadUsers();

                        for (int i = 0; i < arrSize; i++) {
                            Passwords passwords = new Passwords();
                            passwords.loadPasswords(i);
                            Manager.add(passwords);
                        }

                    }
                }

            } else if (choice == 2) {
                int userID = userDatabase.Register();

                if (userID >= 0) {
                    Manager.add(new Passwords());
                    arrSize++;
                }

            }

            //When user decides to quit, passwords for User class and passwords for Passwords class save before system.exit

            else if (choice == 3) {
                System.out.print("Have a nice day!");
                userDatabase.saveUsers();

                for (int i = 0; i < arrSize; i++)
                    Manager.get(i).savePasswords(i);

                System.exit(0);
            }

            //else System.out.println("Incorrect input.");
        }
    }
}
