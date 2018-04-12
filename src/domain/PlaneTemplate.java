package domain;

import java.util.HashMap;

public class PlaneTemplate {
	
	private String name;
	public HashMap<SeatTypes, Integer> seats = new HashMap<SeatTypes, Integer>();
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void addSeats(SeatTypes seatType, Integer numberOfSeats) {
		seats.put(seatType, numberOfSeats);
	}
	
}
