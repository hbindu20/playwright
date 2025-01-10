import com.google.gson.JsonObject;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.BoundingBox;
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
            Thread.sleep(2000);
            Locator sVal = page.locator("//input[@type='range' and @value='15']");
            Locator oVal = page.locator("//output[@id='rangeSuccess']");
            double slider = 0;

            for(int i=1;i<=31;i++)
            {
                BoundingBox bb = sVal.boundingBox();
                page.mouse().move(bb.x + slider, bb.y);
                page.mouse().down();
                slider += 15;
                page.mouse().move(bb.x + slider, bb.y);
                page.mouse().up();
            }
            String upd = oVal.textContent();
            System.out.println("Updated Output Val:" + upd);

            if(upd.equals("95"))
            {
                System.out.println("Test passed:  Output correctly updated to 95.");
            }else {
                System.out.println("Test failed:");
            }
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