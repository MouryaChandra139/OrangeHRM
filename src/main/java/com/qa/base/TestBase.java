package com.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestBase {

	public static WebDriver driver;
	public static Properties prop;
	public static Logger log = Logger.getLogger("TestBase.class");
	
	public TestBase() {
		try {
			prop = new Properties();
			
			String currentDir = System.getProperty("user.dir");
			FileInputStream ip = new FileInputStream(currentDir + "\\src\\main\\java\\com\\qa\\config\\config.properties");
			prop.load(ip);
			
			FileInputStream logFile = new FileInputStream(currentDir+"\\src\\main\\resources\\Log4j.properties");
			prop.load(logFile);
			BasicConfigurator.configure();

		} catch (FileNotFoundException fe ) {
			fe.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void initialization() {
		String browserName = prop.getProperty("browser");
		if(browserName.equals("chrome")) {
			driver = new ChromeDriver();
		}
		else if(browserName.equals("firefox")) {
			driver = new FirefoxDriver();
		}
		else {
			log.info("Browser not found");
		}
				
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(20,TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);		
		driver.get(prop.getProperty("url"));
	}

}
