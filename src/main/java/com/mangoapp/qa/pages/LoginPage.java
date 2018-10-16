package com.mangoapp.qa.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.mangoapp.qa.base.Testbase;

public class LoginPage extends Testbase {
	
	WebDriver driver;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//input[@name='username']")
	WebElement txtUsername;
	
	@FindBy(xpath = "//input[@name='password']")
	WebElement txtPassword;
	
	@FindBy(xpath = "//input[@class='btn btn-small']")
	WebElement btnLogin;
	
	@FindBy(xpath = "//a[@class='navbar-brand']//img[@class='img-responsive']")
	WebElement imgLogo;
	
	
	public void logMeIn() {
		txtUsername.sendKeys(prop.getProperty("username"));
		txtPassword.sendKeys(prop.getProperty("password"));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", btnLogin);
	}


	public String getTitle() {
		return driver.getTitle();
	}


	public String getLogo() {
		return imgLogo.getAttribute("src");
	}

}
