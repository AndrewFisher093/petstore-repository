package io.swagger.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryTagDto {

    @JsonProperty(value = "id", access = JsonProperty.Access.READ_WRITE)
    private Long id;
    @JsonProperty(value = "name", access = JsonProperty.Access.READ_WRITE)
    private String name;

    public Long getId() {
        return id;
    }

    public CategoryTagDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CategoryTagDto setName(String name) {
        this.name = name;
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

        CategoryTagDto nameIdDto = (CategoryTagDto) o;

        return new EqualsBuilder().append(id, nameIdDto.id).append(name, nameIdDto.name)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id).append(name).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("name", name)
            .toString();
    }
}