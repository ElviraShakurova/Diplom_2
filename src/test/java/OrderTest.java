import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.example.order.Orders;
import org.junit.Test;

public class OrderTest extends BaseTest {

    @Description("Тест проводит проверку успешного создания заказа")
    @Test
    public void testOrderSuccessfulCreate() {
        ValidatableResponse orderResponse = order.create(new Orders());
        checkOrder.createdSuccessfullyOrder(orderResponse);
    }

    @Description("Тест проводит проверку неуспешного создания заказа, без авторизации")
    @Test
    public void testOrderCreateWithoutAuthorization() {
        ValidatableResponse responseLogout = clientLogin.logout(refreshToken);
        checkLogin.logoutSuccessfully(responseLogout);

        ValidatableResponse orderResponse = order.create(new Orders());
        checkOrder.createdWithoutAuthorization(orderResponse);
    }

    @Description("Тест проводит проверку неуспешного создания заказа, без ингредиентов")
    @Test
    public void testOrderCreateWithoutIngredients() {
        ValidatableResponse orderResponse = order.createOrderWithoutIngredients(new Orders());
        checkOrder.createdWithoutIngredients(orderResponse);
    }

    @Description("Тест проводит проверку неуспешного создания заказа, с невалидными ингредиентами")
    @Test
    public void testOrderCreateWithInvalid() {
        ValidatableResponse orderResponse = order.createInvalidIngredients(new Orders());
        checkOrder.createdWithInvalidIngredients(orderResponse);
    }
}