import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
public class Passwords {

    private final ArrayList<String> service = new ArrayList<>();
    private final ArrayList<String> password = new ArrayList<>();

    public void Choose() { //Basic interface for user

        int choice;
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Store a new password\n2. Access passwords\n3. Show which services are stored\n4. Delete a password\n5. Change a password\n6. Go back to user interface");
            choice = sc.nextInt();
            if (choice == 1) {
                setPassword();
            } else if (choice == 2) {
                getPassword();
            } else if (choice == 3) {
                ShowServices();
            } else if (choice == 4) {
                deletePassword();
            } else if (choice == 5) {
                changePassword();
            } else if (choice == 6) {
                System.out.println("Have a nice day!");
                return;
            } else {
                System.out.println("Invalid choice");
            }
        }
    }

    public void setPassword() {

        Scanner sc = new Scanner(System.in);
        System.out.print("Service: ");
        String addService = sc.nextLine();

        int checkEmpty = 0;

        if (this.service.isEmpty()) {
            this.service.add(addService.toLowerCase());
            checkEmpty = 1;
        } else {

            for (int i = 0; i < this.service.size(); i++) {

                if (Objects.equals(this.service.get(i).toLowerCase(), addService.toLowerCase())) {
                    System.out.println("A password for " + addService.toLowerCase() + " has already been stored.");
                    return;
                }
            }
        }

        if (checkEmpty == 0)
            this.service.add(addService.toLowerCase());

        System.out.print("Password of the service: ");
        this.password.add(sc.nextLine());

        return;

    }

    public void getPassword() {
        Scanner sc = new Scanner(System.in);

        String whichService;

        System.out.print("For which service would you like the access? > ");
        whichService = sc.nextLine();
        for (int i = 0; i < this.service.size(); i++) {

            if (Objects.equals(whichService.toLowerCase(), this.service.get(i))) {
                System.out.println("Password for " + this.service.get(i) + " is: " + this.password.get(i));
                return;
            }
        }

        System.out.println("No password stored for that service.");
        return;

    }


    public void ShowServices() {

        System.out.println("Stored services: ");
        for (String s : this.service) {
            System.out.println(s);
        }

        if (this.service.isEmpty()) {
            System.out.println("No services added yet!");
        }

        return;
    }

    public void deletePassword() {

        Scanner sc = new Scanner(System.in);

        System.out.print("Which service would you like to delete? > ");
        String whichService = sc.nextLine();

        for (int i = 0; i < this.service.size(); i++) {

            if (Objects.equals(whichService.toLowerCase(), this.service.get(i))) {
                System.out.println("Password for " + this.service.get(i) + " has been successfully deleted!");

                this.service.remove(i);
                this.password.remove(i);

                return;
            }
        }

        System.out.println("There is no password for " + whichService + " stored!");
        return;

    }

    public void changePassword() {

        Scanner sc = new Scanner(System.in);

        System.out.print("For which service would you like to change the password? > ");
        String whichService = sc.nextLine();

        for (int i = 0; i < this.service.size(); i++) {

            if (Objects.equals(whichService.toLowerCase(), this.service.get(i))) {
                System.out.print("To what do you wish to change the password of " + this.service.get(i) + "? > ");
                String new_password = sc.nextLine();

                if (Objects.equals(new_password, this.password.get(i))) {
                    System.out.println("This password is the same as the old password.");
                } else {
                    this.password.set(i, new_password);
                    System.out.println("The password of " + this.service.get(i) + " has been successfully changed!");
                }
                return;

            }
        }

        System.out.println("There is no password stored for " + whichService);
        return;
    }

    public void deleteAllPasswords(){
        this.service.clear();
        this.password.clear();
        return;
    }

    public void savePasswords(int checkIfAppend) {

        //Reason this is split in 2 things, first if checkIfAppend == 0 then I am saving passwords for the first user,
        //meaning that I need to overwrite the old files first
        //after that checkIfAppend will be bigger than 0 (I am passing iterator of for loop for checkIfAppend)
        //when checkIfAppend is > 0 then I do the same code but put "true" for appending instead of writing, so I don't overwrite.

        if (checkIfAppend == 0) {
            try (FileWriter writer = new FileWriter("C:\\Users\\Bonic\\IdeaProjects\\PasswordManager\\src\\savefiles\\passwords.txt")) {
                for (int i = 0; i < this.service.size(); i++)
                    writer.write(this.service.get(i) + "," + this.password.get(i) + "\n");

                if (this.service.size() > 0)
                    writer.write("\n");

            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        }

        else {
            try (FileWriter writer = new FileWriter("C:\\Users\\Bonic\\IdeaProjects\\PasswordManager\\src\\savefiles\\passwords.txt", true)) {
                for (int i = 0; i < this.service.size(); i++)
                    writer.write(this.service.get(i) + "," + this.password.get(i) + "\n");

                if (this.service.size() > 0)
                    writer.write("\n");

            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        }
    }

    public void loadPasswords(int skipLines){

        if (!this.service.isEmpty() && skipLines == 0){
            this.service.clear();
            this.password.clear();
        }

        //skipLines is used so only load passwords that I need to for specific user.
        //The passwords.txt splits passwords with blank line, so according to those blank lines I can manipulate which passwords
        //for which users I will save

        try (Scanner fileScanner = new Scanner(new File("C:\\Users\\Bonic\\IdeaProjects\\PasswordManager\\src\\savefiles\\passwords.txt"))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");

                if (line.equals("") && skipLines == 0)
                    break;

                else if (line.equals(""))
                    skipLines--;

                if (!line.equals("") && skipLines <= 0) {
                    this.service.add(parts[0]);
                    this.password.add(parts[1]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
