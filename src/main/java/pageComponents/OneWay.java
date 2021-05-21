package pageComponents;

import java.util.HashMap;

import abstractComponents.SearchFlightAvail;

public class OneWay implements SearchFlightAvail{

	public void checkAvailability(String origin, String destination) {
		
		System.out.println("Inside one way trip");
		
		
	}
	
	//Using Hashmap Concept
	@Override
	public void checkAvailability(HashMap<String, String> reservationDetails) {
		// TODO Auto-generated method stub
		
	}

}
