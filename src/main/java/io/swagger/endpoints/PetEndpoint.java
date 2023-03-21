package io.swagger.endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.swagger.helpers.HttpStatus;
import io.swagger.models.PetDto;
import io.swagger.restclient.RestAssuredConfiguration;
import java.util.HashMap;
import java.util.List;

/**
 * The PetEndpoint describes manipulation with Pet entity.
 */
public class PetEndpoint extends AuthorizationEndpoint {

    private static final String PET_ENDPOINT = "/pet";
    private static final String PET_ID = "/{petId}";
    private static final String FIND_BY_STATUS = "/findByStatus";
    private final RequestSpecification requestSpecification;

    public PetEndpoint() {
        String token = login()
            .authorizeWithQueryParams()
            .retrieveBearerToken();

        RestAssuredConfiguration.baseUriVersioned();

        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + token);

        this.requestSpecification = buildRequestSpecification(ContentType.JSON, headers);
    }

    /**
     * Create pet.
     *
     * @param pet the pet
     * @return the pet dto
     */
    public PetDto createPet(PetDto pet) {
        return extractObjectAsDto(createPet(pet, HttpStatus.OK), PetDto.class);
    }

    /**
     * Create pet.
     *
     * @param pet the pet
     * @param status the status
     * @return the validatable response
     */
    public ValidatableResponse createPet(PetDto pet, HttpStatus status) {
        return post(this.requestSpecification, PET_ENDPOINT, pet).statusCode(status.getCode());
    }

    /**
     * Get pet by id.
     *
     * @param petId the pet id
     * @return the pet by id
     */
    public PetDto getPetById(Long petId) {
        return extractObjectAsDto(getPetById(petId, HttpStatus.OK), PetDto.class);
    }

    /**
     * Get pet by id.
     *
     * @param petId the pet id
     * @param status the status
     * @return the pet by id
     */
    public ValidatableResponse getPetById(Long petId, HttpStatus status) {
        return get(this.requestSpecification, PET_ENDPOINT + PET_ID, petId).statusCode(status.getCode());
    }

    /**
     * Get pets by status.
     *
     * @param petStatus the pet status
     * @return the pet by status
     */
    public List<PetDto> getPetByStatus(String petStatus) {
        return extractObjectsAsDtoList(getPetByStatus(petStatus, HttpStatus.OK), PetDto.class);
    }

    /**
     * Get pet by status.
     *
     * @param petStatus the pet status
     * @param status the status
     * @return the pet by status
     */
    public ValidatableResponse getPetByStatus(String petStatus, HttpStatus status) {
        this.requestSpecification.queryParam("status", petStatus);

        return get(this.requestSpecification, PET_ENDPOINT + FIND_BY_STATUS).statusCode(status.getCode());
    }

    /**
     * Delete pet by id.
     *
     * @param petId the pet id
     * @param status the status
     * @return the validatable response
     */
    public ValidatableResponse deletePetById(Long petId, HttpStatus status) {
        return deletePetById(petId).statusCode(status.getCode());
    }

    /**
     * Delete pet by id.
     *
     * @param petId the pet id
     * @return the validatable response
     */
    public ValidatableResponse deletePetById(Long petId) {
        return delete(this.requestSpecification, PET_ENDPOINT + PET_ID, petId);
    }

    /**
     * Update pet.
     *
     * @param pet the pet
     * @return the pet dto
     */
    public PetDto updatePet(PetDto pet) {
        return extractObjectAsDto(updatePet(pet, HttpStatus.OK), PetDto.class);
    }

    /**
     * Update pet.
     *
     * @param pet the pet
     * @param status the status
     * @return the validatable response
     */
    public ValidatableResponse updatePet(PetDto pet, HttpStatus status) {
        return put(this.requestSpecification, PET_ENDPOINT, pet).statusCode(status.getCode());
    }
}