package org.example.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.hamcrest.CoreMatchers.*;

public class OrdersAssertions {

    @Step("Проверка успешного создания заказа")
    public void createdSuccessfullyOrder(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("success", equalTo(true))
                .extract()
                .as(OrderResponse.class);
    }

    @Step("Проверка неуспешного создания заказа, бз ингредиентов")
    public void createdWithoutIngredients(ValidatableResponse response) {
        response.assertThat()
                .statusCode(400)
                .body("success", equalTo(false))
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Step("Проверка неуспешного создания заказа неавторизированным пользователем")
    public void createdWithoutAuthorization(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }

    @Step("Проверка неуспешного создания заказа с невалидными ингредиентами")
    public void createdWithInvalidIngredients(ValidatableResponse response) {
        response.assertThat()
                .statusCode(500);
    }

    @Step("Проверка успешного получение списка заказов пользователя")
    public void getSuccessfullyOrder(ValidatableResponse response) {
        response.assertThat()
                .statusCode(200)
                .body("order", instanceOf(Order.class))
                .body("order", notNullValue())
                .log().all();
    }

    @Step("Проверка неуспешного получения списка заказов, без авторизации")
    public void getErrorOrder(ValidatableResponse response) {
        response.assertThat()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}