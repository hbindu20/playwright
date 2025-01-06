import com.google.gson.JsonObject;
import com.microsoft.playwright.Page;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DataProviderRunner.class)
public class DragDropSlider extends  BaseTest{
    String testURL = "https://www.lambdatest.com/selenium-playground/";
    @Test
    @UseDataProvider(value = "getDefaultTestCapability",location = LTCapability.class)
    public void dragDrop(JsonObject capability) {
        Driver driver;
        Page page = null;
        try {
            driver = super.createConnection(capability);
            page = driver.getPage();
            page.navigate(testURL);
            Thread.sleep(200);
            page.setViewportSize(1900, 1050);

            page.locator("//a[text()='Drag & Drop Sliders']").click();
            page.waitForTimeout(5000);
            page.locator("//div[@id='slider3']//div/input").fill("95");
            page.waitForTimeout(5000);
            String expVal = page.locator("#rangeSuccess").innerText();
            Assert.assertEquals("value doesn't match", expVal, "95");
            super.closeConnection(driver);
        } catch (Exception e) {
            e.printStackTrace();
            super.setTestStatus("failed",e.getMessage(),page);
        }
    }
}