package org.spariharconsultinginc.DesignPatterns;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageComponents.MultiCity;
import pageComponents.OneWay;
import pageComponents.RoundTrip;
import pageObjects.TravelHomePage;

public class DemoTest extends BaseTest{
	
	WebDriver driver;
	TravelHomePage travelHomepage;
	
	By sectionElement = By.id("flightSearchContainer");
	
	@BeforeTest
	public void setUp(){
		
		driver = initializeDriver();
		travelHomepage = new TravelHomePage(driver);		
	}
	
	//@Test(dataProvider="getData")
	@Test(dataProvider="getDataFromJson")
	public void flightTest(HashMap<String, String> resDetails) throws InterruptedException{
		
//		WebDriverManager.chromedriver().setup();
//		WebDriver driver = new ChromeDriver();
		
//		TravelHomePage travelHomepage = new TravelHomePage(driver);
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
		//a. using TestNG Data Providers and providing data manually
		//b. using TestNG Data Providers and reading data from JSON file as well 
		
		travelHomepage.setBookingStrategy("multiCity");
		travelHomepage.checkAvailability(resDetails);
		
		//7 Drive data from external source(JSON file)
		
	}
	
	@AfterTest
	public void tearDown(){
		driver.quit();	
	}
	
	@DataProvider
	public Object[][] getData(){
		HashMap<String, String> resDetails = new HashMap<String, String>();
		resDetails.put("origin", "MAA");
		resDetails.put("destination", "HYD");
		resDetails.put("origin2", "DEL");
		
		HashMap<String, String> resDetails1 = new HashMap<String, String>();
		resDetails1.put("origin", "MAA");
		resDetails1.put("destination", "HYD");
		resDetails1.put("origin2", "DEL");
		
		//If we have multiple datasets(Hashmaps), we can pass them as a list
		List<HashMap<String,String>> l = new ArrayList<HashMap<String, String>>();
		l.add(resDetails);
		l.add(resDetails1);
		
		return new Object[][]{
			{resDetails}
			//if we want to have 2 different datasets, we have to return different datasets like
			//{resDetails},{resDetails1}
			//or we can pass as list of HashMaps as below
			//{l.get(0)},{l.get(1)}
		};
	}
	
	//Creating new data provider to read data from JSON file
	@DataProvider
	public Object[][] getDataFromJson() throws IOException{
		
		List<HashMap<String, String>> list = getJsonData(System.getProperty("user.dir")+
				"/src/test/java/org/spariharconsultinginc/DesignPatterns"
				+ "/DataLoads/ReservationDetails.json");
		return new Object[][]{
		
		//For every index in the JSON file, it will create a HashMap. and all HashMaps will be
		//put into a list and we can access content of JSON file using list index
		{list.get(0)},{list.get(1)}
		//Runs the test case twice with both sets of data if 2 indexes are present in list(json file)
		};
		
	}
	

}
