package org.example.Base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Set2TestCaseDataDriven {
    public AndroidDriver driver;


    @DataProvider(name = "data-provider")
    public Object[][] dpMethod() throws IOException {

        File f= new File("C:\\Git\\IBMSDET\\Magento.xlsx");
        FileInputStream fis = new FileInputStream(f);

        XSSFWorkbook wb= new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheetAt(0);
        int rc= sheet.getLastRowNum();
        System.out.println("total number of rows having data= "+rc);

            String firstName1 = sheet.getRow(1).getCell(0).getStringCellValue();
            String lastName1 = sheet.getRow(1).getCell(1).getStringCellValue();

        String firstName2 = sheet.getRow(2).getCell(0).getStringCellValue();
        String lastName2= sheet.getRow(2).getCell(1).getStringCellValue();

        String firstName3 = sheet.getRow(3).getCell(0).getStringCellValue();
        String lastName3= sheet.getRow(3).getCell(1).getStringCellValue();



        return new Object[][] {{firstName1, lastName1 },{firstName2,lastName2},{firstName3,lastName3}};
    }


    @Test (dataProvider = "data-provider")
    public void contactUs(String firstName,String lastName) throws InterruptedException, IOException {


        DesiredCapabilities capability = new DesiredCapabilities();
        //capability.setCapability("deviceName","Abhinav");
        capability.setCapability(MobileCapabilityType.DEVICE_NAME, "Abhinav");
        capability.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capability.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");

        driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), capability);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://magento.com/contact-us?inquiry=talk-to-sales");


            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0,0)");
            do {
                try {
                    driver.findElement(By.xpath("//*[@id=\"LblFirstName\"]")).click();
                    break;
                } catch (Exception e) {
                    js = (JavascriptExecutor) driver;
                    js.executeScript("window.scrollBy(0,200)", "");

                }
            } while (true);
            //driver.hideKeyboard();
            Thread.sleep(5000);
            //driver.findElement(By.xpath("//*[@id=\"LblFirstName\"]")).sendKeys("Abhinav");
            driver.findElement(By.xpath("//*[@id=\"FirstName\"]")).sendKeys(firstName);

            //String inputText = "Rozmeen";
            // WebElement myElement = driver.findElement(By.xpath("//*[@id=\"LblFirstName\"]"));
            // String js1 = "arguments[0].setAttribute('value','"+inputText+"')";
            //  ((JavascriptExecutor) driver).executeScript(js1, myElement);


            driver.findElement(By.xpath("//*[@id=\"LastName\"]")).sendKeys(lastName);
            js.executeScript("window.scrollBy(0,200)", "");
            driver.findElement(By.xpath("//*[@id=\"Email\"]")).sendKeys("abhinavgoyal13@gmail.com");
            driver.findElement(By.xpath("//*[@id=\"Phone\"]")).sendKeys("8791715283");
            js.executeScript("window.scrollBy(0,200)", "");
            // select from drop down

            Select country = new Select(driver.findElement(By.xpath("//*[@id=\"Country\"]")));
            country.selectByVisibleText("India");

            Select job = new Select(driver.findElement(By.xpath("//*[@id=\"Job_Role__c\"]")));
            job.selectByVisibleText("IT Executive");

            driver.findElement(By.xpath("//*[@id=\"Company\"]")).sendKeys("IBM");

            Select businessType = new Select(driver.findElement(By.xpath("//*[@id=\"Organizational_Role__c\"]")));
            businessType.selectByVisibleText("Technology or Solution Provider");

            Select reasonEnquiry = new Select(driver.findElement(By.xpath("//*[@id=\"MktoPersonNotes\"]")));
            reasonEnquiry.selectByVisibleText("I want to become a Magento Developer");
            js.executeScript("window.scrollBy(0,200)", "");
            js.executeScript("window.scrollBy(0,200)", "");
            js.executeScript("window.scrollBy(0,200)", "");

            Thread.sleep(5000);
            // driver.findElement(By.xpath("//*[@id=\"top-target\"]/div[1]/div/div[1]/form/div[48]/span/button")).click();
            driver.findElement(By.xpath("//button[@type='submit']")).click();
            Thread.sleep(5000);
            String actualMessage = driver.findElement(By.xpath("//*[@id=\"top-target\"]/div[2]/h2")).getText();
            //System.out.println(actualMessage);
            String ExpectedMessage = "Thanks! We’re Working\n" +
                    "On Your Request";
            Assert.assertEquals(actualMessage, ExpectedMessage);

        driver.close();

    }


}
