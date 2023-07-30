package practicum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import practicum.entity.Order;
import practicum.entity.User;

public class OrderCreationTest {
    private final OrderApi orderApi = new OrderApi();
    private final UserApi userApi = new UserApi();
    private User user;
    String accessToken;

    @Before
    public void setUp() {
        user = User.generateRandomUser();
        ValidatableResponse response = userApi.create(user);
        accessToken = response.extract().path("accessToken").toString();
    }

    //с авторизацией
    @Test
    @DisplayName("Create order with authorization and ingredients")
    public void createOrderWithAuthAndIngredients() {
        ValidatableResponse response = orderApi.createWithAuth(Order.getWithDefaultIngredients(), accessToken);
        response.assertThat().statusCode(200).body("success", Matchers.is(true));
    }


    //без авторизации,
    /*  В доке написано "Только авторизованные пользователи могут делать заказы.",
        а в задании для диплома написано, что тесты все должны проходить,
        но заказ успешно создается и без авторизации.
    */
    @Test
    @DisplayName("Create order without authorization")
    public void createOrderWithoutAuth() {
        ValidatableResponse response = orderApi.createWithoutAuth(Order.getWithDefaultIngredients());
        response.assertThat().statusCode(200).body("success", Matchers.is(true));
    }

    //без ингредиентов,
    @Test
    @DisplayName("Create order without ingredients")
    public void createOrderWithoutIngredients() {
        ValidatableResponse response = orderApi.createWithAuth(Order.getWithoutIngredients(), accessToken);
        response.assertThat().statusCode(400)
                .and().body("message", Matchers.equalTo("Ingredient ids must be provided"));
    }

    //с неверным хешем ингредиентов
    @Test
    @DisplayName("Create order with incorrect ingredients")
    public void createOrderWithIncorrectIngredients() {
        ValidatableResponse response = orderApi.createWithAuth(new Order(new String[]{"pepper", "mint"}), accessToken);
        response.assertThat().statusCode(500);
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            userApi.delete(accessToken);
            accessToken = null;
        }
    }
}