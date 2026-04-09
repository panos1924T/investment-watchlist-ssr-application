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
import pants.pro.investment_watchlist.dto.AnalystInsertDTO;
import pants.pro.investment_watchlist.dto.AnalystReadOnlyDTO;
import pants.pro.investment_watchlist.dto.FirmReadOnlyDTO;

import java.util.List;

@Controller
@RequestMapping ("/assets")
public class AnalystController {

    @GetMapping("/insert")
    public String getAssetForm(Model model) {
        model.addAttribute("assetInsertDTO", AnalystInsertDTO.empty());

        return "analyst-insert";
    }

    @PostMapping("/insert")
    public String assetInsert(@Valid @ModelAttribute("assetInsertDTO") AnalystInsertDTO analystInsertDTO,
                              BindingResult bindingResult, Model model,
                              RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "analyst-insert";
        }

        AnalystReadOnlyDTO analystReadOnlyDTO = new AnalystReadOnlyDTO("abc123", "Panos", "Tsitsikas", "abc@gmail.gr", "JP Morgan");
        redirectAttributes.addFlashAttribute("assetReadOnlyDTO", analystReadOnlyDTO);
        return "redirect:/assets/success";
    }

    @ModelAttribute("assetTypeReadOnlyDTO")
    public List<FirmReadOnlyDTO> assetTypes() {
        return List.of(
                new FirmReadOnlyDTO(1L, "Stock"),
                new FirmReadOnlyDTO(2L, "ETF"),
                new FirmReadOnlyDTO(3L, "Bond"),
                new FirmReadOnlyDTO(4L, "Commodity"),
                new FirmReadOnlyDTO(5L, "Crypto")
        );
    }

    @GetMapping("/success")
    public String assetSuccess(Model model) {
        return "analyst-success";
    }
}
