package com.qa.tests;

import java.io.IOException;

import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.pageobjects.DirectoryPage;
import com.qa.pageobjects.LoginPage;
import com.qa.util.ListenerTest;
import com.qa.util.TestUtil;

@Listeners(ListenerTest.class)
public class TestScenarioOne extends TestBase{
	
	public static LoginPage loginPage;
	public static DirectoryPage directoryPage;
	public static String validDataSheetName = "ValidSearchData";
	public static String invalidDataSheetName = "InvalidSearchData";

	public TestScenarioOne() {
		super();
	}

	@BeforeMethod
	public void setUp()
	{
		initialization();
		loginPage = new LoginPage();
	}
	
	@Test(priority = 0)
	public static void loginPageTitleCheck() {
		System.out.println("loginPageTitleCheck method is started");
		String pageTitle = loginPage.LoginPageTitle();
		try {
		Assert.assertEquals(pageTitle, prop.getProperty("pageTitle"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("loginPageTitleCheck method ends");
	}

	@Test (priority = 1)
	public void loginUserConfirmation() {
		System.out.println("loginUserConfirmation method is started");
		String loginUsr = loginPage.userLogin().homePageTitleTest();
		try {
		Assert.assertEquals(loginUsr, prop.getProperty("loginName"));
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		System.out.println("loginUserConfirmation method ends");
	}

	@Test (priority = 2)
	public void openDirectoryPage() {
		System.out.println("openDirectoryPage method is started");
		loginPage.userLogin().clickDirectory();
		System.out.println("openDirectory method ends");
	}

	@Test (priority = 3, dataProvider ="validTestData")
	public void validDataSearch(String empName, String jobTitle, String location) {
		System.out.println("validDataSearch method is started");
		directoryPage = loginPage.userLogin().clickDirectory();
		directoryPage.directorySearch(empName, jobTitle, location);
		String searchResult = directoryPage.searchResults().trim();
		try {
		Assert.assertTrue(searchResult.contains(empName));
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("validDataSearch method ends");
	}
	
	@Test (priority = 4, dataProvider ="inValidTestData")
	public void invalidDataSearch(String empName, String jobTitle, String location) {
		System.out.println("invalidDataSearch method is started");
		directoryPage = loginPage.userLogin().clickDirectory();
		directoryPage.directorySearch(empName, jobTitle, location);
		String searchResult = directoryPage.searchResults().trim();
		try {
		Assert.assertTrue((searchResult.equalsIgnoreCase("No Records Found")));
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("invalidDataSearch method ends");
	}
	
	@DataProvider(name = "validTestData")
	public Object[][] validTestData() throws IOException {
		Object data[][] = TestUtil.readTestData(validDataSheetName);
		return data;
	}

	@DataProvider(name = "inValidTestData")
	public Object[][] inValidTestData() throws IOException {
		Object data[][] = TestUtil.readTestData(invalidDataSheetName);
		return data;
	}

	@AfterMethod
	public void tearDown() {
		driver.close();
		System.out.println("Browser driver is closed");
	}
}
