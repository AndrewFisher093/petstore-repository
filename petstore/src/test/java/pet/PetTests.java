package pet;

import static org.assertj.core.api.Assertions.assertThat;

import io.qameta.allure.Feature;
import io.swagger.endpoints.PetEndpoint;
import io.swagger.helpers.PetStatus;
import io.swagger.models.CategoryTagDto;
import io.swagger.models.PetDto;
import java.util.Collections;
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

        var newPet = PetDto.Builder.newBuilder()
            .withId(faker.number().randomDigit())
            .withName(faker.name().name())
            .withCategory(
                new CategoryTagDto().setId(faker.number().randomDigit()).setName(faker.name().title()))
            .withTags(Collections.singletonList(
                new CategoryTagDto().setId(faker.number().randomDigit()).setName(faker.name().username())))
            .withPhotoUrls(Collections.singletonList(faker.witcher().quote()))
            .withStatus(PetStatus.AVAILABLE.getStatus())
            .build();

        var response = petEndpoint.createPet(newPet);

        assertThat(response)
            .isEqualTo(newPet);
    }
}