package com.example.demo;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import com.google.common.collect.Ordering;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static com.jayway.restassured.RestAssured.given;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

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
    public void deleteExistObject(){
        int id = 2;
        RestAssured.baseURI="https://reqres.in/api";

        RequestSpecification httpRequest=RestAssured.given();

        httpRequest.header("Content-Type","application/json");


        Response response=httpRequest.delete("/users/"+id);

        String responseBody=response.getBody().asString();
        System.out.println("Response Body is:" +responseBody);

        int statusCode=response.getStatusCode();
        System.out.println("Status code is: "+statusCode);
        Assert.assertEquals(statusCode, 204);
    }
    @Test
    public void deleteObjectByNotValidId(){
        String id = "agaefefe";
        RestAssured.baseURI="https://reqres.in/api";

        RequestSpecification httpRequest=RestAssured.given();

        httpRequest.header("Content-Type","application/json");
        Response response=httpRequest.delete("/users/"+id);

        String responseBody=response.getBody().asString();
        System.out.println("Response Body is:" +responseBody);

        int statusCode=response.getStatusCode();
        System.out.println("Status code is: "+statusCode);
        Assert.assertEquals(statusCode, 204);
    }
    @Test
    public void postValidFields(){
        JSONObject request = new JSONObject();
        request.put("name", "Roma");
        request.put("job", "developer");

        System.out.println(request);
        System.out.println(request.toString());

        given().
                body(request.toJSONString()).
                when().
                post("https://reqres.in/api/users").
                then().statusCode(201 );
    }
    @Test
    public void postNotNullFields(){
        JSONObject request = new JSONObject();
        request.put("name", "Roma");
        request.put("job", "developer");

        System.out.println(request);
        System.out.println(request.toString());

        given().
                body(request.toJSONString()).
                when().
                post("https://reqres.in/api/users").
                then().statusCode(201 );
    }
    @Test
    public void postAllFieldsAreNull(){
        JSONObject request = new JSONObject();


        System.out.println(request);
        System.out.println(request.toString());

        given().
                body(request.toJSONString()).
                when().
                post("https://reqres.in/api/users").
                then().statusCode(201 );
    }
    @Test
    public void postNotNullFieldsNotEverything(){
        JSONObject request = new JSONObject();
        request.put("name", "Roma");
        System.out.println(request);
        System.out.println(request.toString());

        given().
                body(request.toJSONString()).
                when().
                post("https://reqres.in/api/users").
                then().statusCode(201 );
    }
    @Test
    public void postEmptyJSON(){
        JSONObject request = new JSONObject();
        System.out.println(request);
        System.out.println(request.toString());

        given().
                body(request.toJSONString()).
                when().
                post("https://reqres.in/api/users").
                then().statusCode(201 );
    }
    @Test
    public void postValidation(){
        JSONObject request = new JSONObject();
        request.put("name", "Roma");
        request.put("job", 123);

        System.out.println(request);
        System.out.println(request.toString());

        given().
                body(request.toJSONString()).
                when().
                post("https://reqres.in/api/users").
                then().statusCode(201 );
    }
    @Test
    public void postDate() {
        JSONObject request = new JSONObject();
        request.put("name", "Roma");
        request.put("job", "developer");

        String createdDate = given().contentType("application/json")
                .body(request)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .path("createdAt");
        assertThat(createdDate).isNotEmpty();
    }
    @Test
    public void deleteByNotExistId(){
        int id = 777777777;
        RestAssured.baseURI="https://reqres.in/api";

        RequestSpecification httpRequest=RestAssured.given();

        httpRequest.header("Content-Type","application/json");


        Response response=httpRequest.delete("/users/"+id);

        String responseBody=response.getBody().asString();
        System.out.println("Response Body is:" +responseBody);

        int statusCode=response.getStatusCode();
        System.out.println("Status code is: "+statusCode);
        Assert.assertEquals(statusCode, 400);
    }
    @Test
    public void putUsingCorrectFields(){
        int id=2;
        String name = "Roma";
        String job = "CEO";
        baseURI="https://reqres.in/api";
        RequestSpecification httpRequest = RestAssured.given()
                .header("Content-Type", "application/json");
        Response res = httpRequest.body("{ \"name\": \"" + name + "\", \"job\": \"" + job + "\"}").put("/users/2");
        System.out.println("The response code - " +res.getStatusCode());
        Assert.assertEquals(res.getStatusCode(),200);
    }
    @Test
    public void putUsingNotExistId(){
        String name = "Roma";
        String job = "CEO";
        baseURI="https://reqres.in/api";
        RequestSpecification httpRequest = RestAssured.given()
                .header("Content-Type", "application/json");
        Response res = httpRequest.body("{ \"name\": \"" + name + "\", \"job\": \"" + job + "\"}").put("/users/77777");
        System.out.println("The response code - " +res.getStatusCode());
        Assert.assertEquals(res.getStatusCode(),404);
    }
    @Test
    public void putUsingNotValidId(){
        String name = "Roma";
        String job = "CEO";
        baseURI="https://reqres.in/api";
        RequestSpecification httpRequest = RestAssured.given()
                .header("Content-Type", "application/json");
        Response res = httpRequest.body("{ \"name\": \"" + name + "\", \"job\": \"" + job + "\"}").put("/users/asdasdasd");
        System.out.println("The response code - " +res.getStatusCode());
        Assert.assertEquals(res.getStatusCode(),404);
    }
    @Test
    public void putValidation(){
        String name = "Roma";
        int job = 123;
        baseURI="https://reqres.in/api";
        RequestSpecification httpRequest = RestAssured.given()
                .header("Content-Type", "application/json");
        Response res = httpRequest.body("{ \"name\": \"" + name + "\", \"job\": \"" + job + "\"}").put("/users/2");
        System.out.println("The response code - " +res.getStatusCode());
        Assert.assertEquals(res.getStatusCode(),404);
    }
    @Test
    public void putJsonHalf(){
        String name = "Roma";
        baseURI="https://reqres.in/api";
        RequestSpecification httpRequest = RestAssured.given()
                .header("Content-Type", "application/json");
        Response res = httpRequest.body("{ \"name\": \"" + name + "\"}").put("/users/2");
        System.out.println("The response code - " +res.getStatusCode());
        Assert.assertEquals(res.getStatusCode(),200);
    }
    @Test
    public void getByNotValidId(){
        RequestSpecification request = RestAssured.given();

//        Response response = request.get();
        Response res =request.given().baseUri("https://reqres.in/").get("/api/users?page=777777");
        Assert.assertEquals(res.getStatusCode(), 404);
    }
    @Test
    public void getByNotValidId_andGetting404WithDescription(){
        RequestSpecification request = RestAssured.given();

//        Response response = request.get();
        Response res =request.given().baseUri("https://reqres.in/").get("/api/users?page=777777");
        Assert.assertEquals(res.getStatusCode(), 400);
    }
    @Test
    public void getPagination(){
        RequestSpecification requestSpecification = RestAssured.given();
        Response res =requestSpecification.given().baseUri("https://reqres.in/").get("/api/users?page=2");
        ResponseBody body = res.getBody();
        Assert.assertTrue(body.asString().contains("page"));
    }
    @Test
    public void getLimit(){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.given().baseUri("https://reqres.in/").get("api/users?page=2&per_page=5").then().assertThat()
                .body("data", hasSize(5));
   }
    @Test
    public void getPage(){
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.given().baseUri("https://reqres.in/").get("api/users?page=2").then().assertThat()
                .body("page", is(2));
    }
    @Test
    public void getOffsetNegative(){
        int page = -1;
        RequestSpecification requestSpecification =RestAssured.given();
        requestSpecification.given().baseUri("https://reqres.in/").get("api/users?page=-2&per_page=5").then().assertThat().
                body("data.id", hasItem(1));
    }
    @Test
    public void getNoOffset(){
        int page = -1;
        RequestSpecification requestSpecification =RestAssured.given();
        requestSpecification.given().baseUri("https://reqres.in/").get("api/users?page=245&per_page=5").then().assertThat().
                body("data.id", empty());
    }
    @Test
    public void sortTheList(){
        baseURI = "https://reqres.in/api/users?page=2&per_page=5";
        Response response = RestAssured.given()
                .queryParam("sort", "data.id")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when().get("https://reqres.in/api/users?page=2&per_page=5")
                .then().statusCode(200).extract().response();
        List<Integer> jsonResponse = response.jsonPath().getList("data.id");
        Assert.assertTrue(Ordering.natural().isOrdered(jsonResponse));
    }
    @Test
    public void getValidIdCorrectData(){
        baseURI="https://reqres.in/api/users/2";
        RequestSpecification requestSpecification = RestAssured.given();
        requestSpecification.given().get("https://reqres.in/api/users/2").then().assertThat().
                body("data.email", is("janet.weaver@reqres.in")).and().assertThat().body("data.first_name", is("Janet"))
                .and().assertThat().body("data.last_name", is("Weaver"));
    }
    @Test
    public void getByNotValidIdButValidFormat(){
        RequestSpecification request = RestAssured.given();

//        Response response = request.get();
        Response res =request.given().baseUri("https://reqres.in/").get("/api/users/777777");
        Assert.assertEquals(res.getStatusCode(), 404);
    }
    @Test
    public void deleteDeletedObject(){
        int id = 2;
        RestAssured.baseURI="https://reqres.in/api";

        RequestSpecification httpRequest=RestAssured.given();

        httpRequest.header("Content-Type","application/json");


        Response response=httpRequest.delete("/users/"+id);

        String responseBody=response.getBody().asString();
        System.out.println("Response Body is:" +responseBody);

        int statusCode=response.getStatusCode();
        System.out.println("Status code is: "+statusCode);
        RestAssured.baseURI="https://reqres.in/api";

        RequestSpecification httpRequest1=RestAssured.given();

        httpRequest.header("Content-Type","application/json");


        Response response1=httpRequest1.delete("/users/"+id);

        String responseBody1=response1.getBody().asString();
        System.out.println("Response Body is:" +responseBody1);

        int statusCode1=response1.getStatusCode();
        System.out.println("Status code is: "+statusCode1);
        Assert.assertThat(statusCode1, is(404));
    }
    @Test
    public void deleteAndRegister(){
        int id = 2;
        RestAssured.baseURI="https://reqres.in/api";

        RequestSpecification httpRequest=RestAssured.given();

        httpRequest.header("Content-Type","application/json");


        Response response=httpRequest.delete("/users/"+id);

        String responseBody=response.getBody().asString();
        System.out.println("Response Body is:" +responseBody);

        int statusCode=response.getStatusCode();
        System.out.println("Status code is: "+statusCode);

        JSONObject request = new JSONObject();
        request.put("name", "Roma");
        request.put("job", "developer");

        System.out.println(request);
        System.out.println(request.toString());

        given().
                body(request.toJSONString()).
                when().
                post("https://reqres.in/api/users").
                then().statusCode(201 );
    }

}
