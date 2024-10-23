# Quiz Application

The **Quiz Application** is a JavaFX-based program that presents users with multiple-choice questions, tracks their scores, and provides a time limit for answering each question.

## Features

- Multiple-choice questions with four options.
- Score tracking based on correct answers.
- Countdown timer for each question.
- Displays the final score after the quiz.

## How to Run

### Prerequisites

- **Java JDK 8** or later installed.
- **JavaFX SDK** (for JavaFX applications). Ensure that JavaFX is properly configured in your IDE.

### Downloading Dependencies

1. **JavaFX SDK**: Download the JavaFX SDK from [here](https://openjfx.io/). Extract the downloaded file to a known location on your system.

### Adding JavaFX to Your IDE

#### For IntelliJ IDEA:

1. Open your project in IntelliJ IDEA.
2. Go to **File** -> **Project Structure**.
3. Under **Project Settings**, select **Libraries**.
4. Click the **+** icon and choose **Java**.
5. Navigate to the `lib` folder of the JavaFX SDK you downloaded and select all the `.jar` files.
6. Click **OK** to add the library to your project.
7. Ensure to add the following VM options in **Run/Debug Configurations**:
    --module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml

#### For Eclipse:

1. Open your project in Eclipse.
2. Right-click on your project and select **Properties**.
3. Go to **Java Build Path** and then the **Libraries** tab.
4. Click on **Add External JARs** and navigate to the `lib` folder of the JavaFX SDK you downloaded. Select all the `.jar` files.
5. Click **Apply and Close**.
6. Add the following VM arguments in your run configuration:
    --module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml

### Compiling and Running

1. Open your terminal or command prompt.
2. Navigate to the directory containing the `QuizApp.java` file.
3. Compile the Java file using the following command:
    javac QuizApp.java
    java QuizApp
