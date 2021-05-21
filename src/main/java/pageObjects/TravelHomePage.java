package pageObjects;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import abstractComponents.SearchFlightAvail;
import abstractComponents.StrategyFactory;
import pageComponents.FooterNav;
import pageComponents.NavigationBar;

public class TravelHomePage {
	//Using Single Responsibility Principle, this class will redirect
	//us to the proper sub-classes
	
	WebDriver driver;
	By footerNavSectionElement = By.id("traveller-home");
	By navBarsectionElement = By.id("buttons");
	SearchFlightAvail searchFlightAvail;
	
	public TravelHomePage(WebDriver driver) {
		this.driver = driver;
	}

	public void goTo(){
		driver.get("https://rahulshettyacademy.com/dropdownsPractise/");
	}
		
	public NavigationBar getNavigationBar(){
		return new NavigationBar(driver, navBarsectionElement);
	}
	
	public FooterNav getFooterNav(){
		return new FooterNav(driver,footerNavSectionElement);
	}
	
	//Using Strategy Design Pattern
	//Instead of having return type of MultiCity or OneWay or RoundTrip class, we can have 
	//return type of SearchFlightAvail interface of whose checkAvailability() these classes are implementing
	
	//public void setBookingStrategy(MultiCity multiCity){
	public void setBookingStrategy(SearchFlightAvail searchFlightAvail){
		
		this.searchFlightAvail = searchFlightAvail;	
	}
	
	public SearchFlightAvail setBookingStrategy(String strategyType){
		
		StrategyFactory strategyFactory = new StrategyFactory(driver);
		return strategyFactory.createStrategy(strategyType);
	}
	
	public void checkAvailability(String origin, String destination){
		searchFlightAvail.checkAvailability(origin, destination);
	}
	
	//Using HashMap concept
	public void checkAvailability(HashMap<String, String> reservationDetails){
		searchFlightAvail.checkAvailability(reservationDetails);
	}
}
