package io.swagger.restclient;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.restassured.RestAssured;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RestAssuredConfiguration {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Config CONFIG = ConfigFactory.load();

    private RestAssuredConfiguration() {
    }

    public static void baseUriVersioned() {
        LOGGER.info("Building base URI with versioned path");

        RestAssured.baseURI = CONFIG.getString("base.uri");
        RestAssured.basePath = CONFIG.getString("base.version");
    }

    public static void baseUriUnVersioned() {
        LOGGER.info("Building base URI with un-versioned path");

        RestAssured.baseURI = CONFIG.getString("base.uri");
        RestAssured.basePath = StringUtils.EMPTY;
    }
}