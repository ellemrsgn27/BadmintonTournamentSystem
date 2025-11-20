package tournament;

public class Player extends Participant {
    private String name;

    public Player(String name) {
        super();
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Player name cannot be empty.");
        this.name = name.trim();
        this.label = "Player: " + this.name;
    }

    @Override
    public String getDisplayName() {
        return name;
    }
}