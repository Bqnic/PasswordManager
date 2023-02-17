import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

    private final ArrayList<String> username = new ArrayList<>();
    private final ArrayList<String> userPassword = new ArrayList<>();
    static int userID = -1;


    //I use regex to manipulate user's input for password (must be correct format!)
    public boolean regex(String checkPass){

        String validPass = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()-[{}]:;'?.,/*+=<>]).{8,20}$";

        Pattern pattern = Pattern.compile(validPass);
        Matcher matcher = pattern.matcher(checkPass);

        if (matcher.matches())
            return true;

        else{
            System.out.println("Invalid password.\nPassword must include:\n- At least one number\n- At least one lowercase and uppercase letter\n- At least one special character\n- Must be between 8 and 20 characters long\n");
            return false;
        }
    }

    public int UserInterface(int currentID){
        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.println("1. Access password manager\n2. Change password\n3. Change username\n4. Delete account\n5. Logout");
            int choice = sc.nextInt();

            if (choice == 1)
                return currentID;

            else if (choice == 2)
                changePassword(currentID);

            else if (choice == 3)
                changeUsername(currentID);

            else if (choice == 4)
                System.out.println("placeholder");

            else if (choice == 5)
                return -1;

            else
                System.out.println("Incorrect input");
        }
    }
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


        while(true) {
            System.out.print("Enter password > ");
            newPassword = sc.nextLine();

            boolean validPassword = regex(newPassword);

            if (validPassword) {
                break;
            }
        }

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

    public void changePassword(int currentID){
        Scanner sc = new Scanner(System.in);
        String oldPass, newPass;

        System.out.print("Enter your old password: ");
        oldPass = sc.nextLine();

        if (Objects.equals(this.userPassword.get(currentID), oldPass)){

            System.out.print("Enter your new password: ");
            newPass = sc.nextLine();

            boolean validPassword = regex(newPass);

            if (validPassword) {
                if (Objects.equals(this.userPassword.get(currentID), newPass)) {
                    System.out.println("Your new password can't be the same as the old one.");
                }

                else{
                    this.userPassword.set(currentID, newPass);
                    System.out.println("Password successfully changed!");
                }
            }
        }

        else{
            System.out.println("You entered incorrect password.");
        }
        return;
    }

    public void changeUsername(int currentID){
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter your password: ");
        String checkPass = sc.nextLine();

        if (Objects.equals(this.userPassword.get(currentID), checkPass)){
            System.out.print("New username: ");
            String newUsername = sc.nextLine();

            if (Objects.equals(this.username.get(currentID), newUsername)){
                System.out.println("Your new username can't be the same as the old one.");
            }

            else{
                for (String names : this.username){
                    if (Objects.equals(names, newUsername)){
                        System.out.println("User with " + names + " username already exists.");
                        return;
                    }
                }

                this.username.set(currentID, newUsername);
                System.out.println("Your username has been successfully changed!");
            }
        }

        else{
            System.out.println("Incorrect password.");
        }
        return;
    }

    public void saveUsers(){

        //when i == 0, I save userID as well, so when I loadUsers I load the userID, so I know how many users I actually have.

        try (FileWriter writer = new FileWriter("C:\\Users\\Bonic\\IdeaProjects\\PasswordManager\\src\\savefiles\\accounts.txt")) {
            for (int i = 0; i < this.username.size(); i++) {
                if (i == 0)
                    writer.write(this.username.get(i) + "," + this.userPassword.get(i) + "," + userID + "\n");

                else
                    writer.write(this.username.get(i) + "," + this.userPassword.get(i) + "\n");
            }
        }

        catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public int loadUsers(){

        int numofUsers = 0;

        try (Scanner fileScanner = new Scanner(new File("C:\\Users\\Bonic\\IdeaProjects\\PasswordManager\\src\\savefiles\\accounts.txt"))) {
            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                this.username.add(parts[0]);
                this.userPassword.add(parts[1]);

                if (numofUsers == 0)
                    userID = Integer.parseInt(parts[2]);

                numofUsers++;
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return numofUsers;
    }
}
