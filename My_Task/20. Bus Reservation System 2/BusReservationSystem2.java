import java.util.Scanner;
import java.util.ArrayList;

// Bus Class
class Bus{
    private String busNumber;
    private String source;
    private String destination;
    private int capacity;
   private int bookedSeats;

    public Bus(String busNumber, String source, String destination, int capacity) {
        this.busNumber = busNumber;
        this.source = source;
        this.destination = destination;
        this.capacity = capacity;
        this.bookedSeats = 0;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getBookedSeats() {
        return bookedSeats;
    }

    
    public void setCapacity(int capacity) {
    this.capacity = capacity;
}
        
    
        public void bookSeat() {
            bookedSeats++;

    } 
    public void cancelSeat() {
        if (bookedSeats > 0) {
            bookedSeats--;
        }
    }
    public boolean seatsAvailable() {
    return bookedSeats < capacity;
}
    
     @Override
    public String toString() {
        return String.format("%-10s %-15s %-15s %-10d %-10d",
                busNumber, source, destination, capacity, bookedSeats);
}
}

// Bookings Class
class Booking {
     private String passengerId;
    private String passengerName;
    private Bus bus;

    public Booking(String passengerId, String passengerName, Bus bus) {
        this.passengerId = passengerId;
        this.passengerName = passengerName;
        this.bus = bus;
    }
    public String getPassengerId() {
        return passengerId;
    }
    public String getPassengerName() {
        return passengerName;
    }
    public Bus getBus() {
        return bus;
    }
}

//Main Class
public class BusReservationSystem2 {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Bus> buses = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();

    public static void main(String[] args) {
        int choice;
        do {
           System.out.println("\n======================================");
            System.out.println("      BUS RESERVATION SYSTEM");
            System.out.println("======================================");
            System.out.println("1. Add Bus");
            System.out.println("2. View All Buses");
            System.out.println("3. Update Bus Capacity");
            System.out.println("4. Delete Bus");
            System.out.println("5. Search Bus");
            System.out.println("6. Book Ticket");
            System.out.println("7. View Bookings");
            System.out.println("8. Cancel Booking");
            System.out.println("9. Exit");
            System.out.println("======================================");
            System.out.print("Enter your choice : ");

            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addBus();
                    break;
                case 2:
                    viewBuses();
                    break;

                case 3:
                    updateBus();
                    break;

                case 4:
                    deleteBus();
                    break;

                case 5:
                    searchBus();
                    break;

                case 6:
                    bookTicket();
                    break;
                
                case 7:
                    viewBookings();
                    break;

                case 8:
                    cancelBooking();
                    break;

                case 9:
                     System.out.println("\nThank You...");
                    System.out.println("Program Closed Successfully.");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 9);
    }

    static void addBus(){

        System.out.print("Enter bus Number: ");
         String busNo = sc.nextLine();

    // Check duplicate bus number
       for (Bus b : buses) {
        if (b.getBusNumber().equalsIgnoreCase(busNo)) {
            System.out.println("Bus Number already exists!");
            return;
        }
    }
      System.out.print("Enter Source: ");
    String source = sc.nextLine();

    System.out.print("Enter Destination: ");
    String destination = sc.nextLine();

    System.out.print("Enter Capacity: ");
    int capacity = sc.nextInt();
    sc.nextLine();

    buses.add(new Bus(busNo, source, destination, capacity));

    System.out.println("Bus Added Successfully!");
}


// view buses
     
      static void viewBuses() {

    if (buses.isEmpty()) {
        System.out.println("No buses available.");
        return;
    }

    System.out.println("\n--------------------------------------------------------------------------");
    System.out.printf("%-10s %-15s %-15s %-10s %-10s%n",
            "Bus No", "Source", "Destination", "Capacity", "Booked");
    System.out.println("--------------------------------------------------------------------------");

    for (Bus b : buses) {
        System.out.println(b);
    }
}

 // update bus
 static void updateBus() {

    System.out.print("Enter Bus Number: ");
    String busNo = sc.nextLine();

    for (Bus b : buses) {

        if (b.getBusNumber().equalsIgnoreCase(busNo)) {

            System.out.print("Enter New Capacity: ");
            int newCapacity = sc.nextInt();
            sc.nextLine();

            if (newCapacity < b.getBookedSeats()) {
                System.out.println("Capacity cannot be less than booked seats.");
                return;
            }

            b.setCapacity(newCapacity);

            System.out.println("Bus Capacity Updated Successfully!");
            return;
        }
    }

    System.out.println("Bus Not Found.");
}

     // delete bus
     static void deleteBus() {

    System.out.print("Enter Bus Number: ");
    String busNo = sc.nextLine();

    for (int i = 0; i < buses.size(); i++) {

        if (buses.get(i).getBusNumber().equalsIgnoreCase(busNo)) {

            buses.remove(i);

            System.out.println("Bus Deleted Successfully!");
            return;
        }
    }

    System.out.println("Bus Not Found.");
} 

// SEARCH BUS 

static void searchBus() {

    System.out.print("Enter Bus Number: ");
    String busNo = sc.nextLine();

    for (Bus b : buses) {

        if (b.getBusNumber().equalsIgnoreCase(busNo)) {

            System.out.println("\nBus Found");
            System.out.println("---------------------------------------------");
            System.out.println("Bus Number   : " + b.getBusNumber());
            System.out.println("Source       : " + b.getSource());
            System.out.println("Destination  : " + b.getDestination());
            System.out.println("Capacity     : " + b.getCapacity());
            System.out.println("Booked Seats : " + b.getBookedSeats());
            return;
        }
    }

    System.out.println("Bus Not Found.");
}

//  BOOK TICKET 
static void bookTicket() {

    System.out.print("Enter Passenger ID: ");
    String passengerId = sc.nextLine();

    // Check duplicate Passenger ID
    for (Booking booking : bookings) {
        if (booking.getPassengerId().equalsIgnoreCase(passengerId)) {
            System.out.println("Passenger ID already exists!");
            return;
        }
    }

    System.out.print("Enter Passenger Name: ");
    String passengerName = sc.nextLine();

    System.out.print("Enter Bus Number: ");
    String busNo = sc.nextLine();

    Bus bus = findBus(busNo);

    if (bus == null) {
        System.out.println("Bus Not Found.");
        return;
    }

    if (!bus.seatsAvailable()) {
        System.out.println("No Seats Available.");
        return;
    }

    bus.bookSeat();

    bookings.add(new Booking(passengerId, passengerName, bus));

    System.out.println("Ticket Booked Successfully!");
}

//  VIEW BOOKINGS 
static void viewBookings() {

    if (bookings.isEmpty()) {
        System.out.println("No Bookings Available.");
        return;
    }

    System.out.println("\n================ BOOKINGS ================\n");

    for (Booking booking : bookings) {

        Bus bus = booking.getBus();

        System.out.println("Passenger ID   : " + booking.getPassengerId());
        System.out.println("Passenger Name : " + booking.getPassengerName());
        System.out.println("Bus Number     : " + bus.getBusNumber());
        System.out.println("Source         : " + bus.getSource());
        System.out.println("Destination    : " + bus.getDestination());
        System.out.println("-------------------------------------------");
    }
}

// ====================== CANCEL BOOKING ======================
static void cancelBooking() {

    System.out.print("Enter Passenger ID: ");
    String passengerId = sc.nextLine();

    Booking booking = findBooking(passengerId);

    if (booking == null) {
        System.out.println("Booking Not Found.");
        return;
    }

    booking.getBus().cancelSeat();

    bookings.remove(booking);

    System.out.println("Booking Cancelled Successfully!");
}

//  HELPER METHOD 
static Bus findBus(String busNo) {

    for (Bus bus : buses) {

        if (bus.getBusNumber().equalsIgnoreCase(busNo)) {
            return bus;
        }

    }

    return null;
}

//  HELPER METHOD 
static Booking findBooking(String passengerId) {

    for (Booking booking : bookings) {

        if (booking.getPassengerId().equalsIgnoreCase(passengerId)) {
            return booking;
        }

    }

    return null;
}
    }


