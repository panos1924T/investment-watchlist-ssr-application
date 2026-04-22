package pants.pro.analyst_registry.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pants.pro.analyst_registry.core.exceptions.EntityAlreadyExistsException;
import pants.pro.analyst_registry.core.exceptions.EntityInvalidArgumentException;
import pants.pro.analyst_registry.dto.RoleReadOnlyDTO;
import pants.pro.analyst_registry.dto.UserInsertDTO;
import pants.pro.analyst_registry.dto.UserReadOnlyDTO;
import pants.pro.analyst_registry.service.IRoleService;
import pants.pro.analyst_registry.service.IUserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
/**
 * MVC controller for user registration and success flow.
 */
public class UserController {

    private final IUserService userService;
    private final IRoleService roleService;

    /**
     * Shows the user registration form.
     * @param model MVC model used by the view.
     * @return user registration page.
     */
    @GetMapping("/register")
    public String getUserForm(Model model) {
        model.addAttribute("userInsertDTO", UserInsertDTO.empty());
        return "user-form";
    }

    /**
     * Registers a new user and redirects to success page.
     * @param userInsertDTO submitted user form data.
     * @param bindingResult validation result for submitted data.
     * @param redirectAttributes flash attributes for redirect messages.
     * @param model MVC model used to show errors.
     * @return redirect to success page or the registration page on error.
     */
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userInsertDTO") UserInsertDTO userInsertDTO,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               Model model) {

        // user validator TODO

        if (bindingResult.hasErrors()) {
            return "user-form";
        }

        try {

            UserReadOnlyDTO userReadOnlyDTO = userService.saveUser(userInsertDTO);
            redirectAttributes.addFlashAttribute("userReadOnlyDTO", userReadOnlyDTO);
            return "redirect:/users/success";


        } catch (EntityAlreadyExistsException | EntityInvalidArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user-form";
        }

    }

    /**
     * Shows the user registration success page.
     * @param model MVC model used by the view.
     * @return user success page.
     */
    @GetMapping("success")
    public String success(Model model) {
        return "user-success";
    }

    /**
     * Provides roles for the registration form dropdown.
     * @return list of roles sorted by name.
     */
    @ModelAttribute("roles")
    public List<RoleReadOnlyDTO> roles() {
        return roleService.findAllRolesSortedByName();
    }
}
