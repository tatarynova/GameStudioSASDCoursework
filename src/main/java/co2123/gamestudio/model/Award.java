package co2123.gamestudio.model;

import jakarta.persistence.*;

@Entity
public class Award {
    @Id
    @GeneratedValue
    private int id;
    private String title;
    private int year;

    @ManyToOne
    @JoinColumn
    private Studio studio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }
}

