import java.util.*;

abstract class Participant {
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

class Player extends Participant {
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

class Team extends Participant {
    private String teamName;
    private String player1;
    private String player2;

    public Team(String teamName, String player1, String player2) {
        super();
        setTeamName(teamName);
        setPlayer1(player1);
        setPlayer2(player2);
    }

    public String getTeamName() { return teamName; }

    public void setTeamName(String teamName) {
        if (teamName == null || teamName.trim().isEmpty())
            throw new IllegalArgumentException("Team name cannot be empty.");
        this.teamName = teamName.trim();
        updateLabel();
    }

    public String getPlayer1() { return player1; }

    public void setPlayer1(String player1) {
        if (player1 == null || player1.trim().isEmpty())
            throw new IllegalArgumentException("Player 1 name cannot be empty.");
        this.player1 = player1.trim();
        updateLabel();
    }

    public String getPlayer2() { return player2; }

    public void setPlayer2(String player2) {
        if (player2 == null || player2.trim().isEmpty())
            throw new IllegalArgumentException("Player 2 name cannot be empty.");
        this.player2 = player2.trim();
        updateLabel();
    }

    private void updateLabel() {
        if (teamName != null && player1 != null && player2 != null)
            this.label = "Team: " + teamName + " (" + player1 + " & " + player2 + ")";
    }

    @Override
    public String getDisplayName() {
        return teamName + " (" + player1 + " & " + player2 + ")";
    }
}

abstract class Tournament {
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
                System.out.println("|      \\");
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

                    // Match display
                    System.out.println("|-- " + p1.getDisplayName());
                    System.out.println("|      \\");
                    System.out.println("|       > Winner: " + winner.getDisplayName());
                    System.out.println("|      /");
                    System.out.println("|-- " + p2.getDisplayName() + "\n");

                    nextRound.add(winner);

                    // Record eliminated player/team
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

        // FINAL RANKINGS
        List<Participant> ranking = new ArrayList<>(participants);
        ranking.remove(champion);

        int finalRoundNum = roundNum;

        ranking.sort((a, b) -> {
            int r1 = eliminatedRound.getOrDefault(a, finalRoundNum);
            int r2 = eliminatedRound.getOrDefault(b, finalRoundNum);
            return Integer.compare(r2, r1);
        });

        ranking.add(0, champion);

        // DISPLAY FINAL RANKINGS
        System.out.println("\n=== FINAL RANKINGS ===");
        for (int i = 0; i < ranking.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, ranking.get(i).getDisplayName());
        }
    }

    public abstract Participant createParticipantFromInput(Scanner sc);
    public abstract String getParticipantLabel();
}

class SinglesTournament extends Tournament {
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

    public String getParticipantLabel() {
        return "Player";
    }
}

class DoublesTournament extends Tournament {
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

    public String getParticipantLabel() {
        return "Team";
    }
}

public class BadmintonTournamentSystem {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Badminton Tournament System 2025!\n");

        Tournament tournament = null;
        int competitionType = 0;

        while (competitionType != 1 && competitionType != 2) {
            System.out.println("Choose Competition Type:");
            System.out.println("1. Singles Competition");
            System.out.println("2. Doubles Competition");
            System.out.print("Enter choice: ");

            try {
                competitionType = Integer.parseInt(sc.nextLine().trim());
                if (competitionType != 1 && competitionType != 2)
                    System.out.println("Invalid choice! Try again.\n");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter 1 or 2.\n");
            }
        }

        if (competitionType == 1)
            tournament = new SinglesTournament();
        else
            tournament = new DoublesTournament();

        boolean running = true;

        while (running) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Register " + tournament.getParticipantLabel());
            System.out.println("2. View " + tournament.getParticipantLabel() + "s / Bracket");
            System.out.println("3. Start Tournament");
            System.out.println("4. Update " + tournament.getParticipantLabel());
            System.out.println("5. Delete " + tournament.getParticipantLabel());
            System.out.println("0. Exit");
            System.out.print("Select option: ");

            String line = sc.nextLine().trim();
            int choice;

            try {
                choice = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1: {
                    Participant p = tournament.createParticipantFromInput(sc);
                    if (p != null) {
                        if (existsEquivalent(tournament, p))
                            System.out.println(tournament.getParticipantLabel() + " already exists!");
                        else {
                            tournament.addParticipant(p);
                            System.out.println(tournament.getParticipantLabel() + " registered successfully!");
                        }
                    }
                    break;
                }

                case 2: {
                    tournament.listParticipants();
                    tournament.showBracket();
                    break;
                }

                case 3: {
                    tournament.startTournament();
                    break;
                }

                case 4: {
                    if (tournament.size() == 0) {
                        System.out.println("No entries to update.");
                        break;
                    }

                    tournament.listParticipants();
                    System.out.print("Enter number to update: ");

                    try {
                        int idx = Integer.parseInt(sc.nextLine().trim()) - 1;

                        if (idx >= 0 && idx < tournament.size()) {
                            Participant old = tournament.getParticipants().get(idx);

                            if (old instanceof Player) {
                                System.out.print("Enter new player name: ");
                                String newName = sc.nextLine().trim();
                                ((Player) old).setName(newName);
                                System.out.println("Updated successfully!");

                            } else if (old instanceof Team) {
                                System.out.print("Enter new team name: ");
                                String tname = sc.nextLine().trim();

                                System.out.print("Enter new Player 1 name: ");
                                String p1 = sc.nextLine().trim();

                                System.out.print("Enter new Player 2 name: ");
                                String p2 = sc.nextLine().trim();

                                ((Team) old).setTeamName(tname);
                                ((Team) old).setPlayer1(p1);
                                ((Team) old).setPlayer2(p2);

                                System.out.println("Updated successfully!");
                            }

                        } else
                            System.out.println("Invalid number!");

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input!");
                    }
                    break;
                }

                case 5: {
                    if (tournament.size() == 0) {
                        System.out.println("No entries to delete.");
                        break;
                    }

                    tournament.listParticipants();
                    System.out.print("Enter number to delete: ");

                    try {
                        int idx = Integer.parseInt(sc.nextLine().trim()) - 1;

                        if (idx >= 0 && idx < tournament.size()) {
                            Participant removed = tournament.getParticipants().get(idx);
                            tournament.removeParticipant(idx);
                            System.out.println("Deleted: " + removed.getDisplayName());
                        } else {
                            System.out.println("Invalid number!");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input!");
                    }
                    break;
                }

                case 0: {
                    running = false;
                    System.out.println("Exiting system. Goodbye!");
                    break;
                }

                default:
                    System.out.println("Invalid option!");
            }
        }
        sc.close();
    }

    private static boolean existsEquivalent(Tournament t, Participant candidate) {
        for (Participant p : t.getParticipants()) {
            if (p.getDisplayName().equalsIgnoreCase(candidate.getDisplayName()))
                return true;
        }
        return false;
    }
}
