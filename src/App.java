import java.util.List;
import java.util.Scanner;

import domain.Flight;
import domain.PlaneTemplate;
import domain.Reservation;
import domain.SeatTypes;
import services.FlightService;
import services.PlaneTemplateService;

public class App {

	public static void main(String[] args) {
		
		boolean activeSession = true;
		
		while(activeSession) {
			runAirlineReservationApplication();
		}
	}
	
	public static void runAirlineReservationApplication() {
		displayMainMenu();
	}
	
	public static void displayMainMenu() {
		System.out.println("\n\n***** MAIN MENU *****");
		System.out.println("What would you like to do?");
		System.out.println("\t 1. Create a new flight \n\t 2. Get all flights \n\t 3. Create a new aircraft seating configuration \n\t 4. Reserve a seat on a flight \n\t 5. Search for a reservation \n\t 6. Cancel a reservation \n\t 7. Exit");
		Scanner sc = new Scanner(System.in);
		int selection = sc.nextInt();
		switch(selection) {
			case 1: createFlight();
				break;
			case 2: getAllFlights();
				break;
			case 3: configureNewAircraft();
				break;
			case 4: makeReservation();
				break;
			case 5: searchForReservation();
				break;
			case 6: cancelReservation();
				break;
			case 7:
				System.exit(1);
		default: System.out.println("You must enter a valid number!");
				 break;
		}
	}
	
	public static void createFlight() {
		System.out.println("*****  CREATE A NEW FLIGHT *****");
		PlaneTemplateService planeTemplateService = PlaneTemplateService.getInstance();
		List<PlaneTemplate> planeTemplateList = planeTemplateService.getAllPlaneTemplates();
		if(planeTemplateList.isEmpty()) {
			System.out.println("A plane must first be configured");
			return;
		}
		System.out.println("What type of plane will this flight be on?");
		Scanner sc = new Scanner(System.in);
		int i = 1;
		for(PlaneTemplate plane : planeTemplateList) {
			System.out.println(i + ":\t" + plane.getName() + "\n");
			i++;
		}
		
		int selection = sc.nextInt();
		sc.nextLine();
		PlaneTemplate selectedPlaneTemplate = planeTemplateList.get(selection-1);
		System.out.println("What is the local departure time?");
		Flight flight = new Flight();
		flight.setPlaneType(selectedPlaneTemplate);
		String departTime = sc.nextLine();
		flight.setLocalDepartTime(departTime);
		System.out.println("What is the local arrival time?");
		String arrivalTime = sc.nextLine();
		flight.setLocalArrivalTime(arrivalTime);
		FlightService flightService = FlightService.getInstance();
		flightService.addFlight(flight);
	}
	
	public static void getAllFlights() {
		FlightService flightService = FlightService.getInstance();
		List<Flight> flightList = flightService.getAllFlights();
		int i = 1;
		if(flightList.isEmpty()) {
			System.out.println("There are no flights to display");
			return;
		}
		for(Flight flight : flightList) {
			System.out.println(i + ".\tFlight number: " + flight.getFlightNumber() + "\t Departure Time: " + flight.getLocalDepartTime() + "\tArrival Time: " + flight.getLocalArrivalTime() + "\tPlaneType: " + flight.getPlaneType().getName());
			i++;
		}
	}
	
	public static void configureNewAircraft() {
		System.out.println("*****  CONFIGURE NEW AIRCRAFT *****");
		System.out.println("What is the name of the aircraft?");
		Scanner sc = new Scanner(System.in);
		String name = sc.nextLine();
		PlaneTemplate plane = new PlaneTemplate();
		plane.setName(name);
		
		for(SeatTypes seatType : SeatTypes.values()) {
			System.out.println("How many " + seatType.getName() + " seats?");
			int numberOfSeats = sc.nextInt();
			plane.addSeats(seatType, numberOfSeats);
		}
		
		PlaneTemplateService planeTemplateService = PlaneTemplateService.getInstance();
		planeTemplateService.addPlaneTemplate(plane);
	}
	
	public static SeatTypes chooseSeat() {
		System.out.println("What kind of seat?");
		Scanner sc = new Scanner(System.in);
		int i = 1;
		for(SeatTypes seatType : SeatTypes.values()) {
			System.out.println(i + ".\t" + seatType.getName());
			i++;
		}
		System.out.println("5.\tExit");
		SeatTypes seatType = null;
		int selection = sc.nextInt();
		switch(selection) {
			case 1:  seatType = SeatTypes.FIRST_CLASS;
				return seatType;
			case 2: seatType = SeatTypes.BUSINESS_CLASS;
				return seatType;
			case 3: seatType = SeatTypes.EMERGENCY;
				return seatType;
			case 4: seatType = SeatTypes.COACH;
				return seatType;
			case 5:
				System.exit(1);
			default:
				return null;
		}
	}
	
	public static boolean checkSeatsAvailable(Flight flight, SeatTypes seatType) {
		FlightService flightService = FlightService.getInstance();
		int numberOfSeatsAvailable = flightService.getNumberOfSeatsAvailable(flight.getFlightNumber(), seatType);
		
		if(numberOfSeatsAvailable > 0) {
			return true;
		}
		System.out.println("This flight has no more available " + seatType.getName() + " seats.");
		return false;
	}
	
	public static void makeReservation() {
		Reservation reservation = new Reservation();
		
		System.out.println("*****  MAKE A RESERVATION *****");
		System.out.println("Enter customer's name");
		Scanner sc = new Scanner(System.in);
		String customerName = sc.nextLine();
		
		
		reservation.setCustomerName(customerName);
		
		System.out.println("Enter a flight number");
		
		FlightService flightService = FlightService.getInstance();
		List<Flight> flightList = flightService.getAllFlights();
		
		for(Flight flight : flightList) {
			System.out.println("\tFlight number:\t" + flight.getFlightNumber());
		}
		int flightNumber = sc.nextInt();
		
		Flight flight = flightService.getFlightByFlightNumber(flightNumber);
		
		boolean seatsAvailable = false;
		SeatTypes seatType = null;
		while(!seatsAvailable) {
			seatType = chooseSeat();
			seatsAvailable = checkSeatsAvailable(flight, seatType);
		}
		reservation.setSeatType(seatType);
		reservation.setFlightNumber(flightNumber);
		flight.addReservation(reservation);
	}
	
	public static void searchForReservation() {
		System.out.println("***** SEARCH FOR RESERVATION(S) *****");
		System.out.println("1.\tSearch by customer name\n2.\tSearch by flight number");
		Scanner sc = new Scanner(System.in);
		int selection = sc.nextInt();
		switch(selection) {
		case 1: searchForReservationsByCustomerName();
			break;
		case 2: searchForReservationsByFlightNumber();
			break;
		default:
			break;
		}
	}
	
	public static void searchForReservationsByCustomerName() {
		System.out.println("Please enter the customer's name");
		Scanner sc = new Scanner(System.in);
		String customerName = sc.nextLine();
		FlightService flightService = FlightService.getInstance();
		List<Reservation> customerReservationList = flightService.getReservationsByCustomerName(customerName);
		if(customerReservationList.isEmpty()) {
			System.out.println("That customer has no active reservations.");
			return;
		}
		int i = 1;
		for(Reservation reservation : customerReservationList) {
			System.out.println(i + ".\tName: " + reservation.customerName + "\tFlight number: " + reservation.flightNumber + "\tSeat: " + reservation.getSeatType().getName());
			i++;
		}
	}
	
	public static void searchForReservationsByFlightNumber() {
		System.out.println("Please enter a flight number");
		Scanner sc = new Scanner(System.in);
		int flightNumber = sc.nextInt();
		FlightService flightService = FlightService.getInstance();
		List<Reservation> reservationList = flightService.getReservationsByFlightNumber(flightNumber);
		if(reservationList.isEmpty()) {
			System.out.println("There are no reservations on that flight");
			return;
		}
		int i = 1;
		for(Reservation reservation : reservationList) {
			System.out.println(i + ".\tName: " + reservation.getCustomerName());
			i++;
		}
	}
	
	public static void cancelReservation() {
		System.out.println("***** CANCEL RESERVATION *****");
		System.out.println("What is the name of the customer?");
		Scanner sc = new Scanner(System.in);
		String customerName = sc.nextLine();
		
		FlightService flightService = FlightService.getInstance();
		boolean success = flightService.deleteReservationByCustomerName(customerName);
		if(!success) {
			System.out.println("There is no reservation under that name");
		}
		
	}
}
