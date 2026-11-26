package co2123.gamestudio.controller;

import co2123.gamestudio.GameStudioApplication;
import co2123.gamestudio.model.Studio;
import co2123.gamestudio.repository.StudioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AdminController {
    @Autowired
    private StudioRepository studioRepo;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new StudioValidator());
    }

//    updated
    @RequestMapping("/admin")
    public String showAdminPage(Model model) {
        model.addAttribute("studios", studioRepo.findAll());
        return "admin";
    }


    @RequestMapping("/newStudio")
    public String newStudio(Model model) {
        model.addAttribute("studio", new Studio());
        return "forms/newStudio";
    }

//updated
    @RequestMapping("/addStudio")
    public String addStudio(@Valid @ModelAttribute Studio studio, BindingResult result) {
        if (result.hasErrors()) {
            return "forms/newStudio";
        }

        System.out.println(studioRepo.count()+1);
//        System.out.println(GameStudioApplication.studioList.size()+1);

        studioRepo.save(studio);
//        GameStudioApplication.studioList.add(studio);
        return "redirect:/admin";
    }

//    updated
    @RequestMapping("/studio")
    public String listStudio(@RequestParam Integer id, Model model) {

        Optional<Studio> foundStudio = studioRepo.findById(id);
        if (foundStudio.isEmpty()) {
            return "redirect:/admin";
        }

//        for(Studio a :  GameStudioApplication.studioList) {
//            if(a.getId() == id) {
//                foundStudio = a;
//            }
//        }

//        if(foundStudio == null) {
//            return "redirect:/admin";
//        }

        model.addAttribute("studio", foundStudio);
        return "studioSummary";
    }
}
