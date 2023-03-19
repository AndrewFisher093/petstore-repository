package pet;

import io.qameta.allure.Feature;
import io.swagger.endpoints.PetEndpoint;
import io.swagger.helpers.PetStatus;
import io.swagger.models.CategoryTagDto;
import io.swagger.models.PetDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Feature("API: Pet endpoint")
public class PetTests extends BasePetstoreTests {

    @Test
    @DisplayName("Verify ability to create new pet")
    @Tag("pets")
    @Tag("regression")
    public void testCreatePet() {
        PetEndpoint petEndpoint = new PetEndpoint();

        PetDto newPet = PetDto.Builder.newBuilder()
            .withId(faker.number().randomDigit())
            .withName(faker.name().name())
            .withCategory(
                new CategoryTagDto().setId(faker.number().randomDigit()).setName(faker.name().title()))
            .withStatus(PetStatus.AVAILABLE.getStatus())
            .build();

        PetDto response = petEndpoint.createPet(newPet);
    }
}