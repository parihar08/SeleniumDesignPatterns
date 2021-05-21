package abstractComponents;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class AbstractComponent {
	
	WebElement sectionElement;
	WebDriver driver;
	
	
	public AbstractComponent(WebDriver driver, By sectionElement) {
		this.driver = driver;
		this.sectionElement = driver.findElement(sectionElement);
		
	}

	public WebElement findElement(By findElementBy){
		return sectionElement.findElement(findElementBy);
		
	}
	
	public List<WebElement> findElements(By findElementBy){
		return sectionElement.findElements(findElementBy);
		
	}
	
	public void waitForElementToDisappear(By element){
		WebDriverWait wait = new WebDriverWait(driver, 5);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(element));
	}

}
