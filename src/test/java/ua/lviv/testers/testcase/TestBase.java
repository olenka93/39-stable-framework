package ua.lviv.testers.testcase;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import ua.lviv.testers.pages.MyPageFactory;
import ua.lviv.testers.pages.enterapp.HomePage;
import ua.lviv.testers.util.Browser;
import ua.lviv.testers.util.PropertyLoader;
import ua.lviv.testers.webdriver.WebDriverFactory;


/*
 * Base class for all the test classes
 * 
 * @author Taras Lytvyn
 */

public class TestBase{
	
	protected WebDriver webDriver;
	protected EventFiringWebDriver eventDriver;
	
	protected static String testUrl;
	protected static String username;
	protected static String password;
	
	public static String getUsermail() {
		return username;
	}

	public static String getPassword() {
		return password;
	}
	
	public static String getUrl() {
		return testUrl;
	}
	
	protected HomePage home;

	@Parameters({"browserName"})
	@BeforeMethod (groups = {"groupLQAS", "all", "mobile"})
	public void init(String browserName) throws Exception {

		Browser.setBrowser(browserName);
		
		testUrl = PropertyLoader.loadProperty("test.url");
		username = PropertyLoader.loadProperty("user.username");
		password = PropertyLoader.loadProperty("user.password");
		
		webDriver = WebDriverFactory.getInstance();
		eventDriver = new EventFiringWebDriver(webDriver);
		eventDriver.register(new WebDriverListener());
		
		WebDriverFactory.server.newHar(testUrl);
		
		eventDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
		eventDriver.get(testUrl);
		home = MyPageFactory.initElements(eventDriver, HomePage.class);
	
	}
	
	@AfterMethod(groups = {"groupLQAS", "all", "mobile"}, alwaysRun = true)
	public void reopenApp() throws Exception{	
		if (webDriver != null) {
			WebDriverFactory.killDriverInstance();
		}
		WebDriverFactory.server.stop();
	}
	
}
