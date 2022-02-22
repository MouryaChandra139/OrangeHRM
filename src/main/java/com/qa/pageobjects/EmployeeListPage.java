package com.qa.pageobjects;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.base.TestBase;

public class EmployeeListPage extends TestBase {

	@FindBy(id = "empsearch_id")
	WebElement empIdSearch;

	@FindBy(id = "searchBtn")
	WebElement searchButton;

	@FindBy(name = "btnAdd")
	WebElement addBtn;

	@FindBy(id = "btnDelete")
	WebElement deleteButton;

	@FindBy(id = "firstName")
	WebElement firstName;

	@FindBy(id = "lastName")
	WebElement lastName;

	@FindBy(id = "employeeId")
	WebElement employeeId;

	@FindBy(id = "chkLogin")
	WebElement loginCheckbox;

	@FindBy(id = "user_name")
	WebElement usrName;

	@FindBy(id = "user_password")
	WebElement usrPassword;

	@FindBy(id = "re_password")
	WebElement confirmPassword;

	@FindBy(id = "btnSave")
	WebElement saveButton;

	@FindBy(xpath = "//*[@id=\"resultTable\"]/tbody/tr/td[2]/a") 
	WebElement searchResult;
	
	@FindBy(xpath = "//*[@id=\"resultTable\"]/tbody/tr/td")
	WebElement noSearchResult;

	@FindBy(className = "validation-error")
	WebElement validationMsg;

	@FindBy(xpath = "//*[@id=\"menu_pim_viewEmployeeList\"]")
	WebElement employeeListBtn;

	@FindBy(name = "chkSelectRow[]")
	WebElement deleteCheckBox;

	@FindBy(xpath = "//*[@id=\"deleteConfModal\"]/div[3]/input[2]")
	WebElement cancelButton;
	
	@FindBy(xpath = "//*[@id=\"dialogDeleteBtn\"]")
	WebElement confirmButton;

	public EmployeeListPage() {
		PageFactory.initElements(driver, this);
	}

	public void addNewEmployee(String firstNm,String lastNm, String empID, String usrNm, String usrPwd, String confirmPwd) throws IOException {
		addBtn.click();
		loginCheckbox.click();

		firstName.sendKeys(firstNm);
		lastName.sendKeys(lastNm);
		employeeId.clear();
		employeeId.sendKeys(empID);
		usrName.sendKeys(usrNm);
		usrPassword.sendKeys(usrPwd);
		confirmPassword.sendKeys(confirmPwd);
		saveButton.click();
	}

	public Boolean validationMsg() {
		return validationMsg.isDisplayed();
	}

	public void employeeSearch(String empID) throws InterruptedException {
		Thread.sleep(1000);
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"menu_pim_viewEmployeeList\"]")));
		employeeListBtn.click();
		empIdSearch.sendKeys(empID);
		searchButton.click();
	}

	public String searchResults() {
		return searchResult.getText();
	}	
	
	public String noSearchResult() {
		return noSearchResult.getText();
	}

	public void deleteEmployeeData() {
		deleteCheckBox.click();
		deleteButton.click();
		confirmButton.click();
	}

	public void notDeleteEmployeeData() {
		deleteCheckBox.click();
		deleteButton.click();
		cancelButton.click();
	}
}
