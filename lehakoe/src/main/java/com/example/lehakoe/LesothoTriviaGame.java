package com.example.lehakoe;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.File;

public class LesothoTriviaGame extends Application {
    private int score = 0;
    private int questionIndex = 0;

    // Define questions, answers, and corresponding images
    private String[] questions = {
            "What is the capital city of Lesotho?",
            "Which mountain is the highest point in Lesotho?",
            "What is the official language of Lesotho?",
            "What is the traditional hat worn by Basotho people called?"
    };
    private String[][] options = {
            {"Maseru", "Leribe", "Mafeteng", "Qacha's Nek"},
            {"Drakensberg", "Limmele", "Lesoba", "ThabanaNtlenyana"},
            {"Sesotho", "English", "Zulu", "Sepedi"},
            {"Mokorotlo", "sepoti", "Kupaete", "Kepisi"},


    };
    private int[] correctAnswers = {0, 3, 0, 0};

    // Array of image paths corresponding to each question
    private String[] imagePaths = {
            "/images/Maseru.jpg",
            "/images/Thabana ntlenyane.jpg",
            "/images/Sesotho language.jpg",
            "/images/mokorotlo.jpg",


    };

    // Array of video paths corresponding to each question
    private String[] videoPaths = {
            "D:/video/Maseru.mp4",
            "D:/video/ThabanaNtlenyana.mp4",
            "D:/video/SesothoLanguage.mp4",
            "D:/video/Mokorotlo.mp4"
    };

    private Label questionLabel;
    private Button[] optionButtons;
    private ImageView imageView;
    private Label scoreLabel;
    private BorderPane root;
    private MediaPlayer mediaPlayer;

    @Override
    public void start(Stage primaryStage) {
        root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);

        // Question label
        questionLabel = new Label();
        questionLabel.setStyle("-fx-font-size: 20;");
        root.setTop(questionLabel);
        BorderPane.setAlignment(questionLabel, Pos.CENTER);

        // Image view
        imageView = new ImageView();
        imageView.setFitHeight(400);
        imageView.setPreserveRatio(true);
        root.setCenter(imageView);
        BorderPane.setAlignment(imageView, Pos.CENTER);

        // Score label
        scoreLabel = new Label("Score: 0");
        scoreLabel.setStyle("-fx-font-size: 16;");
        root.setLeft(scoreLabel);
        BorderPane.setAlignment(scoreLabel, Pos.CENTER_LEFT);

        // Options buttons
        HBox optionsBox = new HBox(10);
        optionButtons = new Button[4];
        for (int i = 0; i < optionButtons.length; i++) {
            int index = i;
            optionButtons[i] = new Button();
            optionButtons[i].setOnAction(e -> checkAnswer(index));
            optionsBox.getChildren().add(optionButtons[i]);
        }
        root.setBottom(optionsBox);
        optionsBox.setAlignment(Pos.CENTER);

        // Next Question button
        Button nextQuestionButton = new Button("Next Question");
        nextQuestionButton.setOnAction(e -> displayNextQuestion());
        root.setRight(nextQuestionButton);
        BorderPane.setAlignment(nextQuestionButton, Pos.CENTER_RIGHT);

        primaryStage.setTitle("Lesotho Trivia Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Start the game
        displayQuestion();
    }

    private void displayQuestion() {
        if (questionIndex < questions.length) {
            questionLabel.setText(questions[questionIndex]);
            for (int i = 0; i < optionButtons.length; i++) {
                optionButtons[i].setText(options[questionIndex][i]);
            }
            try {
                Image image = new Image(getClass().getResourceAsStream(imagePaths[questionIndex]));
                imageView.setImage(image);
            } catch (Exception e) {
                System.err.println("Error loading image: " + e.getMessage());
            }

            // Play video for the question
            try {
                Media media = new Media(new File(videoPaths[questionIndex]).toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                MediaView mediaView = new MediaView(mediaPlayer);
                root.setCenter(mediaView);
                mediaPlayer.setOnError(() -> {
                    System.err.println("Error occurred while playing media.");
                });
                mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(javafx.util.Duration.ZERO));
                mediaPlayer.play();
            } catch (Exception e) {
                System.err.println("Error loading media: " + e.getMessage());
            }
        } else {
            endGame();
        }
    }

    private void displayNextQuestion() {
        questionIndex++;
        if (questionIndex < questions.length) {
            displayQuestion();
        } else {
            endGame();
        }
    }

    private void checkAnswer(int selectedOption) {
        if (selectedOption == correctAnswers[questionIndex]) {
            score++;
        }
        scoreLabel.setText("Score: " + score);
    }

    private void endGame() {
        // Display final score summary
        String message = "Congratulations! You have completed the trivia game.\n";
        message += "Your final score is: " + score + " out of " + questions.length + ".\n";
        message += "Thank you for playing!";
        questionLabel.setText("Trivia Game Completed");
        imageView.setImage(null); // Clear image
        root.setCenter(null); // Clear media view
        for (Button button : optionButtons) {
            button.setVisible(false); // Hide option buttons
        }
        scoreLabel.setVisible(false); // Hide score label
        root.setBottom(null); // Remove next question button
        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
        VBox messageBox = new VBox(10);
        messageBox.getChildren().add(messageLabel);
        messageBox.setAlignment(Pos.CENTER);
        root.setCenter(messageBox);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
