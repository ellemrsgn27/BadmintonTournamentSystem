package tournament;

import java.util.Scanner;

public class SinglesTournament extends Tournament {
    @Override
    public Participant createParticipantFromInput(Scanner sc) {
        System.out.print("Enter player name: ");
        String name = sc.nextLine().trim();

        try {
            return new Player(name);
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }

    @Override
    public String getParticipantLabel() {
        return "Player";
    }
}
