package com.qa.tests;

import org.openqa.selenium.NoSuchElementException;
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
		log.info("loginPageTitleCheck method is started");
		String pageTitle = loginPage.LoginPageTitle();
		try {
		Assert.assertEquals(pageTitle, prop.getProperty("pageTitle"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("loginPageTitleCheck method ends");
	}
	
	@Test (priority = 1)
	public void loginUserConfirmation() {
		log.info("loginUserConfirmation method is started");
		String loginUsr = loginPage.userLogin().homePageTitleTest();
		try {
			Assert.assertEquals(loginUsr, prop.getProperty("loginName"));
		}catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		log.info("User login successfully");
	}

	@Test (priority = 2, dataProvider = "validUserData", enabled = true)
	public void addNewEmployeeData(String firstNm, String lastNm, String empID, String usrNm, String usrPwd, String confirmPwd) throws Exception {
		log.info("addNewEmployeeData method is started");
		employeeListPage = loginPage.userLogin().clickpimLink();
		employeeListPage.addNewEmployee(firstNm, lastNm, empID, usrNm, usrPwd, confirmPwd);
		employeeListPage.employeeSearch(empID);
		String searchResult = employeeListPage.searchResults();
		try {
			Assert.assertEquals(searchResult,empID);
		}catch (Exception e) {
			e.printStackTrace();
		}
		log.info("addNewEmployeeData method is ends");
	}

	@Test (priority = 3, dataProvider = "inValidUserData", enabled = true)
	public void empDataValidation(String firstNm, String lastNm, String empID, String usrNm, String usrPwd, String confirmPwd) throws Exception {
		log.info("empDataValidation method is started");
		employeeListPage = loginPage.userLogin().clickpimLink();
		employeeListPage.addNewEmployee(firstNm, lastNm, empID, usrNm, usrPwd, confirmPwd);
		try {
			Assert.assertTrue(employeeListPage.validationMsg());
		} catch (Exception e){
			e.printStackTrace();
		}
		log.info("empDataValidation method ends");
	}

	@Test (priority = 4, dataProvider = "emmpDeleteData", enabled = true)
	public void cancelDeleteEmpData(String id) throws Exception {
		log.info("cancelDeleteEmpData method is started");
		employeeListPage = loginPage.userLogin().clickpimLink();
		employeeListPage.employeeSearch(id);
		employeeListPage.notDeleteEmployeeData();
		String searchResult = employeeListPage.searchResults();
		try {
			Assert.assertEquals(searchResult,id);
		}catch (Exception e) {
			e.printStackTrace();
		}
		log.info("canelDeleteEmpData method ends");
	}


	@Test (priority = 5, dataProvider = "emmpDeleteData", enabled = true)
	public void deleteEmpData(String id) throws Exception {
		log.info("deleteEmpData method is started");
		employeeListPage = loginPage.userLogin().clickpimLink();
		employeeListPage.employeeSearch(id);
		employeeListPage.deleteEmployeeData();
		String searchResult = employeeListPage.noSearchResult().trim();
		try {
			Assert.assertTrue(searchResult.equalsIgnoreCase("No Records Found"));
		}catch (Exception e) {
			e.printStackTrace();
		}
		log.info("deleteEmpData method ends");
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
		log.info("Browser driver is closed");
	}
}
