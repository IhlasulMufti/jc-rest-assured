package com.juaracoding;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TmdbTest {

//    String baseUrl = "https://api.themoviedb.org/3";

    String myToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1OGVjNGFjNTlhYzc5NWY2ZGY2YzFkNGY1MGM0ZmUyMSIsIm5iZiI6MTcyOTg1ODUyNC42MDg3NzIsInN1YiI6IjY3MWExOGI3NGJlMTU0NjllNzBkODM3NCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.gE6KooaSlzlG9IeJDFwXFMyI4upsJr9GQiA7Jj08h9g";

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = "https://api.themoviedb.org/3";
    }

    @Test
    public void testMovieTopRated(){
        /* Cara static given() */
        /*given()
                .queryParam("language","en-US")
                .queryParam("page", "1")
                .header("Authorization",myToken)
                .when()
                .get(baseUrl+"/movie/top_rated")
                .then()
                .statusCode(200)
                .body("page",equalTo(1))
                .body("results.title[0]",equalTo("The Shawshank Redemption"))
                .log().all();*/

        /* Cara response Json Path */
        RequestSpecification request = RestAssured.given();

        request.queryParam("language","en-US");
        request.queryParam("page","1");
        request.header("Authorization",myToken);
        Response response = request.get("/movie/top_rated");

        int statusCode = response.statusCode();
        int page = response.getBody().jsonPath().getInt("page");
        String title = response.getBody().jsonPath().getString("results.title[0]");
        System.out.println(statusCode);
        System.out.println(page);
        System.out.println(title);
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals(page, 1);
        Assert.assertEquals(title, "The Shawshank Redemption");

    }

}
