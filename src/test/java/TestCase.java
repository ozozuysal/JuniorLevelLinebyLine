import static org.junit.Assert.*;
import jdk.internal.util.xml.impl.Input;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class TestCase {

    private final String HOME_URL = "https://libertex.fxclub.org/#modal_register";

    WebDriver driver;
    JavascriptExecutor jsExecutor;

    Properties prop = new Properties();

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver","C:/Users/Özgür Özuysal/Downloads/Caspar/src/main/resources/driver/chromedriver.exe");

        InputStream input = null;

        try {

            input = getClass().getClassLoader().getResourceAsStream("config.properties");

            // loading property file to our test
            prop.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //defining browser
        driver = new ChromeDriver();
        jsExecutor = (JavascriptExecutor) driver;
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void RegisterWork() {

        //open the page
        driver.get(HOME_URL);

        //get username from config properties
        WebElement email =  driver.findElement(By.xpath("//*[@id=\"modal\"]/div/div[2]/form/div[1]/dl[1]/dd/input"));
        email.sendKeys(prop.getProperty("username"));


        //get password from config properties
        WebElement passwordField = driver.findElement(By.xpath("//*[@id=\"modal\"]/div/div[2]/form/div[1]/dl[2]/dd/input"));
        passwordField.sendKeys(prop.getProperty("password"));

        //I agree checkbox
        WebElement formClick = driver.findElement(By.xpath("//*[@id=\"modal\"]/div/div[2]/form/div[1]/div/label[1]"));
        formClick.click();

        //Register button
        WebElement registerButton = driver.findElement(By.xpath("//*[@id=\"modal\"]/div/div[2]/form/div[2]/input"));
        registerButton.click();

        //wait for maximum 20 seconds for Welcome popUp and clicable Start Trading button
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"modal\"]/div/p[2]/span")));

        //Start Trading
        WebElement startButton = driver.findElement(By.xpath("//*[@id=\"modal\"]/div/p[2]/span"));
        startButton.click();
    }

    @Test
    public void RegisterEmptyEmailTest() {

        //open the page
        driver.get(HOME_URL);

        //type the password
        WebElement passwordField = driver.findElement(By.xpath("//*[@id=\"modal\"]/div/div[2]/form/div[1]/dl[2]/dd/input"));
        passwordField.sendKeys("Password123!");

        // i agree
        WebElement formClick = driver.findElement(By.xpath("//*[@id=\"modal\"]/div/div[2]/form/div[1]/div/label[1]"));
        formClick.click();

        // register button click
        WebElement registerButton = driver.findElement(By.xpath("//*[@id=\"modal\"]/div/div[2]/form/div[2]/input"));
        registerButton.click();

        WebElement msj = driver.findElement(By.xpath("//*[@id=\"modal\"]/div/div[2]/form/div[1]/dl[1]/dd/div"));
        msj.equals("The field must be completed");

    }


    }
