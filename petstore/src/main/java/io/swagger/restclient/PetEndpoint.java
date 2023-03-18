package io.swagger.restclient;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.swagger.helpers.HttpStatus;
import io.swagger.models.PetDto;

/**
 * The PetEndpoint describes manipulation with Pet entity.
 */
public class PetEndpoint extends AbstractWebEndpoint {

    private static final String PET_ENDPOINT = "/pet";
    private static final String PET_ID = "/{petId}";

    /**
     * Create pet.
     *
     * @param pet the pet
     * @return the pet dto
     */
    public PetDto createPet(PetDto pet) {
        return extractObjectAsDto(createPet(pet, HttpStatus.CREATED), PetDto.class);
    }

    /**
     * Create pet.
     *
     * @param pet the pet
     * @param status the status
     * @return the validatable response
     */
    public ValidatableResponse createPet(PetDto pet, HttpStatus status) {
        RequestSpecification requestSpecification = new RequestSpecBuilder().build();

        return post(requestSpecification, PET_ENDPOINT, pet).statusCode(status.getCode());
    }
}