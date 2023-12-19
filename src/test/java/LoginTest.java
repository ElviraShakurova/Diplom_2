import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.example.login.Login;
import org.example.login.LoginAssertions;
import org.example.login.LoginIn;
import org.example.user.User;
import org.example.user.UserAssertions;
import org.example.user.UserCreate;
import org.example.user.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginTest {
    private final UserCreate client = new UserCreate();
    private final UserAssertions check = new UserAssertions();
    private final LoginIn clientLogin = new LoginIn();
    private final LoginAssertions checkLogin = new LoginAssertions();

    private String accessToken;

    private User user;

    @Before
    public void setUp() {
        user = UserGenerator.random();
        client.create(user);
    }

    @Description("Тест проводит проверку успешной авторизации пользователя")
    @Test
    public void testSuccessfulLogin() {
        var creds = Login.from(user);
        ValidatableResponse loginResponse = clientLogin.login(creds);
        checkLogin.loginSuccessfully(loginResponse, user);
    }

    @Description("Тест проводит проверки неуспешной авторизации пользователя, с неверным логином и паролем")
    @Test
    public void testErrorLogin() {
        user.setEmail("testPupsik@mail.ru");
        var creds = Login.from(user);
        ValidatableResponse loginResponse = clientLogin.login(creds);
        checkLogin.errorLogin(loginResponse);

        user.setPassword("12");
        var creds1 = Login.from(user);
        ValidatableResponse passwordResponse = clientLogin.login(creds1);
        checkLogin.errorLogin(passwordResponse);
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            clientLogin.logout(accessToken);

            ValidatableResponse deleteResponse = client.delete(accessToken);
            check.deleteSuccessfully(deleteResponse);
        }
    }
}