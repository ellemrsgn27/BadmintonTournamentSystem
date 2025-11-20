import java.util.Scanner;
import tournament.*;

public class BadmintonTournamentSystem {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Badminton Tournament System 2025!\n");
       
    }

    private static boolean existsEquivalent(Tournament t, Participant candidate) {
        for (Participant p : t.getParticipants()) {
            if (p.getDisplayName().equalsIgnoreCase(candidate.getDisplayName()))
                return true;
        }
        return false;
    }
}