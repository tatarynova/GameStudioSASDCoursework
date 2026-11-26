package co2123.gamestudio.controller;

import co2123.gamestudio.GameStudioApplication;
import co2123.gamestudio.model.Game;
import co2123.gamestudio.model.Studio;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FilterController {

    public List<Studio> method1(String search){
        List<Studio> studios = new ArrayList<Studio>();
        for(Studio a : GameStudioApplication.studioList){
            if(a.getName().contains(search)){
                studios.add(a);
            }
        }
        return studios;
    }

    @GetMapping("/search1")
    public String search1(@RequestParam String studio, Model model) {
        List<Studio> list = method1(studio);
        if(list.isEmpty()){
            model.addAttribute("studios", GameStudioApplication.studioList);
        } else {
            model.addAttribute("studios", list);
        }
        return "studios";
    }

    public List<Studio> method2(String search){
        List<Studio> studios = new ArrayList<>();
        for(Studio a : GameStudioApplication.studioList){
            for(Game d : a.getGames()){
                if(d.getTitle().contains(search) && !studios.contains(a)){
                    studios.add(a);

                }
            }
        }
        return studios;
    }

    @GetMapping("/search2")
    public String search2(@RequestParam String game, Model model) {
        List<Studio> list = method2(game);
        if(list.isEmpty()){
            model.addAttribute("studios", GameStudioApplication.studioList);
        } else {
            model.addAttribute("studios", list);
        }
        return "studios";
    }

}
