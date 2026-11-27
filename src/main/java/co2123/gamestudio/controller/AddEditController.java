package co2123.gamestudio.controller;

import co2123.gamestudio.GameStudioApplication;
import co2123.gamestudio.model.*;
import co2123.gamestudio.repository.StudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AddEditController {

    @Autowired
    private StudioRepository studioRepo;

//    updated
    @RequestMapping("editStudio")
    public String editStudioForm(@RequestParam("id") Integer id, Model model) {

        Optional<Studio> foundStudio = studioRepo.findById(id);
//        Studio foundStudio = findStudio(id);
        if (foundStudio.isEmpty()) {
            return "redirect:/admin";
        }
//        if(foundStudio == null) {
//            return "redirect:/admin";
//        }
        model.addAttribute("studio", foundStudio.get());
        return "forms/editStudio";
    }

//    updated
    @RequestMapping("editedStudio")
    public String submittedEditForm(@RequestParam("id") Integer id, @ModelAttribute Studio studio, Model model) {
        Optional<Studio> foundStudio = studioRepo.findById(id);
        if (foundStudio.isEmpty()) {
            return "redirect:/admin";
        }
//        Studio foundStudio = findStudio(id);
        Studio existingStudio = foundStudio.get();

        existingStudio.setName(studio.getName());
        existingStudio.setHeadquarters(studio.getHeadquarters());
        existingStudio.setSpecialism(studio.getSpecialism());
        studioRepo.save(existingStudio);
//        foundStudio.setName(foundStudio.get());
//        foundStudio.setHeadquarters(studio.getHeadquarters());
//        foundStudio.setSpecialism(studio.getSpecialism());

        model.addAttribute("studio", existingStudio);
        return "redirect:/studio?id=" + id;
    }

//    updated
    @RequestMapping("editStudioProfile")
    public String editStudioProfileForm(@RequestParam Integer id, Model model) {
        Optional<Studio> foundStudio = studioRepo.findById(id);
        if (foundStudio.isEmpty()) {
            return "redirect:/admin";
        }
//        Studio foundStudio = findStudio(id);
//        if (foundStudio == null) {
//            return "redirect:/admin";
//        }

        StudioProfile profile = foundStudio.get().getProfile();
//        StudioProfile profile = foundStudio.getProfile();

        if(profile == null){
            model.addAttribute("profile", new StudioProfile());
        } else {
            model.addAttribute("profile", profile);
        }
        model.addAttribute("studio", id);
        return "forms/editStudioProfile";
    }

//    updated
    @RequestMapping("editedStudioProfile")
    public String submittedProfileEditForm(@RequestParam Integer id, @ModelAttribute StudioProfile profile, Model model) {
        Optional<Studio> foundStudio = studioRepo.findById(id);
        if (foundStudio.isEmpty()) {
            return "redirect:/admin";
        }
//        Studio foundStudio = findStudio(id);
//        if (foundStudio == null) {
//            return "redirect:/admin";
//        }

        Studio existingStudio = foundStudio.get();
        StudioProfile existingProfile = existingStudio.getProfile();

        if(existingProfile == null){
//            profile.setId(GameStudioApplication.studioprofileList.size()+1);
            existingStudio.setProfile(profile);
//            GameStudioApplication.studioprofileList.add(profile);
        } else {
//            GameStudioApplication.studioprofileList.remove(foundStudio.getProfile());
//            GameStudioApplication.studioprofileList.add(profile);

            existingProfile.setBio(profile.getBio());
            existingProfile.setWebsite(profile.getWebsite());
            existingProfile.setSocialMediaHandle(profile.getSocialMediaHandle());
//            foundStudio.getProfile().setBio(profile.getBio());
//            foundStudio.getProfile().setSocialMediaHandle(profile.getSocialMediaHandle());
//            foundStudio.getProfile().setWebsite(profile.getWebsite());
        }

        model.addAttribute("studio", existingStudio);
        studioRepo.save(existingStudio);
        return "redirect:/studio?id=" + id;
    }

//    GAME

    @RequestMapping("newGame")
    public String newGameForm(@RequestParam Integer id, Model model) {
        Studio foundStudio = findStudio(id);
        if (foundStudio == null) {
            return "redirect:/admin";
        }

        model.addAttribute("studio", foundStudio);
        model.addAttribute("game", new Game());
        model.addAttribute("genres", GameStudioApplication.genreList);
        return "forms/newGame";
    }

    @RequestMapping("addGame")
    public String addGame(@RequestParam Integer studioid, @RequestParam List<Integer> genreIds, @ModelAttribute Game game, Model model) {
        Studio foundStudio = findStudio(studioid);
        if (foundStudio == null) {
            return "redirect:/admin";
        }

        if(foundStudio.getGames() == null || foundStudio.getGames().isEmpty()){
            foundStudio.setGames(new ArrayList<>());
        }

        game.setGenres(new ArrayList<>());
        for(Integer genreId : genreIds){
            game.getGenres().add(GameStudioApplication.genreList.get(genreId-1));
        }

        game.setReviews(new ArrayList<>());
        game.setStudio(foundStudio);
//        game.setId(GameStudioApplication.gameList.size()+1);
        GameStudioApplication.gameList.add(game);

        foundStudio.getGames().add(game);

        model.addAttribute("studio", foundStudio);
        return "redirect:/studio?id=" + studioid;
    }

//    REVIEW

    @RequestMapping("newReview")
    public String newReview(@RequestParam Integer studioid, @RequestParam Integer gameid, Model model) {
        Studio foundStudio = findStudio(studioid);
        if (foundStudio == null) {
            return "redirect:/admin";
        }

        model.addAttribute("studio", foundStudio);
        model.addAttribute("gameid",gameid);
        model.addAttribute("review", new Review());
        return "forms/newReview";
    }

    @RequestMapping("addReview")
    public String addReview(@RequestParam Integer studioid, @RequestParam Integer gameid, @ModelAttribute Review review, Model model) {
        Studio foundStudio = findStudio(studioid);
        if (foundStudio == null) {
            return "redirect:/admin";
        }

        Game foundGame = null;
        for (Game game : foundStudio.getGames()) {
            if (game.getId() == gameid) {
                foundGame = game;
                break;
            }
        }

        if (foundGame == null) {
            return "redirect:/admin";
        }

        review.setReviewDate(LocalDateTime.now());
        review.setGame(foundGame);
//        review.setId(GameStudioApplication.reviewList.size()+1);
        GameStudioApplication.reviewList.add(review);

        if(foundGame.getReviews().isEmpty()){
            foundGame.setReviews(new ArrayList<>());
        }
        foundGame.getReviews().add(review);

        model.addAttribute("studio", foundStudio);
        return "redirect:/studio?id=" + studioid;
    }

//    SCREENSHOT
    @RequestMapping("newScreenshot")
    public String newScreenshot(@RequestParam Integer studioid, Model model) {
        Studio foundStudio = findStudio(studioid);
        if (foundStudio == null) {
            return "redirect:/admin";
        }

        model.addAttribute("studio", foundStudio);
        model.addAttribute("screenshot", new Screenshot());
        return "forms/newScreenshot";
    }

    @RequestMapping("addScreenshot")
    public String addScreenshot(@RequestParam Integer studioid, @ModelAttribute Screenshot screenshot, Model model) {
        Studio foundStudio = findStudio(studioid);
        if (foundStudio == null) {
            return "redirect:/admin";
        }

        screenshot.setStudio(foundStudio);
//        screenshot.setId(GameStudioApplication.screenshotList.size()+1);
        GameStudioApplication.screenshotList.add(screenshot);

        if(foundStudio.getScreenshots() == null || foundStudio.getScreenshots().isEmpty()){
            foundStudio.setScreenshots(new ArrayList<>());
        }
        foundStudio.getScreenshots().add(screenshot);

        model.addAttribute("studio", foundStudio);
        return "redirect:/studio?id=" + studioid;
    }
//    AWARD

    @RequestMapping("newAward")
    public String newAward(@RequestParam Integer studioid, Model model) {
        Studio foundStudio = findStudio(studioid);
        if (foundStudio == null) {
            return "redirect:/admin";
        }

        model.addAttribute("studio", foundStudio);
        model.addAttribute("award", new Award());
        return "forms/newAward";
    }

    @RequestMapping("addAward")
    public String addAward(@RequestParam Integer studioid, @ModelAttribute Award award, Model model) {
        Studio foundStudio = findStudio(studioid);
        if (foundStudio == null) {
            return "redirect:/admin";
        }

        award.setStudio(foundStudio);
//        award.setId(GameStudioApplication.awardList.size()+1);
        GameStudioApplication.awardList.add(award);

        if(foundStudio.getAwards() == null || foundStudio.getAwards().isEmpty()){
            foundStudio.setAwards(new ArrayList<>());
        }
        foundStudio.getAwards().add(award);

        model.addAttribute("studio", foundStudio);
        return "redirect:/studio?id=" + studioid;
    }


//    GAME

    @RequestMapping("editGame")
    public String editGameForm(@RequestParam Integer studioid, @RequestParam Integer gameid, Model model) {
        Studio foundStudio = findStudio(studioid);
        if (foundStudio == null) {
            return "redirect:/admin";
        }

        Game foundGame = null;
        for (Game game : foundStudio.getGames()) {
            if (game.getId() == gameid) {
                foundGame = game;
                break;
            }
        }

        if (foundGame == null) {
            return "redirect:/admin";
        }

        model.addAttribute("studio", foundStudio);
        model.addAttribute("game", foundGame);
        model.addAttribute("genres", GameStudioApplication.genreList);
        return "forms/editGame";
    }
    @RequestMapping("editedGame")
    public String submittedEditGameForm(@RequestParam Integer studioid, @RequestParam Integer gameid, @RequestParam(required = false) List<Integer> genreIds, @ModelAttribute Game game, Model model) {
        Studio foundStudio = findStudio(studioid);
        if (foundStudio == null) {
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

        foundGame.setTitle(game.getTitle());
        foundGame.setPrice(game.getPrice());
        foundGame.setGenres(game.getGenres());
        foundGame.setDescription(game.getDescription());
        foundGame.setDifficultyLevel(game.getDifficultyLevel());

        if(foundGame.getGenres() == null){
            foundGame.setGenres(new ArrayList<>());
        } else {
            foundGame.getGenres().clear();
        }

        for(Integer genreId : genreIds){
            foundGame.getGenres().add(GameStudioApplication.genreList.get(genreId-1));
        }

        model.addAttribute("studio", foundStudio);
        return "redirect:/studio?id=" + studioid;
    }
//    REVIEW

    @RequestMapping("editReview")
    public String editReview(@RequestParam Integer studioId, @RequestParam Integer reviewId, Model model) {
        Studio foundStudio = findStudio(studioId);
        if (foundStudio == null) {
            return "redirect:/admin";
        }

        Review foundReview = null;
        for(Review r : GameStudioApplication.reviewList){
            if(r.getId() == reviewId){
                foundReview = r;
            }
        }

        if (foundReview == null) {
            return "redirect:/admin";
        }

        model.addAttribute("studio", foundStudio);
        model.addAttribute("review", foundReview);
        return "forms/editReview";
    }

    @RequestMapping("editedReview")
    public String editedReview(@RequestParam Integer studioId, @RequestParam Integer reviewId, @ModelAttribute Review review, Model model) {
        Review foundReview = null;
        for(Review r : GameStudioApplication.reviewList){
            if(r.getId() == reviewId){
                foundReview = r;
            }
        }

        if (foundReview == null) {
            return "redirect:/admin";
        }

        foundReview.setReviewerName(review.getReviewerName());
        foundReview.setComment(review.getComment());
        foundReview.setRating(review.getRating());

        Studio foundStudio = findStudio(studioId);
        if (foundStudio == null) {
            return "redirect:/admin";
        }

        model.addAttribute("studio", foundStudio);
        return "redirect:/studio?id=" + studioId;
    }
//    SCREENSHOT

    @RequestMapping("editScreenshot")
    public String editScreenshot(@RequestParam Integer screenshotId, Model model) {
        Screenshot foundScreenshot = null;
        for(Screenshot p : GameStudioApplication.screenshotList){
            if(p.getId() == screenshotId){
                foundScreenshot = p;
            }
        }

        if (foundScreenshot == null) {
            return "redirect:/admin";
        }

        model.addAttribute("screenshot", foundScreenshot);
        return "forms/editScreenshot";
    }

    @RequestMapping("editedScreenshot")
    public String editedScreenshot(@RequestParam Integer screenshotId, @ModelAttribute Screenshot screenshot, Model model) {
        Screenshot foundScreenshot = null;
        for(Screenshot p : GameStudioApplication.screenshotList){
            if(p.getId() == screenshotId){
                foundScreenshot = p;
            }
        }

        if (foundScreenshot == null) {
            return "redirect:/admin";
        }

        foundScreenshot.setDescription(screenshot.getDescription());
        foundScreenshot.setImageUrl(screenshot.getImageUrl());

        Studio foundStudio = findStudio(foundScreenshot.getStudio().getId());
        if (foundStudio == null) {
            return "redirect:/admin";
        }

        model.addAttribute("studio", foundStudio);
        return "redirect:/studio?id=" + foundStudio.getId();
    }

    @RequestMapping("editAward")
    public String editAward(@RequestParam Integer awardId, Model model) {
        Award foundAward = null;
        for(Award a : GameStudioApplication.awardList){
            if(a.getId() == awardId){
                foundAward = a;
            }
        }

        if (foundAward == null) {
            return "redirect:/admin";
        }

        model.addAttribute("award", foundAward);
        return "forms/editAward";
    }
//    AWARD

    @RequestMapping("editedAward")
    public String editedAward(@RequestParam Integer awardId, @ModelAttribute Award award, Model model) {
        Award foundAward = null;
        for(Award a : GameStudioApplication.awardList){
            if(a.getId() == awardId){
                foundAward = a;
            }
        }

        if (foundAward == null) {
            return "redirect:/admin";
        }

        foundAward.setTitle(award.getTitle());
        foundAward.setYear(award.getYear());

        Studio foundStudio = findStudio(foundAward.getStudio().getId());
        if (foundStudio == null) {
            return "redirect:/admin";
        }

        model.addAttribute("studio", foundStudio);
        return "redirect:/studio?id=" + foundStudio.getId();
    }

}
