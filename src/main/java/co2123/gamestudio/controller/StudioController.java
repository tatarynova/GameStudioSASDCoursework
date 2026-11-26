package co2123.gamestudio.controller;

import co2123.gamestudio.GameStudioApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StudioController {

    @RequestMapping("/studios")
    public String showStudios(Model model) {
        model.addAttribute("studios", GameStudioApplication.studioList);
        return "studios";
    }
}

