package io.swagger.endpoints;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.swagger.helpers.HttpStatus;
import io.swagger.restclient.RestAssuredConfiguration;
import java.util.HashMap;
import java.util.Map;

public class AuthorizationEndpoint extends AbstractWebEndpoint {

    private static final String OAUTH_ENDPOINT = "/oauth/authorize";
    private static final String LOGIN_ENDPOINT = "/oauth/login";

    // variables
    private static final Config CONFIG = ConfigFactory.load();
    private static final String JSESSIONID = "JSESSIONID";
    private static final String JSESSIONID_IS = "JSESSIONID=";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private String sessionId;

    public AuthorizationEndpoint() {
        RestAssuredConfiguration.baseUriUnVersioned();
    }

    public AuthorizationEndpoint login() {
        Map<String, String> formParams = new HashMap<>();

        formParams.put(USERNAME, CONFIG.getString("user.username"));
        formParams.put(PASSWORD, CONFIG.getString("user.password"));

        RequestSpecification requestSpecification = buildRequestSpecification(ContentType.URLENC, new HashMap<>())
            .formParams(formParams)
            .redirects()
            .follow(false);

        this.sessionId = postWithoutBody(requestSpecification, LOGIN_ENDPOINT)
            .statusCode(HttpStatus.FOUND.getCode())
            .extract().cookies().get(JSESSIONID);
        return this;
    }

    public AuthorizationEndpoint authorizeWithQueryParams() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", JSESSIONID_IS + sessionId);

        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("response_type", "token");
        queryParams.put("client_id", "test");
        queryParams.put("scope", "read:pets write:pets");
        queryParams.put("redirect_uri", "https://oauth.pstmn.io/v1/callback");

        var requestSpecification = buildRequestSpecification(ContentType.JSON, headers)
            .queryParams(queryParams)
            .redirects()
            .follow(false);

        get(requestSpecification, OAUTH_ENDPOINT).statusCode(HttpStatus.OK.getCode());
        return this;
    }

    public String retrieveBearerToken() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Cookie", JSESSIONID_IS + sessionId);

        Map<String, String> formParams = new HashMap<>();
        formParams.put("user_oauth_approval", "true");
        formParams.put("scope.read:pets", "true");
        formParams.put("scope.write:pets", "true");
        formParams.put("authorize", "Authorize");

        RequestSpecification requestSpecification = buildRequestSpecification(ContentType.URLENC, headers)
            .formParams(formParams)
            .redirects()
            .follow(false);

        var locationValue = postWithoutBody(requestSpecification, OAUTH_ENDPOINT)
            .statusCode(HttpStatus.FOUND.getCode())
            .extract().response().headers().getValue("Location");

        var start = locationValue.indexOf("=");
        var end = locationValue.indexOf("&");

        return locationValue.substring(start + 1, end);
    }
}