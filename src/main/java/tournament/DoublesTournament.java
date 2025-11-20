package tournament;

import java.util.Scanner;

public class DoublesTournament extends Tournament {
    @Override
    public Participant createParticipantFromInput(Scanner sc) {
        System.out.print("Enter team name: ");
        String teamName = sc.nextLine().trim();
        System.out.print("Enter Player 1 name: ");
        String p1 = sc.nextLine().trim();
        System.out.print("Enter Player 2 name: ");
        String p2 = sc.nextLine().trim();
        try {
            return new Team(teamName, p1, p2);
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
            return null;
        }
    }

    @Override
    public String getParticipantLabel() {
        return "Team";
    }
}