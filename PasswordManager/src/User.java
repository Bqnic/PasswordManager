import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        //I use regex to manipulate user's input for password (must be correct format!)

        while(true) {
            System.out.print("Enter password > ");
            newPassword = sc.nextLine();

            String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()-[{}]:;'?.,/*+=<>]).{8,20}$";

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(newPassword);
            boolean validPassword = matcher.matches();

            if (!validPassword){
                System.out.println("Invalid password.\nPassword must include:\n- Atleast one number\n- Atleast one lowercase and uppercase letter\n- Atleast one special character\n- Must be between 8 and 20 characters long\n");
            }
            else break;
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

    public void saveUsers(){

        //when i == 0, i save userID aswell, so when i loadUsers i load the userID so i know how much users i actually have.

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

                if (numofUsers == 0) {
                    String line = fileScanner.nextLine();
                    String[] parts = line.split(",");

                    this.username.add(parts[0]);
                    this.userPassword.add(parts[1]);
                    userID = Integer.parseInt(parts[2]);

                    numofUsers++;
                }

                else{
                    String line = fileScanner.nextLine();
                    String[] parts = line.split(",");

                    this.username.add(parts[0]);
                    this.userPassword.add(parts[1]);

                    numofUsers++;
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return numofUsers;
    }
}
