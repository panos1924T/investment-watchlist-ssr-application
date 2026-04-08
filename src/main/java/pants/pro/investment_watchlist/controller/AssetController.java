package pants.pro.investment_watchlist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pants.pro.investment_watchlist.dto.AssetInsertDTO;
import pants.pro.investment_watchlist.dto.AssetTypeReadOnlyDTO;

import java.util.List;

@Controller
@RequestMapping ("/assets")
public class AssetController {

    @GetMapping("/insert")
    public String getAssetForm(Model model) {
        model.addAttribute("assetInsertDTO", AssetInsertDTO.empty());

        return "asset-insert";
    }

    @ModelAttribute("assetTypeReadOnlyDTO")
    public List<AssetTypeReadOnlyDTO> assetTypes() {
        return List.of(
                new AssetTypeReadOnlyDTO(1L, "Stock"),
                new AssetTypeReadOnlyDTO(2L, "ETF"),
                new AssetTypeReadOnlyDTO(3L, "Bond"),
                new AssetTypeReadOnlyDTO(4L, "Commodity"),
                new AssetTypeReadOnlyDTO(5L, "Crypto")
        );

    }
}
