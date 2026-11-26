package co2123.gamestudio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StringUtils;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.util.AssertionErrors.fail;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class GameStudioApplicationTests {

    //Task 1: Checking application.properties
    @Test
    public void checkProperties() throws Exception {
        Properties p = new Properties();
        p.load(new FileReader("src/main/resources/application.properties"));
        assertTrue("Folder for JSPs not defined.", "/WEB-INF/views/".equals(p.getProperty("spring.mvc.view.prefix")));
        assertTrue("JSP extension not defined.", ".jsp".equals(p.getProperty("spring.mvc.view.suffix")));
        assertTrue("URl for the database not defined", p.getProperty("spring.datasource.url") != null);
        assertTrue("Username for the database not defined", p.getProperty("spring.datasource.username") != null);
        assertTrue("Password for the database not defined", p.getProperty("spring.datasource.password") != null);
        assertTrue("Missing/Incomplete configuration for printing out SQL to the command line with spring.jpa.show-sql.",p.getProperty("spring.jpa.show-sql").equals("true"));

        String s = p.getProperty("logging.level.org.hibernate.SQL");
        if(s != null){
            s = s.toLowerCase();
            assertTrue("Missing/Incomplete configuration for printing out SQL to the command line with logging.level.org.hibernate.SQL.",s.contains("debug"));
        } else {
            assertTrue("Missing/Incomplete configuration for printing out SQL to the command line with logging.level.org.hibernate.SQL.",p.getProperty("logging.level.org.hibernate.SQL").contains("debug"));
        }
    }

    //Task 1: Checking dependencies in build.gradle
    @Test
    public void dependencies() throws Exception {
        String content = Files.readString(Paths.get("build.gradle"));
        assertTrue("Jasper libraries not loaded for JSPs.", content.contains("tomcat-embed-jasper"));
        assertTrue("JSTL library not included in build.gradle.", content.contains("jstl"));
        assertTrue("Missing a dependency for connecting to the database.", content.contains("mysql-connector"));
        assertTrue("Missing a dependency for connecting to the database.", content.contains("spring-boot-starter-data-jpa"));
    }

    @Autowired
    private EntityManager entityManager;

    //TASK 2: Checking that the tables and rows exist as described in the handout
    //NOTE: Task 1 must be completed and Task 4 not completed for these tests to pass
    @Test
    void tableAndColumnsExistTask2(){
        assertTableExists("award");
        assertTableExists("game");
        assertTableExists("game_reviews"); //fails after Task 4
        assertTableExists("game_genres"); //fails after Task 4
        assertTableExists("genre");
        assertTableExists("review");
        assertTableExists("screenshot");
        assertTableExists("studio");
        assertTableExists("studio_awards"); //fails after Task 4
        assertTableExists("studio_games"); //fails after Task 4
        assertTableExists("studio_screenshots"); //fails after Task 4
        assertTableExists("studio_profile"); //fails after Task 4

        assertColumnsExist("award", new String[]{"id", "studio_id", "year", "title"});
        assertColumnsExist("game", new String[]{"id", "price", "difficulty_level", "studio_id", "description", "title"});
        assertColumnsExist("game_reviews", new String[]{"game_id", "reviews_id"}); //fails after Task 4
        assertColumnsExist("game_genres", new String[]{"game_id", "genres_id"}); //fails after Task 4
        assertColumnsExist("genre", new String[]{"id", "name"});
        assertColumnsExist("review", new String[]{"game_id", "id", "rating", "review_date", "comment", "reviewer_name"});
        assertColumnsExist("screenshot", new String[]{"id", "studio_id", "description", "image_url"});
        assertColumnsExist("studio", new String[]{"id", "profile_id", "specialism", "headquarters", "name"});
        assertColumnsExist("studio_awards", new String[]{"awards_id", "studio_id"}); //fails after Task 4
        assertColumnsExist("studio_games", new String[]{"games_id", "studio_id"}); //fails after Task 4
        assertColumnsExist("studio_screenshots", new String[]{"screenshots_id", "studio_id"}); //fails after Task 4
        assertColumnsExist("studio_profile", new String[]{"id", "bio", "social_media_handle", "website"}); //fails after Task 4

    }

    //TASK 3: Checking if each repository exists

    @Test
    void repositoriesExistTask3(){
        boolean exists = classExists("co2123.gamestudio.repository.AwardRepository");
        Assertions.assertTrue(exists, "AwardRepository should exist in either 'gamestudio' or 'gamestudio_solution' package");

        exists = classExists("co2123.gamestudio.repository.ScreenshotRepository");
        Assertions.assertTrue(exists, "ScreenshotRepository should exist in either package");

        exists = classExists("co2123.gamestudio.repository.ReviewRepository");
        Assertions.assertTrue(exists, "ReviewRepository should exist in either package");

        exists = classExists("co2123.gamestudio.repository.GenreRepository");
        Assertions.assertTrue(exists, "GenreRepository should exist in either package");

        exists = classExists("co2123.gamestudio.repository.StudioRepository");
        Assertions.assertTrue(exists, "StudioRepository should exist in either package");
    }

    //TASK 4: Tests that each table exists with the correct columns

    @Test
    void tableAndColumnsExistTask4(){
        assertTableExists("award");
        assertTableExists("game");
        assertTableExists("game_genres");
        assertTableExists("genre");
        assertTableExists("review");
        assertTableExists("screenshot");
        assertTableExists("studio");
        assertTableExists("studio_profile");

        assertColumnsExist("award", new String[]{"id", "studio_id", "year", "title"});
        assertColumnsExist("game", new String[]{"id", "price", "difficulty_level", "studio_id", "description", "title"});
        assertColumnsExist("game_genres", new String[]{"games_id", "genres_id"});
        assertColumnsExist("genre", new String[]{"id", "name"});
        assertColumnsExist("review", new String[]{"game_id", "id", "rating", "review_date", "comment", "reviewer_name"});
        assertColumnsExist("screenshot", new String[]{"id", "studio_id", "description", "image_url"});
        assertColumnsExist("studio", new String[]{"id", "profile_id", "specialism", "headquarters", "name"});
        assertColumnsExist("studio_profile", new String[]{"id", "bio", "social_media_handle", "website"});
    }

    //TASK 6: Checks each table has at least 1 row of data

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void tablesHaveAtLeastOneRowTask6(){
        Integer count = rowCountChecker("award");
        Assertions.assertTrue(count != null && count > 0, "Award table should have at least one row");

        count = rowCountChecker("game");
        Assertions.assertTrue(count != null && count > 0, "Dish table should have at least one row");

        count = rowCountChecker("game_genres");
        Assertions.assertTrue(count != null && count > 0, "Dish_genres table should have at least one row");

        count = rowCountChecker("screenshot");
        Assertions.assertTrue(count != null && count > 0, "Screenshot table should have at least one row");

        count = rowCountChecker("review");
        Assertions.assertTrue(count != null && count > 0, "Review table should have at least one row");

        count = rowCountChecker("genre");
        Assertions.assertTrue(count != null && count > 0, "Genre table should have at least one row");

        count = rowCountChecker("studio");
        Assertions.assertTrue(count != null && count > 0, "Studio table should have at least one row");

        count = rowCountChecker("studio_profile");
        Assertions.assertTrue(count != null && count > 0, "Studio_profile table should have at least one row");
    }

    //Task 6: Checking original data from website is still in the database
    //Runs the checking method assertRowExists so you don't have to keep creating maps
    private void awardTableHelper(int id, int studio_id, int year, String title){
        Map<String, Object> conditions = new LinkedHashMap<>();
        conditions.put("id",id);
        conditions.put("studio_id", studio_id);
        conditions.put("year",year);
        conditions.put("title", title);
        assertRowExists("award",conditions);
    }

    @Test
    void awardTableData(){
        awardTableHelper(1,1,2025,"Best Independent Strategy Game");
        awardTableHelper(2,1,2024,"Players' Choice Award");
        awardTableHelper(3,2,2026, "Leicester Independent Game Award");
        awardTableHelper(4,3,2026,"Best Narrative Adventure");
    }

    //Runs the checking method assertRowExists so you don't have to keep creating maps
    private void gameTableHelper(int id, double price, int difficulty_level, int studio_id, String description, String title){
        Map<String, Object> conditions = new LinkedHashMap<>();
        conditions.put("id",id);
        conditions.put("price",price);
        conditions.put("difficulty_level",difficulty_level);
        conditions.put("studio_id", studio_id);
        conditions.put("description",description);
        conditions.put("title", title);
        assertRowExists("game",conditions);
    }

    @Test
    void gameTableData(){
        gameTableHelper(1,8.99,5,1,"A light-hearted strategy game in which chess pieces wear silly little hats.","Silly Chess");
        gameTableHelper(2,6.55,2,1,"A colourful adventure through mysterious worlds filled with puzzles.","Dr. Funky's Adventure");
        gameTableHelper(3,4.99,3,2,"Build and manage a busy market while supporting independent traders.","Leicester Market Manager");
        gameTableHelper(4,2.99,1,2,"Grow unusual plants and manage resources in a magical garden.","Magic Garden");
        gameTableHelper(5,3.75,1,2,"Solve programming puzzles to guide a robot through a digital world.","Code Quest");
        gameTableHelper(6,3.15,4,3,"Explore a mechanical labyrinth filled with moving rooms and intricate puzzles.","Clockwork Maze");
        gameTableHelper(7,3.85,3,3,"Investigate crimes with a part bear part motorbike.","MotoBear");
        gameTableHelper(8,1.45,2,3,"Solve literary puzzles inside a magical library that changes each night.","The Lantern Library");
    }

    //Runs the checking method assertRowExists so you don't have to keep creating maps
    private void gameGenresTableHelper(int games_id, int genres_id){
        Map<String, Object> conditions = new LinkedHashMap<>();
        conditions.put("games_id",games_id);
        conditions.put("genres_id",genres_id);
        assertRowExists("game_genres",conditions);
    }

    @Test
    void gameGenresTableData(){
        gameGenresTableHelper(1,1);
        gameGenresTableHelper(2,2);
        gameGenresTableHelper(3,3);
        gameGenresTableHelper(3,4);
        gameGenresTableHelper(4,3);
        gameGenresTableHelper(5,4);
        gameGenresTableHelper(6,5);
        gameGenresTableHelper(6,6);
        gameGenresTableHelper(7,6);
        gameGenresTableHelper(8,5);
    }

    //Runs the checking method assertRowExists so you don't have to keep creating maps
    private void genreTableHelper(int id, String name){
        Map<String, Object> conditions = new LinkedHashMap<>();
        conditions.put("id",id);
        conditions.put("name",name);
        assertRowExists("genre",conditions);
    }

    @Test
    void genreTableData(){
        genreTableHelper(1,"Strategy");
        genreTableHelper(2,"Adventure");
        genreTableHelper(3,"Simulation");
        genreTableHelper(4,"Educational");
        genreTableHelper(5,"Puzzle");
        genreTableHelper(6,"Narrative");
    }

    //Runs the checking method assertRowExists so you don't have to keep creating maps
    private void reviewTableHelper(int game_id,int id, int rating, String comment, String reviewer_name){
        Map<String, Object> conditions = new LinkedHashMap<>();
        conditions.put("game_id",game_id);
        conditions.put("id",id);
        conditions.put("rating",rating);
        //conditions.put("review_date", review_date); //Can't check as it's done on local time, assume it's okay?
        conditions.put("comment", comment);
        conditions.put("reviewer_name",reviewer_name);
        assertRowExists("review",conditions);
    }


    @Test
    void reviewTableData(){
        reviewTableHelper(1,1,5,"Funny and surprisingly challenging!", "Sofia");
        reviewTableHelper(2,2,4,"What even is this game?!", "Jamie");
        reviewTableHelper(3,3,5,"An entertaining way to learn about running a market!", "Tom");
        reviewTableHelper(4,4,4,"The different plants make every magic garden unique.", "Ayesha");
        reviewTableHelper(5,5,5,"The programming puzzles were challenging and enjoyable!", "Pierre");
        reviewTableHelper(6,6,5,"The clockwork puzzles were clever!", "Lucie");
        reviewTableHelper(7,7,4,"I wish I was part motorbike.", "Priya");
        reviewTableHelper(7,8,3,"The story was interesting but some puzzles were difficult.", "Harriet");

    }

    //Runs the checking method assertRowExists so you don't have to keep creating maps
    private void screenshotTableHelper(int id, int studio_id, String description, String image_url){
        Map<String, Object> conditions = new LinkedHashMap<>();
        conditions.put("id",id);
        conditions.put("studio_id", studio_id);
        conditions.put("description", description);
        conditions.put("image_url",image_url);
        assertRowExists("screenshot",conditions);
    }

    @Test
    void screenshotTableData(){
        screenshotTableHelper(1,1,"A chess board.","silly-chess.jpg");
        screenshotTableHelper(2,1,"Dr. Funky poses.","funky-adventure.jpg");
        screenshotTableHelper(3,2,"A busy market.","market-manager.jpg");
        screenshotTableHelper(4,2,"A magic garden.","magic-garden.jpg");
        screenshotTableHelper(5,2,"Image of code and a cute plush toy.","code-quest.jpg");
        screenshotTableHelper(6,3,"Clocks and interlocking gears.","clockwork-maze.jpg");
        screenshotTableHelper(7,3,"Teddy bear in a car.","motobear.jpg");
    }

    //Runs the checking method assertRowExists so you don't have to keep creating maps
    private void studioTableHelper(int id,int profile_id, String headquarters, String name, String specialism){
        Map<String, Object> conditions = new LinkedHashMap<>();
        conditions.put("id",id);
        conditions.put("profile_id",profile_id);
        conditions.put("specialism",specialism);
        conditions.put("headquarters", headquarters);
        conditions.put("name",name);
        assertRowExists("studio",conditions);
    }

    @Test
    void studioTableData(){
        studioTableHelper(1,1,"London","Pixel Forge","Puzzle and Strategy Games");
        studioTableHelper(2,2,"Leicester","Foxglove Games","Simulation and Educational Games");
        studioTableHelper(3,3,"Manchester","Moonbeam Interactive","Narrative Adventure Games");

    }

    //Runs the checking method assertRowExists so you don't have to keep creating maps
    private void studioProfileTableHelper(int id, String bio, String social_media_handle, String website){
        Map<String, Object> conditions = new LinkedHashMap<>();
        conditions.put("id",id);
        conditions.put("bio",bio);
        conditions.put("social_media_handle", social_media_handle);
        conditions.put("website",website);
        assertRowExists("studio_profile",conditions);
    }

    @Test
    void studioProfileTableData(){
        studioProfileTableHelper(1,"An independent studio creating imaginative puzzle and strategy games.","@pixelforgegames","http://pixelforge.fakewebsite.com");
        studioProfileTableHelper(2,"A Leicester-based studio creating accessible games inspired by local stories.","@foxglovegames","http://foxglovegames.fakewebsite.com");
        studioProfileTableHelper(3,"Creating atmospheric games based on exploration, mystery, and storytelling.","@moonbeaminteractive","http://moonbeaminteractive.fakewebsite.com");

    }

    //Helper method to check if data exists in table for Task 6
    private void assertRowExists(String tableName, Map<String, ?> columnsAndValues) {
        if (columnsAndValues.isEmpty()) {
            throw new IllegalArgumentException("columnsAndValues must not be empty");
        }

        // Build WHERE clause: "col1 = ? AND col2 = ? ..."
        StringBuilder where = new StringBuilder();
        Object[] params = new Object[columnsAndValues.size()];
        int i = 0;
        for (Map.Entry<String, ?> entry : columnsAndValues.entrySet()) {
            if (i > 0) where.append(" AND ");
            where.append(entry.getKey()).append(" = ?");
            params[i++] = entry.getValue();
        }

        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE " + where;

        System.out.println("SQL: " + sql);
        System.out.println("SQL: " + Arrays.toString(params));

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, params);
        Assertions.assertTrue(count != null && count > 0,
                "Expected at least one matching row in '" + tableName + "' for conditions: " + columnsAndValues);
    }

    //Helper method to check table existence after Task 2
    //Gets URL from application properties
    private void assertTableExists(String tableName) {
        Properties p = new Properties();
        String URL = "";
        try {
            p.load(new FileReader("src/main/resources/application.properties"));
            int count = StringUtils.countOccurrencesOf(p.getProperty("spring.datasource.url"),"/");
            URL = p.getProperty("spring.datasource.url").split("/")[count];
        } catch (Exception e){
            fail("Cannot get database URl from application.properties");
        }

        if(URL.equals("")){
            fail("Cannot get database URl from application.properties");
        }

        Query query = entityManager.createNativeQuery(
                "SELECT COUNT(*) FROM information_schema.tables " +
                        "WHERE table_schema = '"+URL+"' AND table_name = '" + tableName + "'"
        );
        Number count = (Number) query.getSingleResult();
        Assertions.assertTrue(count.intValue() > 0, "Table '" + tableName + "' should exist in schema '"+URL+"'");
    }

    //Helper method to check columns exist
    private void assertColumnsExist(String tableName, String[] columns) {
        Properties p = new Properties();
        String URL = "";
        try {
            p.load(new FileReader("src/main/resources/application.properties"));
            int count = StringUtils.countOccurrencesOf(p.getProperty("spring.datasource.url"),"/");
            URL = p.getProperty("spring.datasource.url").split("/")[count];
        } catch (Exception e){
            fail("Cannot get database URl from application.properties");
        }

        if(URL.equals("")){
            fail("Cannot get database URl from application.properties");
        }

        for (String column : columns) {
            Query query = entityManager.createNativeQuery(
                    "SELECT COUNT(*) FROM information_schema.columns " +
                            "WHERE table_schema = '"+URL+"' AND table_name = '" + tableName + "' AND column_name = '" + column + "'"
            );
            Number count = (Number) query.getSingleResult();
            Assertions.assertTrue(count.intValue() > 0,
                    "Column '" + column + "' should exist in table '" + tableName + "'");
        }
    }

    //Helper method to find files
    private boolean classExists(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    //Helper method for counting amount of rows in a table
    private int rowCountChecker(String table){
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + table, Integer.class);
        return count;
    }
}
