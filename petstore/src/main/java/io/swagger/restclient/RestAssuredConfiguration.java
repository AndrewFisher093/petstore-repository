package io.swagger.restclient;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.restassured.RestAssured;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RestAssuredConfiguration {

    protected static final Logger LOGGER = LogManager.getLogger();

    private RestAssuredConfiguration() {
        LOGGER.info("Building base URI");

        Config config = ConfigFactory.load();

        RestAssured.baseURI = config.getString("base.uri");
        RestAssured.basePath = config.getString("base.version");
    }

    public static void instance() {
        new RestAssuredConfiguration();
    }
}