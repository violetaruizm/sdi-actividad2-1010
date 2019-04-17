package java.com.uniovi.tests;

import java.util.List;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;



import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class sdiEntrega11010Tests {

   

  

    static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    static String Geckdriver022 = "C:\\Users\\viole\\Desktop\\PL-SDI-Sesi�n5-material\\geckodriver024win64.exe";
    // En MACOSX (Debe ser la versión 65.0.1y desactivar las actualizacioens
    // automáticas):
    // staticString
    // PathFirefox65="/Applications/Firefox.app/Contents/MacOS/firefox-­�?bin";
    // staticString Geckdriver024= "/Users/delacal/selenium/geckodriver024mac";
    // Común a Windows y a MACOSX
    static WebDriver driver = getDriver(PathFirefox65, Geckdriver022);
    static String URL = "http://localhost:8090/home";

    public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
	System.setProperty("webdriver.firefox.bin", PathFirefox);
	System.setProperty("webdriver.gecko.driver", Geckdriver);
	WebDriver driver = new FirefoxDriver();
	return driver;
    }

    @Before
    public void setUp() {
	//initdb();
	driver.navigate().to(URL);
    }

   /* private void initdb() {
	salesRepository.deleteAll();
	userRepository.deleteAll();

	User admin = new User();
	admin.setEmail("admin@email.com");
	admin.setPassword(bCryptPasswordEncoder.encode("admin"));
	admin.setRole(Role.ROLE_ADMIN);
	admin.setName("Admin");
	admin.setSurname("Admin");
	admin.setValid(true);
	userRepository.save(admin);

	User user1 = new User();
	user1.setEmail("user1@email.com");
	user1.setPassword(bCryptPasswordEncoder.encode("12345"));
	user1.setRole(Role.ROLE_STANDARD);
	user1.setName("User1");
	user1.setSurname("user1");
	user1.setValid(true);
	user1.setMoney(100);
	userRepository.save(user1);

	User user2 = new User();
	user2.setEmail("user2@email.com");
	user2.setPassword(bCryptPasswordEncoder.encode("12345"));
	user2.setRole(Role.ROLE_STANDARD);
	user2.setName("User2");
	user2.setSurname("User2");
	user2.setValid(true);
	user2.setMoney(250);
	userRepository.save(user2);

	User user3 = new User();
	user3.setEmail("user3@email.com");
	user3.setPassword(bCryptPasswordEncoder.encode("12345"));
	user3.setRole(Role.ROLE_STANDARD);
	user3.setName("User3");
	user3.setSurname("User3");
	user3.setValid(true);
	user3.setMoney(80.95);
	userRepository.save(user3);

	User user4 = new User();
	user4.setEmail("user4@email.com");
	user4.setPassword(bCryptPasswordEncoder.encode("12345"));
	user4.setRole(Role.ROLE_STANDARD);
	user4.setName("User4");
	user4.setSurname("User4");
	user4.setValid(true);
	user4.setMoney(125.30);
	userRepository.save(user4);

	User user5 = new User();
	user5.setEmail("user5@email.com");
	user5.setPassword(bCryptPasswordEncoder.encode("12345"));
	user5.setRole(Role.ROLE_STANDARD);
	user5.setName("User5");
	user5.setValid(true);
	user5.setSurname("User5");
	user5.setMoney(50);
	userRepository.save(user5);

	// Ofertas de cada usuario

	Sale oferta1a = new Sale();
	Sale oferta1b = new Sale();
	Sale oferta1c = new Sale();
	oferta1a.setTitle("Oferta 1a");
	oferta1b.setTitle("Oferta 1b");
	oferta1c.setTitle("Oferta 1c");
	oferta1a.setValid(true);
	oferta1b.setValid(true);
	oferta1c.setValid(true);
	oferta1a.setStatus(Status.SOLD);
	oferta1b.setStatus(Status.SOLD);
	oferta1c.setStatus(Status.ONSALE);
	oferta1a.setPrice(10);
	oferta1b.setPrice(110);
	oferta1c.setPrice(50);

	oferta1a.setOwner(user1);
	oferta1b.setOwner(user1);
	oferta1c.setOwner(user1);

	Sale oferta2a = new Sale();
	Sale oferta2b = new Sale();
	Sale oferta2c = new Sale();
	oferta2a.setTitle("Oferta 2a");
	oferta2b.setTitle("Oferta 2b");
	oferta2c.setTitle("Oferta 2c");
	oferta2a.setOwner(user2);
	oferta2b.setOwner(user2);
	oferta2c.setOwner(user2);
	oferta2a.setValid(true);
	oferta2b.setValid(true);
	oferta2c.setValid(true);
	oferta2a.setStatus(Status.ONSALE);
	oferta2b.setStatus(Status.SOLD);
	oferta2c.setStatus(Status.SOLD);
	oferta2a.setPrice(100);
	oferta2b.setPrice(10.50);
	oferta2c.setPrice(55.6);

	Sale oferta3a = new Sale();
	Sale oferta3b = new Sale();
	Sale oferta3c = new Sale();
	oferta3a.setTitle("Oferta 3a");
	oferta3b.setTitle("Oferta 3b");
	oferta3c.setTitle("Oferta 3c");
	oferta3a.setOwner(user3);
	oferta3b.setOwner(user3);
	oferta3c.setOwner(user3);
	oferta3a.setValid(true);
	oferta3b.setValid(true);
	oferta3c.setValid(true);
	oferta3a.setStatus(Status.SOLD);
	oferta3b.setStatus(Status.ONSALE);
	oferta3c.setStatus(Status.SOLD);
	oferta3a.setPrice(1000);
	oferta3b.setPrice(116);
	oferta3c.setPrice(35.25);

	Sale oferta4a = new Sale();
	Sale oferta4b = new Sale();
	Sale oferta4c = new Sale();
	oferta4a.setTitle("Oferta 4a");
	oferta4b.setTitle("Oferta 4b");
	oferta4c.setTitle("Oferta 4c");
	oferta4a.setOwner(user4);
	oferta4b.setOwner(user4);
	oferta4c.setOwner(user4);

	oferta4a.setValid(true);
	oferta4b.setValid(true);
	oferta4c.setValid(true);
	oferta4a.setStatus(Status.SOLD);
	oferta4b.setStatus(Status.SOLD);
	oferta4c.setStatus(Status.ONSALE);

	oferta4a.setPrice(12.6);
	oferta4b.setPrice(63);
	oferta4c.setPrice(21.50);
	Sale oferta5a = new Sale();
	Sale oferta5b = new Sale();
	Sale oferta5c = new Sale();
	oferta5a.setTitle("Oferta 5a");
	oferta5b.setTitle("Oferta 5b");
	oferta5c.setTitle("Oferta 5c");
	oferta5a.setOwner(user5);
	oferta5b.setOwner(user5);
	oferta5c.setOwner(user5);
	oferta5a.setValid(true);
	oferta5b.setValid(true);
	oferta5c.setValid(true);
	oferta5a.setStatus(Status.SOLD);
	oferta5b.setStatus(Status.ONSALE);
	oferta5c.setStatus(Status.SOLD);
	oferta5a.setPrice(47.23);
	oferta5b.setPrice(222);
	oferta5c.setPrice(2.50);
	// Compras
	oferta1a.setBuyer(user2);
	oferta1b.setBuyer(user3);

	oferta2c.setBuyer(user1);
	oferta2b.setBuyer(user4);

	oferta3a.setBuyer(user4);
	oferta3c.setBuyer(user5);

	oferta4a.setBuyer(user3);
	oferta4b.setBuyer(user5);

	oferta5a.setBuyer(user1);
	oferta5c.setBuyer(user2);

	salesRepository.save(oferta1a);
	salesRepository.save(oferta1b);
	salesRepository.save(oferta1c);

	salesRepository.save(oferta2a);
	salesRepository.save(oferta2b);
	salesRepository.save(oferta2c);

	salesRepository.save(oferta3a);
	salesRepository.save(oferta3b);
	salesRepository.save(oferta3c);

	salesRepository.save(oferta4a);
	salesRepository.save(oferta4b);
	salesRepository.save(oferta4c);

	salesRepository.save(oferta5a);
	salesRepository.save(oferta5b);
	salesRepository.save(oferta5c);

    }*/

    @Test
    public void testP01() {
	driver.get("http://localhost:8090/login");
	driver.findElement(By.xpath("//*[contains(text(),'Regístrate')]")).click();
	driver.findElement(By.name("email")).click();
	driver.findElement(By.name("email")).clear();
	driver.findElement(By.name("email")).sendKeys("nuevousuario@email.com");
	driver.findElement(By.name("name")).click();
	driver.findElement(By.name("name")).clear();
	driver.findElement(By.name("name")).sendKeys("UsuarioNuevo");
	driver.findElement(By.name("surname")).click();
	driver.findElement(By.name("surname")).clear();
	driver.findElement(By.name("surname")).sendKeys("UsuarioNuevo");
	driver.findElement(By.name("password")).click();
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys("12345");
	driver.findElement(By.name("passwordConfirm")).click();
	driver.findElement(By.name("passwordConfirm")).clear();
	driver.findElement(By.name("passwordConfirm")).sendKeys("12345");
	driver.findElement(By.xpath("//*[contains(text(),'Registrarse')]")).click();

	// El usuario pasa a la página de inicio.
	List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'Usuario autenticado como:\t')]"));
	assertTrue(list.size() > 0);

	// Usuario se deconecta
	driver.findElement(By.xpath("//*[contains(text(),'Desconectar')]")).click();

    }

    @Test
    public void testP02() {

	driver.get("http://localhost:8090/login");
	driver.findElement(By.xpath("//*[contains(text(),'Regístrate')]")).click();
	// Todos los campos vacios
	driver.findElement(By.xpath("//*[contains(text(),'Registrarse')]")).click();
	// El usuario sigue en la página de registro
	List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'Regístrate')]"));
	assertTrue(list.size() > 0);
	driver.findElement(By.name("email")).clear();
	driver.findElement(By.name("email")).sendKeys("123@email.com");
	// Todos los campos vacios menos el email
	// Usuario sigue en página de registro
	driver.findElement(By.xpath("//*[contains(text(),'Registrarse')]")).click();
	driver.findElement(By.name("name")).click();
	driver.findElement(By.name("name")).clear();
	driver.findElement(By.name("name")).sendKeys("Carmen");
	// Todos los campos vacios menos email y nombre
	// Usuario sigue en página de registro
	driver.findElement(By.xpath("//*[contains(text(),'Registrarse')]")).click();
	driver.findElement(By.name("surname")).click();
	driver.findElement(By.name("surname")).clear();
	driver.findElement(By.name("surname")).sendKeys("Perez Almonte");
	// Campos de contraseñas vacios
	// Usuario sigue en página de registro
	driver.findElement(By.xpath("//*[contains(text(),'Registrarse')]")).click();
    }

    @Test
    public void testP03() {
	driver.get("http://localhost:8090/signup");
	driver.findElement(By.xpath("//*[contains(text(),'Regístrate')]")).click();
	driver.findElement(By.name("email")).click();
	driver.findElement(By.name("email")).clear();
	driver.findElement(By.name("email")).sendKeys("123@email.com");
	driver.findElement(By.name("name")).click();
	driver.findElement(By.name("name")).clear();
	driver.findElement(By.name("name")).sendKeys("Carmen");
	driver.findElement(By.name("surname")).click();
	driver.findElement(By.name("surname")).clear();
	driver.findElement(By.name("surname")).sendKeys("Perez Almonte");
	driver.findElement(By.name("password")).click();
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys("12345");
	driver.findElement(By.name("passwordConfirm")).click();
	driver.findElement(By.name("passwordConfirm")).clear();
	driver.findElement(By.name("passwordConfirm")).sendKeys("abcde");
	driver.findElement(By.xpath("//*[contains(text(),'Registrarse')]")).click();

	// El usuario vuelve a la página de registro
	List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'Regístrate')]"));
	assertTrue(list.size() > 0);

	// Se muestra el mensaje del error.
	List<WebElement> list1 = driver.findElements(By.xpath("//*[contains(text(),'Las contraseñas no coinciden')]"));
	assertTrue(list1.size() > 0);
    }

    @Test
    public void testP04() {
	driver.get("http://localhost:8090/login");
	driver.findElement(By.linkText("Regístrate")).click();
	driver.findElement(By.name("email")).click();
	driver.findElement(By.name("email")).clear();
	driver.findElement(By.name("email")).sendKeys("user1@email.com");
	driver.findElement(By.name("name")).clear();
	driver.findElement(By.name("name")).sendKeys("Carmen");
	driver.findElement(By.name("surname")).clear();
	driver.findElement(By.name("surname")).sendKeys("Perez Almonte");
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys("12345");
	driver.findElement(By.name("passwordConfirm")).clear();
	driver.findElement(By.name("passwordConfirm")).sendKeys("12345");
	driver.findElement(By.xpath("//*[contains(text(),'Registrarse')]")).click();

	// El usuario vuelve a la página de registro
	List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'Regístrate')]"));
	assertTrue(list.size() > 0);

	// Se muestra el mensaje del error.
	List<WebElement> list1 = driver
		.findElements(By.xpath("//*[contains(text(),'Este Email ya está registrado.')]"));
	assertTrue(list1.size() > 0);
    }

    @Test
    public void testP05() {

	driver.get("http://localhost:8090/login");
	driver.findElement(By.name("username")).click();
	driver.findElement(By.name("username")).clear();
	driver.findElement(By.name("username")).sendKeys("admin@email.com");
	driver.findElement(By.name("password")).click();
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys("admin");
	driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();
	// El usuario pasa a la página de inicio.
	List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'Usuario autenticado como:\t')]"));
	assertTrue(list.size() > 0);

	// Usuario se deconecta
	driver.findElement(By.xpath("//*[contains(text(),'Desconectar')]")).click();

    }

    @Test
    public void testP06() {
	driver.get("http://localhost:8090/login");
	driver.findElement(By.name("username")).click();
	driver.findElement(By.name("username")).clear();
	driver.findElement(By.name("username")).sendKeys("user1@email.com");
	driver.findElement(By.name("password")).click();
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys("12345");
	driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();
	// El usuario pasa a la página de inicio.
	List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'Usuario autenticado como:\t')]"));
	assertTrue(list.size() > 0);

	// Usuario se deconecta
	driver.findElement(By.xpath("//*[contains(text(),'Desconectar')]")).click();
    }

    @Test
    public void testP07() {
	driver.get("http://localhost:8090/login");
	driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();
	// Todos los campos vacios
	// El usuario sigue en la página de login
	List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'Email:')]"));
	assertTrue(list.size() > 0);
	driver.findElement(By.name("username")).click();
	driver.findElement(By.name("username")).clear();
	driver.findElement(By.name("username")).sendKeys("user1@email.com");
	driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();
	// Contraseña vacia
	// El usuario sigue en la página de login
	list = driver.findElements(By.xpath("//*[contains(text(),'Email:')]"));
	assertTrue(list.size() > 0);
	driver.findElement(By.name("username")).clear();
	driver.findElement(By.name("username")).sendKeys("");
	driver.findElement(By.name("password")).click();
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys("12345");
	driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();
	// Usuario vacio
	// El usuario sigue en la página de login
	list = driver.findElements(By.xpath("//*[contains(text(),'Email:')]"));
	assertTrue(list.size() > 0);
    }

    @Test
    public void testP08() {
	driver.get("http://localhost:8090/login");
	driver.findElement(By.name("username")).click();
	driver.findElement(By.name("username")).clear();
	driver.findElement(By.name("username")).sendKeys("user1@email.com");
	driver.findElement(By.name("password")).click();
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys("abcde");
	driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();
	// El usuario sigue en la página de login
	// Se muestra el error
	List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'No se pudo iniciar sesión')]"));
	assertTrue(list.size() > 0);
    }

    @Test
    public void testP09() {
	driver.get("http://localhost:8090/login");
	driver.findElement(By.name("username")).click();
	driver.findElement(By.name("username")).clear();
	driver.findElement(By.name("username")).sendKeys("123@email.com");
	driver.findElement(By.name("password")).click();
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys("12345");
	driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();
	// El usuario sigue en la página de login
	// Se muestra el error
	List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'No se pudo iniciar sesión')]"));
	assertTrue(list.size() > 0);
    }

    @Test
    public void testP10() {
	driver.get("http://localhost:8090/login");
	driver.findElement(By.name("username")).click();
	driver.findElement(By.name("username")).clear();
	driver.findElement(By.name("username")).sendKeys("user1@email.com");
	driver.findElement(By.name("password")).click();
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys("12345");
	driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();
	// El usuario pasa a la página de inicio.
	List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'Usuario autenticado como:\t')]"));
	assertTrue(list.size() > 0);

	// Usuario se deconecta
	driver.findElement(By.xpath("//*[contains(text(),'Desconectar')]")).click();
	// El usuario regresa a la pagina de login
	list = driver.findElements(By.xpath("//*[contains(text(),'Login')]"));
	assertTrue(list.size() > 0);

    }

    @Test
    public void testP11() {
	driver.get("http://localhost:8090/login");
	driver.findElement(By.name("username")).click();
	driver.findElement(By.name("username")).clear();
	driver.findElement(By.name("username")).sendKeys("user1@email.com");
	driver.findElement(By.name("password")).click();
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys("12345");
	// El usuario no tiene la opción de desconectarse
	List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'Desconectarse')]"));
	assertTrue(list.size() == 0);
	// El usuario se autentifica
	driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();

	// El usuario tiene la opción de desconectarse
	list = driver.findElements(By.xpath("//*[contains(text(),'Desconectar')]"));
	assertTrue(list.size() > 0);
	// Usuario se deconecta
	driver.findElement(By.xpath("//*[contains(text(),'Desconectar')]")).click();
	// La opción de desconectarse vuelve a no aparecer para el usuario
	list = driver.findElements(By.xpath("//*[contains(text(),'Desconectarse')]"));
	assertTrue(list.size() == 0);
    }

    @Test
    public void testP12() {
	driver.get("http://localhost:8090/login");
	driver.findElement(By.name("username")).click();
	driver.findElement(By.name("username")).clear();
	driver.findElement(By.name("username")).sendKeys("admin@email.com");
	driver.findElement(By.name("password")).click();
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys("admin");
	// El usuario se autentifica
	driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();
	driver.findElement(By.xpath("//*[contains(text(),'Gestionar usuarios')]")).click();
	driver.findElement(By.xpath("//*[contains(text(),'Ver usuarios')]")).click();

	// Comprobamos que están todos los 5 usuarios
	// El usuario administrador no aparece en el listado
	List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'user1@email.com')]"));
	assertTrue(list.size() > 0);
	list = driver.findElements(By.xpath("//*[contains(text(),'user2@email.com')]"));
	assertTrue(list.size() > 0);
	list = driver.findElements(By.xpath("//*[contains(text(),'user3@email.com')]"));
	assertTrue(list.size() > 0);
	list = driver.findElements(By.xpath("//*[contains(text(),'user4@email.com')]"));
	assertTrue(list.size() > 0);
	list = driver.findElements(By.xpath("//*[contains(text(),'user5@email.com')]"));
	assertTrue(list.size() > 0);

	// El administrador se desconecta
	driver.findElement(By.xpath("//*[contains(text(),'Desconectar')]")).click();
    }

    @Test
    public void testP13() {
	driver.get("http://localhost:8090/login");
	driver.findElement(By.name("username")).click();
	driver.findElement(By.name("username")).clear();
	driver.findElement(By.name("username")).sendKeys("admin@email.com");
	driver.findElement(By.name("password")).click();
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys("admin");
	// El usuario se autentifica
	driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();
	driver.findElement(By.xpath("//*[contains(text(),'Gestionar usuarios')]")).click();
	driver.findElement(By.xpath("//*[contains(text(),'Ver usuarios')]")).click();

	// Comprobamos que está el primer usuario
	// El usuario administrador no aparece en el listado
	List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'user1@email.com')]"));
	assertTrue(list.size() > 0);
	// Lo eliminamos
	driver.findElement(By.name("idsUser")).click();
	// driver.findElement(By.xpath("(.//*[normalize-space(text()) and
	// normalize-space(.)='User5'])[2]/following::input[2]")).click();
	driver.findElement(By.name("delete")).click();

	// Comprobamos que el primer usuario ya no aparece
	list = driver.findElements(By.xpath("//*[contains(text(),'user1@email.com')]"));
	assertTrue(list.size() == 0);

	// Se muestra el mensaje de borrado correcto
	list = driver.findElements(By.xpath("//*[contains(text(),'Se eliminó correctamente el usuario')]"));
	assertTrue(list.size() > 0);

	// El administrador se desconecta
	driver.findElement(By.xpath("//*[contains(text(),'Desconectar')]")).click();
    }

    @Test
    public void testP14() {
	driver.get("http://localhost:8090/login");
	driver.findElement(By.name("username")).click();
	driver.findElement(By.name("username")).clear();
	driver.findElement(By.name("username")).sendKeys("admin@email.com");
	driver.findElement(By.name("password")).click();
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys("admin");
	// El usuario se autentifica
	driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();
	driver.findElement(By.xpath("//*[contains(text(),'Gestionar usuarios')]")).click();
	driver.findElement(By.xpath("//*[contains(text(),'Ver usuarios')]")).click();

	// Comprobamos que está el último usuario
	// El usuario administrador no aparece en el listado
	List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'user5@email.com')]"));
	assertTrue(list.size() > 0);
	driver.findElement(
		By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='User5'])[2]/following::input[1]"))
		.click();
	driver.findElement(By.name("delete")).click();

	// Comprobamos que el primer usuario ya no aparece
	list = driver.findElements(By.xpath("//*[contains(text(),'user5@email.com')]"));
	assertTrue(list.size() == 0);

	// Se muestra el mensaje de borrado correcto
	list = driver.findElements(By.xpath("//*[contains(text(),'Se eliminó correctamente el usuario')]"));
	assertTrue(list.size() > 0);

	// El administrador se desconecta
	driver.findElement(By.xpath("//*[contains(text(),'Desconectar')]")).click();
    }

    @Test
    public void testP15() {
	driver.get("http://localhost:8090/login");
	driver.findElement(By.name("username")).click();
	driver.findElement(By.name("username")).clear();
	driver.findElement(By.name("username")).sendKeys("admin@email.com");
	driver.findElement(By.name("password")).click();
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys("admin");
	// El usuario se autentifica
	driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();
	driver.findElement(By.xpath("//*[contains(text(),'Gestionar usuarios')]")).click();
	driver.findElement(By.xpath("//*[contains(text(),'Ver usuarios')]")).click();

	// Comprobamos que está los 3 usuarios que vamos a borrar
	// El usuario administrador no aparece en el listado
	List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'user1@email.com')]"));
	assertTrue(list.size() > 0);
	list = driver.findElements(By.xpath("//*[contains(text(),'user2@email.com')]"));
	assertTrue(list.size() > 0);
	list = driver.findElements(By.xpath("//*[contains(text(),'user3@email.com')]"));
	assertTrue(list.size() > 0);

	// Eliminamos los tres usuarios
	driver.findElement(By.name("idsUser")).click();
	
	driver.findElement(
		By.xpath("//*[contains(text(),  'User2')][2]/following::input[1]"))
		.click();

	driver.findElement(
		By.xpath("//*[contains(text(),  'User3')][2]/following::input[1]"))
		.click();
	

	driver.findElement(By.name("delete")).click();

	// Comprobamos que ya no están los usuarios

	list = driver.findElements(By.xpath("//*[contains(text(),'user1@email.com')]"));
	assertTrue(list.size() == 0);
	list = driver.findElements(By.xpath("//*[contains(text(),'user2@email.com')]"));
	assertTrue(list.size() == 0);
	list = driver.findElements(By.xpath("//*[contains(text(),'user3@email.com')]"));
	assertTrue(list.size() == 0);

	// Se muestra el mensaje de borrado correcto
	list = driver.findElements(By.xpath("//*[contains(text(),'Se eliminó correctamente el usuario')]"));
	assertTrue(list.size() > 0);
	// El administrador se desconecta
	driver.findElement(By.xpath("//*[contains(text(),'Desconectar')]")).click();
    }

    @Test
    public void testP16() {
	driver.get("http://localhost:8090/login");
	driver.findElement(By.name("username")).click();
	driver.findElement(By.name("username")).clear();
	driver.findElement(By.name("username")).sendKeys("user1@email.com");
	driver.findElement(By.name("password")).click();
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys("12345");
	// El usuario se autentifica
	driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();
	driver.findElement(By.linkText("Ofertas")).click();
	driver.findElement(By.linkText("Nueva oferta")).click();
	driver.findElement(By.name("title")).click();
	driver.findElement(By.name("title")).clear();
	driver.findElement(By.name("title")).sendKeys("Nueva oferta");
	driver.findElement(By.name("description")).click();
	driver.findElement(By.name("description")).clear();
	driver.findElement(By.name("description")).sendKeys("Nueva oferta del user1");
	driver.findElement(By.name("price")).click();
	driver.findElement(By.name("price")).clear();
	driver.findElement(By.name("price")).sendKeys("120");
	driver.findElement(By.xpath("//*[contains(text(),'Publicar')]")).click();

	// Vamos a la pagina con nuestras ofertas publicadas
	List<WebElement> list = driver
		.findElements(By.xpath("//*[contains(text(),'Estas son las ofertas que has publicado')]"));
	assertTrue(list.size() > 0);
	// Comprobamos la oferta aparece en el listado.
	list = driver.findElements(By.xpath("//*[contains(text(),'Nueva oferta')]"));
	assertTrue(list.size() > 0);
	// El usuario se desconecta
	driver.findElement(By.xpath("//*[contains(text(),'Desconectar')]")).click();
    }

    @Test
    public void testP17() {
	driver.get("http://localhost:8090/login");
	driver.findElement(By.name("username")).click();
	driver.findElement(By.name("username")).clear();
	driver.findElement(By.name("username")).sendKeys("user1@email.com");
	driver.findElement(By.name("password")).click();
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys("12345");
	// El usuario se autentifica
	driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();
	driver.findElement(By.linkText("Ofertas")).click();
	driver.findElement(By.linkText("Nueva oferta")).click();
	// Todos los campos vacios
	driver.findElement(By.xpath("//*[contains(text(),'Publicar')]")).click();
	// Seguimos en la página para añadir una oferta
	List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'Nueva oferta')]"));
	assertTrue(list.size() > 0);

	// Solo el título vacio
	driver.findElement(By.name("description")).click();
	driver.findElement(By.name("description")).clear();
	driver.findElement(By.name("description")).sendKeys("Nueva oferta de user1");
	driver.findElement(By.name("price")).click();
	driver.findElement(By.name("price")).clear();
	driver.findElement(By.name("price")).sendKeys("20");
	driver.findElement(By.xpath("//*[contains(text(),'Publicar')]")).click();
	// Continuamos en la misma página
	list = driver.findElements(By.xpath("//*[contains(text(),'Nueva oferta')]"));
	assertTrue(list.size() > 0);

	// Solo descripción vacía
	driver.findElement(By.name("description")).clear();
	driver.findElement(By.name("description")).sendKeys("");
	driver.findElement(By.name("title")).click();
	driver.findElement(By.name("title")).clear();
	driver.findElement(By.name("title")).sendKeys("Nueva oferta");
	driver.findElement(By.xpath("//*[contains(text(),'Publicar')]")).click();
	// Continuamos en la misma página
	list = driver.findElements(By.xpath("//*[contains(text(),'Nueva oferta')]"));
	assertTrue(list.size() > 0);
	// Solo el precio vacio
	driver.findElement(By.name("description")).click();
	driver.findElement(By.name("description")).clear();
	driver.findElement(By.name("description")).sendKeys("Nueva oferta del user1");
	driver.findElement(By.name("price")).clear();
	driver.findElement(By.name("price")).sendKeys("");
	driver.findElement(By.xpath("//*[contains(text(),'Publicar')]")).click();
	// Continuamos en la misma página
	list = driver.findElements(By.xpath("//*[contains(text(),'Nueva oferta')]"));
	assertTrue(list.size() > 0);
	// El usuario se desconecta
	driver.findElement(By.xpath("//*[contains(text(),'Desconectar')]")).click();
    }

    @Test
    public void testP18() {
	driver.get("http://localhost:8090/login");
	driver.findElement(By.name("username")).click();
	driver.findElement(By.name("username")).clear();
	driver.findElement(By.name("username")).sendKeys("user1@email.com");
	driver.findElement(By.name("password")).click();
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys("12345");
	// El usuario se autentifica
	driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();
	driver.findElement(By.linkText("Personal")).click();
	driver.findElement(By.linkText("Ofertas publicadas")).click();

	// Comprobamos que estamos en la página adecuada
	List<WebElement> list = driver
		.findElements(By.xpath("//*[contains(text(),'Estas son las ofertas que has publicado')]"));
	assertTrue(list.size() > 0);

	// Comprobamos que están todas las ofertas
	list = driver.findElements(By.xpath("//*[contains(text(),'Oferta 1a')]"));
	assertTrue(list.size() > 0);
	list = driver.findElements(By.xpath("//*[contains(text(),'Oferta 1b')]"));
	assertTrue(list.size() > 0);
	list = driver.findElements(By.xpath("//*[contains(text(),'Oferta 1c')]"));
	assertTrue(list.size() > 0);

	// El usuario se desconecta
	driver.findElement(By.xpath("//*[contains(text(),'Desconectar')]")).click();
    }

    @Test
    public void testP19() {
	driver.get("http://localhost:8090/login");
	driver.findElement(By.name("username")).click();
	driver.findElement(By.name("username")).clear();
	driver.findElement(By.name("username")).sendKeys("user1@email.com");
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys("12345");
	// El usuario se autentifica
	driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();
	driver.findElement(By.linkText("Personal")).click();
	driver.findElement(By.linkText("Ofertas publicadas")).click();

	// Comprobamos que estamos en la página adecuada
	List<WebElement> list = driver
		.findElements(By.xpath("//*[contains(text(),'Estas son las ofertas que has publicado')]"));
	assertTrue(list.size() > 0);

	// Comprobamos que está la primera oferta
	list = driver.findElements(By.xpath("//*[contains(text(),'Oferta 1a')]"));
	assertTrue(list.size() > 0);
	
	// La borramos
	driver.findElement(
		By.xpath("//*[contains(text(),  'Vendida')][1]/following::input[1]"))
		.click();
	
	// Comprobamos que ya no está
	list = driver.findElements(By.xpath("//*[contains(text(),'Oferta 1a')]"));
	assertTrue(list.size() == 0);
	// Mensaje de borrado correcto
	list = driver.findElements(By.xpath("//*[contains(text(),'La oferta fue borrada correctamente')]"));
	assertTrue(list.size() > 0);
	// El usuario se desconecta
	driver.findElement(By.xpath("//*[contains(text(),'Desconectar')]")).click();
    }

    @Test
    public void testP20() {
	driver.get("http://localhost:8090/login");
	driver.findElement(By.name("username")).click();
	driver.findElement(By.name("username")).clear();
	driver.findElement(By.name("username")).sendKeys("user1@email.com");
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys("12345");
	// El usuario se autentifica
	driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();
	driver.findElement(By.linkText("Personal")).click();
	driver.findElement(By.linkText("Ofertas publicadas")).click();

	// Comprobamos que estamos en la página adecuada
	List<WebElement> list = driver
		.findElements(By.xpath("//*[contains(text(),'Estas son las ofertas que has publicado')]"));
	assertTrue(list.size() > 0);

	// Comprobamos que está la última oferta
	list = driver.findElements(By.xpath("//*[contains(text(),'Oferta 1c')]"));
	assertTrue(list.size() > 0);

	driver.findElement(
		By.xpath("//*[contains(text(),  'Sin vender')][1]/following::input[1]"))
		.click();
	// Comprobamos que ya no está
	list = driver.findElements(By.xpath("//*[contains(text(),'Oferta 1c')]"));
	assertTrue(list.size() == 0);
	// Mensaje de borrado correcto
	list = driver.findElements(By.xpath("//*[contains(text(),'La oferta fue borrada correctamente')]"));
	assertTrue(list.size() > 0);

	// El usuario se desconecta
	driver.findElement(By.xpath("//*[contains(text(),'Desconectar')]")).click();
    }

    @Test
    public void testP21() {
	driver.get("http://localhost:8090/login");
	driver.findElement(By.name("username")).click();
	driver.findElement(By.name("username")).clear();
	driver.findElement(By.name("username")).sendKeys("user1@email.com");
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys("12345");
	// El usuario se autentifica
	driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();
	driver.findElement(By.linkText("Ofertas")).click();
	driver.findElement(By.linkText("Buscar ofertas")).click();

	// Buscamos con el texto vacio
	driver.findElement(By.name("search")).click();

	// Comprobamos que está la primera oferta de la página
	List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'Oferta 2a')]"));
	assertTrue(list.size() > 0);
	// Comprobamos que está la última oferta de la página
	list = driver.findElements(By.xpath("//*[contains(text(),'Oferta 3b')]"));
	assertTrue(list.size() > 0);

	// El usuario se desconecta
	driver.findElement(By.xpath("//*[contains(text(),'Desconectar')]")).click();
    }

    @Test
    public void testP22() {
	driver.get("http://localhost:8090/login");
	driver.findElement(By.name("username")).click();
	driver.findElement(By.name("username")).clear();
	driver.findElement(By.name("username")).sendKeys("user1@email.com");
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys("12345");
	// El usuario se autentifica
	driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();
	driver.findElement(By.linkText("Ofertas")).click();
	driver.findElement(By.linkText("Buscar ofertas")).click();

	driver.findElement(By.name("searchText")).click();
	driver.findElement(By.name("searchText")).clear();
	// Buscamos un texto que no existe
	driver.findElement(By.name("searchText")).sendKeys("12345");
	driver.findElement(By.name("search")).click();

	// Comprobamos que no se muestra ninguna oferta
	List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'12345')]"));
	assertTrue(list.size() == 0);

	// El usuario se desconecta
	driver.findElement(By.xpath("//*[contains(text(),'Desconectar')]")).click();
    }

    @Test
    public void testP23() {
	driver.get("http://localhost:8090/login");
	driver.findElement(By.name("username")).click();
	driver.findElement(By.name("username")).clear();
	driver.findElement(By.name("username")).sendKeys("user3@email.com");
	driver.findElement(By.name("password")).click();
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys("12345");
	// El usuario se autentifica
	driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();

	// Comprobamos la cantidad de dinero que tiene el usuario antes de la compra
	List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'80.95')]"));
	assertTrue(list.size() > 0);
	driver.findElement(By.linkText("Ofertas")).click();
	driver.findElement(By.linkText("Buscar ofertas")).click();

	driver.findElement(By.name("searchText")).click();
	driver.findElement(By.name("searchText")).clear();

	// Buscamos la oferta 1c
	driver.findElement(By.name("searchText")).sendKeys("1c");
	driver.findElement(By.name("search")).click();

	// La compramos
	driver.findElement(
		By.xpath("//*[contains(text(),  'Oferta 1c')][1]/following::input[1]"))
		.click();
	// Vamos a la vista del usuario
	driver.findElement(By.xpath("//*[contains(text(),'Home')]")).click();
	// Comprobamos que se ha actualizado su dinero
	list = driver.findElements(By.xpath("//*[contains(text(),'30.950000000000003')]"));
	assertTrue(list.size() > 0);

	// El usuario se desconecta
	driver.findElement(By.xpath("//*[contains(text(),'Desconectar')]")).click();

    }

    @Test
    public void testP24() {
	driver.get("http://localhost:8090/login");
	driver.findElement(By.name("username")).click();
	driver.findElement(By.name("username")).clear();
	driver.findElement(By.name("username")).sendKeys("user1@email.com");
	driver.findElement(By.name("password")).click();
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys("12345");
	// El usuario se autentifica
	driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();

	// Comprobamos la cantidad de dinero que tiene el usuario antes de la compra
	List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'100.0')]"));
	assertTrue(list.size() > 0);
	driver.findElement(By.linkText("Ofertas")).click();
	driver.findElement(By.linkText("Buscar ofertas")).click();
	// Buscamos la oferta 2c
	driver.findElement(By.name("searchText")).click();
	driver.findElement(By.name("searchText")).clear();
	driver.findElement(By.name("searchText")).sendKeys("2a");
	driver.findElement(By.name("search")).click();
	
	driver.findElement(
		By.xpath("//*[contains(text(),  'Oferta 2a')][1]/following::input[1]"))
		.click();
	driver.findElement(By.linkText("Home")).click();
	// Comprobamos que se ha actualizado su dinero
	list = driver.findElements(By.xpath("//*[contains(text(),'0.0')]"));
	assertTrue(list.size() > 0);

	// El usuario se desconecta
	driver.findElement(By.xpath("//*[contains(text(),'Desconectar')]")).click();
    }

    @Test
    public void testP25() {
	driver.get("http://localhost:8090/login");
	driver.findElement(By.name("username")).click();
	driver.findElement(By.name("username")).clear();
	driver.findElement(By.name("username")).sendKeys("user3@email.com");
	driver.findElement(By.name("password")).click();
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys("12345");
	// El usuario se autentifica
	driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();

	// Comprobamos la cantidad de dinero que tiene el usuario antes de la compra
	List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'80.95')]"));
	assertTrue(list.size() > 0);
	driver.findElement(By.linkText("Ofertas")).click();
	driver.findElement(By.linkText("Buscar ofertas")).click();

	// Buscamos la oferta 2a
	driver.findElement(By.name("searchText")).click();
	driver.findElement(By.name("searchText")).clear();
	driver.findElement(By.name("searchText")).sendKeys("2a");
	driver.findElement(By.name("search")).click();

	// La compramos
	driver.findElement(
		By.xpath("//*[contains(text(),  'Oferta 2a')][1]/following::input[1]"))
		.click();
	// Comprobamos que sale el mensaje de que no se ha podido comprar la oferta
	list = driver.findElements(By.xpath("//*[contains(text(),'La compra no pudo completarse')]"));
	assertTrue(list.size() > 0);

	// Comprobamos que la cantidad de dinero sigue siendo la misma
	driver.findElement(By.linkText("Home")).click();
	list = driver.findElements(By.xpath("//*[contains(text(),'80.95')]"));
	assertTrue(list.size() > 0);

	// El usuario se desconecta
	driver.findElement(By.xpath("//*[contains(text(),'Desconectar')]")).click();
    }

    @Test
    public void testP26() {
	driver.get("http://localhost:8090/login");
	driver.findElement(By.name("username")).click();
	driver.findElement(By.name("username")).clear();
	driver.findElement(By.name("username")).sendKeys("user1@email.com");
	driver.findElement(By.name("password")).click();
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys("12345");
	// El usuario se autentifica
	driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();
	driver.findElement(By.linkText("Personal")).click();
	driver.findElement(By.linkText("Ofertas compradas")).click();

	// Comprobamos que está en la página correcta
	List<WebElement> list = driver
		.findElements(By.xpath("//*[contains(text(),'Estas son las ofertas que has comprado')]"));
	assertTrue(list.size() > 0);

	// Comprobamos que aparecen todas las ofertas
	list = driver.findElements(By.xpath("//*[contains(text(),'Oferta 2c')]"));
	assertTrue(list.size() > 0);
	list = driver.findElements(By.xpath("//*[contains(text(),'Oferta 5a')]"));
	assertTrue(list.size() > 0);

	// El usuario se desconecta
	driver.findElement(By.xpath("//*[contains(text(),'Desconectar')]")).click();
    }

    @Test
    public void testP27() {
	driver.get("http://localhost:8090/login");
	driver.findElement(By.name("username")).click();
	driver.findElement(By.name("username")).clear();
	driver.findElement(By.name("username")).sendKeys("user1@email.com");
	driver.findElement(By.name("password")).click();
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys("12345");
	// El usuario se autentifica
	driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();

	// Página principal

	// Comprobamos algunos elementos en español
	List<WebElement> list = driver
		.findElements(By.xpath("//*[contains(text(),'Bienvenidos a la página principal')]"));
	assertTrue(list.size() > 0);
	list = driver.findElements(By.xpath("//*[contains(text(),'Esta es una zona privada la web')]"));
	assertTrue(list.size() > 0);

	// Cambiamos el idioma
	driver.findElement(By.id("btnLanguage")).click();
	driver.findElement(
		By.id("btnEnglish"))
		.click();

	// Volvemos a comprobar los mismos mensajes
	list = driver.findElements(By.xpath("//*[contains(text(),'Welcome to homepage')]"));
	assertTrue(list.size() > 0);
	list = driver.findElements(By.xpath("//*[contains(text(),'This is a private area of the web')]"));
	assertTrue(list.size() > 0);

	// Vamos a la vista de las ofertas publicadas
	driver.findElement(By.linkText("Personal")).click();
	driver.findElement(By.linkText("Posted sales")).click();

	// Comprobamos algunos mensajes
	list = driver.findElements(By.xpath("//*[contains(text(),'These are all the sales that you have posted')]"));
	assertTrue(list.size() > 0);
	list = driver.findElements(By.xpath("//*[contains(text(),'Your sales')]"));
	assertTrue(list.size() > 0);

	// Cambiamos el idioma a español de nuevo
	driver.findElement(By.id("btnLanguage")).click();
	driver.findElement(
		By.id("btnSpanish"))
		.click();
	// Volvemos a comprobar los mensajes
	list = driver.findElements(By.xpath("//*[contains(text(),'Estas son las ofertas que has publicado')]"));
	assertTrue(list.size() > 0);
	list = driver.findElements(By.xpath("//*[contains(text(),'Tus ofertas')]"));
	assertTrue(list.size() > 0);

	// Vamos a la página para añadir una oferta nueva
	driver.findElement(By.linkText("Ofertas")).click();
	driver.findElement(By.linkText("Nueva oferta")).click();
	// Comprobamos algunos mensajes
	list = driver.findElements(By.xpath("//*[contains(text(),'Nueva oferta')]"));
	assertTrue(list.size() > 0);
	list = driver.findElements(By.xpath("//*[contains(text(),'Titulo de la oferta:')]"));
	assertTrue(list.size() > 0);
	// Cambiamos el idioma al inglés
	driver.findElement(By.id("btnLanguage")).click();
	driver.findElement(
		By.id("btnEnglish"))
		.click();
	// Comprobamos los mensajes de nuevo
	list = driver.findElements(By.xpath("//*[contains(text(),'New sale')]"));
	assertTrue(list.size() > 0);
	list = driver.findElements(By.xpath("//*[contains(text(),'Title of the sale:')]"));
	assertTrue(list.size() > 0);

	// El usuario se desconecta
	driver.findElement(By.xpath("//*[contains(text(),'Disconnect')]")).click();
	// El administrador inicia sesión
	driver.findElement(By.name("username")).click();
	driver.findElement(By.name("username")).clear();
	driver.findElement(By.name("username")).sendKeys("admin@email.com");
	driver.findElement(By.name("password")).click();
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys("admin");
	driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();

	// Vamos a la vista con la lista de usuarios
	driver.findElement(By.linkText("Gestionar usuarios")).click();
	driver.findElement(By.linkText("Ver usuarios")).click();
	// Comprobamos algunos mensajes
	list = driver.findElements(By.xpath("//*[contains(text(),'Estos son los usuarios que están en el sistema')]"));
	assertTrue(list.size() > 0);
	list = driver.findElements(By.xpath("//*[contains(text(),'Apellidos')]"));
	assertTrue(list.size() > 0);

	// Cambiamos el idioma al inglés
	driver.findElement(By.id("btnLanguage")).click();
	driver.findElement(By.id("btnEnglish")).click();

	// Comprobamos de nuevo los mensajes
	list = driver.findElements(By.xpath("//*[contains(text(),'These are the users in the system')]"));
	assertTrue(list.size() > 0);
	list = driver.findElements(By.xpath("//*[contains(text(),'Surname')]"));
	assertTrue(list.size() > 0);
	// El usuario se desconecta
	driver.findElement(By.xpath("//*[contains(text(),'Disconnect')]")).click();
    }

    @Test
    public void testP28() {
	driver.get("http://localhost:8090/login");
	// Intentamos ir a la lista de usuarios sin autentificar
	driver.get("http://localhost:8090/user/list");
	// Comprobamos que volvemos a la página de login
	List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'Identifícate')]"));
	assertTrue(list.size() > 0);

    }

    @Test
    public void testP29() {
	driver.get("http://localhost:8090/login");
	// Intentamos ir a la lista de ofertas del usuario
	driver.get("http://localhost:8090/sale/list");
	// Comprobamos que volvemos a la página de login
	List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'Identifícate')]"));
	assertTrue(list.size() > 0);

    }

    @Test
    public void testP30() {
	driver.get("http://localhost:8090/login");
	driver.findElement(By.name("username")).click();
	driver.findElement(By.name("username")).clear();
	driver.findElement(By.name("username")).sendKeys("user1@email.com");
	driver.findElement(By.name("password")).click();
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys("12345");
	// El usuario se autentifica
	driver.findElement(By.xpath("//*[contains(text(),'Login')]")).click();
	driver.get("http://localhost:8090/user/list");
	// Comprobamos que no está permitido
	List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'HTTP Status 403 – Forbidden')]"));
	assertTrue(list.size() > 0);
    }

}
