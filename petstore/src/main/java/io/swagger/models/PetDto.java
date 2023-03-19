package io.swagger.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetDto {

    @JsonProperty(value = "id", access = JsonProperty.Access.READ_WRITE)
    private Integer id;
    @JsonProperty(value = "category", access = JsonProperty.Access.READ_WRITE)
    private CategoryTagDto category;
    @JsonProperty(value = "name", access = JsonProperty.Access.READ_WRITE)
    private String name;
    @JsonProperty(value = "photoUrls", access = JsonProperty.Access.READ_WRITE)
    private List<String> photoUrls;
    @JsonProperty(value = "tags", access = JsonProperty.Access.READ_WRITE)
    private List<CategoryTagDto> tags;
    @JsonProperty(value = "status", access = JsonProperty.Access.READ_WRITE)
    private String status;

    public Integer getId() {
        return id;
    }

    public PetDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public CategoryTagDto getCategory() {
        return category;
    }

    public PetDto setCategory(CategoryTagDto category) {
        this.category = category;
        return this;
    }

    public String getName() {
        return name;
    }

    public PetDto setName(String name) {
        this.name = name;
        return this;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public PetDto setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
        return this;
    }

    public List<CategoryTagDto> getTags() {
        return tags;
    }

    public PetDto setTags(List<CategoryTagDto> tags) {
        this.tags = tags;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public PetDto setStatus(String status) {
        this.status = status;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PetDto petDto = (PetDto) o;

        return new EqualsBuilder().append(id, petDto.id).append(category, petDto.category)
            .append(name, petDto.name).append(photoUrls, petDto.photoUrls).append(tags, petDto.tags)
            .append(status, petDto.status).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).append(category)
            .append(name).append(photoUrls).append(tags).append(status).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("category", category)
            .append("name", name)
            .append("photoUrls", photoUrls)
            .append("tags", tags)
            .append("status", status)
            .toString();
    }

    public static final class Builder {

        private Integer id;
        private CategoryTagDto category;
        private String name;
        private List<String> photoUrls;
        private List<CategoryTagDto> tags;
        private String status;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withCategory(CategoryTagDto category) {
            this.category = category;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withPhotoUrls(List<String> photoUrls) {
            this.photoUrls = photoUrls;
            return this;
        }

        public Builder withTags(List<CategoryTagDto> tags) {
            this.tags = tags;
            return this;
        }

        public Builder withStatus(String status) {
            this.status = status;
            return this;
        }

        public PetDto build() {
            PetDto petDto = new PetDto();
            petDto.setId(id);
            petDto.setCategory(category);
            petDto.setName(name);
            petDto.setPhotoUrls(photoUrls);
            petDto.setTags(tags);
            petDto.setStatus(status);
            return petDto;
        }
    }
}