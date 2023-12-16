import io.restassured.response.ValidatableResponse;
import org.example.login.Login;
import org.example.login.LoginAssertions;
import org.example.login.LoginIn;
import org.example.order.OrdersAssertions;
import org.example.order.OrdersCreate;
import org.example.user.UserAssertions;
import org.example.user.UserCreate;
import org.example.user.UserGenerator;
import org.junit.After;
import org.junit.Before;

public class BaseTest {
    protected final UserCreate client = new UserCreate();
    protected final UserAssertions check = new UserAssertions();
    protected final LoginIn clientLogin = new LoginIn();
    protected final LoginAssertions checkLogin = new LoginAssertions();
    protected final OrdersCreate order = new OrdersCreate();
    protected final OrdersAssertions checkOrder = new OrdersAssertions();
    protected String accessToken;
    protected String refreshToken;

    @Before
    public void setup() {
        var user = UserGenerator.random();
        client.create(user);

        var creds = Login.from(user);
        ValidatableResponse loginResponse = clientLogin.login(creds);
        accessToken = clientLogin.getAccessToken(loginResponse);
        refreshToken = clientLogin.getRefreshToken(loginResponse);

    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            ValidatableResponse deleteResponse = client.delete(accessToken);
            check.deleteSuccessfully(deleteResponse);
        }
    }
}