package org.example.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.io.File;

public class UserCreate extends org.example.Client {
    public static final String REGISTER_PATH = "/register";
    public static final String USER_PATH = "/user";
    public static final String JSON_FILE_PATH = "src/main/resources/newUser.json";

    @Step("Создание пользователя")
    public ValidatableResponse create(User user) {
        return spec()
                .body(user)
                .when()
                .post(REGISTER_PATH).then();
    }

    @Step("Создание пользователя без ввода значений в поле Пароль")
    public ValidatableResponse createWithoutPassword(UserWithoutPassword userWithoutPassword) {
        return spec()
                .body(userWithoutPassword)
                .when()
                .post(REGISTER_PATH)
                .then();
    }

    @Step("Создание пользователя без ввода значений в поле Имя")
    public ValidatableResponse createWithoutName(UserWithoutName userWithoutName) {
        return spec()
                .body(userWithoutName)
                .when()
                .post(REGISTER_PATH)
                .then();
    }

    @Step("Создание пользователя без ввода значений в поле Email")
    public ValidatableResponse createWithoutEmail(UserWithoutEmail userWithoutEmail) {
        return spec()
                .body(userWithoutEmail)
                .when()
                .post(REGISTER_PATH)
                .then();
    }

    @Step("Изменение данных о пользователе")
    public ValidatableResponse patch(String accessToken) {
        File json = new File(JSON_FILE_PATH);
        return spec()
                .header("authorization", accessToken)
                .body(json)
                .when()
                .patch(USER_PATH)
                .then();

    }

    @Step("Удаление пользователя")
    public ValidatableResponse delete(String accessToken) {
        return spec()
                .header("authorization", accessToken)
                .when()
                .delete(USER_PATH)
                .then();
    }

    @Step("Изменение данных о пользователе, без авторизации")
    public ValidatableResponse patchWithoutLogin() {
        File json = new File(JSON_FILE_PATH);
        return spec()
                .body(json)
                .when()
                .patch(USER_PATH)
                .then();
    }
}