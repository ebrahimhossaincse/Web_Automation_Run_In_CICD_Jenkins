package me.ebrahimhossain.pages;

import java.nio.file.Paths;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import me.ebrahimhossain.utilities.CommonMethods;

public class LoginPage extends CommonMethods {

	Page page;
	ExtentTest test;

	private ElementHandle username;
	private ElementHandle password;
	private ElementHandle login_button;

	public LoginPage(Page page, ExtentTest test) {
		this.page = page;
		this.test = test;

		this.username = page.querySelector("//input[@name='username']");
		this.password = page.querySelector("//input[@name='password']");
		this.login_button = page.querySelector("//button[@type='submit']");
	}

	public ElementHandle getUsername() {
		return username;
	}

	public void setUsername(ElementHandle username) {
		this.username = username;
	}

	public ElementHandle getPassword() {
		return password;
	}

	public void setPassword(ElementHandle password) {
		this.password = password;
	}

	public ElementHandle getLogin_button() {
		return login_button;
	}

	public void setLogin_button(ElementHandle login_button) {
		this.login_button = login_button;
	}

	// Reports
	public void handlePass(String message) {
		test.pass("<p style=\"color:#85BC63; font-size:13px\"><b>" + message + "</b></p>");
	}

	public void handlePassWithScreenshot(String message, String screenshotName) {
		test.pass("<p style=\"color:#85BC63; font-size:13px\"><b>" + message + "</b></p>");
		page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./screenshots/" + screenshotName + ".png"))
				.setFullPage(true));
		String dest = System.getProperty("user.dir") + "/screenshots/" + "" + screenshotName + ".png";
		test.pass(MediaEntityBuilder.createScreenCaptureFromPath(dest).build());
	}

	public void handleFail(String message, String screenshotName) {
		test.fail("<p style=\"color:#FF5353; font-size:13px\"><b>" + message + "</b></p>");
		Throwable t = new InterruptedException("Exception");
		test.fail(t);
		page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./screenshots/" + screenshotName + ".png"))
				.setFullPage(true));
		String dest = System.getProperty("user.dir") + "/screenshots/" + "" + screenshotName + ".png";
		test.fail(MediaEntityBuilder.createScreenCaptureFromPath(dest).build());
	}

	public void login() throws InterruptedException {
		test.info("Login Process");
		try {
			if (username.isVisible()) {
				test.info("Please enter your email address.");
				username.fill("Admin");
				Thread.sleep(2000);
				handlePass("You had successfully entered your username");
				try {
					if (password.isVisible()) {
						test.info("Please enter your password.");
						password.fill("admin123");
						Thread.sleep(2000);
						handlePass("You had successfully entered your password");
						try {
							if (login_button.isVisible()) {
								test.info("Please click on the Login Button.");
								login_button.click();
								Thread.sleep(5000);
								handlePassWithScreenshot("You had successfully logged in", "login_success");
							}
						} catch (Exception e) {
							handleFail("Login Button was not locateable. Please check the error message",
									"login_button_fail");
						}
					}
				} catch (Exception e) {
					handleFail("Password was not locateable. Please check the error message", "password_fail");
				}
			}
		} catch (Exception e) {
			handleFail("User Name was not locateable. Please check the error message", "username_fail");
		}
	}

}
