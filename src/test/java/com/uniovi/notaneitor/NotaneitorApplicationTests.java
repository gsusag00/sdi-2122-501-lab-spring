package com.uniovi.notaneitor;

import com.uniovi.notaneitor.pageobjects.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NotaneitorApplicationTests {

    @Test
    void contextLoads() {
    }

    static String PathFirefox = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    //static String Geckodriver = "C:\\Path\\geckodriver-v0.30.0-win64.exe";
//    static String Geckodriver = "C:\\Dev\\tools\\selenium\\geckodriver-v0.30.0-win64.exe";
    static String Geckodriver = "C:\\Users\\jesus\\Downloads\\PL-SDI-Sesión5-material\\geckodriver-v0.30.0-win64.exe";
    //C:\Users\jesus\Downloads\PL-SDI-Sesión5-material
    //static String PathFirefox = "/Applications/Firefox.app/Contents/MacOS/firefox-bin";
//static String Geckodriver = "/Users/USUARIO/selenium/geckodriver-v0.30.0-macos";
//Común a Windows y a MACOSX
    static WebDriver driver = getDriver(PathFirefox, Geckodriver);
    static String URL = "http://localhost:8090";
    public static WebDriver getDriver(String PathFirefox, String Geckodriver) {
        System.setProperty("webdriver.firefox.bin", PathFirefox);
        System.setProperty("webdriver.gecko.driver", Geckodriver);
        driver = new FirefoxDriver();
        return driver;
    }

    @BeforeEach
    public void setUp(){
        driver.navigate().to(URL);
    }
    //Después de cada prueba se borran las cookies del navegador
    @AfterEach
    public void tearDown(){
        driver.manage().deleteAllCookies();
    }
    //Antes de la primera prueba
    @BeforeAll
    static public void begin() {}
    //Al finalizar la última prueba
    @AfterAll
    static public void end() {
//Cerramos el navegador al finalizar las pruebas
        driver.quit();
    }

    /**
     *
     */
    @Test
    @Order(1)
    void PR01A() {
        PO_HomeView.checkWelcomeToPage(driver, PO_Properties.getSPANISH());
    }

    /**
     * Prueba que se devuelve un elemento HTML con la bienvenida
     */
    @Test
    @Order(2)
    void PR01B() {
        List<WebElement> welcomeMessageElement = PO_HomeView.getWelcomeMessageText(driver,
                PO_Properties.getSPANISH());
        Assertions.assertEquals(welcomeMessageElement.get(0).getText(),
                PO_HomeView.getP().getString("welcome.message", PO_Properties.getSPANISH()));
    }

    //PR02. Opcion de navegacion. Pinchar en el enlace Registro en la pagina home
    @Test
    @Order(3)
    void PR02() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
    }

    //PR03. Opcion de navegacion. Pinchar en el enlace Identificate en la pagina home.
    @Test
    @Order(4)
    void PR03() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
    }

    //PR04. Opcion de navegacion. Cambio de idioma de Español a Ingles y vuelta aa Español
    @Test
    @Order(5)
    void PR04() {
        PO_HomeView.checkChangeLanguage(driver, "btnSpanish", "btnEnglish",
                PO_Properties.getSPANISH(), PO_Properties.getENGLISH());
    }

    //PR05. Prueba del formulario de registro. registro con datos correctos
    @Test
    @Order(6)
    public void PR05() {
//Vamos al formulario de registro
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
//Rellenamos el formulario.
        PO_SignUpView.fillForm(driver, "77777778A", "Josefo", "Perez", "77777", "77777");
//Comprobamos que entramos en la sección privada y nos nuestra el texto a buscar
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    //PR06A. Prueba del formulario de registro. DNI repetido en la BD
// Propiedad: Error.signup.dni.duplicate
    @Test
    @Order(7)
    public void PR06A() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990A", "Josefo", "Perez", "77777", "77777");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.dni.duplicate",
                PO_Properties.getSPANISH() );
//Comprobamos el error de DNI repetido.
        String checkText = PO_HomeView.getP().getString("Error.signup.dni.duplicate",
                PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());
    }
    //PR06B. Prueba del formulario de registro. Nombre corto.
// Propiedad: Error.signup.dni.length
    @Test
    @Order(8)
    public void PR06B() {
        PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
        PO_SignUpView.fillForm(driver, "99999990A", "Jose", "Perez", "77777", "77777");
        List<WebElement> result = PO_SignUpView.checkElementByKey(driver, "Error.signup.name.length",
                PO_Properties.getSPANISH() );
//Comprobamos el error de Nombre corto de nombre corto .
        String checkText = PO_HomeView.getP().getString("Error.signup.name.length",
                PO_Properties.getSPANISH());
        Assertions.assertEquals(checkText , result.get(0).getText());
    }

    @Test
    @Order(9)
    void PR07() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillForm(driver, "99999990A", "123456");
//Comprobamos que entramos en la sección privada y nos nuestra el texto a buscar
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    @Test
    @Order(10)
    void PR08() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillForm(driver, "99999993D", "123456");
//Comprobamos que entramos en la sección privada y nos nuestra el texto a buscar
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    @Test
    @Order(11)
    void PR09() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillForm(driver, "99999988F", "123456");
//Comprobamos que entramos en la sección privada y nos nuestra el texto a buscar
        String checkText = "Notas del usuario";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    @Test
    @Order(12)
    void PR010() {PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillForm(driver, "99999990A", "123456789");
//Comprobamos que entramos en la sección privada y nos nuestra el texto a buscar
        String checkText = "Identifícate";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
    }

    @Test
    @Order(13)
    void PR011() {
        PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
        PO_LoginView.fillForm(driver, "99999990A", "123456789");
//Comprobamos que entramos en la sección privada y nos nuestra el texto a buscar
        String checkText = "Identifícate";
        List<WebElement> result = PO_View.checkElementBy(driver, "text", checkText);
        Assertions.assertEquals(checkText, result.get(0).getText());
        PO_HomeView.clickOption(driver, "logout", "class", "btn btn-primary");
    }



}
