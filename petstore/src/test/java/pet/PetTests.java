package pet;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Feature("API: Pet endpoint")
public class PetTests {

    @Test
    @DisplayName("Verify ability to create new pet")
    @Tag("pets")
    @Tag("regression")
    public void testCreatePet() {
    }
}