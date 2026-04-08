package pants.pro.investment_watchlist.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pants.pro.investment_watchlist.dto.AssetInsertDTO;
import pants.pro.investment_watchlist.dto.AssetReadOnlyDTO;
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

    @PostMapping("/insert")
    public String assetInsert(@Valid @ModelAttribute("assetInsertDTO")AssetInsertDTO assetInsertDTO,
                              BindingResult bindingResult, Model model,
                              RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "asset-insert";
        }

        AssetReadOnlyDTO assetReadOnlyDTO = new AssetReadOnlyDTO("abc123", "Apple");
        redirectAttributes.addFlashAttribute("assetReadOnlyDTO", assetReadOnlyDTO);
        return "redirect:/assets/success";
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

    @GetMapping("/success")
    public String assetSuccess(Model model) {
        return "asset-success";
    }
}
