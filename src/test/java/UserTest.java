import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.example.login.LoginIn;
import org.example.user.*;
import org.junit.After;
import org.junit.Test;

public class UserTest {
    private final UserCreate client = new UserCreate();
    private final UserAssertions check = new UserAssertions();
    private final LoginIn clientLogin = new LoginIn();
    private String accessToken;

    @Description("Тест проводит проверку успешного создания уникального пользователя")
    @Test
    public void testCreateSuccessfullyUser() {
        var user = UserGenerator.random();
        ValidatableResponse response = client.create(user);
        check.createdSuccessfully(response, user);
    }

    @Description("Тест проводит проверку создания уже зарегистрованного пользователя")
    @Test
    public void testCreateExistsUser() {
        var user = UserGenerator.random();
        client.create(user);
        ValidatableResponse dublicateResponse = client.create(user);
        check.alreadyExists(dublicateResponse, user.getEmail());
        }

    @Description("Тест проводит проверку создания пользователя без ввода значения в поле Пароль")
    @Test
    public void testCreateUserWithoutPassword() {
        ValidatableResponse errorResponseWithoutPassword = client.createWithoutPassword(new UserWithoutPassword());
        check.createdWithError(errorResponseWithoutPassword);
    }

    @Description("Тест проводит проверку создания пользователя без ввода значения в поле Имя")
    @Test
    public void testCreateUserWithoutName() {
        ValidatableResponse errorResponseWithoutName = client.createWithoutName(new UserWithoutName());
        check.createdWithError(errorResponseWithoutName);
    }

    @Description("Тест проводит проверку создания пользователя без ввода значения в поле Email")
    @Test
    public void testCreateUserWithoutEmail() {
        ValidatableResponse errorResponseWithoutEmail = client.createWithoutEmail(new UserWithoutEmail());
        check.createdWithError(errorResponseWithoutEmail);
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