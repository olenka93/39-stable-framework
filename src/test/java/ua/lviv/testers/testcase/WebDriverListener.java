package ua.lviv.testers.testcase;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.testng.Reporter;
 
public class WebDriverListener implements WebDriverEventListener {
 
	public void beforeNavigateTo(String url, WebDriver driver) {
		
	}
 
	public void afterNavigateTo(String url, WebDriver driver) {
		// TODO Auto-generated method stub
 
	}
 
	public void beforeNavigateBack(WebDriver driver) {
		// TODO Auto-generated method stub
 
	}
 
	public void afterNavigateBack(WebDriver driver) {
		// TODO Auto-generated method stub
 
	}
 
	public void beforeNavigateForward(WebDriver driver) {
		// TODO Auto-generated method stub
	}
 
	public void afterNavigateForward(WebDriver driver) {
		// TODO Auto-generated method stub
 
	}
 
	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
 
	}
 
	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
 
	}
 
	public void beforeClickOn(WebElement element, WebDriver driver) {
		Reporter.log("Was cliked on webelement " + getElementDescriptorXPATH(driver, element) + "<br>", true);
		Reporter.log("Element tag " + getElementDescriptorName(driver, element) + "<br>", true);
	}
 
	public void afterClickOn(WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
	}
 
	public void beforeChangeValueOf(WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
	}
 
	public void afterChangeValueOf(WebElement element, WebDriver driver) {
		Reporter.log("Was performed on " + getElementDescriptorXPATH(driver, element) + "<br>", true);
		Reporter.log("Element tag " + getElementDescriptorName(driver, element) + "<br>", true);
	}
 
	public void beforeScript(String script, WebDriver driver) {
		Reporter.log("Execution of script " + script, true);

	}
 
	public void afterScript(String script, WebDriver driver) {
		// TODO Auto-generated method stub
 
	}
 
	public void onException(Throwable throwable, WebDriver driver) {
		// TODO Auto-generated method stub
 
	}
	
	public String getElementDescriptorXPATH(WebDriver driver, WebElement element){
		return (String)((JavascriptExecutor)driver).executeScript("gPt=function(c){if(c.id!==''){return'id(\"'+c.id+'\")'}if(c===document.body){return c.tagName}var a=0;var e=c.parentNode.childNodes;for(var b=0;b<e.length;b++){var d=e[b];if(d===c){return gPt(c.parentNode)+'/'+c.tagName+'['+(a+1)+']'}if(d.nodeType===1&&d.tagName===c.tagName){a++}}};return gPt(arguments[0]).toLowerCase();", element);
	}
	
	public String getElementDescriptorName(WebDriver driver, WebElement element){
		return element.getTagName() + "<br>" + element.getText();
	}
	
}
