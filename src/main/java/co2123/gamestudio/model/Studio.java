package co2123.gamestudio.model;

import java.util.List;
import jakarta.persistence.*;

@Entity
public class Studio {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String headquarters;
    private String specialism;

    @OneToMany(mappedBy = "studio")
    private List<Game> games;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private StudioProfile profile;

    @OneToMany(mappedBy = "studio")
    private List<Screenshot> screenshots;

    @OneToMany(mappedBy = "studio")
    private List<Award> awards;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadquarters() {
        return headquarters;
    }

    public void setHeadquarters(String headquarters) {
        this.headquarters = headquarters;
    }

    public String getSpecialism() {
        return specialism;
    }

    public void setSpecialism(String specialism) {
        this.specialism = specialism;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public StudioProfile getProfile() {
        return profile;
    }

    public void setProfile(StudioProfile profile) {
        this.profile = profile;
    }

    public List<Screenshot> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(List<Screenshot> screenshots) {
        this.screenshots = screenshots;
    }

    public List<Award> getAwards() {
        return awards;
    }

    public void setAwards(List<Award> awards) {
        this.awards = awards;
    }
}

