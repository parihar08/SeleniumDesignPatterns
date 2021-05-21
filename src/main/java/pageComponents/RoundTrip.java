package pageComponents;

import java.util.HashMap;
import java.util.function.Consumer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import abstractComponents.AbstractComponent;
import abstractComponents.SearchFlightAvail;

public class RoundTrip extends AbstractComponent implements SearchFlightAvail{
	
	public RoundTrip(WebDriver driver, By sectionElement) {
		super(driver, sectionElement);
	}

	private By tripType = By.id("ctl00_mainContent_rbtnl_Trip_1");
	private By from = By.id("ctl00_mainContent_ddl_originStation1_CTXT");
	private By to = By.id("ctl00_mainContent_ddl_destinationStation1_CTXT");
	private By checkBox = By.id("ctl00_mainContent_chk_IndArm");
	private By searchBox = By.id("ctl00_mainContent_btn_FindFlights");
	

	public void checkAvailability(String origin, String destination) {
		System.out.println("Inside round trip");
		findElement(tripType).click();
		selectOriginCity(origin);
		selectDestinationCity(destination);
		findElement(checkBox).click();	
		findElement(searchBox).click();
		
	}
	
	//Using Hashmap Concept
	@Override
	public void checkAvailability(HashMap<String, String> reservationDetails) {
		makeStateReady(s ->selectOriginCity(reservationDetails.get("origin")));
		selectDestinationCity(reservationDetails.get("destination"));
		findElement(checkBox).click();	
		findElement(searchBox).click();
		
	}

	
	public void selectOriginCity(String origin){
		findElement(from).click();
		findElement(By.xpath("//a[@value='"+origin+"']")).click();
	}
	
	public void selectDestinationCity(String destination){
		findElement(to).click();
		findElement(By.xpath("(//a[@value='"+destination+"'])[2]")).click();
		//findElement(By.cssSelector("a[value='"+destination+"']:nth-of-type(2)")).click();
		
	}

	//For Execute Around Pattern 
		public void makeStateReady(Consumer<RoundTrip> consumer){
			//common pre-requisite code- e.g. select multicity radio button and click on popup button
			System.out.println("Inside round trip");
			findElement(tripType).click();
			//execute actual function - originCitySelection/book/calendar
			//the method which we want to execute, we will send dynamically as argument to our makeStateReady method
			consumer.accept(this);
			//tear down method
			System.out.println("I'm done!");
		}

}
