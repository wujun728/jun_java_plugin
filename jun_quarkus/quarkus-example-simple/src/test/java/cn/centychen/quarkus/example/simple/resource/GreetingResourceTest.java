package cn.centychen.quarkus.example.simple.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

/**
 * Created at 2019/5/13 by centychen<292462859@qq.com>
 */
@QuarkusTest
public class GreetingResourceTest {


    @Test
    public void testGreeting() {
        String name = UUID.randomUUID().toString();
        given().pathParam("name", name)
                .when().get("/hello/{name}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(is(String.format("Hello,%s!", name)));
    }
}
