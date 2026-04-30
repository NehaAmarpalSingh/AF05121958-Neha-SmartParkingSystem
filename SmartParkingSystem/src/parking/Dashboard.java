package parking;

import java.util.Scanner;

public class Dashboard {

    Scanner sc = new Scanner(System.in);
    ParkingDAO dao = new ParkingDAO();

    public void menu() {
        while (true) {
            System.out.println("\n===== DASHBOARD =====");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Show Available Slots");
            System.out.println("3. Book Parking Slot");
            System.out.println("4. Vehicle Exit");
            System.out.println("5. Search Booking");
            System.out.println("6. Cancel Booking");
            System.out.println("7. Admin Panel");
            System.out.println("8. Logout");
            System.out.print("Enter Choice: ");

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1:
                    dao.addVehicle();
                    break;

                case 2:
                    dao.showSlots();
                    break;

                case 3:
                    dao.bookSlot();
                    break;

                case 4:
                    dao.vehicleExit();
                    break;

                case 5:
                    dao.searchBooking();
                    break;

                case 6:
                    dao.cancelBooking();
                    break;

                case 7:
                    adminPanel();
                    break;

                case 8:
                    System.out.println("Logged Out");
                    return;

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }

    public void adminPanel() {
        while (true) {
            System.out.println("\n===== ADMIN PANEL =====");
            System.out.println("1. Add Slot");
            System.out.println("2. Update Slot");
            System.out.println("3. Delete Slot");
            System.out.println("4. Back");
            System.out.print("Enter Choice: ");

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1:
                    dao.addSlot();
                    break;

                case 2:
                    dao.updateSlot();
                    break;

                case 3:
                    dao.deleteSlot();
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
}