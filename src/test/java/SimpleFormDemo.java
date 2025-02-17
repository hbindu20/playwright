import com.google.gson.JsonObject;
import com.microsoft.playwright.Page;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@RunWith(DataProviderRunner.class)
public class SimpleFormDemo extends  BaseTest{
  String testURL = "https://www.lambdatest.com/selenium-playground/";

 @Test
 @UseDataProvider(value = "getDefaultTestCapability",location = LTCapability.class)
 public void simpleForm(JsonObject capability) {
   Driver driver;
   Page page = null;
   try {
     driver = super.createConnection(capability);
     page = driver.getPage();
     page.navigate(testURL);
     Thread.sleep(200);
     page.setViewportSize(1900, 1050);
     
     page.locator("//a[text()='Simple Form Demo']").click();
     Thread.sleep(1000);
     String presenturl = page.url();
     Assert.assertTrue(presenturl.contains("simple-form-demo"));
     String actText = "Welcome to LambdaTest";
     page.locator("//input[@placeholder='Please enter your Message']").fill(actText);
     Thread.sleep(1000);
     page.locator("//button[@id='showInput']").click();
     Thread.sleep(1000);
     assertThat(page.locator("//p[@id='message']")).containsText(actText);
     Thread.sleep(2000);
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
