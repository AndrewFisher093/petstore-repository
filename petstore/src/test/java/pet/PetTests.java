package pet;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;
import io.qameta.allure.Feature;
import io.swagger.generators.PetGenerator;
import io.swagger.helpers.HttpStatus;
import io.swagger.helpers.PetStatus;
import io.swagger.models.PetDto;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

@Feature("API: Pet endpoint")
@DisplayName("Pet endpoint")
public class PetTests {

    private PetGenerator petGenerator;

    @BeforeEach
    public void initHelpers() {
        petGenerator = new PetGenerator();
    }

    @AfterEach
    public void cleanupTestData() {
        petGenerator.getEndpointExecutor().cleanPets();
    }

    @Test
    @DisplayName("Verify ability to create a new pet")
    @Tags(value = {@Tag("pets"), @Tag("regression")})
    public void testCreatePet() {
        var savedPet = petGenerator
            .getPetBuilder()
            .buildDefaultPet()
            .getEndpointExecutor()
            .createPet();

        assertThat(savedPet)
            .isEqualTo(petGenerator.getPetDto());
    }

    @Test
    @DisplayName("Verify ability to retrieve pet")
    @Tags(value = {@Tag("pets"), @Tag("regression")})
    public void testRetrievePet() {
        var savedPet = petGenerator
            .getPetBuilder()
            .buildDefaultPet()
            .getEndpointExecutor()
            .createPet();

        var retrievedPet = petGenerator
            .getEndpointExecutor()
            .getPetById(savedPet.getId());

        assertThat(savedPet)
            .isEqualTo(retrievedPet);
    }

    @Test
    @DisplayName("Verify ability to retrieve pets by status")
    @Tags(value = {@Tag("pets"), @Tag("regression")})
    public void testRetrievePetsByStatus() {
        var savedPet = petGenerator
            .getPetBuilder()
            .buildDefaultPet()
            .getEndpointExecutor()
            .createPet();

        var listOfPetsWithAvailableStatus = petGenerator
            .getEndpointExecutor()
            .getPetByStatus(savedPet.getStatus());

        assertThat(listOfPetsWithAvailableStatus
            .stream()
            .map(PetDto::getStatus)
            .collect(Collectors.toList()))
            .containsOnly(PetStatus.AVAILABLE.getStatus());
    }

    @Test
    @DisplayName("Verify ability to delete pet")
    @Tags(value = {@Tag("pets"), @Tag("regression")})
    public void testDeletePet() {
        var savedPet = petGenerator
            .getPetBuilder()
            .buildDefaultPet()
            .getEndpointExecutor()
            .createPet();

        petGenerator
            .getEndpointExecutor()
            .deletePetById(savedPet.getId(), HttpStatus.OK);

        petGenerator
            .getEndpointExecutor()
            .deletePetById(savedPet.getId(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Verify ability to update pet")
    @Tags(value = {@Tag("pets"), @Tag("regression")})
    public void testUpdatePet() {
        var savedPet = petGenerator
            .getPetBuilder()
            .buildDefaultPet()
            .getEndpointExecutor()
            .createPet();

        var updatedPet = PetDto.Builder.newBuilder()
            .withId(savedPet.getId())
            .withName(new Faker().name().nameWithMiddle())
            .withTags(savedPet.getTags())
            .withCategory(savedPet.getCategory())
            .withPhotoUrls(savedPet.getPhotoUrls())
            .withStatus(PetStatus.SOLD.getStatus())
            .build();

        petGenerator
            .getEndpointExecutor()
            .updatePet(updatedPet);

        assertThat(updatedPet)
            .isNotEqualTo(savedPet);
    }
}