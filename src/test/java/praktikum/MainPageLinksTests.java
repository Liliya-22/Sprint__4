package praktikum;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import praktikum.pages.MainPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.Assert.assertTrue;
/**
 * Класс тестов для проверки корреткности ссылок на главной странице
 */
public class MainPageLinksTests {
    /** Веб-драйвер */
    private WebDriver webDriver;
    /** URL тестируемой страницы */
    private final String mainPageUrl = "https://qa-scooter.praktikum-services.ru";
    /** URL ожидаемый для логотипа "Яндекс" */
    private final String yandexUrl = "//yandex.ru";
    /** URL ожидаемый для логотипа "Самокат" */
    private final String scooterUrl = "//qa-scooter.praktikum-services.ru";

@Before
    public void startUp() {
        if ("firefox".equals(System.getProperty("browser"))) {
            startFirefox();
        } else {
            startChrome();
        }
    }
    public void startChrome() {
        WebDriverManager.chromedriver().setup();
        this.webDriver = new ChromeDriver();    // здесь тест падает на подтверждении оформления заказа
        //webDriver = new SafariDriver();       // здесь тест проходит успешно
        this.webDriver.get(mainPageUrl);
        this.webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    public void startFirefox() {
        WebDriverManager.firefoxdriver().setup();
        var opts = new FirefoxOptions()
                .configureFromEnv();
        this.webDriver = new FirefoxDriver(opts);
        this.webDriver.get(mainPageUrl);
        this.webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }



    @After
    public void tearDown() {
        this.webDriver.quit();
    }
    /**
     * Тест для проверки открытия корректной ссылки в логотипе "Яндекс" в новом окне
     */
    @Test
    public void checkYandexLinkIsCorrect() {
        MainPage mainPage = new MainPage(this.webDriver);
        assertTrue(
                "Yandex Logo Link doesn't go to " + this.yandexUrl,
                mainPage.getYandexLogoLink().contains(this.yandexUrl)
        );
        assertTrue(
                "Yandex Logo Link doesn't open in new tab",
                mainPage.isYandexLogoLinkOpenedInNewTab()
        );
    }
    /**
     * Тест для проверки открытия корректной ссылки в логотипе "Самокат"
     */
    @Test
    public void checkScooterLinkIsCorrect() {
        MainPage mainPage = new MainPage(this.webDriver);
        assertTrue(
                "Scooter Logo Link doesn't go to " + this.scooterUrl,
                mainPage.getScooterLogoLink().contains(this.scooterUrl)
        );
    }
}