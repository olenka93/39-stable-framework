package ua.lviv.testers.webdriver;

import java.net.URL;

import net.lightbody.bmp.proxy.ProxyServer;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import ua.lviv.testers.util.Browser;

/*
 * 
 * @author Taras Lytvyn
 */

public class WebDriverFactory {

	/* Browsers constants */
	public static final String CHROME = "chrome";
	public static final String FIREFOX = "firefox";
	public static final String INTERNET_EXPLORER = "ie";
	
	//private static WebDriver webDriver;
	private RemoteWebDriver webDriver;

	public WebDriverFactory() {

	}

	public RemoteWebDriver getInstance() throws Exception {
		
		String browser = Browser.getInstance().getName();

		//if (webDriver == null) {
			
			DesiredCapabilities capabilities = new DesiredCapabilities();
			
			//ProxyServerInstance.getServerInstance().startProxyServer(capabilities, 4444);
			
			capabilities.setJavascriptEnabled(true);
			/*
			if (CHROME.equals(browser)) {
				setChromeDriver();
				webDriver = new ChromeDriver(capabilities);
			} else if (FIREFOX.equals(browser)) {
				FirefoxProfile ffProfile = new FirefoxProfile();
				capabilities.setCapability(FirefoxDriver.PROFILE, ffProfile);
				webDriver = new FirefoxDriver(ffProfile);
			} else if (INTERNET_EXPLORER.equals(browser)) {
				webDriver = new InternetExplorerDriver();
			}*/
			
			//parallel
			if (CHROME.equals(browser)) {
				//setChromeDriver();
				capabilities.setBrowserName(DesiredCapabilities.chrome().getBrowserName());
				webDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
			} else if (FIREFOX.equals(browser)) {
				FirefoxProfile fp = new FirefoxProfile();
				capabilities.setCapability(FirefoxDriver.PROFILE, fp);
				capabilities.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
		        webDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
			} else if (INTERNET_EXPLORER.equals(browser)) {
				webDriver = new InternetExplorerDriver();
			//}
		}
		
		return webDriver;
	}
	
	public void killDriverInstance(){
		webDriver.quit();
		//webDriver = null;
	}
/*
	private static void setChromeDriver() {
		String os = System.getProperty("os.name").toLowerCase().substring(0, 3);
		String chromeBinary = "src/main/resources/drivers/chrome/chromedriver"
				+ (os.equals("win") ? ".exe" : "");
		System.setProperty("webdriver.chrome.driver", chromeBinary);
	}
*/
}