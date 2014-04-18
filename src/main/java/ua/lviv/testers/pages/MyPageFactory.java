package ua.lviv.testers.pages;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Reporter;

import waiter.PageLoadedCreateria;


public class MyPageFactory extends PageFactory{
	
	public static <T extends PageLoadedCreateria> T myInitElements(WebDriver driver, Class<T> pageClassToProxy) {
        T page = instantiatePage(driver, pageClassToProxy);
        initElements(driver, page);
        try {
			waitForNextPageToLoad(driver);
			waitUntil(page, 30);
			} catch (Exception e) {
			Reporter.log("Page was not loaded fully because of " + e.getMessage());
		}
        return page;
      }

    private static <T> T instantiatePage(WebDriver driver, Class<T> pageClassToProxy) {
        try {
          try {
            Constructor<T> constructor = pageClassToProxy.getConstructor(WebDriver.class);
            return constructor.newInstance(driver);
          } catch (NoSuchMethodException e) {
            return pageClassToProxy.newInstance();
          }
        } catch (InstantiationException e) {
          throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
          throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
          throw new RuntimeException(e);
        }
      }
    
    public static void waitForNextPageToLoad(WebDriver driver) throws Exception{
            ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>(){
                    @Override
                    public Boolean apply(WebDriver expectationDriver) {
                            JavascriptExecutor js = (JavascriptExecutor) expectationDriver;
                            return js.executeScript("return document.readyState").equals("complete");
                    }
            };
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)  
		             .withTimeout(30, TimeUnit.SECONDS)  
		             .pollingEvery(5, TimeUnit.SECONDS);
            try {
                    wait.until(expectation);
            } catch(Throwable error){
                    Reporter.log("Timeout waiting page to Load because of: " + error.getMessage());
            }
    }

    private static <T extends PageLoadedCreateria> void waitUntil(T page, int seconds) throws Exception {
		int step = 0;
		while (! page.criteria()){
			Thread.sleep(1000);
			step ++;
			if (step == seconds + 1) {
				throw new Exception ("Fail: seconds spent: " + seconds + ", condition wasn't true");
			}
		}
    }
}
