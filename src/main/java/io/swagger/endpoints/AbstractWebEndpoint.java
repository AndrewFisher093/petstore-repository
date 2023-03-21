package io.swagger.endpoints;

import static io.restassured.RestAssured.given;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The AbstractWebEndpoint describes Http methods to execute for specific endpoint.
 */
public class AbstractWebEndpoint {

    protected static final Logger LOGGER = LogManager.getLogger();

    /**
     * Build request specification.
     *
     * @param contentType the content type
     * @param headers the headers
     * @return the request specification
     */
    protected RequestSpecification buildRequestSpecification(ContentType contentType, Map<String, String> headers) {
        return new RequestSpecBuilder()
            .log(LogDetail.ALL)
            .setContentType(contentType)
            .addFilter(new AllureRestAssured())
            .addFilter(new ResponseLoggingFilter())
            .addHeaders(headers)
            .build();
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
     * Execute POST request.
     *
     * @param reqSpec the request specification
     * @param path the path
     * @param pathParams the path params
     * @return the validatable response
     */
    public ValidatableResponse postWithoutBody(RequestSpecification reqSpec, String path, Object... pathParams) {
        LOGGER.debug("Send POST request to url [{}]", path);

        RequestSpecBuilder specBuilder = new RequestSpecBuilder();
        specBuilder.addRequestSpecification(reqSpec);

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