package com.qa.pageobjects;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.qa.base.TestBase;

public class DirectoryPage extends TestBase {

	@FindBy(id = "searchDirectory_emp_name_empName")
	WebElement empName;

	@FindBy(id = "searchDirectory_job_title")
	WebElement jobTitle;

	@FindBy(id = "searchDirectory_location")
	WebElement jobLocation;

	@FindBy(id = "searchBtn")
	WebElement searchButton;

	@FindBy(xpath = "//*[@id=\"resultTable\"]/tbody/tr[2]/td[2]/ul/li[1]/b | //*[@id=\"content\"]/div[2]/div[2]")
	WebElement searchResult;

	public DirectoryPage() {
		PageFactory.initElements(driver, this);
	}

	public void directorySearch(String name, String job, String indexValue) {
		empName.sendKeys(name);
		empName.sendKeys(Keys.TAB);

		Select jobSelect = new Select(jobTitle);
		jobSelect.selectByVisibleText(job);

		Select locationSelect = new Select(jobLocation);
		locationSelect.selectByVisibleText(indexValue);

		searchButton.click();
	}

	public String searchResults() {
		return searchResult.getText();	
	}
}
