package com.uniovi.tests;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SdiEntrega21010Tests {

	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver022 = "C:\\Users\\viole\\Desktop\\PL-SDI-Sesion5-material\\geckodriver024win64.exe";
	static WebDriver driver = getDriver(PathFirefox65, Geckdriver022);
	static String URL = "http://localhost:8081";
	private static final int TIME = 5;

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	@Before
	public void setUp() {
		// initdb();

		driver.get("http://localhost:8081/login");
		waitForWeb();
		driver.findElement(By.name("email")).click();
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("admin@email.com");
		driver.findElement(By.name("password")).click();
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("admin");
		driver.findElement(By.xpath("//*[contains(text(),'Aceptar')]")).click();
		waitForWeb();

		driver.findElement(By.linkText("reset DB")).click();
		waitForWeb();
	}

	public void waitForWeb() {
		try {
			TimeUnit.SECONDS.sleep(TIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// ------------------------PRUEBAS APLICACIÓN WEB----------------------------

	// [Prueba1] Registro de Usuario con datos válidos
	@Test
	public void testP01() {
		driver.get("http://localhost:8081/signup");
		waitForWeb();
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
		driver.findElement(By.name("password")).sendKeys("123456789");
		driver.findElement(By.name("repassword")).click();
		driver.findElement(By.name("repassword")).clear();
		driver.findElement(By.name("repassword")).sendKeys("123456789");
		driver.findElement(By.xpath("//*[contains(text(),'Registrarse')]")).click();
		waitForWeb();

		// El usuario pasa a la página de inicio.
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'Nuevo usuario registrado')]"));
		assertTrue(list.size() > 0);

	}

	// [Prueba2] Registro de Usuario con datos inválidos (repetición de contraseña
	// inválida).
	@Test
	public void testP02() {
		driver.get("http://localhost:8081/signup");
		waitForWeb();

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
		driver.findElement(By.name("password")).sendKeys("123456789");
		driver.findElement(By.name("repassword")).click();
		driver.findElement(By.name("repassword")).clear();
		driver.findElement(By.name("repassword")).sendKeys("abcdefghijklm");
		driver.findElement(By.xpath("//*[contains(text(),'Registrarse')]")).click();
		waitForWeb();

		// El usuario vuelve a la página de registro
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'Registrar usuario')]"));
		assertTrue(list.size() > 0);

		// Se muestra el mensaje del error.
		List<WebElement> list1 = driver.findElements(By.xpath("//*[contains(text(),'Las contraseñas no coinciden')]"));
		assertTrue(list1.size() > 0);
	}

//[Prueba3] Registro de Usuario con email existente
	@Test
	public void testP03() {
		driver.get("http://localhost:8081/signup");
		waitForWeb();
		driver.findElement(By.name("email")).click();
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("user1@email.com");
		driver.findElement(By.name("name")).clear();
		driver.findElement(By.name("name")).sendKeys("Carmen");
		driver.findElement(By.name("surname")).clear();
		driver.findElement(By.name("surname")).sendKeys("Perez Almonte");
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("123456789");
		driver.findElement(By.name("repassword")).clear();
		driver.findElement(By.name("repassword")).sendKeys("123456789");

		driver.findElement(By.xpath("//*[contains(text(),'Registrarse')]")).click();
		waitForWeb();

		// El usuario vuelve a la página de registro
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'Registrar usuario')]"));
		assertTrue(list.size() > 0);

		// Se muestra el mensaje del error.
		List<WebElement> list1 = driver.findElements(By.xpath(
				"//*[contains(text(),'El email ya está registrado. Inténtelo de nuevo con un email diferente')]"));
		assertTrue(list1.size() > 0);
	}

//[Prueba4] Inicio de sesión con datos válidos.
	@Test
	public void testP04Admin() {

		driver.get("http://localhost:8081/login");
		waitForWeb();
		driver.findElement(By.name("email")).click();
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("admin@email.com");
		driver.findElement(By.name("password")).click();
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("admin");
		driver.findElement(By.xpath("//*[contains(text(),'Aceptar')]")).click();
		waitForWeb();
		// El usuario pasa a la página de inicio.
		List<WebElement> list = driver
				.findElements(By.xpath("//*[contains(text(),'Bienvenidos a la página principal')]"));
		assertTrue(list.size() > 0);

		// Usuario se deconecta
		driver.findElement(By.xpath("//*[contains(text(),'Logout')]")).click();

	}

//[Prueba4] Inicio de sesión con datos válidos.
	@Test
	public void testP04Estandar() {
		driver.get("http://localhost:8081/login");
		waitForWeb();
		driver.findElement(By.name("email")).click();
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("user1@email.com");
		driver.findElement(By.name("password")).click();
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("123456789");
		driver.findElement(By.xpath("//*[contains(text(),'Aceptar')]")).click();
		waitForWeb();
		// El usuario pasa a la página de inicio.
		List<WebElement> list = driver
				.findElements(By.xpath("//*[contains(text(),'Bienvenidos a la página principal')]"));
		assertTrue(list.size() > 0);

		// Usuario se deconecta
		driver.findElement(By.xpath("//*[contains(text(),'Logout')]")).click();
	}

	// [Prueba5] Inicio de sesión con datos inválidos (email existente, pero
	// contraseña incorrecta)
	@Test
	public void testP05() {
		driver.get("http://localhost:8081/login");
		waitForWeb();
		driver.findElement(By.name("email")).click();
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("user1@email.com");
		driver.findElement(By.name("password")).click();
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("abcde");
		driver.findElement(By.xpath("//*[contains(text(),'Aceptar')]")).click();
		waitForWeb();
		// El usuario sigue en la página de login
		// Se muestra el error
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'Email o password incorrecto')]"));
		assertTrue(list.size() > 0);
	}

	// [Prueba6] Inicio de sesión con datos válidos (campo email o contraseña
	// vacíos)
	@Test
	public void testP06() {
		driver.get("http://localhost:8081/login");
		waitForWeb();
		driver.findElement(By.xpath("//*[contains(text(),'Aceptar')]")).click();
		waitForWeb();
		// Todos los campos vacios
		// El usuario sigue en la página de login
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'Identificación de usuario')]"));
		assertTrue(list.size() > 0);
		driver.findElement(By.name("email")).click();
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("user1@email.com");
		driver.findElement(By.xpath("//*[contains(text(),'Aceptar')]")).click();
		waitForWeb();
		// Contraseña vacia
		// El usuario sigue en la página de login
		list = driver.findElements(By.xpath("//*[contains(text(),'Identificación de usuario')]"));
		assertTrue(list.size() > 0);
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("");
		driver.findElement(By.name("password")).click();
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("123456789");
		driver.findElement(By.xpath("//*[contains(text(),'Aceptar')]")).click();
		waitForWeb();
		// Usuario vacio
		// El usuario sigue en la página de login
		list = driver.findElements(By.xpath("//*[contains(text(),'Identificación de usuario')]"));
		assertTrue(list.size() > 0);
	}

	// [Prueba7] Inicio de sesión con datos inválidos (email no existente en la
	// aplicación)
	@Test
	public void testP07() {
		driver.get("http://localhost:8081/login");
		waitForWeb();
		driver.findElement(By.name("email")).click();
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("123@email.com");
		driver.findElement(By.name("password")).click();
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("12345");
		driver.findElement(By.xpath("//*[contains(text(),'Aceptar')]")).click();
		waitForWeb();
		// El usuario sigue en la página de login
		// Se muestra el error
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'Email o password incorrecto')]"));
		assertTrue(list.size() > 0);
	}

	// [Prueba8] Hacer click en la opción de salir de sesión y comprobar que se
	// redirige a la página de inicio de
	// sesión (Login).
	// [Prueba9] Comprobar que el botón cerrar sesión no está visible si el usuario
	// no está autenticado
	@Test
	public void testP0809() {
		driver.get("http://localhost:8081/login");
		waitForWeb();
		driver.findElement(By.name("email")).click();
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("user1@email.com");
		driver.findElement(By.name("password")).click();
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("123456789");
		// El usuario no tiene la opción de desconectarse
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'Logout')]"));
		assertTrue(list.size() == 0);
		// El usuario se autentifica
		driver.findElement(By.xpath("//*[contains(text(),'Aceptar')]")).click();
		waitForWeb();

		// El usuario tiene la opción de desconectarse
		list = driver.findElements(By.xpath("//*[contains(text(),'Logout')]"));
		assertTrue(list.size() > 0);
		// Usuario se deconecta
		driver.findElement(By.xpath("//*[contains(text(),'Logout')]")).click();
		waitForWeb();
		// La opción de desconectarse vuelve a no aparecer para el usuario
		list = driver.findElements(By.xpath("//*[contains(text(),'Logout')]"));
		assertTrue(list.size() == 0);
		list = driver.findElements(By.xpath("//*[contains(text(),'Identificación de usuario')]"));
		assertTrue(list.size() > 0);
	}

	// [Prueba10] Mostrar el listado de usuarios y comprobar que se muestran todos
	// los que existen en el
	// sistema.
	@Test
	public void testP10() {
		driver.get("http://localhost:8081/login");
		waitForWeb();
		driver.findElement(By.name("email")).click();
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("admin@email.com");
		driver.findElement(By.name("password")).click();
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("admin");
		// El usuario se autentifica
		driver.findElement(By.xpath("//*[contains(text(),'Aceptar')]")).click();
		waitForWeb();
		driver.findElement(By.xpath("//*[contains(text(),'Usuarios')]")).click();
		waitForWeb();
		// driver.findElement(By.xpath("//*[contains(text(),'Ver usuarios')]")).click();

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
		driver.findElement(By.xpath("//*[contains(text(),'Logout')]")).click();
	}

	// [Prueba11] Ir a la lista de usuarios, borrar el primer usuario de la lista,
	// comprobar que la lista se actualiza
	// y dicho usuario desaparece.
	@Test
	public void testP11() {
		driver.get("http://localhost:8081/login");
		waitForWeb();
		driver.findElement(By.name("email")).click();
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("admin@email.com");
		driver.findElement(By.name("password")).click();
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("admin");
		// El usuario se autentifica
		driver.findElement(By.xpath("//*[contains(text(),'Aceptar')]")).click();
		waitForWeb();
		driver.findElement(By.xpath("//*[contains(text(),'Usuarios')]")).click();
		waitForWeb();
		// driver.findElement(By.xpath("//*[contains(text(),'Ver usuarios')]")).click();

		// Comprobamos que está el primer usuario
		// El usuario administrador no aparece en el listado
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'user1@email.com')]"));
		assertTrue(list.size() > 0);
		// Lo eliminamos
		driver.findElement(By.id("user1@email.com")).click();
		waitForWeb();
		// driver.findElement(By.xpath("(.//*[normalize-space(text()) and
		// normalize-space(.)='User5'])[2]/following::input[2]")).click();
		driver.findElement(By.name("delete")).click();
		waitForWeb();

		// Comprobamos que el primer usuario ya no aparece
		list = driver.findElements(By.xpath("//*[contains(text(),'user1@email.com')]"));
		assertTrue(list.size() == 0);

		// Se muestra el mensaje de borrado correcto
		list = driver.findElements(By.xpath("//*[contains(text(),'Los usuarios se eliminaron correctamente')]"));
		assertTrue(list.size() > 0);

		// El administrador se desconecta
		driver.findElement(By.xpath("//*[contains(text(),'Logout')]")).click();
	}

	// [Prueba12] Ir a la lista de usuarios, borrar el último usuario de la lista,
	// comprobar que la lista se actualiza
	// y dicho usuario desaparece
	@Test
	public void testP12() {
		driver.get("http://localhost:8081/login");
		waitForWeb();
		driver.findElement(By.name("email")).click();
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("admin@email.com");
		driver.findElement(By.name("password")).click();
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("admin");
		// El usuario se autentifica
		driver.findElement(By.xpath("//*[contains(text(),'Aceptar')]")).click();
		waitForWeb();
		driver.findElement(By.xpath("//*[contains(text(),'Usuarios')]")).click();
		waitForWeb();
		// driver.findElement(By.xpath("//*[contains(text(),'Ver usuarios')]")).click();

		// Comprobamos que está el último usuario
		// El usuario administrador no aparece en el listado
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'user5@email.com')]"));
		assertTrue(list.size() > 0);
		driver.findElement(By.id("user5@email.com")).click();
		waitForWeb();
		driver.findElement(By.name("delete")).click();
		waitForWeb();

		// Comprobamos que el primer usuario ya no aparece
		list = driver.findElements(By.xpath("//*[contains(text(),'user5@email.com')]"));
		assertTrue(list.size() == 0);

		// Se muestra el mensaje de borrado correcto
		list = driver.findElements(By.xpath("//*[contains(text(),'Los usuarios se eliminaron correctamente')]"));
		assertTrue(list.size() > 0);

		// El administrador se desconecta
		driver.findElement(By.xpath("//*[contains(text(),'Logout')]")).click();
	}

	// [Prueba13] Ir a la lista de usuarios, borrar 3 usuarios, comprobar que la
	// lista se actualiza y dichos
//	usuarios desaparecen.
	@Test
	public void testP13() {
		driver.get("http://localhost:8081/login");
		waitForWeb();
		driver.findElement(By.name("email")).click();
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("admin@email.com");
		driver.findElement(By.name("password")).click();
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("admin");
		// El usuario se autentifica
		driver.findElement(By.xpath("//*[contains(text(),'Aceptar')]")).click();
		waitForWeb();
		driver.findElement(By.xpath("//*[contains(text(),'Usuarios')]")).click();
		waitForWeb();
		// driver.findElement(By.xpath("//*[contains(text(),'Ver usuarios')]")).click();

		// Comprobamos que está los 3 usuarios que vamos a borrar
		// El usuario administrador no aparece en el listado
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'user1@email.com')]"));
		assertTrue(list.size() > 0);
		list = driver.findElements(By.xpath("//*[contains(text(),'user2@email.com')]"));
		assertTrue(list.size() > 0);
		list = driver.findElements(By.xpath("//*[contains(text(),'user3@email.com')]"));
		assertTrue(list.size() > 0);

		// Eliminamos los tres usuarios
		driver.findElement(By.id("user1@email.com")).click();
		waitForWeb();

		driver.findElement(By.id("user2@email.com")).click();
		waitForWeb();

		driver.findElement(By.id("user3@email.com")).click();
		waitForWeb();

		driver.findElement(By.name("delete")).click();
		waitForWeb();

		// Comprobamos que ya no están los usuarios

		list = driver.findElements(By.xpath("//*[contains(text(),'user1@email.com')]"));
		assertTrue(list.size() == 0);
		list = driver.findElements(By.xpath("//*[contains(text(),'user2@email.com')]"));
		assertTrue(list.size() == 0);
		list = driver.findElements(By.xpath("//*[contains(text(),'user3@email.com')]"));
		assertTrue(list.size() == 0);

		// Se muestra el mensaje de borrado correcto
		list = driver.findElements(By.xpath("//*[contains(text(),'Los usuarios se eliminaron correctamente')]"));
		assertTrue(list.size() > 0);
		// El administrador se desconecta
		driver.findElement(By.xpath("//*[contains(text(),'Logout')]")).click();
	}

	// [Prueba14] Ir al formulario de alta de oferta, rellenarla con datos válidos y
	// pulsar el botón Submit.
	// Comprobar que la oferta sale en el listado de ofertas de dicho usuario
	@Test
	public void testP14() {
		driver.get("http://localhost:8081/login");
		waitForWeb();
		driver.findElement(By.name("email")).click();
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("user1@email.com");
		driver.findElement(By.name("password")).click();
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("123456789");
		// El usuario se autentifica
		driver.findElement(By.xpath("//*[contains(text(),'Aceptar')]")).click();
		waitForWeb();
		driver.findElement(By.linkText("Publicar oferta")).click();
		waitForWeb();
		// driver.findElement(By.linkText("Nueva oferta")).click();
		driver.findElement(By.name("title")).click();
		driver.findElement(By.name("title")).clear();
		driver.findElement(By.name("title")).sendKeys("Nueva oferta");
		driver.findElement(By.name("description")).click();
		driver.findElement(By.name("description")).clear();
		driver.findElement(By.name("description")).sendKeys("Nueva oferta del user1");
		driver.findElement(By.name("price")).click();
		driver.findElement(By.name("price")).clear();
		driver.findElement(By.name("price")).sendKeys("120");
		driver.findElement(By.name("publicar")).click();
		waitForWeb();

		// Vamos a la pagina con nuestras ofertas publicadas
		List<WebElement> list = driver
				.findElements(By.xpath("//*[contains(text(),'La oferta se publicó correctamente')]"));
		assertTrue(list.size() > 0);
		driver.get("http://localhost:8081/sale/own");
		waitForWeb();
		// Comprobamos la oferta aparece en el listado.
		list = driver.findElements(By.xpath("//*[contains(text(),'Nueva oferta')]"));
		assertTrue(list.size() > 0);
		// El usuario se desconecta
		driver.findElement(By.xpath("//*[contains(text(),'Logout')]")).click();
	}

	// [Prueba15] Ir al formulario de alta de oferta, rellenarla con datos inválidos
	// (campo título vacío) y pulsar
	// el botón Submit. Comprobar que se muestra el mensaje de campo obligatorio.
	@Test
	public void testP15() {
		driver.get("http://localhost:8081/login");
		waitForWeb();
		driver.findElement(By.name("email")).click();
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("user1@email.com");
		driver.findElement(By.name("password")).click();
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("123456789");
		// El usuario se autentifica
		driver.findElement(By.xpath("//*[contains(text(),'Aceptar')]")).click();
		waitForWeb();
		driver.findElement(By.linkText("Publicar oferta")).click();
		// Todos los campos vacios
		driver.findElement(By.name("publicar")).click();
		waitForWeb();
		// Seguimos en la página para añadir una oferta
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'Nueva oferta:')]"));
		assertTrue(list.size() > 0);

		// Solo el título vacio
		driver.findElement(By.name("description")).click();
		driver.findElement(By.name("description")).clear();
		driver.findElement(By.name("description")).sendKeys("Nueva oferta de user1");
		driver.findElement(By.name("price")).click();
		driver.findElement(By.name("price")).clear();
		driver.findElement(By.name("price")).sendKeys("20");
		driver.findElement(By.name("publicar")).click();
		waitForWeb();
		// Continuamos en la misma página
		list = driver.findElements(By.xpath("//*[contains(text(),'Nueva oferta:')]"));
		assertTrue(list.size() > 0);

		// Solo descripción vacía
		driver.findElement(By.name("description")).clear();
		driver.findElement(By.name("description")).sendKeys("");
		driver.findElement(By.name("title")).click();
		driver.findElement(By.name("title")).clear();
		driver.findElement(By.name("title")).sendKeys("Nueva oferta");

		driver.findElement(By.name("publicar")).click();
		waitForWeb();
		// Continuamos en la misma página
		list = driver.findElements(By.xpath("//*[contains(text(),'Nueva oferta:')]"));
		assertTrue(list.size() > 0);
		// Solo el precio vacio
		driver.findElement(By.name("description")).click();
		driver.findElement(By.name("description")).clear();
		driver.findElement(By.name("description")).sendKeys("Nueva oferta del user1");
		driver.findElement(By.name("price")).clear();
		driver.findElement(By.name("price")).sendKeys("");
		driver.findElement(By.name("publicar")).click();
		waitForWeb();
		// Continuamos en la misma página
		list = driver.findElements(By.xpath("//*[contains(text(),'Nueva oferta:')]"));
		assertTrue(list.size() > 0);
		// El usuario se desconecta
		driver.findElement(By.xpath("//*[contains(text(),'Logout')]")).click();
	}

//[Prueba16] Mostrar el listado de ofertas para dicho usuario y comprobar que se muestran todas los que
//existen para este usuario. 
	@Test
	public void testP16() {
		driver.get("http://localhost:8081/login");
		waitForWeb();
		driver.findElement(By.name("email")).click();
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("user1@email.com");
		driver.findElement(By.name("password")).click();
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("123456789");
		// El usuario se autentifica
		driver.findElement(By.xpath("//*[contains(text(),'Aceptar')]")).click();
		waitForWeb();

		driver.findElement(By.linkText("Ofertas publicadas")).click();

		// Comprobamos que estamos en la página adecuada
		List<WebElement> list = driver
				.findElements(By.xpath("//*[contains(text(),'Estas son las ofertas que has publicado:')]"));
		assertTrue(list.size() > 0);

		// Comprobamos que están todas las ofertas
		list = driver.findElements(By.xpath("//*[contains(text(),'Oferta 1a')]"));
		assertTrue(list.size() > 0);
		list = driver.findElements(By.xpath("//*[contains(text(),'Oferta 1b')]"));
		assertTrue(list.size() > 0);
		list = driver.findElements(By.xpath("//*[contains(text(),'Oferta 1c')]"));
		assertTrue(list.size() > 0);

		// El usuario se desconecta
		driver.findElement(By.xpath("//*[contains(text(),'Logout')]")).click();
	}

//[Prueba17] Ir a la lista de ofertas, borrar la primera oferta de la lista, comprobar que la lista se actualiza y
	// que la oferta desaparece.
	@Test
	public void testP17() {
		driver.get("http://localhost:8081/login");
		waitForWeb();
		driver.findElement(By.name("email")).click();
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("user1@email.com");
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("123456789");
		// El usuario se autentifica
		driver.findElement(By.xpath("//*[contains(text(),'Aceptar')]")).click();
		waitForWeb();
		// driver.findElement(By.linkText("Personal")).click();
		driver.findElement(By.linkText("Ofertas publicadas")).click();
		waitForWeb();

		// Comprobamos que estamos en la página adecuada
		List<WebElement> list = driver
				.findElements(By.xpath("//*[contains(text(),'Estas son las ofertas que has publicado:')]"));
		assertTrue(list.size() > 0);

		// Comprobamos que está la primera oferta
		list = driver.findElements(By.xpath("//*[contains(text(),'Oferta 1a')]"));
		assertTrue(list.size() > 0);

		// La borramos
		driver.findElement(By.id("Oferta 1a")).click();
		waitForWeb();

		// Comprobamos que ya no está
		list = driver.findElements(By.xpath("//*[contains(text(),'Oferta 1a')]"));
		assertTrue(list.size() == 0);
		// Mensaje de borrado correcto
		list = driver.findElements(By.xpath("//*[contains(text(),'La oferta se eliminó correctamente')]"));
		assertTrue(list.size() > 0);
		// El usuario se desconecta
		driver.findElement(By.xpath("//*[contains(text(),'Logout')]")).click();
	}

	// [Prueba18] Ir a la lista de ofertas, borrar la última oferta de la lista,
	// comprobar que la lista se actualiza y
	// que la oferta desaparece
	@Test
	public void testP18() {
		driver.get("http://localhost:8081/login");
		waitForWeb();
		driver.findElement(By.name("email")).click();
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("user1@email.com");
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("123456789");
		// El usuario se autentifica
		driver.findElement(By.xpath("//*[contains(text(),'Aceptar')]")).click();
		waitForWeb();
		// driver.findElement(By.linkText("Personal")).click();
		driver.findElement(By.linkText("Ofertas publicadas")).click();

		// Comprobamos que estamos en la página adecuada
		List<WebElement> list = driver
				.findElements(By.xpath("//*[contains(text(),'Estas son las ofertas que has publicado:')]"));
		assertTrue(list.size() > 0);

		// Comprobamos que está la última oferta
		list = driver.findElements(By.xpath("//*[contains(text(),'Oferta 1c')]"));
		assertTrue(list.size() > 0);

		driver.findElement(By.id("Oferta 1c")).click();
		waitForWeb();
		// Comprobamos que ya no está
		list = driver.findElements(By.xpath("//*[contains(text(),'Oferta 1c')]"));
		assertTrue(list.size() == 0);
		// Mensaje de borrado correcto
		list = driver.findElements(By.xpath("//*[contains(text(),'La oferta se eliminó correctamente')]"));
		assertTrue(list.size() > 0);

		// El usuario se desconecta
		driver.findElement(By.xpath("//*[contains(text(),'Logout')]")).click();
	}

	// [Prueba19] Hacer una búsqueda con el campo vacío y comprobar que se muestra
	// la página que
	// corresponde con el listado de las ofertas existentes en el sistema
	@Test
	public void testP19() {
		driver.get("http://localhost:8081/login");
		waitForWeb();
		driver.findElement(By.name("email")).click();
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("user1@email.com");
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("123456789");
		// El usuario se autentifica
		driver.findElement(By.xpath("//*[contains(text(),'Aceptar')]")).click();
		waitForWeb();
		// driver.findElement(By.linkText("Ofertas")).click();
		driver.findElement(By.linkText("Buscar oferta")).click();

		// Buscamos con el texto vacio
		driver.findElement(By.id("send")).click();
		waitForWeb();

		// Comprobamos que está la primera oferta de la página
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'Oferta 2a')]"));
		assertTrue(list.size() > 0);
		// Comprobamos que está la última oferta de la página
		list = driver.findElements(By.xpath("//*[contains(text(),'Oferta 3b')]"));
		assertTrue(list.size() > 0);

		// El usuario se desconecta
		driver.findElement(By.xpath("//*[contains(text(),'Logout')]")).click();
	}

	// [Prueba20] Hacer una búsqueda escribiendo en el campo un texto que no exista
	// y comprobar que se
	// muestra la página que corresponde, con la lista de ofertas vacía.
	@Test
	public void testP20() {
		driver.get("http://localhost:8081/login");
		waitForWeb();
		driver.findElement(By.name("email")).click();
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("user1@email.com");
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("123456789");
		// El usuario se autentifica
		driver.findElement(By.xpath("//*[contains(text(),'Aceptar')]")).click();
		waitForWeb();
		// driver.findElement(By.linkText("Ofertas")).click();
		driver.findElement(By.linkText("Buscar oferta")).click();

		driver.findElement(By.name("busqueda")).click();
		driver.findElement(By.name("busqueda")).clear();
		// Buscamos un texto que no existe
		driver.findElement(By.name("busqueda")).sendKeys("12345");
		driver.findElement(By.id("send")).click();
		waitForWeb();

		// Comprobamos que no se muestra ninguna oferta
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'12345')]"));
		assertTrue(list.size() == 0);

		// El usuario se desconecta
		driver.findElement(By.xpath("//*[contains(text(),'Logout')]")).click();
	}

	// [Prueba21] Hacer una búsqueda escribiendo en el campo un texto en minúscula o
	// mayúscula y
	// comprobar que se muestra la página que corresponde, con la lista de ofertas
	// que contengan dicho texto,
	// independientemente que el título esté almacenado en minúsculas o mayúscula.
	// [Prueba22] Sobre una búsqueda determinada (a elección de desarrollador),
	// comprar una oferta que deja
	// un saldo positivo en el contador del comprobador. Y comprobar que el contador
	// se actualiza
	// correctamente en la vista del comprador.
	@Test
	public void testP2122() {
		driver.get("http://localhost:8081/login");
		waitForWeb();
		driver.findElement(By.name("email")).click();
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("user3@email.com");
		driver.findElement(By.name("password")).click();
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("123456789");
		// El usuario se autentifica
		driver.findElement(By.xpath("//*[contains(text(),'Aceptar')]")).click();
		waitForWeb();

		// Comprobamos la cantidad de dinero que tiene el usuario antes de la compra
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'100')]"));
		assertTrue(list.size() > 0);
		// driver.findElement(By.linkText("Ofertas")).click();
		driver.findElement(By.linkText("Buscar oferta")).click();
		waitForWeb();

		driver.findElement(By.name("busqueda")).click();
		driver.findElement(By.name("busqueda")).clear();

		// Buscamos la oferta 1c
		driver.findElement(By.name("busqueda")).sendKeys("1c");
		driver.findElement(By.id("send")).click();
		waitForWeb();
		list = driver.findElements(By.xpath("//*[contains(text(),'Oferta 1c')]"));
		assertTrue(list.size() > 0);

		// La compramos
		driver.findElement(By.xpath("//*[contains(text(),  'Oferta 1c')][1]/following::input[1]")).click();
		waitForWeb();
		// Vamos a la vista del usuario
		driver.findElement(By.xpath("//*[contains(text(),'Inicio')]")).click();
		waitForWeb();
		// Comprobamos que se ha actualizado su dinero
		list = driver.findElements(By.xpath("//*[contains(text(),'85')]"));
		assertTrue(list.size() > 0);

		// El usuario se desconecta
		driver.findElement(By.xpath("//*[contains(text(),'Logout')]")).click();

	}

	// [Prueba23] Sobre una búsqueda determinada (a elección de desarrollador),
	// comprar una oferta que deja
	// un saldo 0 en el contador del comprobador. Y comprobar que el contador se
	// actualiza correctamente en
	// la vista del comprador.
	@Test
	public void testP23() {
		driver.get("http://localhost:8081/login");
		waitForWeb();
		driver.findElement(By.name("email")).click();
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("user1@email.com");
		driver.findElement(By.name("password")).click();
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("123456789");
		// El usuario se autentifica
		driver.findElement(By.xpath("//*[contains(text(),'Aceptar')]")).click();
		waitForWeb();

		// Comprobamos la cantidad de dinero que tiene el usuario antes de la compra
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'100')]"));
		assertTrue(list.size() > 0);
		// driver.findElement(By.linkText("Ofertas")).click();
		driver.findElement(By.linkText("Buscar oferta")).click();
		waitForWeb();
		// Buscamos la oferta 2c
		driver.findElement(By.name("busqueda")).click();
		driver.findElement(By.name("busqueda")).clear();
		driver.findElement(By.name("busqueda")).sendKeys("2c");
		driver.findElement(By.id("send")).click();
		waitForWeb();

		driver.findElement(By.xpath("//*[contains(text(),  'Oferta 2c')][1]/following::input[1]")).click();
		driver.findElement(By.linkText("Inicio")).click();
		waitForWeb();
		// Comprobamos que se ha actualizado su dinero
		list = driver.findElements(By.xpath("//*[contains(text(),'0')]"));
		assertTrue(list.size() > 0);

		// El usuario se desconecta
		driver.findElement(By.xpath("//*[contains(text(),'Logout')]")).click();
	}

	// [Prueba24] Sobre una búsqueda determinada (a elección de desarrollador),
	// intentar comprar una oferta
//	que esté por encima de saldo disponible del comprador. Y comprobar que se muestra el mensaje de
//	saldo no suficiente.
	@Test
	public void testP24() {
		driver.get("http://localhost:8081/login");
		waitForWeb();
		driver.findElement(By.name("email")).click();
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("user3@email.com");
		driver.findElement(By.name("password")).click();
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("123456789");
		// El usuario se autentifica
		driver.findElement(By.xpath("//*[contains(text(),'Aceptar')]")).click();
		waitForWeb();

		// Comprobamos la cantidad de dinero que tiene el usuario antes de la compra
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'100')]"));
		assertTrue(list.size() > 0);
		// driver.findElement(By.linkText("Ofertas")).click();
		driver.findElement(By.linkText("Buscar oferta")).click();

		// Buscamos la oferta 2a
		driver.findElement(By.name("busqueda")).click();
		driver.findElement(By.name("busqueda")).clear();
		driver.findElement(By.name("busqueda")).sendKeys("2b");
		driver.findElement(By.id("send")).click();

		// La compramos
		driver.findElement(By.xpath("//*[contains(text(),  'Oferta 2b')][1]/following::input[1]")).click();
		waitForWeb();
		// Comprobamos que sale el mensaje de que no se ha podido comprar la oferta
		list = driver.findElements(
				By.xpath("//*[contains(text(),'No tienes suficiente dinero para adquirir esa oferta ')]"));
		assertTrue(list.size() > 0);

		// Comprobamos que la cantidad de dinero sigue siendo la misma
		driver.findElement(By.linkText("Inicio")).click();
		waitForWeb();
		list = driver.findElements(By.xpath("//*[contains(text(),'100')]"));
		assertTrue(list.size() > 0);

		// El usuario se desconecta
		driver.findElement(By.xpath("//*[contains(text(),'Logout')]")).click();
	}

//	[Prueba25] Ir a la opción de ofertas compradas del usuario y mostrar la lista. Comprobar que aparecen
//	las ofertas que deben aparecer
	@Test
	public void testP25() {
		driver.get("http://localhost:8081/login");
		waitForWeb();
		driver.findElement(By.name("email")).click();
		driver.findElement(By.name("email")).clear();
		driver.findElement(By.name("email")).sendKeys("user1@email.com");
		driver.findElement(By.name("password")).click();
		driver.findElement(By.name("password")).clear();
		driver.findElement(By.name("password")).sendKeys("123456789");
		// El usuario se autentifica
		driver.findElement(By.xpath("//*[contains(text(),'Aceptar')]")).click();
		waitForWeb();
		driver.findElement(By.linkText("Buscar oferta")).click();
		waitForWeb();

		// Buscamos la oferta 2a
		driver.findElement(By.name("busqueda")).click();
		driver.findElement(By.name("busqueda")).clear();
		driver.findElement(By.name("busqueda")).sendKeys("4a");
		driver.findElement(By.id("send")).click();

		// La compramos
		driver.findElement(By.xpath("//*[contains(text(),  'Oferta 4a')][1]/following::input[1]")).click();
		waitForWeb();

		driver.findElement(By.linkText("Buscar oferta")).click();
		waitForWeb();
		// Buscamos la oferta 2a
		driver.findElement(By.name("busqueda")).click();
		driver.findElement(By.name("busqueda")).clear();
		driver.findElement(By.name("busqueda")).sendKeys("5a");
		driver.findElement(By.id("send")).click();

		// La compramos
		driver.findElement(By.xpath("//*[contains(text(),  'Oferta 5a')][1]/following::input[1]")).click();
		waitForWeb();
		// driver.findElement(By.linkText("Personal")).click();
		driver.findElement(By.linkText("Ofertas compradas")).click();
		waitForWeb();

		// Comprobamos que está en la página correcta
		List<WebElement> list = driver
				.findElements(By.xpath("//*[contains(text(),'Estas son las ofertas que has comprado')]"));
		assertTrue(list.size() > 0);

		// Comprobamos que aparecen todas las ofertas
		list = driver.findElements(By.xpath("//*[contains(text(),'Oferta 4a')]"));
		assertTrue(list.size() > 0);
		list = driver.findElements(By.xpath("//*[contains(text(),'Oferta 5a')]"));
		assertTrue(list.size() > 0);

		// El usuario se desconecta
		driver.findElement(By.xpath("//*[contains(text(),'Logout')]")).click();
	}

	// ------------------PRUEBAS API-----------------------------
	// [Prueba29] Inicio de sesión con datos válidos.
	@Test
	public void testP29() {
		driver.get("http://localhost:8081/cliente.html?w=login");
		waitForWeb();
		driver.findElement(By.id("email")).click();
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("user1@email.com");
		driver.findElement(By.id("password")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("123456789");
		driver.findElement(By.id("boton-login")).click();
		waitForWeb();
		// Comprobamos que el usuario es redirigido al listado de ofertas
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'Descripción')]"));
		assertTrue(list.size() > 0);

	}

//	[Prueba30] Inicio de sesión con datos inválidos (email existente, pero contraseña incorrecta)
	@Test
	public void testP30() {
		driver.get("http://localhost:8081/cliente.html?w=login");
		waitForWeb();
		driver.findElement(By.id("email")).click();
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("user1@email.com");
		driver.findElement(By.id("password")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("dsjkhasdhasb");
		driver.findElement(By.id("boton-login")).click();
		waitForWeb();
		// Comprobamos que se muestra el mensaje de error
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'No se pudo iniciar sesión')]"));
		assertTrue(list.size() > 0);
		// Comprobamos que sigue en la página de login
		list = driver.findElements(By.xpath("//*[contains(text(),'Email:')]"));
		assertTrue(list.size() > 0);

	}

//	[Prueba31] Inicio de sesión con datos válidos (campo email o contraseña vacíos).
	@Test
	public void testP31() {
		driver.get("http://localhost:8081/cliente.html?w=login");
		waitForWeb();
		driver.findElement(By.id("email")).click();
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("user1@email.com");
		// contraseña vacia
		driver.findElement(By.id("boton-login")).click();
		waitForWeb();
		// Comprobamos que se muestra el mensaje de error
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'No se pudo iniciar sesión')]"));
		assertTrue(list.size() > 0);
		// Comprobamos que sigue en la página de login
		list = driver.findElements(By.xpath("//*[contains(text(),'Email:')]"));
		assertTrue(list.size() > 0);
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("password")).click();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("dsjkhasdhasb");
		// email vacio
		driver.findElement(By.id("boton-login")).click();
		waitForWeb();
		list = driver.findElements(By.xpath("//*[contains(text(),'No se pudo iniciar sesión')]"));
		assertTrue(list.size() > 0);
		list = driver.findElements(By.xpath("//*[contains(text(),'Email:')]"));
		assertTrue(list.size() > 0);

	}

	@Test
	public void testP32() {
		driver.get("http://localhost:8081/cliente.html?w=login");
		waitForWeb();
		driver.findElement(By.id("email")).click();
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("user1@email.com");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("123456789");
		driver.findElement(By.id("boton-login")).click();
		waitForWeb();

		// Comprobamos que no están las ofertas del usuario
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'Oferta 1a')]"));
		assertTrue(list.size() == 0);
		list = driver.findElements(By.xpath("//*[contains(text(),'Oferta 1b')]"));
		assertTrue(list.size() == 0);
		list = driver.findElements(By.xpath("//*[contains(text(),'Oferta 1c')]"));
		assertTrue(list.size() == 0);

		// Comprobamos que se muestran las ofertas de otros usuarios
		for (int i = 2; i < 6; i++) {
			list = driver.findElements(By.xpath("//*[contains(text(),'Oferta " + i + "a')]"));
			assertTrue(list.size() > 0);
			list = driver.findElements(By.xpath("//*[contains(text(),'Oferta " + i + "b')]"));
			assertTrue(list.size() > 0);
			list = driver.findElements(By.xpath("//*[contains(text(),'Oferta " + i + "c')]"));
			assertTrue(list.size() > 0);
		}

	}

	@Test
	public void testP33() {
		driver.get("http://localhost:8081/cliente.html?w=login");
		waitForWeb();
		driver.findElement(By.id("email")).click();
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("user1@email.com");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("123456789");
		driver.findElement(By.id("boton-login")).click();
		waitForWeb();
		// *[contains(text(), 'Oferta 4c')][1]/following::button[1]
		driver.findElement(By.id("8")).click();
		waitForWeb();
		// Comprobamos que estamos dentro del chat
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'Mensajes con oferta')]"));
		assertTrue(list.size() > 0);
		// Comprobamos que el mensaje no aparace
		list = driver.findElements(By.xpath("//*[contains(text(),'hola me gustaria adquirir esta oferta')]"));
		assertTrue(list.size() == 0);

		driver.findElement(By.id("agregar-mensaje")).click();
		driver.findElement(By.id("agregar-mensaje")).clear();
		driver.findElement(By.id("agregar-mensaje")).sendKeys("hola me gustaria adquirir esta oferta");
		driver.findElement(By.id("boton-enviar")).click();
		waitForWeb();
		// comprobamos que el mensaje ahora si aparece
		list = driver.findElements(
				By.xpath("//*[contains(text(),'user1@email.com : hola me gustaria adquirir esta oferta')]"));
		assertTrue(list.size() > 0);
	}

	@Test
	public void testP34() {
		driver.get("http://localhost:8081/cliente.html?w=login");
		waitForWeb();
		driver.findElement(By.id("email")).click();
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys("user1@email.com");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("123456789");
		driver.findElement(By.id("boton-login")).click();
		waitForWeb();
		driver.findElement(By.linkText("Mis conversaciones")).click();
		waitForWeb();
		driver.findElement(By.id("7")).click();
		waitForWeb();
		// Comprobamos que estamos dentro del chat
		List<WebElement> list = driver.findElements(By.xpath("//*[contains(text(),'Mensajes con oferta')]"));
		assertTrue(list.size() > 0);
		// Comprobamos que el mensaje no aparace
		list = driver.findElements(By.xpath("//*[contains(text(),'hola me gustaria adquirir esta oferta')]"));
		assertTrue(list.size() == 0);
		driver.findElement(By.id("agregar-mensaje")).click();
		driver.findElement(By.id("agregar-mensaje")).clear();
		driver.findElement(By.id("agregar-mensaje")).sendKeys("hola me gustaria adquirir esta oferta");
		driver.findElement(By.id("boton-enviar")).click();
		waitForWeb();
		// comprobamos que el mensaje ahora si aparece
		list = driver.findElements(
				By.xpath("//*[contains(text(),'user1@email.com : hola me gustaria adquirir esta oferta')]"));
		assertTrue(list.size() > 0);
	}

}
