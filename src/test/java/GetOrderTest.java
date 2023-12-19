import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.example.order.Orders;
import org.junit.Before;
import org.junit.Test;

public class GetOrderTest extends BaseTest {

    @Before
    public void setup() {
        super.setup();
        order.create(new Orders());
    }

    @Description("Тест проводит проверку успешного получения списка заказов пользователя")
    @Test
    public void testGetSuccessfullyOrder() {
        ValidatableResponse getOrderResponse = order.getOrders(accessToken);
        checkOrder.getSuccessfullyOrder(getOrderResponse);
    }

    @Description("Тест проводит проверку неуспешноого получения списка заказов неавторизированного пользователя")
    @Test
    public void testGetErrorOrder() {
        ValidatableResponse getOrderResponse = order.getOrdersWithoutAuthorization();
        checkOrder.getErrorOrder(getOrderResponse);
    }
}