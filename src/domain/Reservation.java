package domain;

public class Reservation {
	
	public String customerName;
	public int flightNumber;
	public SeatTypes seatType;
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public int getFlightNumber() {
		return flightNumber;
	}
	
	public void setFlightNumber(int flightNumber) {
		this.flightNumber = flightNumber;
	}
	
	public SeatTypes getSeatType() {
		return seatType;
	}
	
	public void setSeatType(SeatTypes seatType) {
		this.seatType = seatType;
	}
	
}
