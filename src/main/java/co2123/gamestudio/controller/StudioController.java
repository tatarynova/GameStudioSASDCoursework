package co2123.gamestudio.controller;

import co2123.gamestudio.GameStudioApplication;
import co2123.gamestudio.repository.StudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class StudioController {
    @Autowired
    private StudioRepository studioRepo;

    @RequestMapping("/studios")
    public String showStudios(Model model) {
        model.addAttribute("studios", studioRepo.findAll());
        return "studios";
    }
}

