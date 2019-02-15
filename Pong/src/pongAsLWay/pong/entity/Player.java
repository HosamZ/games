package pongAsLWay.pong.entity;

public class Player {

    public static final int PLAYER_HEIGHT = 100;
    public static final int PLAYER_WIDTH = 15;
    private double positionX;
    private double positionY;
    private int score = 0;

    public Player(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore() {
        score++;
    }
}
