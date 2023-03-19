package pet;

import io.qameta.allure.Feature;
import io.swagger.models.CategoryTagDto;
import io.swagger.models.PetDto;
import io.swagger.endpoints.PetEndpoint;
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
        PetEndpoint petEndpoint = new PetEndpoint();

        PetDto newPet = PetDto.Builder.newBuilder()
            .withId(123)
                .withName("awd")
                    .withCategory(new CategoryTagDto().setId(444).setName("dawdw"))
                        .withStatus("active")
                            .build();

        petEndpoint.createPet(newPet);
    }
}