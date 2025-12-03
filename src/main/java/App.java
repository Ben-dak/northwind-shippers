import java.util.ArrayList;
import java.util.Scanner;

public class App {

    // this class is main and will have scanner and screens etc
    public static void main(String[] args) {
        DataManager dataManager = new DataManager();
        Scanner myScanner = new Scanner(System.in);

        System.out.println("Enter new shipper company name: ");
        String name = myScanner.nextLine();
        System.out.println("Enter phone: ");
        String phone = myScanner.nextLine();

        int newId = dataManager.insertShipper(name, phone);
        System.out.println("New shipper inserted with ID: " + newId);

        System.out.println("All Shippers");
        printShippers(dataManager.getAllShippers());

        System.out.println("Enter shipper ID to update phone: ");
        int updateId = Integer.parseInt(myScanner.nextLine());
        System.out.println("Enter new phone: ");
        String newPhone = myScanner.nextLine();
        dataManager.updateShipperPhone(updateId, newPhone);

        System.out.println("All Shippers - updated");
        printShippers(dataManager.getAllShippers());

        System.out.println("Enter shipper ID to delete (DO NOT ENTER SHIPPERS 1-3 (Delete my new shipper)): ");
        int deleteId = Integer.parseInt(myScanner.nextLine());

        System.out.println("Delete this shipper? (y/n)? ");
        if (myScanner.nextLine().trim().equalsIgnoreCase("y")) {
            dataManager.deleteShipper(deleteId);
        }

        System.out.println("all shippers - updated");
        printShippers(dataManager.getAllShippers());
    }

    private static void printShippers(ArrayList<Shipper> shippers) {
        System.out.println("ID  Name  Phone");

        // int i = 0 creates a counter named i, starting at 0 (ArrayList indexes start at 0)
        // i++ adds 1 to i after each loop, moving from 0 → 1 → 2 … up to size()-1.
        for (int i = 0; i < shippers.size(); i++) {
            // shippers.get(i) fetches the element at position i from the list (constant-time lookup for ArrayList)
            Shipper shipper = shippers.get(i);

            int id = shipper.getShipperId();
            String name = shipper.getName();
            String phone = shipper.getPhone();

            System.out.println(id + " " + name + " " + phone);
        }
    }
}

