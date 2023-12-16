package org.example.login;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.Map;

public class LoginIn extends org.example.Client {
    public static final String LOGIN_PATH = "/login";
    public static final String LOGIN_LOGOUT = "/logout";

    @Step("Авторизация пользователя")
    public ValidatableResponse login(Login login) {
        return spec()
                .body(login)
                .when()
                .post(LOGIN_PATH)
                .then();
    }

    @Step("Получение токена авторизации - accessToken")
    public String getAccessToken(ValidatableResponse response) {
        String accessToken = response
                .extract()
                .path("accessToken");
        return accessToken;
    }

    @Step("Получение токена refreshToken")
    public String getRefreshToken(ValidatableResponse response) {
        String refreshToken = response
                .extract()
                .path("refreshToken");
        return refreshToken;
    }

    @Step("Выход из системы")
    public ValidatableResponse logout(String refreshToken) {
        return spec()
                .body(Map.of("token", String.valueOf(refreshToken)))
                .when()
                .post(LOGIN_LOGOUT)
                .then();
    }
}