package com.bigeek.microservices.userservice;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.bigeek.microservices.userservice.model.User;
import com.jayway.restassured.RestAssured;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceApplicationTests {
	
	@LocalServerPort
	private int port;
	
	@Test
	public void assertGetAll() {
		RestAssured
        .given()
        	.port(port)
            .queryParam("format", "json")
        .when()
            .get("/users")
        .then()
            .assertThat().statusCode(is(equalTo(200)));
	}
	
	@Test
	public void assertGetUser() {
		RestAssured
        .given()
        	.port(port)
            .queryParam("format", "json")
        .when()
            .get("/users/1001")
        .then()
            .assertThat().statusCode(is(equalTo(200)));
	}
	
	@Test
	public void assertUserNotFound() {
		RestAssured
        .given()
        	.port(port)
            .queryParam("format", "json")
        .when()
            .get("/users/8")
        .then()
            .assertThat().statusCode(is(equalTo(404)));
	}
	
	@Test
	public void assertCreateUser() throws JSONException {
		User user = new User(null, "Laura", 30);
        
		 RestAssured
        .given()
        	.port(port)
        	.contentType("application/json")
            .body(user)
        .when()
            .post("/users")
        .then()
            .statusCode(is(equalTo(201)));
	}
	
	@Test
	public void assertCreateUserBadName() throws JSONException {
		User user = new User(null, "aa", 47);
        
		 RestAssured
        .given()
        	.port(port)
        	.contentType("application/json")
            .body(user)
        .when()
            .post("/users")
        .then()
            .statusCode(is(equalTo(400)));
	}
	
	@Test
	public void assertCreateUserBadAge() throws JSONException {
		User user = new User(null, "Fernando", 0);
        
		 RestAssured
        .given()
        	.port(port)
        	.contentType("application/json")
            .body(user)
        .when()
            .post("/users")
        .then()
            .statusCode(is(equalTo(400)));
	}
	
	@Test
	public void assertDeleteUser() throws JSONException {
				
		 RestAssured
        .given()
        	.port(port)
        	.contentType("application/json")
        	.pathParam("id", 1001)
        .when()
            .delete("/users/{id}")
        .then()
            .statusCode(is(equalTo(200)));
	}
	
	@Test
	public void assertDeleteBadUser() throws JSONException {
				
		 RestAssured
        .given()
        	.port(port)
        	.contentType("application/json")
        	.pathParam("id", 1111)
        .when()
            .delete("/users/{id}")
        .then()
            .statusCode(is(equalTo(500)));
	}

}
