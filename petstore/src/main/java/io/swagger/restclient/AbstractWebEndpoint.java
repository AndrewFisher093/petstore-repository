package io.swagger.restclient;

import static io.restassured.RestAssured.given;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

/**
 * The AbstractWebEndpoint describes Http methods to execute for specific endpoint.
 */
public class AbstractWebEndpoint {

    /**
     * Post validatable response.
     *
     * @param reqSpec the request specification
     * @param path the path
     * @param bodyPayload the body payload
     * @param pathParams the path params
     * @return the validatable response
     */
    public ValidatableResponse post(RequestSpecification reqSpec, String path, Object bodyPayload,
        Object... pathParams) {
        RequestSpecBuilder specBuilder = new RequestSpecBuilder();
        specBuilder.addRequestSpecification(reqSpec);

        if (bodyPayload != null) {
            specBuilder.setBody(bodyPayload);
        }
        return given()
            .spec(specBuilder.build())
            .when()
            .post(path, pathParams)
            .then();
    }

    /**
     * A method to use rest assured to extract response as desired object type.
     *
     * @param <T> the type parameter
     * @param validatableResponse the validatable response
     * @param dtoClass the dto class
     * @return the t
     */
    public static <T> T extractObjectAsDto(ValidatableResponse validatableResponse, Class<T> dtoClass) {
        return validatableResponse.extract().as(dtoClass, ObjectMapperType.JACKSON_2);
    }
}