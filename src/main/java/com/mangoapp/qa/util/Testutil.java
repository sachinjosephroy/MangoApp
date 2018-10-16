package com.mangoapp.qa.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.mangoapp.qa.base.Testbase;

public class Testutil extends Testbase {

	public void getScreenshotOnFailure(String screenshotName) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File srcFile = ts.getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		String currentDateTime = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		String destination = currentDir + "/Screenshots/" + screenshotName + "_" + currentDateTime + ".png";
		File destFile = new File(destination);
		FileUtils.copyFile(srcFile, destFile);
	}

}
