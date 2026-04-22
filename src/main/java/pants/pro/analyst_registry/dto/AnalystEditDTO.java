package pants.pro.analyst_registry.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

/**
 * Input payload used to update an existing analyst record.
 */
public record AnalystEditDTO(

        @NotNull
        UUID uuid,

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
     * Returns an empty DTO used to initialize the edit form.
     * @return empty analyst edit DTO.
     */
    public static AnalystEditDTO empty() {
        return new AnalystEditDTO(null,"", "", "", 0L);
    }
}
