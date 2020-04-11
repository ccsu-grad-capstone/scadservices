package edu.ccsu.cs595.capstone.scadservices.exception;

public class EmptyPlayersArrayException extends Exception {

    private static final long serialVersionUID = 1L;
    private Long previousYearGameKey;
    private Long previousYearLeagueId;

    public EmptyPlayersArrayException() {
        super("A Yahoo payload was returned with an empty players array.");
    }

    public Long getPreviousYearGameKey() {
        return previousYearGameKey;
    }

    public void setPreviousYearGameKey(Long previousYearGameKey) {
        this.previousYearGameKey = previousYearGameKey;
    }

    public Long getPreviousYearLeagueId() {
        return previousYearLeagueId;
    }

    public void setPreviousYearLeagueId(Long previousYearLeagueId) {
        this.previousYearLeagueId = previousYearLeagueId;
    }
}
