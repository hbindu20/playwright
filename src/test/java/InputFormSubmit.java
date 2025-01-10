import com.google.gson.JsonObject;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@RunWith(DataProviderRunner.class)
public class InputFormSubmit extends  BaseTest{
    String testURL = "https://www.lambdatest.com/selenium-playground/";
    @Test
    @UseDataProvider(value = "getDefaultTestCapability",location = LTCapability.class)
    public void inputForm(JsonObject capability) {
        Driver driver;
        Page page = null;
        try {

            driver = super.createConnection(capability);
            page = driver.getPage();
            page.navigate(testURL);
            Thread.sleep(200);
            page.setViewportSize(1900, 1050);

            page.locator("//a[text()='Input Form Submit']").click();
            page.locator("//button[text()='Submit']").click();
            Locator name = page.locator("//input[@id='name']");
            Object validationMessage = name.evaluate("element => element.validationMessage");
            Assert.assertEquals(validationMessage, "Please fill out this field.");
            page.locator("//input[@id='name']").fill("hima");
            page.locator("//input[@placeholder='Email']").fill("himabindu.bandi20@gmail.com");
            Thread.sleep(1500);
            page.locator("//input[@placeholder='Password']").fill("111");
            page.locator("//input[@placeholder='Company']").fill("hcom");
            Thread.sleep(1500);
            page.locator("//input[@placeholder='Website']").fill("lt");
            page.locator("//input[@placeholder='City']").fill("CA");
            page.locator("//select[@name='country']").selectOption("US");
            Thread.sleep(1500);
            page.locator("//input[@placeholder='Address 1']").fill("add1");
            page.locator("//input[@placeholder='Address 2']").fill("add2");
            Thread.sleep(1500);
            page.locator("//input[@placeholder='State']").fill("LA");
            page.locator("//input[@placeholder='Zip code']").fill("90000");
            Thread.sleep(1500);
            page.locator("//button[text()='Submit']").click();
            Thread.sleep(1500);

            assertThat(page.locator("//p[@class='success-msg hidden']"))
                    .containsText("Thanks for contacting us, we will get back to you shortly.");

            Thread.sleep(1500);
            if(page.title().equalsIgnoreCase("Selenium Grid Online | Run Selenium Test On Cloud")) {
                super.setTestStatus("passed", "Title matched", page);
            } else {
                super.setTestStatus("failed", "Title not matched", page);
            }
            super.closeConnection(driver);
        } catch (Exception e) {
            e.printStackTrace();
            super.setTestStatus("failed",e.getMessage(),page);
        }
    }
}