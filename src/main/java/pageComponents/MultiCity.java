package pageComponents;

import java.util.HashMap;
import java.util.function.Consumer;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import abstractComponents.AbstractComponent;
import abstractComponents.SearchFlightAvail;

public class MultiCity extends AbstractComponent implements SearchFlightAvail{

	public MultiCity(WebDriver driver, By sectionElement) {
		super(driver, sectionElement);
	}
	
	private By tripType = By.id("ctl00_mainContent_rbtnl_Trip_2");
	private By popUp = By.id("MultiCityModelAlert");
	private By from1 = By.id("ctl00_mainContent_ddl_originStation1_CTXT");
	private By to1 = By.id("ctl00_mainContent_ddl_destinationStation1_CTXT");
	private By from2 = By.id("ctl00_mainContent_ddl_originStation2_CTXT");
	private By to2 = By.id("ctl00_mainContent_ddl_destinationStation2_CTXT");
	private By checkBox = By.id("ctl00_mainContent_chk_IndArm");
	private By searchBox = By.id("ctl00_mainContent_btn_FindFlights");
	
	
	//Execute Around Pattern - It is a pattern where we do things which are always required
	public void checkAvailability(String origin, String destination) {
//		System.out.println("Inside multicity trip");
//		findElement(tripType).click();
//		findElement(popUp).click();	
//		selectOriginCity1(origin);	
//		selectDestinationCity1(destination);
//		selectOriginCity2(destination);
//		findElement(checkBox).click();	
//		findElement(searchBox).click();
		
		makeStateReady(s ->selectOriginCity1(origin)); //We want to run this method - selectOriginCity1(origin), after we make state ready(pre-requisites done)
		selectDestinationCity1(destination);
		selectOriginCity2(destination);
		findElement(searchBox).click();
	}
	
	//Using Hashmap Concept
	@Override
	public void checkAvailability(HashMap<String, String> reservationDetails) {
		makeStateReady(s ->selectOriginCity1(reservationDetails.get("origin")));
		selectDestinationCity1(reservationDetails.get("destination"));
		selectOriginCity2(reservationDetails.get("origin2"));
		findElement(searchBox).click();
		
	}
	
	public void selectOriginCity1(String origin){
		findElement(from1).click();
		findElement(By.xpath("//a[@value='"+origin+"']")).click();
	}
	
	public void selectDestinationCity1(String destination){
		findElement(to1).click();
		findElement(By.xpath("(//a[@value='"+destination+"'])[2]")).click();
		
	}
	
	public void selectOriginCity2(String origin){
		findElement(from2).click();
		findElement(By.xpath("(//a[@value='"+origin+"'])[3]")).click();
	}
	
	//For Execute Around Pattern 
	public void makeStateReady(Consumer<MultiCity> consumer){
		//common pre-requisite code- e.g. select multicity radio button and click on popup button
		System.out.println("Inside multicity trip");
		findElement(tripType).click();
		findElement(popUp).click();
		waitForElementToDisappear(popUp);
		//execute actual function - originCitySelection/book/calendar
		//the method which we want to execute, we will send dynamically as argument to our makeStateReady method
		consumer.accept(this);
		//tear down method
		System.out.println("I'm done!");
	}

	

}
