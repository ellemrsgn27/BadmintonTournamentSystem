import java.util.*;

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
