import java.util.*;
public class User {

    private final ArrayList<String> username = new ArrayList<>();
    private final ArrayList<String> userPassword = new ArrayList<>();
    static int userID = -1;
    public int Register(){

        String newUsername, newPassword, checkPassword;
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter username > ");
        newUsername = sc.nextLine();

        int checkEmpty = 0;
        if (this.username.isEmpty()) {
            this.username.add(newUsername);
            checkEmpty = 1;
        }

        else {

            for (int i = 0; i < this.username.size(); i++) {

                if (Objects.equals(newUsername, this.username.get(i))) {
                    System.out.println("This username is already taken.");
                    return -1;
                }
            }
        }

        if (checkEmpty == 0)
            this.username.add(newUsername);

        System.out.print("Enter password > ");
        newPassword = sc.nextLine();
        System.out.print("Enter the password again > ");
        checkPassword = sc.nextLine();

        if (Objects.equals(newPassword, checkPassword)){
            System.out.println("Account successfully created!");
            this.userPassword.add(newPassword);

            userID++;
            return userID;
        }

        else{
            System.out.println("You entered incorrect password.\nAccount not created.");
            this.username.remove(newUsername);
            return -1;
        }
    }
    public int Login(){

        if (this.username.isEmpty()){
            System.out.println("No users in the system yet!");
            return -1;
        }

        String checkUsername, checkPassword;
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter username > ");
        checkUsername = sc.nextLine();

        for (int i = 0; i < this.username.size(); i++){

            if (Objects.equals(checkUsername, this.username.get(i))){

                System.out.print("Enter password > ");
                checkPassword = sc.nextLine();

                if (Objects.equals(this.userPassword.get(i), checkPassword)){
                    System.out.println("Successful login!");
                    return i;
                }

                else{
                    System.out.println("Incorrect password.");
                    return -1;
                }
            }
        }

        System.out.println("Incorrect username.");
        return -1;

    }
}
