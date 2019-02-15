package pongAsLWay.pong.controller;

import pongAsLWay.pong.entity.Ball;
import pongAsLWay.pong.entity.Player;
import pongAsLWay.pong.setup.Screen;

import java.util.Random;

import static pongAsLWay.pong.entity.Player.PLAYER_WIDTH;

public class BallController {

    public void center(Ball ball) {
        ball.setPositionX(Screen.WIDTH / 2);
        ball.setPositionY(Screen.HEIGHT / 2);
        ball.setSpeedX(calculateStartingSpeed());
        ball.setSpeedY(calculateStartingSpeed());
    }

    private int calculateStartingSpeed() {
        return new Random().nextInt(2) == 0 ? 1 : -1;
    }

    public void move(Ball ball, Player player1, Player player2) {
        keepInsideTopAndBottomEdges(ball);
        if (needsReverse(ball, player1, player2)) {
            ball.increaseSpeed();
            ball.reverseSpeed();
        }
    }

    private void keepInsideTopAndBottomEdges(Ball ball) {
        int ballPositionY = ball.getPositionY();
        if (ballPositionY > Screen.HEIGHT || ballPositionY < 0) {
            ball.reverseSpeedY();
        }
    }

    private boolean needsReverse(Ball ball, Player player1, Player player2) {
        int ballPositionX = ball.getPositionX();
        int ballPositionY = ball.getPositionY();
        //TODO refactor this
        return (touchesPlayer2(player2, ball) && ballPositionY >= player2.getPositionY() && ballPositionY <= player2.getPositionY() + Player.PLAYER_HEIGHT) ||
                ((ballPositionX < player1.getPositionX() + PLAYER_WIDTH) && ballPositionY >= player1.getPositionY() && ballPositionY <= player1.getPositionY() + Player.PLAYER_HEIGHT);
    }

    private boolean touchesPlayer2(Player player, Ball ball) {
        return ball.getPositionX() + ball.getRadius() > player.getPositionX();
    }

}
