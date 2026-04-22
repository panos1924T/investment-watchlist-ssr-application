package pants.pro.analyst_registry.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Input payload used to create a new analyst record.
 */
public record AnalystInsertDTO(
        @NotNull
        @Size(min = 2, max = 50)
        String firstname,

        @NotNull
        @Size(min = 2, max = 50)
        String lastname,

        @NotBlank
        @Email
        String email,

        @NotNull
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
