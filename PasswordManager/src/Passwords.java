import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
public class Passwords {

    private final ArrayList<String> service = new ArrayList<>();
    private final ArrayList<String> password = new ArrayList<>();

    public void Choose() {

        int choice;
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Store a new password\n2. Access passwords\n3. Show which services are stored\n4. Delete a password\n5. Change a password\n6. Logout");
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
            this.service.add(addService);
            checkEmpty = 1;
        } else {

            for (int i = 0; i < this.service.size(); i++) {

                if (Objects.equals(this.service.get(i), addService)) {
                    System.out.println("A password for " + addService + " has already been stored.");
                    return;
                }
            }
        }

        if (checkEmpty == 0)
            this.service.add(addService);

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

            if (Objects.equals(whichService, this.service.get(i))) {
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

            if (Objects.equals(whichService, this.service.get(i))) {
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

            if (Objects.equals(whichService, this.service.get(i))) {
                System.out.print("To what do you wish to change the password of " + this.service.get(i) + "? > ");
                String new_password = sc.nextLine();

                if (Objects.equals(new_password, this.password.get(i))) {
                    System.out.println("This password is the same as the old password.");
                    return;
                } else {
                    this.password.set(i, new_password);
                    System.out.println("The password of " + this.service.get(i) + " has been successfully changed!");
                    return;
                }

            }
        }

        System.out.println("There is no password stored for " + whichService);
        return;
    }

    public void savePasswords(int checkIfAppend) {

        if (checkIfAppend == 0) {
            try (FileWriter writer = new FileWriter("C:\\Users\\Bonic\\IdeaProjects\\PasswordManager\\src\\savefiles\\passwords.txt")) {
                for (int i = 0; i < this.service.size(); i++)
                    writer.write(this.service.get(i) + "," + this.password.get(i) + "\n");

                writer.write("\n");

            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        }

        else {
            try (FileWriter writer = new FileWriter("C:\\Users\\Bonic\\IdeaProjects\\PasswordManager\\src\\savefiles\\passwords.txt", true)) {
                for (int i = 0; i < this.service.size(); i++)
                    writer.write(this.service.get(i) + "," + this.password.get(i) + "\n");

                writer.write("\n");

            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        }
    }

    public void loadPasswords(int skipLines){

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
