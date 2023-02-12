import java.util.*;
public class Passwords {

    private final ArrayList<String> service = new ArrayList<>();
    private final ArrayList<String> password = new ArrayList<>();

    public Passwords(){
        Choose();
    }

    private void Choose() {

        int choice;
        Scanner sc = new Scanner(System.in);
        System.out.println("\n1. Store a new password\n2. Access passwords\n3. Show which services are stored\n4. Delete a password\n5. Change a password\n6. Quit");

        while (true) {
            choice = sc.nextInt();
            if (choice == 1) {
                setPassword();
            }
            else if (choice == 2) {
                getPassword();
            }
            else if (choice == 3){
                ShowServices();
            }

            else if (choice == 4){
                deletePassword();
            }

            else if (choice == 5){
                changePassword();
            }

            else if (choice == 6){
                System.out.println("Have a nice day!");
                System.exit(0);
            }
            else {
                System.out.println("Invalid choice");
            }
        }
    }

    public void setPassword(){

        Scanner sc = new Scanner(System.in);
        System.out.print("Service: ");
        String addService = sc.nextLine();

        int check = 0;

        if (this.service.isEmpty())
            this.service.add(addService);

        else {

            for (int i = 0; i < this.service.size(); i++) {

                if (Objects.equals(this.service.get(i), addService)) {
                    System.out.println("A password for " + addService + " has already been stored.");
                    check = 1;
                }

                else this.service.add(addService);
            }
        }

        if (check == 0) {
            System.out.print("Password of the service: ");
            this.password.add(sc.nextLine());
        }

        Choose();

    }

    public void getPassword(){
        Scanner sc = new Scanner(System.in);

        String whichService;

        int check = 0;

        while(true) {
            System.out.print("For which service would you like the access? > ");
            whichService = sc.nextLine();
            for (int i = 0; i < this.service.size(); i++) {

                if (Objects.equals(whichService, this.service.get(i))) {
                    System.out.println("Password for " + this.service.get(i) + " is: " + this.password.get(i));
                    check = 1;
                    break;
                }
            }

            if (check == 0) {
                System.out.println("No password stored for that service!\nTry again?\nAny key to continue.\nQ to quit.");
                String choice = sc.nextLine();

                if (Objects.equals(choice, "Q") || Objects.equals(choice, "q"))
                    break;
            }

            if (check == 1)
                break;
        }

        Choose();
    }

    public void ShowServices(){

        System.out.println("Stored services: ");
        for (String s : this.service) {
            System.out.println(s);
        }

        if (this.service.isEmpty()){
            System.out.println("No services added yet!");
        }

        Choose();
    }

    public void deletePassword(){

        Scanner sc = new Scanner(System.in);

        System.out.print("Which service would you like to delete? > ");
        String whichService = sc.nextLine();

        int check = 0;

        for (int i = 0; i < this.service.size(); i++){

            if (Objects.equals(whichService, this.service.get(i))){
                System.out.println("Password for " + this.service.get(i) + " has been successfully deleted!");

                this.service.remove(i);
                this.password.remove(i);

                check = 1;
                break;
            }
        }

        if (check == 0){
            System.out.println("There is no password for " + whichService + " stored!");
        }

        Choose();

    }

    public void changePassword(){

        Scanner sc = new Scanner(System.in);

        System.out.println("For which service would you like to change the password? > ");
        String whichService = sc.nextLine();

        int check = 0;

        for (int i = 0; i < this.service.size(); i++){

            if (Objects.equals(whichService, this.service.get(i))){
                System.out.print("To what do you wish to change the password of " + this.service.get(i) +"? > ");
                String newpassword = sc.nextLine();

                if (Objects.equals(newpassword, this.password.get(i))){
                    System.out.println("This is password is the same as the old password.");
                }

                else {
                    this.password.set(i, newpassword);
                    System.out.println("The password of " + this.service.get(i) + " has been successfully changed!");
                }

                check = 1;

            }
        }

        if (check == 0)
            System.out.println("There is no password stored for " + whichService);

        Choose();
    }

}
