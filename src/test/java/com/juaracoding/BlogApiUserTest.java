package com.juaracoding;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BlogApiUserTest {

    String token = null;

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "http://localhost:8081/api";
    }

    @Test(priority = 1)
    public void testUserLogin(){
        /* Response Json Path */
        JSONObject requestBody = new JSONObject();
        requestBody.put("usernameOrEmail", "ervin");
        requestBody.put("password", "password");

        RequestSpecification request = given();
        request.header("content-type", "application/json");
        request.body(requestBody.toJSONString());
        Response response = request.post("/auth/signin");

        token = response.getBody().jsonPath().getString("accessToken");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotNull(token);
    }

    @Test(priority = 2)
    public void testMyProfile(){
        /* static given() */
        given()
                .header("Authorization","Bearer "+token)
                .when()
                .get("/users/me")
                .then()
                .statusCode(200)
                .body("id",equalTo(2))
                .body("username",equalTo("ervin"));
    }

}
