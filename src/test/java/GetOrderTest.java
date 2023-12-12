import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.example.login.Login;
import org.example.login.LoginAssertions;
import org.example.login.LoginIn;
import org.example.order.Orders;
import org.example.order.OrdersAssertions;
import org.example.order.OrdersCreate;
import org.example.user.UserAssertions;
import org.example.user.UserCreate;
import org.example.user.UserGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GetOrderTest {
    private final UserCreate client = new UserCreate();
    private final UserAssertions check = new UserAssertions();
    private final LoginIn clientLogin = new LoginIn();
    private final LoginAssertions checkLogin = new LoginAssertions();
    private final OrdersCreate order = new OrdersCreate();
    private final OrdersAssertions checkOrder = new OrdersAssertions();
    protected String accessToken;

    @Before
    public void setup(){
        var user = UserGenerator.random();
        ValidatableResponse response = client.create(user);
        check.createdSuccessfully(response, user);

        var creds = Login.from(user);
        ValidatableResponse loginResponse = clientLogin.login(creds);
        accessToken = clientLogin.getAccessToken(loginResponse);

        ValidatableResponse orderResponse = order.create(new Orders());
        checkOrder.createdSuccessfullyOrder(orderResponse);

    }

    @Description("Тест проводит проверку успешного получения списка заказов пользователя")
    @Test
    public void testGetSuccessfullyOrder(){
        ValidatableResponse getOrderResponse = order.getOrders(accessToken);
        checkOrder.getSuccessfullyOrder(getOrderResponse);
    }

    @Description("Тест проводит проверку неуспешноого получения списка заказов неавторизированного пользователя")
    @Test
    public void testGetErrorOrder(){
        ValidatableResponse getOrderResponse = order.getOrdersWithoutAuthorization();
        checkOrder.getErrorOrder(getOrderResponse);
    }

    @After
    public void tearDown() {
        if (accessToken != null) {
            ValidatableResponse deleteResponse = client.delete(accessToken);
            check.deleteSuccessfully(deleteResponse);
        }
    }
}