package com.mobiledexterous.gaming;

public class GameData {

    String attemptNo;
    String gameId;
    String score;

    public GameData(){


    }

    public GameData ( String attemptNo, String gameId, String score ){

        this.attemptNo = attemptNo;
        this.gameId = gameId;
        this.score = score;

    }

    public void setAttemptNo(String attemptNo) {
        this.attemptNo = attemptNo;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getAttemptNo() {
        return attemptNo;
    }

    public String getGameId() {
        return gameId;
    }

    public String getScore() {
        return score;
    }

}
