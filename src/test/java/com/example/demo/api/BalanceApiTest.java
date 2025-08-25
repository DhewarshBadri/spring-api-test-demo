package com.example.demo.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BalanceApiTest extends AbstractTestNGSpringContextTests {

    @LocalServerPort
    private int port;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    public void testGetBalance() {
        String accountId = "12345";
        
        given()
            .pathParam("accountId", accountId)
        .when()
            .get("/api/getBalance/{accountId}")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("accountId", equalTo(accountId))
            .body("amount", is(1250.75f))
            .body("currency", equalTo("USD"));
    }

    @Test
    public void testGetBalanceWithInvalidAccount() {
        // In a real app, this might return a 404 or specific error
        // For our demo, we're just verifying the accountId is passed correctly
        String accountId = "invalid-account";
        
        given()
            .pathParam("accountId", accountId)
        .when()
            .get("/api/getBalance/{accountId}")
        .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("accountId", equalTo(accountId));
    }
}
