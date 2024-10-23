import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class QuizApp extends Application {

    private List<Question> questions;
    private int currentQuestionIndex;
    private int score;
    private Label questionLabel;
    private RadioButton[] options;
    private Button submitButton;
    private Label timerLabel;
    private Timeline timeline;
    private int timeLimit = 10;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        initializeQuestions();

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        questionLabel = new Label();
        questionLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        options = new RadioButton[4];
        ToggleGroup group = new ToggleGroup();

        for (int i = 0; i < options.length; i++) {
            options[i] = new RadioButton();
            options[i].setToggleGroup(group);
            options[i].setStyle("-fx-font-size: 14px;");
            layout.getChildren().add(options[i]);
        }

        submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        submitButton.setOnAction(e -> submitAnswer());

        timerLabel = new Label();
        timerLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: red;");

        layout.getChildren().addAll(questionLabel, timerLabel, submitButton);
        Scene scene = new Scene(layout, 400, 300);
        primaryStage.setTitle("Quiz Application");
        primaryStage.setScene(scene);
        primaryStage.show();

        loadQuestion();
    }

    private void initializeQuestions() {
        questions = new ArrayList<>();
        questions.add(new Question("What is the capital of France?", new String[]{"Berlin", "Madrid", "Paris", "Rome"}, 2));
        questions.add(new Question("What is 2 + 2?", new String[]{"3", "4", "5", "6"}, 1));
        questions.add(new Question("What is the color of the sky?", new String[]{"Blue", "Green", "Red", "Yellow"}, 0));
        currentQuestionIndex = 0;
        score = 0;
    }

    private void loadQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);
            questionLabel.setText("Q" + (currentQuestionIndex + 1) + ": " + question.getText());
            for (int i = 0; i < options.length; i++) {
                options[i].setText(question.getOptions()[i]);
                options[i].setSelected(false);
            }
            startTimer();
        } else {
            showResult();
        }
    }

    private void startTimer() {
        timerLabel.setText("Time left: " + timeLimit + " seconds");
        if (timeline != null) {
            timeline.stop();
        }

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeLimit--;
            timerLabel.setText("Time left: " + timeLimit + " seconds");
            if (timeLimit <= 0) {
                timeline.stop();
                if (currentQuestionIndex < questions.size() - 1) {
                    submitAnswer(); 
                } else {
                    showResult();
                }
            }
        }));
        timeline.setCycleCount(timeLimit);
        timeline.play();
    }

    private void submitAnswer() {
        timeline.stop();
        RadioButton selectedOption = (RadioButton) options[0].getToggleGroup().getSelectedToggle();
        if (selectedOption != null) {
            int selectedIndex = -1;
            for (int i = 0; i < options.length; i++) {
                if (options[i] == selectedOption) {
                    selectedIndex = i;
                    break;
                }
            }
            if (selectedIndex == questions.get(currentQuestionIndex).getCorrectAnswerIndex()) {
                score++;
            }
        }
        currentQuestionIndex++;
        timeLimit = 10;
        loadQuestion();
    }

    private void showResult() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Quiz Result");
        alert.setHeaderText("Your Score");
        alert.setContentText("You scored " + score + " out of " + questions.size());
        alert.showAndWait();
        System.exit(0);
    }

    private static class Question {
        private final String text;
        private final String[] options;
        private final int correctAnswerIndex;

        public Question(String text, String[] options, int correctAnswerIndex) {
            this.text = text;
            this.options = options;
            this.correctAnswerIndex = correctAnswerIndex;
        }

        public String getText() {
            return text;
        }

        public String[] getOptions() {
            return options;
        }

        public int getCorrectAnswerIndex() {
            return correctAnswerIndex;
        }
    }
}
