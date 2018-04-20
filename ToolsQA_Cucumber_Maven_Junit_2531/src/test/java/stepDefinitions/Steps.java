package stepDefinitions;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import dataProvider.ConfigFileReader;
import managers.FileReaderManager;
import managers.PageObjectManager;
import managers.WebDriverManager;
import pageObjects.CartPage;
import pageObjects.CheckoutPage;
import pageObjects.HomePage;
import pageObjects.ProductListingPage;

public class Steps 
{
	WebDriver driver;
	HomePage homePage;
	ProductListingPage productListingPage;
	CartPage cartPage;
	CheckoutPage checkoutPage;
	PageObjectManager pageObjectManager;
	//ConfigFileReader configFileReader;
	WebDriverManager webDriverManager;
	
	@Given("^User is on Home Page$")
	public void user_is_on_Home_Page() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
		//configFileReader = new ConfigFileReader();
		//System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		//System.setProperty("webdriver.chrome.driver", configFileReader.getDriverPath());
		/**System.setProperty("webdriver.chrome.driver", FileReaderManager.getInstance().getConfigReader().getDriverPath());
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(FileReaderManager.getInstance().getConfigReader().getImplicitlyWait(), TimeUnit.SECONDS);
		
		pageObjectManager = new PageObjectManager(driver);
		homePage = pageObjectManager.getHomePage();
		homePage.navigateTo_HomePage();	
		**/
		webDriverManager = new WebDriverManager();
		driver = webDriverManager.getDriver();
		pageObjectManager = new PageObjectManager(driver);
		homePage = pageObjectManager.getHomePage();
		homePage.navigateTo_HomePage();
	}

	@Given("^He search for \"([^\"]*)\"$")
	public void he_search_for(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
		//driver.navigate().to("http://shop.demoqa.com/?s="+arg1+"&post_type=product");
		homePage.perform_Search(arg1);
	}

	@Given("^Choose to buy the first item$")
	public void choose_to_buy_the_first_item() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
		productListingPage = new ProductListingPage(driver);
		productListingPage.select_Product(0);
		productListingPage.clickOn_AddToCart();		
	}

	@Given("^Moves to checkout from mini cart$")
	public void moves_to_checkout_from_mini_cart() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
		cartPage = new CartPage(driver);
		cartPage.clickOn_Cart();
		cartPage.clickOn_ContinueToCheckout();
	}

	@Given("^Enter personal details on checkout page$")
	public void enter_personal_details_on_checkout_page() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
		checkoutPage = new CheckoutPage(driver);
		checkoutPage.fill_PersonalDetails();		
	}

	@Given("^Select same delivery address$")
	public void select_same_delivery_address() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
		checkoutPage = new CheckoutPage(driver);
		checkoutPage.check_ShipToDifferentAddress(false);
	}

	@Given("^Select payment method as \"([^\"]*)\" payment$")
	public void select_payment_method_as_payment(String arg1) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
		checkoutPage = new CheckoutPage(driver);
		checkoutPage.select_PaymentMethod("CheckPayment");
	}

	@Given("^Place the orde$")
	public void place_the_orde() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
		checkoutPage = new CheckoutPage(driver);
		checkoutPage.check_TermsAndCondition(true);
		checkoutPage.clickOn_PlaceOrder();

	}

	@Then("^Print the order No$")
	public void print_the_order_No() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
		WebElement orderNumber = driver.findElement(By.xpath("//li[@class='order']/strong"));
		System.out.println("Order No : "+orderNumber.getText());
		
	}
	
}
