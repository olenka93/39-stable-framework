package ua.lviv.testers.webdriver;

import net.lightbody.bmp.proxy.ProxyServer;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

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
	
	public static ProxyServer server;
	private static WebDriver webDriver;

	private WebDriverFactory() {

	}

	public static WebDriver getInstance() throws Exception {
		
		String browser = Browser.getInstance().getName();

		if (webDriver == null) {
			
			server = new ProxyServer(4443);
			server.start();
			Proxy proxy = server.seleniumProxy();
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(CapabilityType.PROXY, proxy);
			capabilities.setJavascriptEnabled(true);
			
			if (CHROME.equals(browser)) {
				setChromeDriver();
				webDriver = new ChromeDriver(capabilities);
			} else if (FIREFOX.equals(browser)) {
				FirefoxProfile ffProfile = new FirefoxProfile();
				capabilities.setCapability(FirefoxDriver.PROFILE, ffProfile);
				webDriver = new FirefoxDriver(ffProfile);
			} else if (INTERNET_EXPLORER.equals(browser)) {
				webDriver = new InternetExplorerDriver();
			}
		}
		
		return webDriver;
	}
	
	public static void killDriverInstance(){
		webDriver.quit();
		webDriver = null;
	}

	private static void setChromeDriver() {
		String os = System.getProperty("os.name").toLowerCase().substring(0, 3);
		String chromeBinary = "src/main/resources/drivers/chrome/chromedriver"
				+ (os.equals("win") ? ".exe" : "");
		System.setProperty("webdriver.chrome.driver", chromeBinary);
	}
}