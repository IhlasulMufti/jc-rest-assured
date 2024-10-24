package com.juaracoding;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserTest {

    String baseUrl = "https://reqres.in/api";

    @Test
    public void testGetListUsers(){
        Response response = RestAssured.get(baseUrl+"/users?page=2");
        System.out.println(response.getBody().asString());
        System.out.println(response.getStatusCode());
        System.out.println(response.getStatusLine());
        System.out.println(response.getHeader("content-type"));
        System.out.println(response.getTime());
        int statusCode = response.statusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test
    public void testGetSingleUser(){
        given()
                .get(baseUrl+"/users/2")
                .then()
                .statusCode(200)
                .body("data.id",equalTo(2));
    }

    @Test
    public void testUserNotFound(){
        given()
                .get(baseUrl+"/users/34")
                .then()
                .statusCode(404);
    }

    @Test
    public void testAddUser(){
        JSONObject request = new JSONObject();
        request.put("name", "morpheus");
        request.put("job", "leader");
        System.out.println(request.toJSONString());

        given()
                .header("content-type", "application/json")
                .body(request.toJSONString())
                .when() // send request
                .post(baseUrl+"/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("leader"));
    }

    @Test
    public void testUpdateUser(){
        JSONObject request = new JSONObject();
        request.put("name", "morpheus");
        request.put("job", "leader");
        System.out.println(request.toJSONString());

        given()
                .header("content-type", "application/json")
                .body(request.toJSONString())
                .when() // send request
                .put(baseUrl+"/users/4")
                .then()
                .statusCode(200)
                .body("name", equalTo("morpheus"))
                .body("job", equalTo("leader"))
                .log().all();
    }

    @Test
    public void testDeleteUser(){

        //Cara 1
        given()
                .delete(baseUrl+"/users/2")
                .then()
                .statusCode(204)
                .log().all();

        // Cara 2
//        Response response = RestAssured.delete(baseUrl+"/users/2");
//        int getStatusCode = response.getStatusCode();
//        Assert.assertEquals(getStatusCode, 204);
    }

}
