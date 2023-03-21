package io.swagger.generators;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import io.swagger.endpoints.PetEndpoint;
import io.swagger.helpers.History;
import io.swagger.helpers.HttpStatus;
import io.swagger.helpers.PetStatus;
import io.swagger.models.CategoryTagDto;
import io.swagger.models.PetDto;
import java.util.Collections;
import java.util.List;

/**
 * The PetGenerator is the class that helps to generate all needed test data and execute API requests.
 */
public class PetGenerator {

    private final EndpointExecutor endpointExecutor;
    private final PetBuilder petBuilder;
    private PetDto petDto;

    public PetGenerator() {
        this.endpointExecutor = new EndpointExecutor(this);
        this.petBuilder = new PetBuilder(this);
    }

    public PetDto getPetDto() {
        return petDto;
    }

    public PetBuilder getPetBuilder() {
        return this.petBuilder;
    }

    public EndpointExecutor getEndpointExecutor() {
        return this.endpointExecutor;
    }

    /**
     * The PetBuilder class helps to build PetDto.
     */
    public static class PetBuilder {

        private final Faker faker;
        private final PetGenerator petGenerator;

        public PetBuilder(PetGenerator petGenerator) {
            this.petGenerator = petGenerator;
            this.faker = new Faker();
        }

        /**
         * Build default pet pet object.
         *
         * @return the pet builder
         */
        public PetGenerator buildDefaultPet() {
            this.petGenerator.petDto = PetDto.Builder.newBuilder()
                .withId(faker.number().randomNumber())
                .withName(faker.name().name())
                .withCategory(
                    new CategoryTagDto().setId(faker.number().randomNumber()).setName(faker.name().title()))
                .withTags(Collections.singletonList(
                    new CategoryTagDto().setId(faker.number().randomNumber()).setName(faker.name().username())))
                .withPhotoUrls(Collections.singletonList(faker.witcher().quote()))
                .withStatus(PetStatus.AVAILABLE.getStatus())
                .build();

            return petGenerator;
        }
    }

    /**
     * The EndpointExecutor class helps to call API requests for Pet Endpoint.
     */
    public static class EndpointExecutor {

        private final PetEndpoint petEndpoint;
        private final History<Long> petsHistory;
        private final PetGenerator petGenerator;

        public EndpointExecutor(PetGenerator petGenerator) {
            this.petGenerator = petGenerator;
            this.petsHistory = new History<>();
            this.petEndpoint = new PetEndpoint();
        }

        /**
         * Call post API request method.
         *
         * @return the pet dto
         */
        @Step("Execute creation of the Pet")
        public PetDto createPet() {
            var createdPet = this.petEndpoint.createPet(this.petGenerator.getPetDto());
            this.petsHistory.add(createdPet.getId());
            return createdPet;
        }

        /**
         * Call get API request method.
         *
         * @param petId the pet id
         * @return the pet by id
         */
        @Step("Execute retrieval of the Pet by id")
        public PetDto getPetById(Long petId) {
            return this.petEndpoint.getPetById(petId);
        }

        /**
         * Call delete API request method.
         *
         * @param petId the pet id
         * @param status the status
         * @return the validatable response
         */
        @Step("Execute deletion of the Pet by id")
        public ValidatableResponse deletePetById(Long petId, HttpStatus status) {
            return this.petEndpoint.deletePetById(petId, status);
        }

        /**
         * Call get API request method.
         *
         * @param petStatus the pet status
         * @return the pets by status
         */
        @Step("Execute retrieval of the Pet by status")
        public List<PetDto> getPetByStatus(String petStatus) {
            return this.petEndpoint.getPetByStatus(petStatus);
        }

        /**
         * Call put API request method.
         *
         * @param petDto the pet dto
         * @return the pet dto
         */
        @Step("Execute update of the Pet")
        public PetDto updatePet(PetDto petDto) {
            return this.petEndpoint.updatePet(petDto);
        }

        /**
         * Clean all pets if after tests.
         */
        public void cleanPets() {
            this.petsHistory.clear(this.petEndpoint::deletePetById);
        }
    }
}