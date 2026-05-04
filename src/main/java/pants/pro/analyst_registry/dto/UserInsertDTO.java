package pants.pro.analyst_registry.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Input payload used to register a new application user.
 */
public record UserInsertDTO(

        @NotNull(message = "{NotNull.User.username}")
        @Size(min = 2, max = 20, message = "{Size.User.username}")
        String username,

        @NotNull(message = "{NotNull.User.password}")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])^.{8,}$",
                message = "{Pattern.User.password}")
        String password,

        @NotNull(message = "{NotNull.User.roleId}")
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
