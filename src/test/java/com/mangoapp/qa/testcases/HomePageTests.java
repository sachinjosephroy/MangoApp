package com.mangoapp.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.mangoapp.qa.base.Testbase;

public class HomePageTests extends Testbase {
	
	public HomePageTests() {
		super();
	}
	
	@Test(priority=1)
	public void testGetLogo() {
		login.logMeIn();
		String actual = home.getLogo();
		String expected = "CRMPRO";
		verifyResultString(actual, expected);
		Assert.assertEquals(actual, expected);
	}

}
