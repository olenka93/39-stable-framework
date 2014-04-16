package ua.lviv.testers.testcase;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import ua.lviv.testers.pages.MyPageFactory;
import ua.lviv.testers.pages.enterapp.HomePage;
import ua.lviv.testers.util.Browser;
import ua.lviv.testers.util.PropertyLoader;
import ua.lviv.testers.webdriver.ProxyServerInstance;
import ua.lviv.testers.webdriver.WebDriverFactory;


/*
 * Base class for all the test classes
 * 
 * @author Taras Lytvyn
 */

public class TestBase{
	
	protected RemoteWebDriver webDriver;
	protected EventFiringWebDriver eventDriver;
	protected ThreadLocal<EventFiringWebDriver> threadDriver = null;
	WebDriverFactory facrotryDriver = new WebDriverFactory();
	
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
		
		/*
		 //Firefox hub 
		threadDriver = new ThreadLocal<RemoteWebDriver>();
        DesiredCapabilities dc = new DesiredCapabilities();
        FirefoxProfile fp = new FirefoxProfile();
        dc.setCapability(FirefoxDriver.PROFILE, fp);
        dc.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
        threadDriver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc));
		*/
		
		/*
		 //Chrome hub
		
		threadDriver = new ThreadLocal<RemoteWebDriver>();
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setBrowserName(DesiredCapabilities.chrome().getBrowserName());
        threadDriver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc));
		*/
		
		threadDriver = new ThreadLocal<EventFiringWebDriver>();
		
		webDriver = facrotryDriver.getInstance();
		eventDriver = new EventFiringWebDriver(webDriver);
		eventDriver.register(new WebDriverListener());
		
		threadDriver.set(eventDriver);
		
		//ProxyServerInstance.getServerInstance().getServer().newHar(testUrl);
		
		/*
		eventDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
		eventDriver.get(testUrl);
		home = MyPageFactory.initElements(webDriver, HomePage.class);
		*/
		
        getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);	
        getDriver().get(testUrl);
		home = MyPageFactory.initElements(getDriver(), HomePage.class);
		
	}
	
	@AfterMethod(groups = {"groupLQAS", "all", "mobile"})
	public void reopenApp() throws Exception{
		//eventDriver.quit();
		//webDriver().quit();
		getDriver().quit();
		/*
		if (webDriver != null) {
			WebDriverFactory.killDriverInstance();
		}
		*/
		//ProxyServerInstance.getServerInstance().stopProxyServer();
	}
	
	
	public WebDriver getDriver() {
        return threadDriver.get();
    }
	
}
