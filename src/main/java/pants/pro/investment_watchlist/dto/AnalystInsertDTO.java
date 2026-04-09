package pants.pro.investment_watchlist.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AnalystInsertDTO(
        @NotBlank(message = "First name must not be blank!")
        @Size(min = 2, max = 50, message = "First name's length must be between 2 and 50 characters.")
        String firstname,

        @NotBlank(message = "Last name must not be blank!")
        @Size(min = 2, max = 50, message = "Last name's length must be between 2 and 50 characters.")
        String lastname,

        @NotBlank(message = "Email must not be blank!")
        @Email(message = "Email must be a valid email address!")
        String email,

        @NotNull(message = "Firm must not be null!")
        Long firmId
) {

    public static AnalystInsertDTO empty() {
        return new AnalystInsertDTO("", "", "", 0L);
    }
}
