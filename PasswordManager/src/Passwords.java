import java.util.*;
public class Passwords {

    private ArrayList<String> service = new ArrayList<>();
    private ArrayList<String> password = new ArrayList<>();
    static int serandpass = -1;

    public Passwords(){
        Choose();
    }

    private void Choose() {

        int choice;
        Scanner sc = new Scanner(System.in);
        System.out.println("\n1. Store new password\n2. Access passwords\n3. Show which services are stored\n4. Delete passwords\n5. Quit");

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
                System.out.println("Have a nice day!");
                System.exit(0);
            }
            else {
                System.out.println("Invalid choice");
            }
        }
    }

    public void setPassword(){

        serandpass++;

        Scanner sc = new Scanner(System.in);
        System.out.print("Service: ");
        this.service.add(sc.nextLine());

        System.out.print("Password of the service: ");
        this.password.add(sc.nextLine());

        Choose();

    }

    public void getPassword(){
        Scanner sc = new Scanner(System.in);

        String whichService;

        int check = 0;

        while(true) {
            System.out.print("For which service would you like the access? > ");
            whichService = sc.nextLine();
            for (int i = 0; i < serandpass + 1; i++) {

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
        for (int i = 0; i < serandpass + 1; i++){
            System.out.println(this.service.get(i));
        }

        if (serandpass == -1){
            System.out.println("No services added yet!");
        }

        Choose();
    }

    public void deletePassword(){

        Scanner sc = new Scanner(System.in);

        System.out.print("Which service would you like to delete? > ");
        String whichService = sc.nextLine();

        int check = 0;

        for (int i = 0; i < serandpass + 1; i++){

            if (Objects.equals(whichService, this.service.get(i))){
                System.out.println("Password for " + this.service.get(i) + " has been successfully deleted!");

                this.service.remove(i);
                this.password.remove(i);

                serandpass--;
                check = 1;
                break;
            }
        }

        if (check == 0){
            System.out.println("There is no password for " + whichService + " stored!");
        }

        Choose();

    }

}
