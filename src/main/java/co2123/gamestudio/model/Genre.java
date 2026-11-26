package co2123.gamestudio.model;

import java.util.List;
import jakarta.persistence.*;

@Entity
public class Genre {
    @Id
    @GeneratedValue
    private int id;
    private String name;

    @ManyToMany(mappedBy = "genres")
    private List<Game> games;

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

    public List<Game> getDishes() {
        return games;
    }

    public void setDishes(List<Game> games) {
        this.games = games;
    }
}

