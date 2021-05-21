package org.spariharconsultinginc.DesignPatterns;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import pageComponents.MultiCity;
import pageComponents.OneWay;
import pageComponents.RoundTrip;
import pageObjects.TravelHomePage;

public class DemoTest {
	
	By sectionElement = By.id("flightSearchContainer");
	
	@Test(dataProvider="getData")
	public void flightTest(HashMap<String, String> resDetails) throws InterruptedException{
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		
		TravelHomePage travelHomepage = new TravelHomePage(driver);
		travelHomepage.goTo();
		Thread.sleep(2000);
		
		//1. Using Single Responsibility Principle, this class will redirect
		//us to the proper sub-classes
		
		System.out.println(travelHomepage.getFooterNav().getFlightAttribute()); 	//group-traveller
		System.out.println(travelHomepage.getFooterNav().getLinksCount());		//14
		
		System.out.println(travelHomepage.getNavigationBar().getFlightAttribute());	//selected
		System.out.println(travelHomepage.getNavigationBar().getLinksCount());		//6
		
		//2. We need to achieve the below code using Strategy Design Pattern without breaking
		//Single Responsibility Principle. Strategy Design Pattern is a behavioral design pattern
		//that allows us to select an algorithm at run time.
		//We will create interface to accomplish this step and define checkAvailbility() method there
		
		//travelHomepage.getLinksCount().("footer");
		//travelHomepage.checkAvailability().("oneway");
		//travelHomepage.checkAvailability().("roundtrip");	
		
		travelHomepage.setBookingStrategy(new OneWay());
		travelHomepage.checkAvailability("MAA", "CCU");	//Inside one way trip
		
		//travelHomepage.setBookingStrategy(new RoundTrip());
		travelHomepage.setBookingStrategy(new RoundTrip(driver,sectionElement));
		travelHomepage.checkAvailability("MAA", "CCU");		//Inside round trip
		
		travelHomepage.setBookingStrategy(new MultiCity(driver,sectionElement));
		travelHomepage.checkAvailability("MAA", "CCU");		//Inside multi city trip
			
		Thread.sleep(3000);
		
		//3. Factory Design Pattern - Creating objects without specifying the exact class
		//of the object it has to create. In Factory pattern, we create objects by calling
		//factory method rather than calling a constructor.For example:
		//travelHomepage.setBookingStrategy("multiCity");

		travelHomepage.setBookingStrategy("multiCity");	//using overloaded factory method
		travelHomepage.checkAvailability("MAA", "CCU");
		
		Thread.sleep(3000);
		
		travelHomepage.setBookingStrategy("roundTrip");	//using overloaded factory method
		travelHomepage.checkAvailability("MAA", "CCU");
		
		Thread.sleep(3000);
		
		//4. Execute Around Pattern - It is a pattern where we do things which are always required
		
		
		//5. Implementing HashMap
		HashMap<String, String> reservationDetails = new HashMap<String, String>();
		reservationDetails.put("origin", "MAA");
		reservationDetails.put("destination", "HYD");
		reservationDetails.put("origin2", "DEL");
		
		travelHomepage.setBookingStrategy("roundTrip");
		travelHomepage.checkAvailability(reservationDetails);
		
		Thread.sleep(3000);
		
		//6 Parameterization to run same test with different data set(reservationDetails)
		//using TestNG Data Providers
		
		travelHomepage.setBookingStrategy("roundTrip");
		travelHomepage.checkAvailability(resDetails);
		
		driver.quit();	
	}
	
	@DataProvider
	public Object[][] getData(){
		HashMap<String, String> resDetails = new HashMap<String, String>();
		resDetails.put("origin", "MAA");
		resDetails.put("destination", "HYD");
		resDetails.put("origin2", "DEL");
		
//		HashMap<String, String> resDetails1 = new HashMap<String, String>();
//		resDetails1.put("origin", "MAA");
//		resDetails1.put("destination", "HYD");
//		resDetails1.put("origin2", "DEL");
		
		return new Object[][]{
			{resDetails}
			//if we want to have 2 different datasets, we have to return different datasets like
			//{resDetails},{resDetails1}
		};
	}
	

}
