package me.ebrahimhossain.tests;

import java.io.IOException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import me.ebrahimhossain.basedriver.BaseDriver;
import me.ebrahimhossain.pages.LoginPage;
import me.ebrahimhossain.utilities.ExtentFactory;

public class LoginTest extends BaseDriver{
	
	ExtentReports report;
	ExtentTest parentTest;
	ExtentTest childTest;

	@BeforeClass
	@Parameters({ "url", "browserName", "headless" })
	public void openUrl(@Optional("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login") String url,
			@Optional("chrome") String browserName, @Optional("false") String headless) throws InterruptedException {
		report = ExtentFactory.getInstance();
		parentTest = report.createTest("<p style=\"color:#FF6000; font-size:20px\"><b>ORANGE HRM - LOGIN</b></p>")
				.assignAuthor("QA TEAM").assignDevice("Windows");
		launchPlaywright(browserName, headless);
		launchApplication(url);
	}

	@Test
	public void loginTest() throws IOException, InterruptedException {
		childTest = parentTest.createNode("<p style=\"color:#3E96E7; font-size:20px\"><b>Login</b></p>");
		LoginPage loginPage = new LoginPage(page, childTest);
		loginPage.login();		
	}
	
	@AfterClass
	public void afterClass() {
		closePlaywright();
		report.flush();
	}
}
