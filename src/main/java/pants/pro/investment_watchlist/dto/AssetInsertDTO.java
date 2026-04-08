package pants.pro.investment_watchlist.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record AssetInsertDTO(
        @NotNull(message = "Ticker must not be null!")
        @Size(min = 2, max = 10, message = "Ticker's length must be between two to ten characters.")
        String ticker,

        @NotNull(message = "Purchase price must not be null!")
        @DecimalMin(value = "1.0", message = "Purchase price must be a positive number!")
        BigDecimal purchasePrice,

        @NotNull(message = "Number of shares must not be null!")
        @DecimalMin(value = "1.0", message = "Number of shares must be a positive number!")
        BigDecimal quantity

//        @NotNull(message = "Asset Type must not be null!")
//        Long assetTypeId
) {

    public static AssetInsertDTO empty() {
        return new AssetInsertDTO("", BigDecimal.ZERO , BigDecimal.ZERO);
    }
}
