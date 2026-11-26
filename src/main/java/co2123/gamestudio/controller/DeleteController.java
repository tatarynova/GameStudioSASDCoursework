package co2123.gamestudio.controller;

import co2123.gamestudio.GameStudioApplication;
import co2123.gamestudio.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DeleteController {


    @RequestMapping("/deleteStudio")
    public String deleteStudio(@RequestParam("id") Integer id) {
        Studio foundStudio = AddEditController.findStudio(id);
        if(foundStudio != null){
            GameStudioApplication.studioList.remove(foundStudio);
        }
        return "redirect:/admin";
    }

    @RequestMapping("/deleteGame")
    public String deleteGame(@RequestParam Integer studioid, @RequestParam Integer gameid) {
        Studio foundStudio = AddEditController.findStudio(studioid);
        if (foundStudio==null) {
            return "redirect:/admin";
        }

        Game foundGame = null;
        for (Game d : foundStudio.getGames()) {
            if (d.getId() == gameid) {
                foundGame = d;
                break;
            }
        }

        if (foundGame == null) {
            return "redirect:/admin";
        }

        foundStudio.getGames().remove(foundGame);
        GameStudioApplication.gameList.remove(foundGame);

        return "redirect:/studio?id=" + studioid;
    }

    @RequestMapping("/deleteReview")
    public String deleteReview(@RequestParam Integer studioId, @RequestParam Integer reviewId) {
        Review foundReview = null;
        for(Review r : GameStudioApplication.reviewList){
            if(r.getId() == reviewId){
                foundReview = r;
            }
        }
        if(foundReview != null){
            GameStudioApplication.reviewList.remove(foundReview);
        }

        Studio foundStudio = AddEditController.findStudio(studioId);
        if(foundStudio == null){
            return "redirect:/admin";
        }

        for(Game d : foundStudio.getGames()){
            if(d.getReviews().contains(foundReview)){
                d.getReviews().remove(foundReview);
                break;
            }
        }

        return "redirect:/studio?id=" + studioId;
    }

    @RequestMapping("/deleteScreenshot")
    public String deleteScreenshot(@RequestParam Integer screenshotId) {
        Screenshot foundScreenshot = null;
        for(Screenshot p : GameStudioApplication.screenshotList){
            if(p.getId() == screenshotId){
                foundScreenshot = p;
            }
        }
        if(foundScreenshot != null){
            GameStudioApplication.screenshotList.remove(foundScreenshot);
        } else {
            return "redirect:/admin";
        }

        Studio foundStudio = AddEditController.findStudio(foundScreenshot.getStudio().getId());
        if(foundStudio == null){
            return "redirect:/admin";
        }
        foundStudio.getScreenshots().remove(foundScreenshot);
        return "redirect:/studio?id=" + foundStudio.getId();
    }

    @RequestMapping("/deleteAward")
    public String deleteAward(@RequestParam Integer awardId) {
        Award foundAward = null;
        for(Award a : GameStudioApplication.awardList){
            if(a.getId() == awardId){
                foundAward = a;
            }
        }
        if(foundAward != null){
            GameStudioApplication.awardList.remove(foundAward);
        } else {
            return "redirect:/admin";
        }

        Studio foundStudio = AddEditController.findStudio(foundAward.getStudio().getId());
        if(foundStudio == null){
            return "redirect:/admin";
        }
        foundStudio.getAwards().remove(foundAward);
        return "redirect:/studio?id=" + foundStudio.getId();
    }

}
