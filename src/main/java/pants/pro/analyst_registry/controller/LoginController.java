package pants.pro.analyst_registry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping
/**
 * MVC controller serving login and root routes with authentication-aware redirects.
 */
public class LoginController {

    /**
     * Shows login page for anonymous users.
     * @param principal current authenticated principal, if any.
     * @return login page or redirect to analysts page.
     */
    @GetMapping("/login")
    public String login(Principal principal) {

        if (principal == null) {
            return "login";
        }

        return "redirect:/analysts";
    }

    /**
     * Handles root route based on authentication status.
     * @param principal current authenticated principal, if any.
     * @return home page for guests or redirect to analysts page.
     */
    @GetMapping("/")
    public String root(Principal principal) {

        if (principal == null) {
            return "index";
        }

        return "redirect:/analysts";
    }
}
