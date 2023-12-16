package org.example.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class OrdersCreate extends org.example.Client {

    public static final String BASE_ORDERS = "api/orders";

    @Step("Создание заказа")
    public ValidatableResponse create(Orders orders) {
        Orders ordersSet = new Orders();
        ordersSet.setIngredients(new String[]{"61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa72"});
        return specOrders()
                .body(ordersSet)
                .when()
                .post(BASE_ORDERS)
                .then();
    }

    @Step("Создание заказа без ингредиентов")
    public ValidatableResponse createOrderWithoutIngredients(Orders orders) {
        Orders ordersSet = new Orders();
        ordersSet.setIngredients(new String[]{});
        return specOrders()
                .body(ordersSet)
                .when()
                .post(BASE_ORDERS)
                .then();
    }

    @Step("Создание заказа с невалидными ингредиентами")
    public ValidatableResponse createInvalidIngredients(Orders orders) {
        Orders ordersSet = new Orders();
        ordersSet.setIngredients(new String[]{"60d3111111111111", "609646e4dc916e00276b2870"});
        return specOrders()
                .body(ordersSet)
                .when()
                .post(BASE_ORDERS)
                .then();
    }

    @Step("Получение списка заказов пользователя")
    public ValidatableResponse getOrders(String accessToken) {
        return specOrders()
                .header("authorization", accessToken)
                .when()
                .get(BASE_ORDERS)
                .then();
    }

    @Step("Получение списка заказов неавторизированного пользователя")
    public ValidatableResponse getOrdersWithoutAuthorization() {
        return specOrders()
                .when()
                .get(BASE_ORDERS)
                .then();
    }
}