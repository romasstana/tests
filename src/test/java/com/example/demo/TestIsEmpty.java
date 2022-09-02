package com.example.demo;






import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.jayway.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.Matchers.*;

public class TestIsEmpty {

    @BeforeAll
    public static void setup() {
        // Setting BaseURI once
        RestAssured.baseURI = "https://reqres.in/";
        // Setting BasePath once
        RestAssured.basePath ="/api/users?page=2";
    }
    @Test
    public void isEmpty() {
        RequestSpecification request = RestAssured.given();

//        Response response = request.get();
        given().baseUri("https://reqres.in/").get("/api/users?page=2") .then() .assertThat().body(hasSize(0));
        System.out.println("List is empty");
    }
    @Test
    public void isNotEmpty() {
        RequestSpecification request = RestAssured.given();

//        Response response = request.get();
        given().baseUri("https://reqres.in/").get("/api/users?page=2") .then() .assertThat().body(notNullValue());
        System.out.println("List is not empty");
    }
    @Test
    public void allFieldsAreValid() {
        RestAssured.baseURI = "https://reqres.in/api";
        RequestSpecification request = RestAssured.given();
        JSONObject requestParams = new JSONObject();
        requestParams.put("name", "Roma");
        requestParams.put("job", "Leader");
        request.body(requestParams.toJSONString());
        Response response = request.put("/users");
        ResponseBody body = response.getBody();
        System.out.println(response.getStatusLine());
        System.out.println("Response Body is: " + body.asString());;

//       Response response = request.get();

    }
}
