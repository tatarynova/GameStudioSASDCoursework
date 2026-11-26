package co2123.gamestudio;

import co2123.gamestudio.model.*;
import co2123.gamestudio.repository.StudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class GameStudioApplication implements CommandLineRunner {

    public static List<Award> awardList = new ArrayList<>();
    public static List<Game> gameList = new ArrayList<>();
    public static List<Screenshot> screenshotList = new ArrayList<>();
    public static List<Review> reviewList = new ArrayList<>();
    public static List<Genre> genreList = new ArrayList<>();
//    public static List<Studio> studioList = new ArrayList<>();
    @Autowired private StudioRepository studioRepo;
//    public static List<StudioProfile> studioprofileList = new ArrayList<>();

    public static void main(String[] args) {
        SpringApplication.run(GameStudioApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //Creating 1 StudioProfile
        StudioProfile profile = new StudioProfile();
//        profile.setId(1);
        profile.setBio("An independent studio creating imaginative puzzle and strategy games.");
        profile.setSocialMediaHandle("@pixelforgegames");
        profile.setWebsite("http://pixelforge.fakewebsite.com");
//        studioprofileList.add(profile);

        //Creating 1 Studio
        Studio studio = new Studio();
//        studio.setId(1);
        studio.setName("Pixel Forge");
        studio.setHeadquarters("London");
        studio.setSpecialism("Puzzle and Strategy Games");
        studio.setProfile(profile);
        studioRepo.save(studio);

        //Creating 2 Genres
        Genre strategy = new Genre();
//        strategy.setId(1);
        strategy.setName("Strategy");

        Genre adventure = new Genre();
//        adventure.setId(2);
        adventure.setName("Adventure");

        genreList.add(strategy);
        genreList.add(adventure);

        //Creating 2 Games then saving the studio with games
        Game sillyChess = new Game();
//        sillyChess.setId(1);
        sillyChess.setTitle("Silly Chess");
        sillyChess.setDescription("A light-hearted strategy game in which chess pieces wear silly little hats.");
        sillyChess.setDifficultyLevel(5);
        sillyChess.setPrice(8.99);
        sillyChess.setGenres(new ArrayList<>());
        sillyChess.getGenres().add(strategy);
        sillyChess.setStudio(studio);
        gameList.add(sillyChess);

        Game funkyAdventure = new Game();
//        funkyAdventure.setId(2);
        funkyAdventure.setTitle("Dr. Funky's Adventure");
        funkyAdventure.setDescription("A colourful adventure through mysterious worlds filled with puzzles.");
        funkyAdventure.setDifficultyLevel(2);
        funkyAdventure.setPrice(6.55);
        funkyAdventure.setGenres(new ArrayList<>());
        funkyAdventure.getGenres().add(adventure);
        funkyAdventure.setStudio(studio);
        gameList.add(funkyAdventure);

        studio.setGames(new ArrayList<>());
        studio.getGames().add(sillyChess);
        studio.getGames().add(funkyAdventure);
        studioList.add(studio);

        //Creating 2 Reviews
        Review review1 = new Review();
//        review1.setId(1);
        review1.setReviewerName("Sofia");
        review1.setRating(5);
        review1.setComment("Funny and surprisingly challenging!");
        review1.setReviewDate(LocalDateTime.now());
        review1.setGame(sillyChess);

        Review review2 = new Review();
//        review2.setId(2);
        review2.setReviewerName("Jamie");
        review2.setRating(4);
        review2.setComment("What even is this game?!");
        review2.setReviewDate(LocalDateTime.now());
        review2.setGame(funkyAdventure);

        reviewList.add(review1);
        reviewList.add(review2);

        // Creating 2 Screenshots
        Screenshot sillyChessScreenshot = new Screenshot();
//        sillyChessScreenshot.setId(1);
        sillyChessScreenshot.setImageUrl("silly-chess.jpg");
        sillyChessScreenshot.setDescription("A chess board.");
        sillyChessScreenshot.setStudio(studio);

        Screenshot funkyAdventureScreenshot = new Screenshot();
//        funkyAdventureScreenshot.setId(2);
        funkyAdventureScreenshot.setImageUrl("funky-adventure.jpg");
        funkyAdventureScreenshot.setDescription("Dr. Funky poses.");
        funkyAdventureScreenshot.setStudio(studio);

        screenshotList.add(sillyChessScreenshot);
        screenshotList.add(funkyAdventureScreenshot);

        //Creating 2 Awards
        Award award1 = new Award();
//        award1.setId(1);
        award1.setTitle("Best Independent Strategy Game");
        award1.setYear(2025);
        award1.setStudio(studio);

        Award award2 = new Award();
//        award2.setId(2);
        award2.setTitle("Players' Choice Award");
        award2.setYear(2024);
        award2.setStudio(studio);

        awardList.add(award1);
        awardList.add(award2);


        //Creating another studio to populate the database
        StudioProfile profile2 = new StudioProfile();
//        profile2.setId(2);
        profile2.setBio("A Leicester-based studio creating accessible games inspired by local stories.");
        profile2.setSocialMediaHandle("@foxglovegames");
        profile2.setWebsite("http://foxglovegames.fakewebsite.com");
//        studioprofileList.add(profile2);

        Studio studio2 = new Studio();
//        studio2.setId(2);
        studio2.setName("Foxglove Games");
        studio2.setHeadquarters("Leicester");
        studio2.setSpecialism("Simulation and Educational Games");
        studio2.setProfile(profile2);

        //Creating 2 more Genres
        Genre simulation = new Genre();
//        simulation.setId(3);
        simulation.setName("Simulation");

        Genre educational = new Genre();
//        educational.setId(4);
        educational.setName("Educational");

        genreList.add(simulation);
        genreList.add(educational);

        //Creating 3 Games
        Game marketManager = new Game();
//        marketManager.setId(3);
        marketManager.setTitle("Leicester Market Manager");
        marketManager.setDescription("Build and manage a busy market while supporting independent traders.");
        marketManager.setDifficultyLevel(3);
        marketManager.setPrice(4.99);
        marketManager.setGenres(new ArrayList<>());
        marketManager.getGenres().add(simulation);
        marketManager.getGenres().add(educational);
        marketManager.setStudio(studio2);
        gameList.add(marketManager);

        Game magicGarden = new Game();
//        magicGarden.setId(4);
        magicGarden.setTitle("Magic Garden");
        magicGarden.setDescription("Grow unusual plants and manage resources in a magical garden.");
        magicGarden.setDifficultyLevel(1);
        magicGarden.setPrice(2.99);
        magicGarden.setGenres(new ArrayList<>());
        magicGarden.getGenres().add(simulation);
        magicGarden.setStudio(studio2);
        gameList.add(magicGarden);

        Game codeQuest = new Game();
//        codeQuest.setId(5);
        codeQuest.setTitle("Code Quest");
        codeQuest.setDescription("Solve programming puzzles to guide a robot through a digital world.");
        codeQuest.setDifficultyLevel(1);
        codeQuest.setPrice(3.75);
        codeQuest.setGenres(new ArrayList<>());
        codeQuest.getGenres().add(educational);
        codeQuest.setStudio(studio2);
        gameList.add(codeQuest);

        studio2.setGames(new ArrayList<>());
        studio2.getGames().add(marketManager);
        studio2.getGames().add(magicGarden);
        studio2.getGames().add(codeQuest);
        studioList.add(studio2);

        //Creating 3 Reviews
        Review review3 = new Review();
//        review3.setId(3);
        review3.setReviewerName("Tom");
        review3.setRating(5);
        review3.setComment("An entertaining way to learn about running a market!");
        review3.setReviewDate(LocalDateTime.now());
        review3.setGame(marketManager);

        Review review4 = new Review();
//        review4.setId(4);
        review4.setReviewerName("Ayesha");
        review4.setRating(4);
        review4.setComment("The different plants make every magic garden unique.");
        review4.setReviewDate(LocalDateTime.now());
        review4.setGame(magicGarden);

        Review review5 = new Review();
//        review5.setId(5);
        review5.setReviewerName("Pierre");
        review5.setRating(5);
        review5.setComment("The programming puzzles were challenging and enjoyable!");
        review5.setReviewDate(LocalDateTime.now());
        review5.setGame(codeQuest);

        reviewList.add(review3);
        reviewList.add(review4);
        reviewList.add(review5);

        //Creating 3 Screenshots
        Screenshot marketScreenshot = new Screenshot();
//        marketScreenshot.setId(3);
        marketScreenshot.setImageUrl("market-manager.jpg");
        marketScreenshot.setDescription("A busy market.");
        marketScreenshot.setStudio(studio2);

        Screenshot gardenScreenshot = new Screenshot();
//        gardenScreenshot.setId(4);
        gardenScreenshot.setImageUrl("magic-garden.jpg");
        gardenScreenshot.setDescription("A magic garden.");
        gardenScreenshot.setStudio(studio2);

        Screenshot codeQuestScreenshot = new Screenshot();
//        codeQuestScreenshot.setId(5);
        codeQuestScreenshot.setImageUrl("code-quest.jpg");
        codeQuestScreenshot.setDescription("Image of code and a cute plush toy.");
        codeQuestScreenshot.setStudio(studio2);

        screenshotList.add(marketScreenshot);
        screenshotList.add(gardenScreenshot);
        screenshotList.add(codeQuestScreenshot);

        //Creating an Award
        Award award3 = new Award();
//        award3.setId(3);
        award3.setTitle("Leicester Independent Game Award");
        award3.setYear(2026);
        award3.setStudio(studio2);

        awardList.add(award3);


        //Creating another Studio
        StudioProfile profile3 = new StudioProfile();
//        profile3.setId(3);
        profile3.setBio("Creating atmospheric games based on exploration, mystery, and storytelling.");
        profile3.setSocialMediaHandle("@moonbeaminteractive");
        profile3.setWebsite("http://moonbeaminteractive.fakewebsite.com");
//        studioprofileList.add(profile3);

        Studio studio3 = new Studio();
//        studio3.setId(3);
        studio3.setName("Moonbeam Interactive");
        studio3.setHeadquarters("Manchester");
        studio3.setSpecialism("Narrative Adventure Games");
        studio3.setProfile(profile3);

        //Creating 2 more Genres
        Genre puzzle = new Genre();
//        puzzle.setId(5);
        puzzle.setName("Puzzle");

        Genre narrative = new Genre();
//        narrative.setId(6);
        narrative.setName("Narrative");

        genreList.add(narrative);
        genreList.add(puzzle);

        //Creating 3 Games
        Game clockworkMaze = new Game();
//        clockworkMaze.setId(6);
        clockworkMaze.setTitle("Clockwork Maze");
        clockworkMaze.setDescription("Explore a mechanical labyrinth filled with moving rooms and intricate puzzles.");
        clockworkMaze.setDifficultyLevel(4);
        clockworkMaze.setPrice(3.15);
        clockworkMaze.setGenres(new ArrayList<>());
        clockworkMaze.getGenres().add(puzzle);
        clockworkMaze.getGenres().add(narrative);
        clockworkMaze.setStudio(studio3);

        Game motoBear = new Game();
//        motoBear.setId(7);
        motoBear.setTitle("MotoBear");
        motoBear.setDescription(
                "Investigate crimes with a part bear part motorbike."
        );
        motoBear.setDifficultyLevel(3);
        motoBear.setPrice(3.85);
        motoBear.setGenres(new ArrayList<>());
        motoBear.getGenres().add(narrative);
        motoBear.setStudio(studio3);

        Game lanternLibrary = new Game();
//        lanternLibrary.setId(8);
        lanternLibrary.setTitle("The Lantern Library");
        lanternLibrary.setDescription("Solve literary puzzles inside a magical library that changes each night.");
        lanternLibrary.setDifficultyLevel(2);
        lanternLibrary.setPrice(1.45);
        lanternLibrary.setGenres(new ArrayList<>());
        lanternLibrary.getGenres().add(puzzle);
        lanternLibrary.setStudio(studio3);

        gameList.add(motoBear);
        gameList.add(lanternLibrary);
        gameList.add(clockworkMaze);

        studio3.setGames(new ArrayList<>());
        studio3.getGames().add(clockworkMaze);
        studio3.getGames().add(motoBear);
        studio3.getGames().add(lanternLibrary);

        studioList.add(studio3);

        //Creating 3 Reviews
        Review review6 = new Review();
//        review6.setId(6);
        review6.setReviewerName("Lucie");
        review6.setRating(5);
        review6.setComment("The clockwork puzzles were clever!");
        review6.setReviewDate(LocalDateTime.now());
        review6.setGame(clockworkMaze);

        Review review7 = new Review();
//        review7.setId(7);
        review7.setReviewerName("Priya");
        review7.setRating(4);
        review7.setComment("I wish I was part motorbike.");
        review7.setReviewDate(LocalDateTime.now());
        review7.setGame(motoBear);

        Review review8 = new Review();
//        review8.setId(8);
        review8.setReviewerName("Harriet");
        review8.setRating(3);
        review8.setComment("The story was interesting but some puzzles were difficult.");
        review8.setReviewDate(LocalDateTime.now());
        review8.setGame(motoBear);

        reviewList.add(review6);
        reviewList.add(review7);
        reviewList.add(review8);

        //Creating 2 Screenshots
        Screenshot clockworkScreenshot = new Screenshot();
//        clockworkScreenshot.setId(6);
        clockworkScreenshot.setImageUrl("clockwork-maze.jpg");
        clockworkScreenshot.setDescription("Clocks and interlocking gears.");
        clockworkScreenshot.setStudio(studio3);

        Screenshot motobearScreenshot = new Screenshot();
//        motobearScreenshot.setId(7);
        motobearScreenshot.setImageUrl("motobear.jpg");
        motobearScreenshot.setDescription("Teddy bear in a car.");
        motobearScreenshot.setStudio(studio3);

        screenshotList.add(clockworkScreenshot);
        screenshotList.add(motobearScreenshot);

        //Creating an Award
        Award award4 = new Award();
//        award4.setId(4);
        award4.setTitle("Best Narrative Adventure");
        award4.setYear(2026);
        award4.setStudio(studio3);
        awardList.add(award4);


        //Additional code for ArrayLists
        sillyChess.setReviews(new ArrayList<>(List.of(review1)));
        funkyAdventure.setReviews(new ArrayList<>(List.of(review2)));
        marketManager.setReviews(new ArrayList<>(List.of(review3)));
        magicGarden.setReviews(new ArrayList<>(List.of(review4)));
        codeQuest.setReviews(new ArrayList<>(List.of(review5)));
        clockworkMaze.setReviews(new ArrayList<>(List.of(review6)));
        motoBear.setReviews(new ArrayList<>(Arrays.asList(review7, review8)));

        studio.setScreenshots(new ArrayList<>(Arrays.asList(sillyChessScreenshot, funkyAdventureScreenshot)));
        studio2.setScreenshots(new ArrayList<>(Arrays.asList(marketScreenshot, gardenScreenshot, codeQuestScreenshot)));
        studio3.setScreenshots(new ArrayList<>(Arrays.asList(clockworkScreenshot, motobearScreenshot)));

        studio.setAwards(new ArrayList<>(Arrays.asList(award1, award2)));
        studio2.setAwards(new ArrayList<>(List.of(award3)));
        studio3.setAwards(new ArrayList<>(List.of(award4)));
    }
}
