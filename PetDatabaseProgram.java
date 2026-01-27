import java.io.*;
import java.util.*;

public class PetDatabaseProgram {


    private static final int MAX_PETS = 5;
    private static final int MIN_AGE = 1;
    private static final int MAX_AGE = 20;

    // File name used for load/save
    private static final String DATA_FILE = "pets.txt";

    // Pet storage (array size 5)
    private static final Pet[] pets = new Pet[MAX_PETS];
    private static int petCount = 0;

    public static void main(String[] args) {

        // Load pets when program starts (Milestone 3) :contentReference[oaicite:6]{index=6}
        loadPetsFromFile();

        Scanner sc = new Scanner(System.in);

        System.out.println("Pet Database Program.");

        while (true) {
            System.out.println("What would you like to do?");
            System.out.println("1) View all pets");
            System.out.println("2) Add new pets");
            System.out.println("3) Remove a pet");
            System.out.println("4) Exit program");
            System.out.print("Your choice: ");

            String choiceLine = sc.nextLine().trim();

            // Basic error handling (Milestone 4) :contentReference[oaicite:7]{index=7}
            int choice;
            try {
                choice = Integer.parseInt(choiceLine);
            } catch (NumberFormatException e) {
                System.out.println("Error: " + choiceLine + " is not a valid menu option.");
                continue;
            }

            if (choice == 1) {
                viewAllPets();
            } else if (choice == 2) {
                addPets(sc);
            } else if (choice == 3) {
                removePet(sc);
            } else if (choice == 4) {
                // Save pets when user exits (Milestone 3) :contentReference[oaicite:8]{index=8}
                savePetsToFile();
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Error: " + choice + " is not a valid menu option.");
            }
        }

        sc.close();
    }


    // Milestone 3: Load from file

    private static void loadPetsFromFile() {
        File file = new File(DATA_FILE);

        // If file doesn't exist, start empty (not an error)
        if (!file.exists()) {
            return;
        }

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine() && petCount < MAX_PETS) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue;

                // Expect: "name age"
                String[] parts = line.split("\\s+");
                if (parts.length != 2) {
                    // skip bad lines
                    continue;
                }

                String name = parts[0];
                int age;
                try {
                    age = Integer.parseInt(parts[1]);
                } catch (NumberFormatException e) {
                    continue;
                }

                // Only load valid ages
                if (age < MIN_AGE || age > MAX_AGE) {
                    continue;
                }

                pets[petCount] = new Pet(name, age);
                petCount++;
            }
        } catch (FileNotFoundException e) {
            // Shouldn't happen because we checked exists(), but safe anyway
            System.out.println("Error: Could not load data file.");
        }
    }


    // Milestone 3: Save to file

    private static void savePetsToFile() {
        try (PrintWriter out = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (int i = 0; i < petCount; i++) {
                out.println(pets[i].name + " " + pets[i].age);
            }
        } catch (IOException e) {
            System.out.println("Error: Could not save data file.");
        }
    }


    // View

    private static void viewAllPets() {
        System.out.println("+----------------------+");
        System.out.println("| ID | NAME | AGE |");
        System.out.println("+----------------------+");

        for (int i = 0; i < petCount; i++) {
            System.out.println("| " + i + " | " + pets[i].name + " | " + pets[i].age + " |");
        }

        System.out.println("+----------------------+");
        System.out.println(petCount + " rows in set.");
    }


    // Add

    private static void addPets(Scanner sc) {
        int added = 0;

        while (true) {
            if (petCount >= MAX_PETS) {
                System.out.println("Error: Database is full.");
                break;
            }

            System.out.print("add pet (name, age): ");
            String line = sc.nextLine().trim();

            if (line.equalsIgnoreCase("done")) {
                break;
            }

            // Must have exactly TWO values: name and age :contentReference[oaicite:9]{index=9}
            String[] parts = line.split("\\s+");
            if (parts.length != 2) {
                System.out.println("Error: " + line + " is not a valid input.");
                continue;
            }

            String name = parts[0];
            String ageStr = parts[1];

            int age;
            try {
                age = Integer.parseInt(ageStr);
            } catch (NumberFormatException e) {
                System.out.println("Error: " + line + " is not a valid input.");
                continue;
            }

            // Age must be 1 to 20 :contentReference[oaicite:10]{index=10}
            if (age < MIN_AGE || age > MAX_AGE) {
                System.out.println("Error: " + age + " is not a valid age.");
                continue;
            }

            pets[petCount] = new Pet(name, age);
            petCount++;
            added++;
        }

        System.out.println(added + " pets added.");
    }

// Remove

    private static void removePet(Scanner sc) {
        // Show table first (like sample)
        viewAllPets();

        System.out.print("Enter the pet ID to remove: ");
        String line = sc.nextLine().trim();

        int id;
        try {
            id = Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("Error: ID " + line + " does not exist.");
            return;
        }

        // ID must be an index of the array }
        if (id < 0 || id >= petCount) {
            System.out.println("Error: ID " + id + " does not exist.");
            return;
        }

        String removedName = pets[id].name;
        int removedAge = pets[id].age;

        // Shift left to fill the gap
        for (int i = id; i < petCount - 1; i++) {
            pets[i] = pets[i + 1];
        }

        pets[petCount - 1] = null;
        petCount--;

        System.out.println(removedName + " " + removedAge + " is removed.");
    }


    private static class Pet {
        String name;
        int age;

        Pet(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}
