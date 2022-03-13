package com.qa.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.pageobjects.EmployeeListPage;
import com.qa.pageobjects.LoginPage;
import com.qa.util.ListenerTest;

@Listeners(ListenerTest.class)
public class TestScenarioTwo extends TestBase{
	public static LoginPage loginPage;
	public static EmployeeListPage employeeListPage;
	public static String sheetName = "EmpData";

	public TestScenarioTwo() {
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
		}catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		System.out.println("User login successfully");
	}

	@Test (priority = 2, dataProvider = "validUserData", enabled = true)
	public void addNewEmployeeData(String firstNm, String lastNm, String empID, String usrNm, String usrPwd, String confirmPwd) throws Exception {
		System.out.println("addNewEmployeeData method is started");
		employeeListPage = loginPage.userLogin().clickpimLink();
		employeeListPage.addNewEmployee(firstNm, lastNm, empID, usrNm, usrPwd, confirmPwd);
		employeeListPage.employeeSearch(empID);
		String searchResult = employeeListPage.searchResults();
		try {
			Assert.assertEquals(searchResult,empID);
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("addNewEmployeeData method is ends");
	}

	@Test (priority = 3, dataProvider = "inValidUserData", enabled = true)
	public void empDataValidation(String firstNm, String lastNm, String empID, String usrNm, String usrPwd, String confirmPwd) throws Exception {
		System.out.println("empDataValidation method is started");
		employeeListPage = loginPage.userLogin().clickpimLink();
		employeeListPage.addNewEmployee(firstNm, lastNm, empID, usrNm, usrPwd, confirmPwd);
		try {
			Assert.assertTrue(employeeListPage.validationMsg());
		} catch (Exception e){
			e.printStackTrace();
		}
		System.out.println("empDataValidation method ends");
	}

	@Test (priority = 4, dataProvider = "emmpDeleteData", enabled = true)
	public void cancelDeleteEmpData(String id) throws Exception {
		System.out.println("cancelDeleteEmpData method is started");
		employeeListPage = loginPage.userLogin().clickpimLink();
		employeeListPage.employeeSearch(id);
		employeeListPage.notDeleteEmployeeData();
		String searchResult = employeeListPage.searchResults();
		try {
			Assert.assertEquals(searchResult,id);
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("canelDeleteEmpData method ends");
	}


	@Test (priority = 5, dataProvider = "emmpDeleteData", enabled = true)
	public void deleteEmpData(String id) throws Exception {
		System.out.println("deleteEmpData method is started");
		employeeListPage = loginPage.userLogin().clickpimLink();
		employeeListPage.employeeSearch(id);
		employeeListPage.deleteEmployeeData();
		String searchResult = employeeListPage.noSearchResult().trim();
		try {
			Assert.assertTrue(searchResult.equalsIgnoreCase("No Records Found"));
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("deleteEmpData method ends");
	}

	@Test (priority = 6, enabled = true)
	public void elementAvailable() throws Exception {
		
		try {
			WebElement element = driver.findElement(By.id("txtUsername"));
			Boolean elementAvailable = element.isDisplayed();
			Assert.assertFalse(elementAvailable, "Element is avaialble in page");
		}catch(Exception e) {
			System.out.println("Item is not available");
		}
	}

	
	@DataProvider(name = "validUserData")
	public Object[][] validUserData() {
		return new Object[][] { 
			{ "test", "user1", "7195", "testuserhrm1", "testuser123", "testuser123"},
			{ "test", "user2", "7196", "testuserhrm2", "testuser123", "testuser123"}, 
			{ "test", "user3", "7197", "testuserhrm3", "testuser123", "testuser123"}, 
			{ "test", "user4", "7198", "testuserhrm4", "testuser123", "testuser123" }
		};
	}

	@DataProvider(name = "inValidUserData")
	public Object[][] inValidUserData() {
		return new Object[][] { 
			{ "test", " ", "9151","testuserMourya1", "testuser123", "testuser123"},
			{ " ", "user2", "9152", "testuserMourya2", "testuser123", "testuser123"}, 
			{ "test", "user3","9153", "testuserMourya3", "test", "test"}, 
			{ "test", "user4", "9154","", "testuser123", "test" }
		};
	}

	@DataProvider(name = "emmpDeleteData")
	public Object[][] emmpDeleteData() {
		return new Object[][] { 
			{ "0239" }
		};
	}

	@AfterMethod
	public void tearDown() {
		driver.close();
		System.out.println("Browser driver is closed");
	}
}
