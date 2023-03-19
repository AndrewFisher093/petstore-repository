package pet;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;

public class BasePetstoreTests {

    protected static Faker faker;

    @BeforeAll
    public static void initHelpers() {
        faker = new Faker();
    }
}