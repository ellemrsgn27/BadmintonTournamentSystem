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