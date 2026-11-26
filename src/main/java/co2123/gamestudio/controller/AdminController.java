package co2123.gamestudio.controller;

import co2123.gamestudio.GameStudioApplication;
import co2123.gamestudio.model.Studio;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new StudioValidator());
    }

    @RequestMapping("/admin")
    public String showAdminPage(Model model) {
        model.addAttribute("studios", GameStudioApplication.studioList);
        return "admin";
    }


    @RequestMapping("/newStudio")
    public String newStudio(Model model) {
        model.addAttribute("studio", new Studio());
        return "forms/newStudio";
    }


    @RequestMapping("/addStudio")
    public String addStudio(@Valid @ModelAttribute Studio studio, BindingResult result) {
        if (result.hasErrors()) {
            return "forms/newStudio";
        }
        System.out.println(GameStudioApplication.studioList.size()+1);
//        studio.setId(GameStudioApplication.studioList.size());
        GameStudioApplication.studioList.add(studio);
        return "redirect:/admin";
    }

    @RequestMapping("/studio")
    public String listStudio(@RequestParam Integer id, Model model) {

        Studio foundStudio = null;
        for(Studio a :  GameStudioApplication.studioList) {
            if(a.getId() == id) {
                foundStudio = a;
            }
        }

        if(foundStudio == null) {
            return "redirect:/admin";
        }

        model.addAttribute("studio", foundStudio);
        return "studioSummary";
    }



}
