package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.Random;

public class Pong extends Application {
    //stage
    private static final int Game_Borders_width = 2024;
    private static final int Game_Borders_height = 1024;
    //player/bar
    private static final int PLAYER_HEIGHT = 180;
    private static final int PLAYER_WIDTH = 60;
    private double playerComputerXPos = Game_Borders_width - PLAYER_WIDTH;
    private double playerComputerYPos = Game_Borders_height / 2;
    private int playerHumanXPos = 1;
    private double playerHumanYPos = Game_Borders_height / 2;
    //ball
    private static final double BALL_SIZE_R = 35;
    private int ballYDistance = 1;
    private int ballXDistance = 1;
    private double ballXPos = Game_Borders_width / 2;
    private double ballYPos = Game_Borders_height / 2;
    //score
    private int scoreP1 = 0;
    private int scoreP2 = 0;
    //game
    private boolean gameStarted;

    public void start(Stage stage) {
        startGame(stage);
    }

    private void startGame(Stage stage) {
        Timeline game = setupGame(stage);
        stage.show();
        game.play();
    }

    private Timeline setupGame(Stage stage) {
        Canvas canvas = new Canvas(Game_Borders_width, Game_Borders_height);
        GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
        Timeline timeLine = new Timeline(new KeyFrame(Duration.millis(4), e -> runGame(graphicsContext2D)));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        canvas.setOnMouseMoved(e ->  playerHumanYPos = e.getY());
        canvas.setOnMouseClicked(e -> gameStarted = true);
        stage.setScene(new Scene(new StackPane(canvas)));
        return timeLine;
    }

    private void runGame(GraphicsContext gameGraphics) {
        run(gameGraphics);
    }

    private void run(GraphicsContext graphicsContext) {
        setColorsUi(graphicsContext);
        if (gameStarted) {
            ballXPos += ballXDistance;
            ballYPos += ballYDistance;

            if (ballXPos < Game_Borders_width - Game_Borders_width / 4) {
                playerComputerYPos = ballYPos - PLAYER_HEIGHT / 2;
            } else {
                //cont  rol ball speed
                playerComputerYPos = ballYPos > playerComputerYPos + PLAYER_HEIGHT / 2 ? playerComputerYPos += 20
                        : playerComputerYPos - 20;
            }
            graphicsContext.fillOval(ballXPos,ballYPos,BALL_SIZE_R,BALL_SIZE_R);
//            graphicsContext.fillPolygon(ballXPos, ballYPos, 100, 90,60,30);
//            double starAngle = 0;

//            graphicsContext.fillPolygon(ballXPos,ballYDistance, BALL_SIZE_R, 15, 6, starAngle);

//            graphicsContext.fillArc(ballXPos, ballYPos, 200, BALL_SIZE_R,400,100, ArcType.ROUND);
        } else {
            paintStartingText(graphicsContext);
            fixBallPosition();
        }
        setScoresEncounter();
        setBallAccelerationComparedToPlayers();
        setupScoresAndPlayersPosition(graphicsContext);
    }

    private void setBallAccelerationComparedToPlayers() {
        if (((ballXPos + BALL_SIZE_R > playerComputerXPos)
                && ballYPos >= playerComputerYPos && ballYPos <= playerComputerYPos + PLAYER_HEIGHT) ||
                ((ballXPos < playerHumanXPos + PLAYER_WIDTH)
                        && ballYPos >= playerHumanYPos && ballYPos <= playerHumanYPos + PLAYER_HEIGHT)) {
            ballYDistance += 1 * Math.signum(ballYDistance);
            ballXDistance += 1 * Math.signum(ballXDistance);
            ballXDistance *= -1;
            ballYDistance *= -1;
        }
    }

    private void setupScoresAndPlayersPosition(GraphicsContext graphicsContext) {
        graphicsContext.fillText(scoreP1 + "\t\t\t\t\t\t\t\t" + scoreP2, Game_Borders_width / 2, 100);
        graphicsContext.fillRect(playerComputerXPos, playerComputerYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
        //here we set scores logic for both players
//        graphicsContext.fillRect(playerHuman2XPos, playerHuman2YPos, PLAYER_WIDTH, PLAYER_HEIGHT);
        graphicsContext.fillRect(playerHumanXPos, playerHumanYPos, PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    private void setScoresEncounter() {
        if (ballYPos > Game_Borders_height || ballYPos < 0) ballYDistance *= -1;
        if (ballXPos < playerHumanXPos - PLAYER_WIDTH) {
            scoreP2++;
            gameStarted = false;
        }
        if (ballXPos > playerComputerXPos + PLAYER_WIDTH) {
            scoreP1++;
            gameStarted = false;
        }
    }

    private void fixBallPosition() {
        ballXPos = Game_Borders_width / 2;
        ballYPos = Game_Borders_height / 2;
        ballXDistance = new Random().nextInt(2) == 0 ? 1 : -1;
        ballYDistance = new Random().nextInt(2) == 0 ? 1 : -1;
    }

    private void paintStartingText(GraphicsContext graphicsContext) {
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.strokeText("Click to Start", Game_Borders_width / 2, Game_Borders_height / 2);
        graphicsContext.setTextAlign(TextAlignment.CENTER);
    }

    private void setColorsUi(GraphicsContext graphicsContext) {
        graphicsContext.setFill(Color.DARKRED);
        graphicsContext.fillRect(0, 0, Game_Borders_width, Game_Borders_height);
        graphicsContext.setFill(Color.BLUEVIOLET);
        graphicsContext.setFont(Font.font(25));
    }
}