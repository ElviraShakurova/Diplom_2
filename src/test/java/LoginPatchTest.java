import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

public class LoginPatchTest extends BaseTest {

    @Description("Тест проводит проверку успешного обновления данных о пользователе")
    @Test
    public void testPatchLogin() {
        ValidatableResponse responseNewUser = client.patch(accessToken);
        check.updateUser(responseNewUser);
    }

    @Description("Тест проводит проверку неуспешного обновления данных о пользователе, без авторизации")
    @Test
    public void testPatchErrorLogin() {
        ValidatableResponse responseNewUser = client.patchWithoutLogin();
        check.updateUserError(responseNewUser);
    }
}