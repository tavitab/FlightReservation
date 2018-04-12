package services;

import java.util.ArrayList;
import java.util.List;

import domain.Flight;
import domain.Reservation;
import domain.SeatTypes;

public class FlightService {
	
	private static FlightService instance = null;
	List<Flight> flightList = new ArrayList<Flight>();
	
	private FlightService() {
	}
	
	public void addFlight(Flight flight) {
		flight.setFlightNumber(flightList.size() + 1);
		flightList.add(flight);
	}
	
	public Flight getFlightByFlightNumber(int flightNumber) {
		for(Flight flight: flightList) {
			if(flightNumber == flight.getFlightNumber()) {
				return flight;
			}
		}
		return null;
	}
	
	public int getNumberOfSeatsAvailable(int id, SeatTypes seatType) {
		Flight flight = getFlightByFlightNumber(id);
		List<Reservation> reservationList = flight.getReservationList();
		int numberOfSeatsAvailable = flight.getPlaneType().seats.get(seatType);
		for(Reservation reservation : reservationList) {
			if(reservation.getSeatType().equals(seatType)) {
				numberOfSeatsAvailable = numberOfSeatsAvailable - 1;
			}
		}
		return numberOfSeatsAvailable;
	}
	
	public List<Reservation> getReservationsByCustomerName(String customerName) {
		List<Reservation> customerReservationList = new ArrayList<Reservation>();
		for(Flight flight : flightList) {
			for(Reservation reservation : flight.getReservationList()) {
				if(customerName.equals(reservation.customerName)) {
					customerReservationList.add(reservation);
				}
			}
		}
		return customerReservationList;
	}
	
	public boolean deleteReservationByCustomerName(String customerName) {
		Integer flightNumber = null;
		Integer index = null;
		for(Flight flight : flightList) {
			for(Reservation reservation : flight.getReservationList()) {
				if(customerName.equals(reservation.getCustomerName())) {
					flightNumber = flight.getFlightNumber();
					index = flight.getReservationList().indexOf(reservation);
				}
			}
		}
		if(flightNumber != null && index != null) {
			Flight flight = getFlightByFlightNumber(flightNumber);
			flight.getReservationList().remove(index.intValue());
			return true;
		}
		return false;
	}
	
	public List<Reservation> getReservationsByFlightNumber(int flightNumber) {
		for(Flight flight : flightList) {
			if(flightNumber == flight.getFlightNumber()) {
				return flight.getReservationList();
			}
		}
		return new ArrayList<Reservation>();
	}
	
	public List<Flight> getAllFlights() {
		return flightList;
	}
	
	public static FlightService getInstance() {
		if(instance == null) {
			instance = new FlightService();
		}
		return instance;
	}
}