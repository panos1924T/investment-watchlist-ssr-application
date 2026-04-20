package pants.pro.investment_watchlist.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping
public class LoginController {

    @GetMapping("/login")
    public String login(Principal principal) {

        if (principal == null) {
            return "login";
        }

        return "redirect:/analysts";
    }

    @GetMapping("/")
    public String root(Principal principal) {

        if (principal == null) {
            return "index";
        }

        return "redirect:/analysts";
    }
}
