import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.concurrent.TimeUnit;


public class MainClass {

    private static void loginOnStage (WebDriver driver, String loginName, String password){
        try {
            //Открывается окно для логина, вводятся данные учётной записи
            WebElement loginButton = driver.findElement(By.xpath("//*[@title='Авторизоваться']"));
            loginButton.click();
            WebElement nameField = driver.findElement(By.name("login"));
            WebElement passField = driver.findElement(By.name("password"));
            WebElement submitButton = driver.findElement(By.xpath("//*[text() = 'Войти']"));
            nameField.sendKeys(loginName);
            passField.sendKeys(password);
            submitButton.click();
        }
        catch (Exception e){
            System.out.println("Something went wrong in loginOnStage function.");
        }
    }

    private static void getOrdersNumbers(WebDriver driver, Integer ... rowNumbers){
        try {
            //Выбираются n-е строки из ТЧ с заказами, после чего выводятся в консоль номера заказов
            WebElement myOrdersButton = driver.findElement(By.xpath("//ol//span[text() = \"Мои заказы\"]"));
            myOrdersButton.click();
            for (Integer index : rowNumbers){
                String path = "//*[@id=\"orders-table\"]/tbody/tr[" + index + "]";
                WebElement row = driver.findElement(By.xpath(path));
                String orderNumber = row.findElement(By.className("data_numer")).getText().split(" ")[0];
                System.out.println(orderNumber);
            }

        }
        catch (Exception e){
            System.out.println("Something went wrong in getOrdersNumbers function.");
        }


    }

    public static void main(String[] args) {
        try {
            System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");//изменить путь на нормальный!
            ChromeOptions options = new ChromeOptions();

            //Игнор отсутствия сертификата для запуска драйвера
            options.addArguments("--ignore-certificate-errors");
            WebDriver driver = new ChromeDriver(options);
            //Задержка поиска для каждого элемента
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            //Выбор максимального размера окна и получение страницы
            driver.manage().window().maximize();
            driver.get("https://www.dellin.stage");

            loginOnStage(driver, "info@italrussia.ru", "1234567uU");
            getOrdersNumbers(driver, 1, 4, 7);
            driver.quit();
        }

        catch (Exception e){
            System.out.println("Something went wrong in main function.");
        }
    }
}
