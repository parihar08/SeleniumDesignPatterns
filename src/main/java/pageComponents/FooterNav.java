package pageComponents;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FooterNav extends abstractComponents.AbstractComponent{
	
	//when selenium executes any method in this class, scope of selenium
	//should be in the footer only
	//To restrict the scope, we should get the locator of the entire section
	WebDriver driver;
	
	//By sectionElement = By.id("traveller-home");
	By flights = By.cssSelector("[title='Flights']");
	By links = By.cssSelector("a");
	
	public FooterNav(WebDriver driver, By sectionElement) {
		super(driver,sectionElement); 
		//When you inherit the parent class, you should invoke parent class constructor
		//in the child class constructor
	}

	//method to handle flights
	public String getFlightAttribute(){
		
	//driver.findElement(flights).click();
	//instead of writing driver.findElement,use sectionElement.findElement to restrict the scope 
	//and create custom find element using Abstract Component class(to create reusable methods) 
		
		return findElement(flights).getAttribute("class");
		//findElement(flights).click();
		
	}
	
	public int getLinksCount(){
		return findElements(links).size();
	}
}
