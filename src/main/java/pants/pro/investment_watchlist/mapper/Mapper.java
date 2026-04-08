package pants.pro.investment_watchlist.mapper;

import org.springframework.stereotype.Component;
import pants.pro.investment_watchlist.dto.AssetInsertDTO;
import pants.pro.investment_watchlist.dto.AssetReadOnlyDTO;
import pants.pro.investment_watchlist.model.Asset;

@Component
public class Mapper {

    public Asset toAssetEntity(AssetInsertDTO dto) {
        return new Asset(null, null, dto.ticker(), dto.purchasePrice(), dto.quantity());
    }

    public AssetReadOnlyDTO toReadOnlyDTO(Asset asset) {
        return new AssetReadOnlyDTO(
                asset.getUuid().toString(),
                asset.getTicker()
        );
    }
}
