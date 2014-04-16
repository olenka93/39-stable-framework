package ua.lviv.testers.webdriver;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;

import net.lightbody.bmp.proxy.ProxyServer;

public class ProxyServerInstance {
	
	private ProxyServer server;
	private static ProxyServerInstance INSTANCE;
	private boolean serverStarted = false;
	
	private ProxyServerInstance(){
		server = new ProxyServer();
	}
	
	public static ProxyServerInstance getServerInstance(){
		if (INSTANCE == null){
			INSTANCE = new ProxyServerInstance();
		}
		return INSTANCE;
	}
	
	public ProxyServer getServer() {
		return server;
	}
	
	public void startProxyServer(DesiredCapabilities capabilities, int port) throws Exception{
		if (!serverStarted) {
			server.setPort(port);
			server.start();
			Proxy proxy = server.seleniumProxy();
			capabilities.setCapability(CapabilityType.PROXY, proxy);
			serverStarted = true;
		}
		else {
			String errorMessage = "Another server instance is currently running on port: " + port + ", ";
			Reporter.log(errorMessage);
			throw new Exception(errorMessage);
		}
	}
	
	public void stopProxyServer() throws Exception{
		if (serverStarted){
			server.cleanup();
			server.stop();
			serverStarted = false;
		}
	}
}
