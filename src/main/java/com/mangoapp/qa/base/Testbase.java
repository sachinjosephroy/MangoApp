package com.mangoapp.qa.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.mangoapp.qa.logs.WebEventListener;
import com.mangoapp.qa.pages.HomePage;
import com.mangoapp.qa.pages.LoginPage;
import com.mangoapp.qa.util.Testutil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Testbase {
	
	String configFilePath = "C:\\Users\\simir\\eclipse-workspace\\MangoApp\\src\\main\\java\\com\\mangoapp\\qa\\configuration\\config.properties";
	String extentReportPath = "C:\\Users\\simir\\eclipse-workspace\\MangoApp\\ExtentReports\\MangoAppReport.html";
	String extentReportConfigPath = "C:\\Users\\simir\\eclipse-workspace\\MangoApp\\ExtentReports\\extent-config.xml";
	public static WebDriver driver;
	public static Properties prop;
	EventFiringWebDriver e_driver;
	WebEventListener eListener;
	public static ExtentReports extent;
	public static ExtentTest test;
	
	public LoginPage login;
	public HomePage home;
	public static Testutil util;
	
	public Testbase() {
		try {
			FileInputStream fis = new FileInputStream(configFilePath);
			prop = new Properties();
			prop.load(fis);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void initializeBrowser() {
		String browserName = prop.getProperty("browser");
		if(browserName.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver", prop.getProperty("driverpath"));
			driver = new EdgeDriver();
		}
		e_driver = new EventFiringWebDriver(driver);
		eListener = new WebEventListener();
		e_driver.register(eListener);
		driver = e_driver;
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(prop.getProperty("url"));
	}
	
	public void initializePages() {
		login = new LoginPage(driver);
		home = new HomePage(driver);
		util = new Testutil();
	}
	
	@BeforeSuite
	public void setUpSuite() {
		extent = new ExtentReports(extentReportPath);
		extent.loadConfig(new File(extentReportConfigPath));
	}
	
	@BeforeMethod
	public void setUpMethod(Method method) {
		test = extent.startTest(this.getClass().getSimpleName() + "::" + method.getName(), method.getName());
		test.assignAuthor("Sachin Roy");
		test.assignCategory("Functional Test");
		
		initializeBrowser();
		initializePages();
	}
	
	@AfterMethod
	public void tearDownMethod(ITestResult result) throws IOException {
		if(result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.FAIL, "Testing Failed");
			String screenshotName = result.getName();
			util.getScreenshotOnFailure(screenshotName);
			extent.endTest(test);
		}
		else if(result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, "Testing Skipped");
			extent.endTest(test);
		}
		else {
			test.log(LogStatus.PASS, "Testing Passed");
			extent.endTest(test);
		}
		driver.quit();
	}
	
	@AfterSuite
	public void tearDownSuite() {
		extent.flush();
		extent.close();
	}
	
	public void verifyResultString(String actual, String expected){
		if(!actual.equals(expected)) {
			test.log(LogStatus.FAIL, "EXP RESULT: " + expected + "<br/>" + "ACT RESULT: " + actual);
		}
	}

}
