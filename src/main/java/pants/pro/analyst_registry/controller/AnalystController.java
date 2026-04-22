package pants.pro.analyst_registry.controller;

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
import pants.pro.analyst_registry.core.exceptions.EntityAlreadyExistsException;
import pants.pro.analyst_registry.core.exceptions.EntityInvalidArgumentException;
import pants.pro.analyst_registry.core.exceptions.EntityNotFoundException;
import pants.pro.analyst_registry.dto.AnalystEditDTO;
import pants.pro.analyst_registry.dto.AnalystInsertDTO;
import pants.pro.analyst_registry.dto.AnalystReadOnlyDTO;
import pants.pro.analyst_registry.dto.FirmReadOnlyDTO;
import pants.pro.analyst_registry.service.IAnalystService;
import pants.pro.analyst_registry.service.IFirmService;
import pants.pro.analyst_registry.validator.AnalystEditValidator;
import pants.pro.analyst_registry.validator.AnalystInsertValidator;

import java.util.List;
import java.util.UUID;


@Controller
@RequiredArgsConstructor
@RequestMapping ("/analysts")
/**
 * MVC controller for analyst create, list, edit, and delete workflows.
 */
public class AnalystController {

    private final IAnalystService analystService;
    private final IFirmService firmService;
    private final AnalystInsertValidator analystInsertValidator;
    private final AnalystEditValidator analystEditValidator;

    /**
     * Shows the analyst creation form.
     * @param model MVC model used by the view.
     * @return analyst insert page.
     */
    @GetMapping("/insert")
    public String getAnalystForm(Model model) {
        model.addAttribute("analystInsertDTO", AnalystInsertDTO.empty());

        return "analyst-insert";
    }

    /**
     * Creates a new analyst and redirects to the success page.
     * @param analystInsertDTO submitted analyst form data.
     * @param bindingResult validation result for submitted data.
     * @param model MVC model used to show errors.
     * @param redirectAttributes flash attributes for redirect messages.
     * @return redirect to success page or the insert page on error.
     */
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

    /**
     * Shows the paginated analyst list.
     * @param pageable pagination and sorting settings.
     * @param model MVC model used by the list page.
     * @return analysts list page.
     */
    @GetMapping({"", "/"})
    public String getPaginatedAnalysts(@PageableDefault(size = 5, sort = "lastname") Pageable pageable,
                                       Model model) {

        Page<AnalystReadOnlyDTO> analystsPage = analystService.getPaginatedAnalystsDeletedFalse(pageable);

        model.addAttribute("analysts", analystsPage.getContent());
        model.addAttribute("page", analystsPage);

        return "analysts";
    }

    /**
     * Loads analyst data and shows the edit form.
     * @param uuid analyst identifier from the route.
     * @param model MVC model used by the view.
     * @return analyst edit page.
     */
    @GetMapping("/edit/{uuid}")
    public String getAnalystEdit(@PathVariable UUID uuid, Model model) {
        try {
//            AnalystEditDTO analystEditDTO = analystService.getAnalystByUuid(uuid);
            AnalystEditDTO analystEditDTO = analystService.getAnalystByUuidDeletedFalse(uuid);
            model.addAttribute("analystEditDTO", analystEditDTO);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        return "analyst-edit";
    }

    /**
     * Updates an existing analyst and redirects to success page.
     * @param analystEditDTO submitted analyst update data.
     * @param bindingResult validation result for submitted data.
     * @param model MVC model used to show errors.
     * @param redirectAttributes flash attributes for redirect messages.
     * @return redirect to update success page or the edit page on error.
     */
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

    /**
     * Shows the analyst update success page.
     * @return update success page.
     */
    @GetMapping("/update-success")
    public String analystUpdateSuccess() {
        return "update-analyst-success";
    }

    /**
     * Shows the analyst creation success page.
     * @param model MVC model used by the view.
     * @return create success page.
     */
    @GetMapping("/success")
    public String analystSuccess(Model model) {
        return "analyst-success";
    }

    /**
     * Soft deletes an analyst and redirects to success page.
     * @param uuid analyst identifier from the route.
     * @param model MVC model used to show errors.
     * @param redirectAttributes flash attributes for redirect messages.
     * @return redirect to delete success page or back to list on error.
     */
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

    /**
     * Shows the analyst deletion success page.
     * @return delete success page.
     */
    @GetMapping("/delete-success")
    public String deleteSuccess() {
        return "delete-analyst-success";
    }

    /**
     * Provides firms for analyst form dropdowns.
     * @return list of firms sorted by name.
     */
    @ModelAttribute("firmReadOnlyDTO")
    public List<FirmReadOnlyDTO> firms() {
        return firmService.findAllFirmsSortedByName();
    }
}
