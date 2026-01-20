import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PetDatabase db = new PetDatabase();

        System.out.println("Pet Database Program.");

        while (true) {
            printMenu();
            int choice = readInt(sc, "Your choice: ");

            switch (choice) {

                case 1:
                    db.showAllPets();
                    break;

                case 2:
                    addPets(sc, db);
                    break;

                case 3:
                    // Update
                    if (db.size() == 0) {
                        System.out.println("No pets to update.");
                        break;
                    }
                    db.showAllPets();
                    int updateId = readInt(sc, "Enter the pet ID you can to update: ");
                    System.out.print("Enter new name and new age: ");
                    String newName = sc.next();
                    int newAge = readInt(sc, "");
                    if (!db.updatePet(updateId, newName, newAge)) {
                        System.out.println("Invalid ID.");
                    }
                    break;

                case 4:
                    // Remove
                    if (db.size() == 0) {
                        System.out.println("No pets to remove.");
                        break;
                    }
                    db.showAllPets();
                    int removeId = readInt(sc, "Enter the pet ID to remove: ");
                    if (!db.removePet(removeId)) {
                        System.out.println("Invalid ID.");
                    }
                    break;

                case 5:
                    System.out.print("Enter a name to search: ");
                    String name = sc.next();
                    db.searchByName(name);
                    break;

                case 6:
                    int age = readInt(sc, "Enter age to search: ");
                    db.searchByAge(age);
                    break;

                case 7:
                    System.out.println("Goodbye!");
                    sc.close();
                    return;

                default:
                    System.out.println("Please choose a valid option.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("What would you like to do?");
        System.out.println("1) View all pets");
        System.out.println("2) Add more pets");
        System.out.println("3) Update an existing pet");
        System.out.println("4) Remove an existing pet");
        System.out.println("5) Search pets by name");
        System.out.println("6) Search pets by age");
        System.out.println("7) Exit program");
    }

    private static void addPets(Scanner sc, PetDatabase db) {
        int count = 0;

        while (true) {
            System.out.print("add pet (name, age): ");
            String name = sc.next();

            if (name.equalsIgnoreCase("done")) {
                break;
            }

            int age = readInt(sc, "");
            db.addPet(name, age);
            count++;
        }

        System.out.println(count + " pets added.");
    }

    private static int readInt(Scanner sc, String prompt) {
        if (!prompt.isEmpty()) {
            System.out.print(prompt);
        }

        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Enter a number: ");
        }

        return sc.nextInt();
    }
}
