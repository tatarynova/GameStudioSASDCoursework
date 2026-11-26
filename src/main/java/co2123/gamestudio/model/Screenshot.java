package co2123.gamestudio.model;
import jakarta.persistence.*;

@Entity
public class Screenshot {
    @Id
    @GeneratedValue
    private int id;
    private String imageUrl;
    private String description;

    @ManyToOne
    @JoinColumn
    private Studio studio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }
}

