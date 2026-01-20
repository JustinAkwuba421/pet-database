import java.util.ArrayList;

public class PetDatabase {

    private ArrayList<Pet> pets = new ArrayList<>();

    public int size() {
        return pets.size();
    }

    public void addPet(String name, int age) {
        pets.add(new Pet(name, age));
    }

    public void showAllPets() {
        printHeader();
        for (int i = 0; i < pets.size(); i++) {
            printRow(i, pets.get(i));
        }
        printFooter(pets.size());
    }

   // searrch

    public void searchByName(String name) {
        printHeader();
        int count = 0;

        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i).getName().equalsIgnoreCase(name)) {
                printRow(i, pets.get(i));
                count++;
            }
        }

        printFooter(count);
    }

    public void searchByAge(int age) {
        printHeader();
        int count = 0;

        for (int i = 0; i < pets.size(); i++) {
            if (pets.get(i).getAge() == age) {
                printRow(i, pets.get(i));
                count++;
            }
        }

        printFooter(count);
    }

    //update abd remove 

    public boolean updatePet(int id, String newName, int newAge) {
        if (id < 0 || id >= pets.size()) {
            return false;
        }

        Pet p = pets.get(id);
        String oldName = p.getName();
        int oldAge = p.getAge();

        p.setName(newName);
        p.setAge(newAge);

        System.out.println(oldName + " " + oldAge + " changed to " + newName + " " + newAge + ".");
        return true;
    }

    public boolean removePet(int id) {
        if (id < 0 || id >= pets.size()) {
            return false;
        }

        Pet removed = pets.remove(id);
        System.out.println(removed.getName() + " " + removed.getAge() + " is removed.");
        return true;
    }

 

    private void printHeader() {
        System.out.println("+----------------------+");
        System.out.printf("| %-3s| %-10s| %-4s|%n", "ID", "NAME", "AGE");
        System.out.println("+----------------------+");
    }

    private void printRow(int id, Pet pet) {
        System.out.printf("| %-3d| %-10s| %-4d|%n", id, pet.getName(), pet.getAge());
    }

    private void printFooter(int rows) {
        System.out.println("+----------------------+");
        System.out.println(rows + " rows in set.");
    }
}


