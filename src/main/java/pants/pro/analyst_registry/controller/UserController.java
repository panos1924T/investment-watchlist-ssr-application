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
public class UserController {

    private final IUserService userService;
    private final IRoleService roleService;

    @GetMapping("/register")
    public String getUserForm(Model model) {
        model.addAttribute("userInsertDTO", UserInsertDTO.empty());
        return "user-form";
    }

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

    @GetMapping("success")
    public String success(Model model) {
        return "user-success";
    }

    @ModelAttribute("roles")
    public List<RoleReadOnlyDTO> roles() {
        return roleService.findAllRolesSortedByName();
    }
}
