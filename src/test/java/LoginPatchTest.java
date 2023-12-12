import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.example.login.Login;
import org.example.login.LoginAssertions;
import org.example.login.LoginIn;
import org.example.user.UserAssertions;
import org.example.user.UserCreate;
import org.example.user.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginPatchTest {
    private final UserCreate client = new UserCreate();
    private final UserAssertions check = new UserAssertions();
    private final LoginIn clientLogin = new LoginIn();
    private final LoginAssertions checkLogin = new LoginAssertions();

    protected String accessToken;

    protected String refreshToken;

    @Before
    public void setup(){
        var user = UserGenerator.random();
        ValidatableResponse response = client.create(user);
        check.createdSuccessfully(response, user);

        var creds = Login.from(user);
        ValidatableResponse loginResponse = clientLogin.login(creds);
        accessToken = clientLogin.getAccessToken(loginResponse);

    }

    @Description("Тест проводит проверку успешного обновления данных о пользователе")
    @Test
    public void testPatchLogin(){
        ValidatableResponse responseNewUser = client.patch(accessToken);
        check.updateUser(responseNewUser);
    }

    @Description("Тест проводит проверку неуспешного обновления данных о пользователе, без авторизации")
    @Test
    public void testPatchErrorLogin(){
        ValidatableResponse responseNewUser = client.patchWithoutLogin();
        check.updateUserError(responseNewUser);
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            ValidatableResponse deleteResponse = client.delete(accessToken);
            check.deleteSuccessfully(deleteResponse);
        }
    }
}