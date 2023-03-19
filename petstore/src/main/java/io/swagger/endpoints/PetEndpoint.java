package io.swagger.endpoints;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.swagger.helpers.HttpStatus;
import io.swagger.models.PetDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The PetEndpoint describes manipulation with Pet entity.
 */
public class PetEndpoint extends AbstractWebEndpoint {

    private static final String PET_ENDPOINT = "/pet";
    private static final String PET_ID = "/{petId}";
    private static final String FIND_BY_STATUS = "/findByStatus";

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
        RequestSpecification requestSpecification = buildRequestSpecification(ContentType.JSON, new HashMap<>());

        return post(requestSpecification, PET_ENDPOINT, pet).statusCode(status.getCode());
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
        var requestSpecification = buildRequestSpecification(ContentType.JSON, new HashMap<>());

        return get(requestSpecification, PET_ENDPOINT + PET_ID, petId).statusCode(status.getCode());
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
        var requestSpecification = buildRequestSpecification(ContentType.JSON, new HashMap<>());
        requestSpecification.queryParam("status", petStatus);

        return get(requestSpecification, PET_ENDPOINT + FIND_BY_STATUS).statusCode(status.getCode());
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
        var requestSpecification = buildRequestSpecification(ContentType.JSON, new HashMap<>());

        return delete(requestSpecification, PET_ENDPOINT + PET_ID, petId);
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
        RequestSpecification requestSpecification = buildRequestSpecification(ContentType.JSON, new HashMap<>());

        return put(requestSpecification, PET_ENDPOINT, pet).statusCode(status.getCode());
    }

    private RequestSpecification buildRequestSpecification(ContentType contentType, Map<String, String> headers) {
        return new RequestSpecBuilder()
            .log(LogDetail.ALL)
            .setContentType(contentType)
            .addFilter(new AllureRestAssured())
            .addFilter(new ResponseLoggingFilter())
            .addHeaders(headers)
            .build();
    }
}