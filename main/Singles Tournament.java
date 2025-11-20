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