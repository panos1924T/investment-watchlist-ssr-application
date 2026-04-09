package pants.pro.investment_watchlist.mapper;

import org.springframework.stereotype.Component;
import pants.pro.investment_watchlist.dto.AnalystInsertDTO;
import pants.pro.investment_watchlist.dto.AnalystReadOnlyDTO;
import pants.pro.investment_watchlist.model.Analyst;

@Component
public class Mapper {

    public Analyst toAnalystEntity(AnalystInsertDTO dto) {
        return new Analyst(null, null, dto.firstname(), dto.lastname(), dto.email(), null);
    }

    public AnalystReadOnlyDTO toReadOnlyDTO(Analyst analyst) {
        return new AnalystReadOnlyDTO(
                analyst.getUuid().toString(),
                analyst.getFirstname(),
                analyst.getLastname(),
                analyst.getEmail(),
                analyst.getFirm() != null ? analyst.getFirm().getName() : null
        );
    }
}
