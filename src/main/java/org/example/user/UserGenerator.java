package org.example.user;

import org.apache.commons.lang3.RandomStringUtils;

public class UserGenerator {
    public static User generic(){

        return new User("test-data@yandex.ru", "12345", "Angelina");
    }
    public static User random(){
        return new User("test-data" + RandomStringUtils.randomAlphanumeric(5,10) + "@yandex.ru","12345", "Angelina");
    }
}
