package pants.pro.analyst_registry.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Input payload used to create a new analyst record.
 */
public record AnalystInsertDTO(
        @NotNull(message = "{NotNull.analyst.firstname}")
        @Size(min = 2, max = 50, message = "{Size.analyst.firstname}")
        String firstname,

        @NotNull(message = "{NotNull.analystInsertDTO.lastname}")
        @Size(min = 2, max = 50, message = "{Size.analyst.lastname}")
        String lastname,

        @NotBlank(message = "{NotBlank.analyst.email}")
        @Email(message = "{Email.analyst.email}")
        String email,

        @NotNull(message = "{NotNull.analyst.firmId}")
        Long firmId
) {

    /**
     * Returns an empty DTO used to initialize the create form.
     * @return empty analyst insert DTO.
     */
    public static AnalystInsertDTO empty() {
        return new AnalystInsertDTO("", "", "", 0L);
    }
}
