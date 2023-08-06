package practicum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import practicum.entity.User;


public class UserCreationTest {


    private User user;
    private String accessToken;
    UserApi userApi = new UserApi();

    @Before
    public void setUser() {
        user = User.generateRandomUser();
    }

    //создать уникального пользователя;
    @Test
    @DisplayName("Create unique user")
    public void createUniqueUserTest() {
        ValidatableResponse response = userApi.create(user);
        accessToken = response.extract().path("accessToken").toString();
        response.assertThat().statusCode(200).body("success", Matchers.is(true));
    }

    //создать пользователя, который уже зарегистрирован;
    @Test
    @DisplayName("Create user already exists")
    public void createDuplicateUserTest() {
        ValidatableResponse response = userApi.create(user);
        accessToken = response.extract().path("accessToken").toString();
        ValidatableResponse response2 = userApi.create(user);
        response2.statusCode(403).and().assertThat().body("message", Matchers.equalTo("User already exists"));
    }

    //создать пользователя и не заполнить одно из обязательных полей.
    @Test
    @DisplayName("Create user with empty email")
    public void createUserWithoutEmailTest() {
        user.setEmail("");
        ValidatableResponse response = userApi.create(user);
        response.statusCode(403).and().assertThat().body("message", Matchers.equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Create user with empty name")
    public void createUserWithoutNameTest() {
        user.setName("");
        ValidatableResponse response = userApi.create(user);
        response.statusCode(403).and().assertThat().body("message", Matchers.equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Create user with empty password")
    public void createUserWithoutPasswordTest() {
        user.setPassword("");
        ValidatableResponse response = userApi.create(user);
        response.statusCode(403).and().assertThat().body("message", Matchers.equalTo("Email, password and name are required fields"));
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            userApi.delete(accessToken);
            accessToken = null;
        }
    }
}

