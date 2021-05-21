package abstractComponents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pageComponents.MultiCity;
import pageComponents.RoundTrip;

public class StrategyFactory {
	
	WebDriver driver;
	By sectionElement = By.id("flightSearchContainer");
	
	public StrategyFactory(WebDriver driver) {
		this.driver = driver;
	}

	public SearchFlightAvail createStrategy(String strategyType){
		
		if(strategyType.equalsIgnoreCase("multiCity")){
			return new MultiCity(driver, sectionElement);
		}
		
		if(strategyType.equalsIgnoreCase("roundTrip")){
			return new RoundTrip(driver, sectionElement);
		}
		return null;
	
		
		
	}

}
