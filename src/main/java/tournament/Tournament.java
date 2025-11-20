package tournament;

import java.util.*;

public abstract class Tournament {
    protected List<Participant> participants;
    protected Random rng = new Random();

    public Tournament() {
        participants = new ArrayList<>();
    }

    public void addParticipant(Participant p) {
        participants.add(p);
    }

    public void removeParticipant(int index) {
        participants.remove(index);
    }

    public int size() {
        return participants.size();
    }

    public List<Participant> getParticipants() {
        return Collections.unmodifiableList(participants);
    }

    public void listParticipants() {
        if (participants.isEmpty()) {
            System.out.println("No participants registered yet.");
            return;
        }
        for (int i = 0; i < participants.size(); i++)
            System.out.printf("%d. %s%n", i + 1, participants.get(i).getDisplayName());
    }

    public void showBracket() {
        if (participants.isEmpty()) {
            System.out.println("Bracket is empty.");
            return;
        }

        System.out.println("\n=== TOURNAMENT BRACKET ===");
        for (int i = 0; i < participants.size(); i += 2) {
            if (i + 1 < participants.size()) {
                System.out.println("|-- " + participants.get(i).getDisplayName());
                System.out.println("|      \");
                System.out.println("|       > TBD Winner");
                System.out.println("|      /");
                System.out.println("|-- " + participants.get(i + 1).getDisplayName() + "\n");
            } else {
                System.out.println("|-- " + participants.get(i).getDisplayName() + " (bye)\n");
            }
        }
    }

    public void startTournament() {
        if (participants.size() < 2) {
            System.out.println("Not enough participants to start!");
            return;
        }

        List<Participant> round = new ArrayList<>(participants);
        Map<Participant, Integer> eliminatedRound = new HashMap<>();
        int roundNum = 1;

        System.out.println("\nTOURNAMENT START");

        while (round.size() > 1) {
            System.out.println("\n=== ROUND " + roundNum + " ===");
            List<Participant> nextRound = new ArrayList<>();

            for (int i = 0; i < round.size(); i += 2) {
                if (i + 1 < round.size()) {
                    Participant p1 = round.get(i);
                    Participant p2 = round.get(i + 1);
                    Participant winner = rng.nextBoolean() ? p1 : p2;

                    System.out.println("|-- " + p1.getDisplayName());
                    System.out.println("|      \");
                    System.out.println("|       > Winner: " + winner.getDisplayName());
                    System.out.println("|      /");
                    System.out.println("|-- " + p2.getDisplayName() + "\n");

                    nextRound.add(winner);
                    Participant loser = (winner == p1) ? p2 : p1;
                    eliminatedRound.put(loser, roundNum);
                } else {
                    System.out.println("|-- " + round.get(i).getDisplayName() + " advances automatically (bye)\n");
                    nextRound.add(round.get(i));
                }
            }
            round = nextRound;
            roundNum++;
        }

        Participant champion = round.get(0);
        System.out.println("Champion: " + champion.getDisplayName());

        List<Participant> ranking = new ArrayList<>(participants);
        ranking.remove(champion);
        int finalRoundNum = roundNum;
        ranking.sort((a, b) -> {
            int r1 = eliminatedRound.getOrDefault(a, finalRoundNum);
            int r2 = eliminatedRound.getOrDefault(b, finalRoundNum);
            return Integer.compare(r2, r1);
        });
        ranking.add(0, champion);

        System.out.println("\n=== FINAL RANKINGS ===");
        for (int i = 0; i < ranking.size(); i++)
            System.out.printf("%d. %s%n", i + 1, ranking.get(i).getDisplayName());
    }

    public abstract Participant createParticipantFromInput(Scanner sc);
    public abstract String getParticipantLabel();
}