import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * ================================
 * Bus Reservation System
 * Single File Version
 *
 * Classes:
 * 1. BusReservationSystem (Main)
 * 2. Bus
 * 3. Booking
 * ================================
 */

public class BusReservationSystem {

    private static List<Bus> busList = new ArrayList<>();
    private static List<Booking> bookingList = new ArrayList<>();

    private static Scanner scanner = new Scanner(System.in);

    private static int bookingCounter = 1;


    public static void main(String[] args) {

        // Sample buses
        busList.add(new Bus("B1", "Colombo - Kandy", 40));
        busList.add(new Bus("B2", "Colombo - Galle", 30));


        boolean running = true;

        while (running) {

            printMenu();

            int choice = readInt("Enter your choice: ");

            switch(choice) {

                case 1:
                    addBus();
                    break;

                case 2:
                    viewAllBuses();
                    break;

                case 3:
                    updateBusCapacity();
                    break;

                case 4:
                    deleteBus();
                    break;

                case 5:
                    bookTicket();
                    break;

                case 6:
                    viewAllBookings();
                    break;

                case 0:
                    running = false;
                    System.out.println("Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

            System.out.println();

        }

        scanner.close();
    }



    // Display menu
    private static void printMenu() {

        System.out.println("==============================");
        System.out.println(" Bus Reservation System");
        System.out.println("==============================");

        System.out.println("1. Add Bus");
        System.out.println("2. View All Buses");
        System.out.println("3. Update Bus Capacity");
        System.out.println("4. Delete Bus");
        System.out.println("5. Book Ticket");
        System.out.println("6. View All Bookings");
        System.out.println("0. Exit");
    }



    // Add new bus
    private static void addBus() {

        System.out.print("Enter Bus ID: ");
        String id = scanner.nextLine();


        if(findBusById(id) != null) {

            System.out.println("Bus ID already exists!");
            return;
        }


        System.out.print("Enter Route: ");
        String route = scanner.nextLine();


        int capacity = readInt("Enter Capacity: ");


        if(capacity <= 0) {

            System.out.println("Invalid capacity!");
            return;
        }


        Bus bus = new Bus(id, route, capacity);

        busList.add(bus);


        System.out.println("Bus added successfully!");
    }




    // View buses
    private static void viewAllBuses() {


        if(busList.isEmpty()) {

            System.out.println("No buses available.");
            return;
        }


        System.out.println("\n------ Bus List ------");


        for(Bus bus : busList) {

            System.out.println(bus);
        }

    }




    // Update capacity
    private static void updateBusCapacity() {


        System.out.print("Enter Bus ID: ");

        String id = scanner.nextLine();



        Bus bus = findBusById(id);


        if(bus == null) {

            System.out.println("Bus not found!");
            return;
        }



        int newCapacity = readInt("Enter new capacity: ");



        if(newCapacity < bus.getBookedSeats()) {

            System.out.println(
              "Capacity cannot be less than booked seats!"
            );

            return;
        }



        bus.updateCapacity(newCapacity);


        System.out.println("Capacity updated successfully!");

    }
        // Delete Bus
    private static void deleteBus() {

        System.out.print("Enter Bus ID to delete: ");

        String id = scanner.nextLine();


        Bus bus = findBusById(id);


        if(bus == null) {

            System.out.println("Bus not found!");
            return;
        }


        // Check existing bookings
        for(Booking booking : bookingList) {

            if(booking.getBusId().equalsIgnoreCase(id)) {

                System.out.println(
                    "Cannot delete. This bus has bookings."
                );

                return;
            }
        }


        busList.remove(bus);


        System.out.println("Bus deleted successfully!");

    }





    // Book Ticket
    private static void bookTicket() {


        System.out.print("Enter Bus ID: ");

        String id = scanner.nextLine();



        Bus bus = findBusById(id);



        if(bus == null) {

            System.out.println("Bus not found!");
            return;
        }



        System.out.print("Passenger Name: ");

        String name = scanner.nextLine();



        int seats = readInt("Number of seats: ");




        if(seats <= 0) {

            System.out.println("Invalid seat count!");
            return;
        }



        if(seats > bus.getAvailableSeats()) {


            System.out.println(
                "Not enough seats available!"
            );

            return;
        }



        String bookingId = "BK" + bookingCounter++;



        Booking booking =
                new Booking(
                    bookingId,
                    name,
                    id,
                    seats
                );



        bookingList.add(booking);



        bus.bookSeats(seats);



        System.out.println(
            "Booking successful!"
        );


        System.out.println(booking);


        System.out.println(
            "Remaining Seats : "
            + bus.getAvailableSeats()
        );

    }





    // View all bookings
    private static void viewAllBookings() {


        if(bookingList.isEmpty()) {

            System.out.println(
                "No bookings available."
            );

            return;
        }



        System.out.println(
            "\n------ Booking List ------"
        );



        for(Booking booking : bookingList) {

            System.out.println(booking);

        }

    }




    // Find bus using ID
    private static Bus findBusById(String id) {


        for(Bus bus : busList) {


            if(bus.getBusId()
                    .equalsIgnoreCase(id)) {


                return bus;
            }

        }


        return null;
    }





    // Read integer safely
    private static int readInt(String message) {


        while(true) {


            try {


                System.out.print(message);


                return Integer.parseInt(
                    scanner.nextLine()
                );


            } catch(Exception e) {


                System.out.println(
                    "Enter valid number!"
                );

            }

        }

    }

}





// ================= BUS CLASS =================


class Bus {


    private String busId;

    private String route;

    private int capacity;

    private int availableSeats;



    public Bus(
            String busId,
            String route,
            int capacity
    ) {


        this.busId = busId;

        this.route = route;

        this.capacity = capacity;

        this.availableSeats = capacity;

    }



    public String getBusId() {

        return busId;

    }



    public int getAvailableSeats() {

        return availableSeats;

    }



    public int getBookedSeats() {

        return capacity - availableSeats;

    }



    public void updateCapacity(int newCapacity) {


        this.capacity = newCapacity;


        this.availableSeats =
                newCapacity - getBookedSeats();

    }




    public void bookSeats(int seats) {


        availableSeats -= seats;

    }




    public String toString() {


        return

        "Bus ID: " + busId +

        " | Route: " + route +

        " | Capacity: " + capacity +

        " | Available Seats: " + availableSeats;

    }

}






// ================= BOOKING CLASS =================


class Booking {


    private String bookingId;

    private String passengerName;

    private String busId;

    private int seats;



    public Booking(
            String bookingId,
            String passengerName,
            String busId,
            int seats
    ) {


        this.bookingId = bookingId;

        this.passengerName = passengerName;

        this.busId = busId;

        this.seats = seats;

    }



    public String getBusId() {

        return busId;

    }




    public String toString() {


        return

        "Booking ID: " + bookingId +

        " | Passenger: " + passengerName +

        " | Bus ID: " + busId +

        " | Seats: " + seats;

    }

}
