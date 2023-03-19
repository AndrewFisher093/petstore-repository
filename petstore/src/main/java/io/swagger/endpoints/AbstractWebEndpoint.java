package io.swagger.endpoints;

import static io.restassured.RestAssured.given;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.swagger.restclient.RestAssuredConfiguration;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The AbstractWebEndpoint describes Http methods to execute for specific endpoint.
 */
public class AbstractWebEndpoint {

    protected static final Logger LOGGER = LogManager.getLogger();

    public AbstractWebEndpoint() {
        RestAssuredConfiguration.instance();
    }

    /**
     * Execute POST request.
     *
     * @param reqSpec the request specification
     * @param path the path
     * @param bodyPayload the body payload
     * @param pathParams the path params
     * @return the validatable response
     */
    public ValidatableResponse post(RequestSpecification reqSpec, String path, Object bodyPayload,
        Object... pathParams) {
        LOGGER.debug("Send POST request to url [{}]", path);

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
     * Execute GET request.
     *
     * @param reqSpec the request specification
     * @param path the path
     * @param params the params
     * @return the validatable response
     */
    public ValidatableResponse get(RequestSpecification reqSpec, String path, Object... params) {
        LOGGER.debug("Send GET request to url [{}]", path);

        return given()
            .spec(reqSpec)
            .when()
            .get(path, params)
            .then();
    }

    /**
     * Execute DELETE request.
     *
     * @param reqSpec the request specification
     * @param path the path
     * @param params the params
     * @return the validatable response
     */
    public ValidatableResponse delete(RequestSpecification reqSpec, String path, Object... params) {
        LOGGER.debug("Send DELETE request to url [{}]", path);

        return given()
            .spec(reqSpec)
            .when()
            .delete(path, params)
            .then();
    }

    /**
     * Execute PUT request.
     *
     * @param reqSpec the req spec
     * @param path the path
     * @param bodyPayload the body payload
     * @param params the params
     * @return the validatable response
     */
    public ValidatableResponse put(RequestSpecification reqSpec, String path, Object bodyPayload,
        Object... params) {
        RequestSpecBuilder specBuilder = new RequestSpecBuilder();
        specBuilder.addRequestSpecification(reqSpec);

        if (bodyPayload != null) {
            specBuilder.setBody(bodyPayload);
        }

        return given()
            .spec(specBuilder.build())
            .when()
            .put(path, params)
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

    /**
     * A method to use rest assured to extract response as desired list of object type.
     *
     * @param <T> the type parameter
     * @param validatableResponse the validatable response
     * @param dtoClass the dto class
     * @return the t
     */
    public static <T> List<T> extractObjectsAsDtoList(ValidatableResponse validatableResponse, Class<T> dtoClass) {
        return validatableResponse.extract().body().jsonPath().getList("", dtoClass);
    }
}