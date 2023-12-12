import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.example.login.LoginAssertions;
import org.example.login.LoginIn;
import org.example.user.*;
import org.junit.After;
import org.junit.Test;

public class UserTest {
    private final UserCreate client = new UserCreate();
    private final UserAssertions check = new UserAssertions();
    private final LoginIn clientLogin = new LoginIn();
    private final LoginAssertions checkLogin = new LoginAssertions();
    private String accessToken;

    @Description("Тест проводит проверку создания пользователя")
    @Test
    public void testCreateUser(){
        var user = UserGenerator.random();
        ValidatableResponse response = client.create(user);
        check.createdSuccessfully(response, user);

        ValidatableResponse dublicateResponse = client.create(user);
        check.alreadyExists(dublicateResponse, user.getEmail());

        ValidatableResponse errorResponseWithoutPassword = client.createWithoutPassword(new UserWithoutPassword());
        check.createdWithError(errorResponseWithoutPassword);

        ValidatableResponse errorResponseWithoutName = client.createWithoutName(new UserWithoutName());
        check.createdWithError(errorResponseWithoutName);

        ValidatableResponse errorResponseWithoutEmail = client.createWithoutEmail(new UserWithoutEmail());
        check.createdWithError(errorResponseWithoutEmail);
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            ValidatableResponse logoutResponse = clientLogin.logout(accessToken);
            checkLogin.logoutSuccessfully(logoutResponse);

            ValidatableResponse deleteResponse = client.delete(accessToken);
            check.deleteSuccessfully(deleteResponse);
        }
    }
}