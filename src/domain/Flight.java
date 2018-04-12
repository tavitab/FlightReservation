package domain;

import java.util.ArrayList;
import java.util.List;

public class Flight {
	
	private int flightNumber;
	private String localDepartTime;
	private String localArrivalTime;
	private PlaneTemplate planeType;
	private List<Reservation> reservationList = new ArrayList<Reservation>();
	
	public void setLocalDepartTime(String localDepartTime) {
		this.localDepartTime = localDepartTime;
	}

	public void setLocalArrivalTime(String localArrivalTime) {
		this.localArrivalTime = localArrivalTime;
	}

	public void setPlaneType(PlaneTemplate planeType) {
		this.planeType = planeType;
	}

	public void setReservationList(List<Reservation> reservationList) {
		this.reservationList = reservationList;
	}

	public String getLocalDepartTime() {
		return localDepartTime;
	}

	public String getLocalArrivalTime() {
		return localArrivalTime;
	}

	public Flight() {
	}
	
	public void setFlightNumber(int flightNumber) {
		this.flightNumber = flightNumber;
	}

	public int getFlightNumber() {
		return flightNumber;
	}
	
	public PlaneTemplate getPlaneType() {
		return planeType;
	}
	
	public List<Reservation> getReservationList() {
		return reservationList;
	}
	
	public void addReservation(Reservation reservation) {
		reservationList.add(reservation);
	}
	
	public void deleteReservation(Reservation reservation) {
		reservationList.remove(reservation);
	}
	
}
