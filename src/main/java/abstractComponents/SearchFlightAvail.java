package abstractComponents;

import java.util.HashMap;

import org.openqa.selenium.By;

public interface SearchFlightAvail {
	
	void checkAvailability(String origin, String destination);
	
	//let's use the concept of HashMap instead of passing Strings to the checkAvailability method
	//In that way we can either pass 2 arguments for round trip or 4 arguments for multicity trips
	
	void checkAvailability(HashMap<String, String> reservationDetails);
}
