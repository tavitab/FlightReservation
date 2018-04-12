package domain;

public enum SeatTypes {
	
	FIRST_CLASS("First Class"),
	BUSINESS_CLASS("Business Class"),
	EMERGENCY("Emergency"),
	COACH("Coach");
	
	private String name;
	
	public String getName() {
		return name;
	}
	
	SeatTypes(String name) {
		this.name = name;
	}
	
}
