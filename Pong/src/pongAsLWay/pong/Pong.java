package pongAsLWay.pong;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import pongAsLWay.pong.controller.BallController;
import pongAsLWay.pong.entity.Ball;
import pongAsLWay.pong.entity.Player;
import pongAsLWay.pong.setup.Screen;

import static pongAsLWay.pong.entity.Player.PLAYER_WIDTH;

public class Pong extends Application {

    // Ball
    private Ball ball = new Ball(Screen.WIDTH / 2, Screen.HEIGHT / 2);
    private BallController ballController = new BallController();

    // Two players
    private Player player1 = new Player(0, Screen.HEIGHT / 2);
    private Player player2 = new Player(Screen.WIDTH - PLAYER_WIDTH, Screen.HEIGHT / 2);

    // Game
    private boolean isGameStarted;

    public void start(Stage stage) throws Exception {
        Canvas canvas = new Canvas(Screen.WIDTH, Screen.HEIGHT);
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), e -> loop(graphicsContext)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        stage.setScene(new Scene(new StackPane(canvas)));
        canvas.setOnMouseMoved(e -> player1.setPositionY((int) e.getY()));
        canvas.setOnMouseClicked(e -> isGameStarted = true);
        stage.show();
        timeline.play();
    }

    private void loop(GraphicsContext graphicsContext) {
        updateLogic();
        paint(graphicsContext);
    }

    private void updateLogic() {
        if (isGameStarted) {
            updateEntityMovements();
        } else {
            ballController.center(ball);
        }
        updateScores();
        ballController.move(ball, player1, player2);
    }

    private void paint(GraphicsContext graphicsContext) {
        paintBackground(graphicsContext);
        if (isGameStarted) {
            paintBall(graphicsContext);
        } else {
            paintStartGameText(graphicsContext);
        }
        paintScore(graphicsContext);
        paintBars(graphicsContext);
    }

    private void updateEntityMovements() {
        ball.move();
        moveComputerBar();
    }

    private int updateScores() {
        int ballPositionX = ball.getPositionX();
        if (ballPositionX < player1.getPositionX() - PLAYER_WIDTH) {
            player2.increaseScore();
            isGameStarted = false;
        }
        if (ballPositionX > player2.getPositionX() + PLAYER_WIDTH) {
            player1.increaseScore();
            isGameStarted = false;
        }
        return ballPositionX;
    }

    private void paintStartGameText(GraphicsContext gc) {
        gc.setStroke(Color.YELLOW);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.strokeText("Click to Start", Screen.WIDTH / 2, Screen.HEIGHT / 2);
    }

    private void moveComputerBar() {
        int ballPositionY = ball.getPositionY();
        if (ball.getPositionX() < Screen.WIDTH - Screen.WIDTH / 4) {
            player2.setPositionY(ballPositionY - Player.PLAYER_HEIGHT / 2);
        } else {
            player2.setPositionY(ballPositionY > player2.getPositionY() + Player.PLAYER_HEIGHT / 2 ? player2.getPositionY() + 1 : player2.getPositionY() - 1);
        }
    }

    private void paintBackground(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, Screen.WIDTH, Screen.HEIGHT);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(25));
    }

    private void paintBars(GraphicsContext gc) {
        gc.fillRect(player1.getPositionX(), player1.getPositionY(), PLAYER_WIDTH, Player.PLAYER_HEIGHT);
        gc.fillRect(player2.getPositionX(), player2.getPositionY(), PLAYER_WIDTH, Player.PLAYER_HEIGHT);
    }

    private void paintScore(GraphicsContext gc) {
        gc.fillText(player1.getScore() + "\t\t\t\t\t\t\t\t" + player2.getScore(), Screen.WIDTH / 2, 100);
    }

    private void paintBall(GraphicsContext gc) {
        gc.fillOval(ball.getPositionX(), ball.getPositionY(), ball.getRadius(), ball.getRadius());
    }

}