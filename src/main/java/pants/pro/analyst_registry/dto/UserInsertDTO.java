package pants.pro.analyst_registry.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Input payload used to register a new application user.
 */
public record UserInsertDTO(

        @NotNull
        @Size(min = 2, max = 20)
        String username,

        @NotNull
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])^.{8,}$")
        String password,

        @NotNull
        Long roleId
) {

    /**
     * Returns an empty DTO used to initialize the registration form.
     * @return empty user insert DTO.
     */
    public static UserInsertDTO empty() {
        return new UserInsertDTO("", "", 0L);
    }
}
