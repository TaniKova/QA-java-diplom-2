package practicum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import practicum.entity.User;

public class UserLoginTest {

    private User user;
    private String accessToken;
    UserApi userApi = new UserApi();

    @Before
    public void setUser() {
        user = User.generateRandomUser();
    }

    //логин под существующим пользователем,
    @Test
    @DisplayName("Login with existing user")
    public void loginExistingUserTest() {
        userApi.create(user);
        ValidatableResponse response = userApi.login(user);
        accessToken = response.extract().path("accessToken").toString();
        response.assertThat().statusCode(200).body("success", Matchers.is(true));
    }

    //логин с неверным логином и паролем.
    @Test
    @DisplayName("Login with wrong email")
    public void loginWithoutEmailTest() {
        user.setEmail("");
        ValidatableResponse response = userApi.login(user);
        response.statusCode(401).and().assertThat().body("message", Matchers.equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Login with wrong password")
    public void loginWithoutPasswordTest() {
        user.setPassword("");
        ValidatableResponse response = userApi.login(user);
        response.statusCode(401).and().assertThat().body("message", Matchers.equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Login with wrong email and password")
    public void loginWithoutEmailAndPasswordTest() {
        user.setPassword("");
        user.setEmail("");
        ValidatableResponse response = userApi.login(user);
        response.statusCode(401).and().assertThat().body("message", Matchers.equalTo("email or password are incorrect"));
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            userApi.delete(accessToken);
            accessToken = null;
        }
    }
}
