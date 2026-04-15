package pants.pro.investment_watchlist.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AnalystInsertDTO(
        @NotNull
        @Size(min = 2, max = 50)
        String firstname,

        @NotNull
        @Size(min = 2, max = 50)
        String lastname,

        @NotNull
        @Email
        String email,

        @NotNull
        Long firmId
) {

    public static AnalystInsertDTO empty() {
        return new AnalystInsertDTO("", "", "", 0L);
    }
}
