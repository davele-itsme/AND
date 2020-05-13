package dk.via.sharestead.model;

public class Game {
    private int id;
    private String name;
    private String backgroundImage;
    public Game(int id, String name, String backgroundImage) {
        this.id = id;
        this.name = name;
        this.backgroundImage = backgroundImage;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }
}
