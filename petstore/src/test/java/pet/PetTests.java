package pet;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;
import io.qameta.allure.Feature;
import io.swagger.generators.PetGenerator;
import io.swagger.helpers.HttpStatus;
import io.swagger.helpers.PetStatus;
import io.swagger.models.PetDto;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;

@Feature("API: Pet endpoint")
public class PetTests extends BasePetstoreTests {

    @Test
    @DisplayName("Verify ability to create a new pet")
    @Tags(value = {@Tag("pets"), @Tag("regression")})
    public void testCreatePet() {
        var petGenerator = new PetGenerator();

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
        var petGenerator = new PetGenerator();

        var savedPet = petGenerator
            .getPetBuilder()
            .buildDefaultPet()
            .getEndpointExecutor()
            .createPet();

        var retrievedPet = petGenerator
            .getPetBuilder()
            .getEndpointExecutor()
            .getPetById(savedPet.getId());

        assertThat(savedPet)
            .isEqualTo(retrievedPet);
    }

    @Test
    @DisplayName("Verify ability to retrieve pets by status")
    @Tags(value = {@Tag("pets"), @Tag("regression")})
    public void testRetrievePetsByStatus() {
        var petGenerator = new PetGenerator();

        var savedPet = petGenerator
            .getPetBuilder()
            .buildDefaultPet()
            .getEndpointExecutor()
            .createPet();

        var listOfPetsWithAvailableStatus = petGenerator
            .getPetBuilder()
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
        var petGenerator = new PetGenerator();

        var savedPet = petGenerator
            .getPetBuilder()
            .buildDefaultPet()
            .getEndpointExecutor()
            .createPet();

        petGenerator
            .getPetBuilder()
            .getEndpointExecutor()
            .deletePetById(savedPet.getId(), HttpStatus.OK);

        petGenerator
            .getPetBuilder()
            .getEndpointExecutor()
            .deletePetById(savedPet.getId(), HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Verify ability to update pet")
    @Tags(value = {@Tag("pets"), @Tag("regression")})
    public void testUpdatePet() {
        var petGenerator = new PetGenerator();

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
            .getPetBuilder()
            .getEndpointExecutor()
            .updatePet(updatedPet);

        assertThat(updatedPet)
            .isNotEqualTo(savedPet);
    }
}