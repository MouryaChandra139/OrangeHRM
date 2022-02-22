package com.qa.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.qa.base.TestBase;

public class HomePage extends TestBase{
	
	@FindBy(id = "menu_directory_viewDirectory")
	WebElement directoryLink;
	
	@FindBy(id = "menu_pim_viewPimModule")
	WebElement pimLink;
	
	@FindBy(id = "welcome")
	WebElement loginUserName;
		
	public HomePage() {
		PageFactory.initElements(driver, this);
	}
	
	public String homePageTitleTest() {
		return loginUserName.getText();	
	}
	
	public DirectoryPage clickDirectory() {
		directoryLink.click();
		return new DirectoryPage();
	}
	
	public EmployeeListPage clickpimLink() {
		pimLink.click();
		return new EmployeeListPage();
	}

}
