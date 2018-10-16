package com.mangoapp.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.mangoapp.qa.base.Testbase;

public class HomePage extends Testbase {
	
	WebDriver driver;
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath = "//td[@class='logo_text']")
	WebElement lblLogo;
	
	
	public String getLogo() {
		driver.switchTo().frame("mainpanel");
		return lblLogo.getText();
	}

}
