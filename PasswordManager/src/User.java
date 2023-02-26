import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {

    private final ArrayList<String> username = new ArrayList<>();
    private final ArrayList<String> userPassword = new ArrayList<>();
    static int userID = -1;

    GUI gui = new GUI();

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
        int choice;

        while(true){

            choice = gui.LoggedInWindow();
            gui.clearWindow();
            gui.disposeWindow();

            if (choice == 1)
                return currentID;

            else if (choice == 2)
                changePassword(currentID);

            else if (choice == 3)
                changeUsername(currentID);

            else if (choice == 4) {
                boolean checkifDeleted = deleteUser(currentID);

                if (checkifDeleted)
                    return currentID + 1000;
            }

            else if (choice == 5)
                return -1;

            else
                System.out.println("Incorrect input");
        }
    }
    public int Register(){

        String checkLine = gui.Register();

        if (Objects.equals(checkLine, ",,")){
            gui.NothingEntered();
            gui.clearWindow();
            gui.disposeWindow();
            return -1;
        }

        String[] line = checkLine.split(",");

        String newUsername = line[0], newPassword = line[1], checkPassword = line[2];

        while(true) {

            int usernameExists = 0;

            if (!this.username.isEmpty()) {

                for (String s : this.username) {

                    if (Objects.equals(newUsername, s)) {
                        usernameExists = 1;
                        gui.RegisterUsernameError();
                        break;
                    }
                }
            }

            if (usernameExists == 0) {
                boolean validPassword = regex(newPassword);

                if (validPassword) {

                    if (Objects.equals(newPassword, checkPassword)) {
                        gui.RegisterSuccessful();
                        gui.clearWindow();
                        gui.disposeWindow();
                        this.username.add(newUsername);
                        this.userPassword.add(newPassword);

                        userID++;
                        return userID;
                    } else {
                        gui.RegisterFailed();
                    }
                }
                else
                    gui.RegisterPasswordError();
            }

            gui.clearWindow();

            line = gui.Register().split(",");

            newUsername = line[0];
            newPassword = line[1];
            checkPassword = line[2];
        }
    }
    public int Login(){

        if (this.username.isEmpty()){
            gui.LoginEmpty();
            return -1;
        }

        String account = gui.Login();

        String[] line = account.split(",");
        String checkUsername = line[0], checkPassword = line[1];

        for (int i = 0; i < this.username.size(); i++){

            if (Objects.equals(checkUsername, this.username.get(i)) && Objects.equals(this.userPassword.get(i), checkPassword)){
                gui.clearWindow();
                gui.disposeWindow();
                return i;
            }
        }

        gui.LoginFailed();
        gui.clearWindow();
        gui.disposeWindow();
        return -1;

    }

    public void changePassword(int currentID){

        String changePass = gui.ChangeUserPassword();

        if (Objects.equals(changePass, ",,")) {
            gui.NothingEntered();
            gui.clearWindow();
            gui.disposeWindow();
            return;
        }

        String[] line = changePass.split(",");
        String oldPass = line[0], newPass = line[1], checkPass = line[2];

        if (Objects.equals(this.userPassword.get(currentID), oldPass)){

            boolean validPassword = regex(newPass);

            if (validPassword) {
                if (Objects.equals(this.userPassword.get(currentID), newPass)) {
                    gui.ChangeUserPasswordSamePassword();
                }

                else{
                    if (Objects.equals(newPass, checkPass)) {
                        this.userPassword.set(currentID, newPass);
                        gui.ChangeUserPasswordSuccess();
                    }

                    else
                        gui.RegisterFailed();
                }
            }

            else
                gui.RegisterPasswordError();

        }

        else
            gui.ChangeUserPasswordFailure();

        gui.clearWindow();
        gui.disposeWindow();

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

    public boolean deleteUser(int currentID){
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Are you sure you want to delete your account?\n1. Yes\n2. Go back");
            int choice = sc.nextInt();

            if (choice == 1) {
                System.out.print("Enter your password: ");
                sc.nextLine();
                String checkPass = sc.nextLine();

                if (Objects.equals(checkPass, this.userPassword.get(currentID))) {

                    this.username.remove(currentID);
                    this.userPassword.remove(currentID);
                    userID--;

                    System.out.println("Account has been successfully deleted!");
                    return true;
                }
            } else if (choice == 2)
                return false;

            else {
                System.out.println("Incorrect input.");
            }
        }
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

        if (!this.username.isEmpty()){
            this.username.clear();
            this.userPassword.clear();
        }

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
