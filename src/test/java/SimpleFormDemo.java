import com.google.gson.JsonObject;
import com.microsoft.playwright.Page;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

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
     String url = page.url();
       Assert.assertTrue("Wrong url", url.contains("simple-form-demo"));
       String actText = "Welcome to LambdaTest";
       page.locator("//input[@placeholder='Please enter your Message']").fill(actText);
       page.locator("button#showInput").click();
       String expText = page.locator("//p[@id='message']").innerText();
       Assert.assertEquals(actText + "doesn't match with" + expText, actText, expText);
       super.closeConnection(driver);
   } catch (Exception e) {
     e.printStackTrace();
     super.setTestStatus("failed",e.getMessage(),page);
   }
 }
}
