package pongAsLWay.pong.entity;

public class Ball {

    private int radius = 15;
    private int positionX;
    private int positionY;
    private int speedX = 1;
    private int speedY = 1;

    public Ball(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public int getRadius() {
        return radius;
    }

    public void move() {
        positionX += speedX;
        positionY += speedY;
    }

    public void reverseSpeedY() {
        speedY *= -1;
    }

    public void reverseSpeed() {        
        speedX *= -1;
        speedY *= -1;
    }
    
    public void increaseSpeed(){
        speedX += 1 * Math.signum(speedX);
        speedY += 1 * Math.signum(speedY);
    }

}
