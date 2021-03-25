package bl.pretest.nrl;

/*Bukalapak Pretest Test Engineer - Nurul Virda F
 * Answer for Essay question number 3
 * 
 * I'm sorry that I put assumption for appPackage, appActivity & all mobile elements
 * because I'm facing some technical issues which haven't solved
 * until this code being pushed to GIT
 * */

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.appium.java_client.android.AndroidDriver;

public class Essay3 {
	private AndroidDriver<WebElement> driver;
	
	@DataProvider(name="usernamePassList")
	public Object[][] userIdsAndPasswordsDataProvider() {
		return new Object[][]{
				{"tester01@mail.com","01", true, true},
				{"tester01","01", false, true},
				{"","01", false, true},
				{"tester01","", true, false},
				{"","", false, false},
			};
	}
	
	@BeforeClass
	public void setUp()throws IOException{
		File classpathRoot = new File(System.getProperty("user.dir"));
        File appDir = new File(classpathRoot, "../apps");
        File app = new File(appDir.getCanonicalPath(), "Sample Android App Login Test_v4.0_apkpure.com.apk");
        
        DesiredCapabilities capabilities = new DesiredCapabilities();
        driver = new AndroidDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        
        capabilities.setCapability("app", app.getAbsolutePath());	   
		capabilities.setCapability("appPackage", "com.sample.androidLogin");
		capabilities.setCapability("appActivity","com.sample.androidLogin.login");
	}

	@Test
	public void login(String username, String password, Boolean isUserValid, Boolean isPwdValid) throws Exception {
	   driver.findElement(By.id("android:id/username")).sendKeys(username);
	   driver.findElement(By.id("android:id/password")).sendKeys(username);
	   driver.findElement(By.id("android:id/login")).click();
	   
	   if(isUserValid && isPwdValid){
		   assertEquals(driver.findElement(By.id("android:id/Title")).getText(), "Android NewLine Learning");
	   }else if(!isUserValid && isPwdValid){
		   assertTrue(driver.findElement(By.id("android:id/emailErrMsg")).isDisplayed());
	   }else if(isUserValid && !isPwdValid){
		   assertTrue(driver.findElement(By.id("android:id/pwdErrMsg")).isDisplayed());
	   }else{
		   assertTrue(driver.findElement(By.id("android:id/emailErrMsg")).isDisplayed());
		   assertTrue(driver.findElement(By.id("android:id/pwdErrMsg")).isDisplayed());
	   }
	}

	@AfterClass
	public void teardown(){
		driver.quit();
	}
}
