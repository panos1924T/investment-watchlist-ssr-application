package pants.pro.investment_watchlist.mapper;

import org.springframework.stereotype.Component;
import pants.pro.investment_watchlist.dto.AnalystInsertDTO;
import pants.pro.investment_watchlist.dto.AnalystReadOnlyDTO;
import pants.pro.investment_watchlist.model.Analyst;

@Component
public class Mapper {

    public Analyst toAssetEntity(AnalystInsertDTO dto) {
        return new Analyst(null, null, dto.firstname(), dto.lastname(), dto.email());
    }

    public AnalystReadOnlyDTO toReadOnlyDTO(Analyst analyst) {
        return new AnalystReadOnlyDTO(
                analyst.getUuid().toString(),
                analyst.getEmail()
        );
    }
}
