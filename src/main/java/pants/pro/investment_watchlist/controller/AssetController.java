package pants.pro.investment_watchlist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping ("/assets")
public class AssetController {

    public String getAssetForm(Model model) {
        return "";
    }
}
