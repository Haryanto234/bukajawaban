package bl.pretest.nrl;

/*Bukalapak Pretest Test Engineer - Nurul Virda F
 * Answer for Essay question number 2
 * */

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;

public class Essay2 {
	public WebDriver driver;
	
	@DataProvider(name="products")
	public Object[][] userIdsAndPasswordsDataProvider() {
		return new Object[][]{
				{"galon", true},
				{"'mnnas~!@'_/", false},
			};
	}
	
	@BeforeTest
	public void setup() throws IOException{
		File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "./driver");
        File app = new File(appDir.getCanonicalPath(), "chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", app.getAbsolutePath());
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	
	@Test(dataProvider="products")
	public void testSearchProduct(String product, boolean isProductAvailable) {
		driver.get("https://www.bukalapak.com/");
		driver.findElement(By.id("v-omnisearch__input")).sendKeys(product);
		driver.findElement(By.xpath("//button[@class='v-omnisearch__submit']")).click();
		
		String titleProd = driver.findElement(By.xpath("//h1[@data-test='title']/b")).getText();
		String title = titleProd.substring(1, titleProd.length() - 1);
		assertTrue(title.equals(product));
		
		if(isProductAvailable) {
			assertTrue(driver.findElement(By.xpath("//*[@id=\"product-explorer-container\"]/div/div[1]/div[2]/div/div[2]/div[1]/div/div/div[2]")).isDisplayed());
		} else {
			assertTrue(driver.findElement(By.xpath("//p[contains(@class,'mb-8')]")).isDisplayed());			
		}
		
	}
	  
	
	@AfterTest
	public void afterTest() {
		driver.close();
	}

}
