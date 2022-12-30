package com.rodrigo.integrationtests.swagger;

import com.rodrigo.configs.TestConfig;
import com.rodrigo.integrationtests.testcontainers.AbstractIntegrationClass;
import static io.restassured.RestAssured.given;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIntegrationTests extends AbstractIntegrationClass {

    @Test
    public void shouldDisplaySwaggerUiPage(){

        var content =
            given()
                    .basePath("/swagger-ui/index.html")
                    .port(TestConfig.SERVER_PORT)
                    .when()
                        .get()
                    .then()
                        .statusCode(200)
                    .extract()
                        .body()
                            .asString();
        assertTrue(content.contains(("Swagger UI")));
    }
}
