package pants.pro.investment_watchlist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pants.pro.investment_watchlist.dto.AssetInsertDTO;
import pants.pro.investment_watchlist.dto.AssetTypeReadOnlyDTO;

import java.util.List;

@Controller
@RequestMapping ("/assets")
public class AssetController {

    public String getAssetForm(Model model) {
        model.addAttribute("assetInsertDTO", AssetInsertDTO.empty());

        return "asset-insert";
    }

    public List<AssetTypeReadOnlyDTO> assetTypes() {
        return List.of(
                new AssetTypeReadOnlyDTO(1L, "Stock"),
                new AssetTypeReadOnlyDTO(1L, "ETF"),
                new AssetTypeReadOnlyDTO(1L, "Bond"),
                new AssetTypeReadOnlyDTO(1L, "Commodity"),
                new AssetTypeReadOnlyDTO(1L, "Crypto")
        );

    }
}
