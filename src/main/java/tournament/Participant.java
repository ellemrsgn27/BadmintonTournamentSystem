package tournament;

import java.util.UUID;

public abstract class Participant {
    private UUID id;
    protected String label;

    public Participant() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public abstract String getDisplayName();

    public String getLabel() {
        return label == null ? getDisplayName() : label;
    }
}
