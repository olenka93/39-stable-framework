package ua.lviv.testers.util;

/*
 * Bean representing a browser. It contains name, version and platform fields.
 */
public class Browser {

	private String name;
	private String version;
	private String platform;
	private static Browser browser;

	private Browser(){
		
	}
	
	private Browser(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public static void setBrowser(String browserName) {
		browser = new Browser(browserName);
	}

	public static Browser getInstance(){
		if (browser == null){
			browser = new Browser("chrome");
		}
		return browser;
	}
	
}