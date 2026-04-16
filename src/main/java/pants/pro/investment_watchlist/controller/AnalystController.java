package pants.pro.investment_watchlist.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pants.pro.investment_watchlist.core.exceptions.EntityAlreadyExistsException;
import pants.pro.investment_watchlist.core.exceptions.EntityInvalidArgumentException;
import pants.pro.investment_watchlist.core.exceptions.EntityNotFoundException;
import pants.pro.investment_watchlist.dto.AnalystEditDTO;
import pants.pro.investment_watchlist.dto.AnalystInsertDTO;
import pants.pro.investment_watchlist.dto.AnalystReadOnlyDTO;
import pants.pro.investment_watchlist.dto.FirmReadOnlyDTO;
import pants.pro.investment_watchlist.service.IAnalystService;
import pants.pro.investment_watchlist.service.IFirmService;
import pants.pro.investment_watchlist.validator.AnalystEditValidator;
import pants.pro.investment_watchlist.validator.AnalystInsertValidator;

import java.util.List;
import java.util.UUID;


@Controller
@RequiredArgsConstructor
@RequestMapping ("/analysts")
public class AnalystController {

    private final IAnalystService analystService;
    private final IFirmService firmService;
    private final AnalystInsertValidator analystInsertValidator;
    private final AnalystEditValidator analystEditValidator;

    @GetMapping("/insert")
    public String getAnalystForm(Model model) {
        model.addAttribute("analystInsertDTO", AnalystInsertDTO.empty());

        return "analyst-insert";
    }

    @PostMapping("/insert")
    public String analystInsert(@Valid @ModelAttribute("analystInsertDTO") AnalystInsertDTO analystInsertDTO,
                              BindingResult bindingResult, Model model,
                              RedirectAttributes redirectAttributes) {

        analystInsertValidator.validate(analystInsertDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            return "analyst-insert";
        }

        try {
            AnalystReadOnlyDTO analystReadOnlyDTO = analystService.saveAnalyst(analystInsertDTO);

            redirectAttributes.addFlashAttribute("analystReadOnlyDTO", analystReadOnlyDTO);
            return "redirect:/analysts/success";

        } catch (EntityAlreadyExistsException | EntityInvalidArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "analyst-insert";
        }
    }

    @GetMapping({"", "/"})
    public String getPaginatedAnalysts(@PageableDefault(size = 5, sort = "lastname") Pageable pageable,
                                       Model model) {

        Page<AnalystReadOnlyDTO> analystsPage = analystService.getPaginatedAnalystsDeletedFalse(pageable);

        model.addAttribute("analysts", analystsPage.getContent());
        model.addAttribute("page", analystsPage);

        return "analysts";
    }

    @GetMapping("/edit/{uuid}")
    public String getAnalystEdit(@PathVariable UUID uuid, Model model) {
        try {
            AnalystEditDTO analystEditDTO = analystService.getAnalystByUuid(uuid);
            model.addAttribute("analystEditDTO", analystEditDTO);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "analyst-edit";
    }

    @PostMapping("/edit")
    public String analystUpdate(@Valid @ModelAttribute("analystEditDTO") AnalystEditDTO analystEditDTO,
                                BindingResult bindingResult, Model model,
                                RedirectAttributes redirectAttributes) {

        analystEditValidator.validate(analystEditDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            return "analyst-edit";
        }

        try {
            AnalystReadOnlyDTO readOnlyDTO = analystService.updateAnalyst(analystEditDTO);
            redirectAttributes.addFlashAttribute("analystReadOnlyDTO", readOnlyDTO);
            return "redirect:/analysts/update-success";

        } catch (EntityNotFoundException | EntityAlreadyExistsException | EntityInvalidArgumentException e) {
            model.addAttribute("error-message", e.getMessage());
            return "analyst-edit";
        }
    }

    @GetMapping("/update-success")
    public String analystUpdateSuccess() {
        return "update-analyst-success";
    }

    @GetMapping("/success")
    public String analystSuccess(Model model) {
        return "analyst-success";
    }

    @PostMapping("/delete/{uuid}")
    public String deleteAnalyst(@PathVariable UUID uuid, Model model, RedirectAttributes redirectAttributes) {

        try {

            AnalystReadOnlyDTO readOnlyDTO = analystService.deleteAnalystByUuid(uuid);
            redirectAttributes.addFlashAttribute("analystReadOnlyDTO", readOnlyDTO);
            return "redirect:/analysts/delete-success";

        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "analysts";
        }
    }

    @ModelAttribute("firmReadOnlyDTO")
    public List<FirmReadOnlyDTO> firms() {
        return firmService.findAllFirmsSortedByName();
    }
}
