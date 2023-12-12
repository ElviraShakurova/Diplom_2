import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.example.login.Login;
import org.example.login.LoginAssertions;
import org.example.login.LoginIn;
import org.example.order.Orders;
import org.example.order.OrdersAssertions;
import org.example.order.OrdersCreate;
import org.example.user.User;
import org.example.user.UserAssertions;
import org.example.user.UserCreate;
import org.example.user.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OrderTest {

    private final UserCreate client = new UserCreate();
    private final UserAssertions check = new UserAssertions();
    private final LoginIn clientLogin = new LoginIn();
    private final LoginAssertions checkLogin = new LoginAssertions();
    private final OrdersCreate order = new OrdersCreate();
    private final OrdersAssertions checkOrder = new OrdersAssertions();
    protected String refreshToken;
    private String accessToken;
    private User user;

    @Before
    public void setup() {
        user = UserGenerator.random();
        ValidatableResponse response = client.create(user);
        check.createdSuccessfully(response, user);

        var creds = Login.from(user);
        ValidatableResponse loginResponse = clientLogin.login(creds);
        checkLogin.loginSuccessfully(loginResponse, user);
        refreshToken = clientLogin.getRefreshToken(loginResponse);
    }

    @Description("Тест проводит проверку успешного создания заказа")
    @Test
    public void testOrderSuccessfulCreate(){
        ValidatableResponse orderResponse = order.create(new Orders());
        checkOrder.createdSuccessfullyOrder(orderResponse);
    }

    @Description("Тест проводит проверку неуспешного создания заказа, без авторизации")
    @Test
    public void testOrderCreateWithoutAuthorization(){
        ValidatableResponse responseLogout = clientLogin.logout(refreshToken);
        checkLogin.logoutSuccessfully(responseLogout);

        ValidatableResponse orderResponse = order.create(new Orders());
        checkOrder.createdWithoutAuthorization(orderResponse);
    }

    @Description("Тест проводит проверку неуспешного создания заказа, без ингредиентов")
    @Test
    public void testOrderCreateWithoutIngredients(){
        ValidatableResponse orderResponse = order.createOrderWithoutIngredients(new Orders());
        checkOrder.createdWithoutIngredients(orderResponse);
    }

    @Description("Тест проводит проверку неуспешного создания заказа, с невалидными ингредиентами")
    @Test
    public void testOrderCreateWithInvalid(){
        ValidatableResponse orderResponse = order.createInvalidIngredients(new Orders());
        checkOrder.createdWithInvalidIngredients(orderResponse);
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