import java.io.File;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    // Declare class variables
    ImageView main, core;
    Text name, tagLine, msg, c2w, tagLine2;
    Button selectDirectory, aboutUs;
    Stage pStage;

    @Override
    public void start(Stage primaryStage) {
        pStage = primaryStage;

        // Set up the main stage
        primaryStage.setTitle("Dilemma IDE");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("ide.png"));

        // Create and configure image views, text elements, and buttons
        main = new ImageView(new Image("main_img.jpg"));
        main.setFitHeight(630);
        main.setFitWidth(1030);

        core = new ImageView(new Image("core_main.png"));
        core.setFitHeight(150);
        core.setFitWidth(150);

        name = new Text("Dilemma IDE");
        name.setFill(Color.ORANGE);
        name.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 80));

        c2w = new Text("Core2web");
        c2w.setFill(Color.DARKGRAY);
        c2w.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));

        tagLine = new Text("Unlock Your Full Coding Potential");
        tagLine.setFill(Color.LIGHTYELLOW);
        tagLine.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.ITALIC, 35));

        msg = new Text("Please Select Directory");
        msg.setFill(Color.ORANGERED);
        msg.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 24));

        selectDirectory = new Button("Select");
        selectDirectory.setPrefSize(120, 15);

        aboutUs = new Button("About Us");
        aboutUs.setPrefSize(120, 15);

        tagLine2 = new Text("When Choices Are Not 'IF' or 'ELSE', It's a Dilemma");
        tagLine2.setFill(Color.DARKKHAKI);
        tagLine2.setFont(Font.font("Arial", 20));

        // Set action for the "Select" button
        selectDirectory.setOnAction(e -> selectDirectoryToOpen());

        // Create StackPane layouts for each element
        StackPane sp = new StackPane(main);
        sp.setLayoutX(0);
        sp.setLayoutY(0);

        StackPane sp1 = new StackPane(name);
        sp1.setLayoutX(180);
        sp1.setLayoutY(0);

        StackPane sp2 = new StackPane(tagLine);
        sp2.setLayoutX(330);
        sp2.setLayoutY(80);

        StackPane sp3 = new StackPane(msg);
        sp3.setLayoutX(350);
        sp3.setLayoutY(300);

        StackPane sp4 = new StackPane(selectDirectory);
        sp4.setLayoutX(430);
        sp4.setLayoutY(340);

        StackPane sp5 = new StackPane(core);
        sp5.setLayoutX(0);
        sp5.setLayoutY(-10);

        StackPane sp6 = new StackPane(c2w);
        sp6.setLayoutX(35);
        sp6.setLayoutY(130);

        StackPane sp7 = new StackPane(aboutUs);
        sp7.setLayoutX(880);
        sp7.setLayoutY(550);

        StackPane sp8 = new StackPane(tagLine2);
        sp8.setLayoutX(250);
        sp8.setLayoutY(585);

        // Create a Group to hold all the StackPanes
        Group root = new Group(sp, sp1, sp2, sp3, sp4, sp5, sp6, sp7, sp8);

        // Create the main scene
        Scene mainScene = new Scene(root, 1000, 600, Color.SKYBLUE);

        primaryStage.setScene(mainScene);

        // Play entrance animations
        playAnimations();

        primaryStage.show();
    }

    void playAnimations() {
        // Create and configure various animation transitions

        RotateTransition rotateTransition = new RotateTransition(new Duration(5000), core);
        rotateTransition.setByAngle(360);
        rotateTransition.setAxis(Rotate.Y_AXIS);
        rotateTransition.setCycleCount(Animation.INDEFINITE);
        rotateTransition.play();

        TranslateTransition ts = new TranslateTransition(Duration.seconds(4), name);
        ts.setFromX(-120);
        ts.setToX(0);
        ts.play();

        TranslateTransition ts2 = new TranslateTransition(Duration.seconds(3), main);
        ts2.setFromY(600);
        ts2.setToY(0);
        ts2.play();

        TranslateTransition ts3 = new TranslateTransition(Duration.seconds(4), tagLine);
        ts3.setFromX(500);
        ts3.setToX(0);
        ts3.play();

        TranslateTransition ts4 = new TranslateTransition(Duration.seconds(4), core);
        ts4.setFromY(-100);
        ts4.setToY(0);
        ts4.play();

        TranslateTransition ts5 = new TranslateTransition(Duration.seconds(4), msg);
        ts5.setFromX(-200);
        ts5.setToX(0);
        ts5.play();

        TranslateTransition ts6 = new TranslateTransition(Duration.seconds(4), selectDirectory);
        ts6.setFromX(300);
        ts6.setToX(0);
        ts6.play();

        TranslateTransition ts7 = new TranslateTransition(Duration.seconds(4), c2w);
        ts7.setFromY(-100);
        ts7.setToY(0);
        ts7.play();

        TranslateTransition ts8 = new TranslateTransition(Duration.seconds(3), aboutUs);
        ts8.setFromX(80);
        ts8.setToX(0);
        ts8.play();

        TranslateTransition ts9 = new TranslateTransition(Duration.seconds(3), tagLine2);
        ts9.setFromY(100);
        ts9.setToY(0);
        ts9.play();
    }

    void selectDirectoryToOpen() {
        // Create a directory chooser dialog
        DirectoryChooser directoryChooser = new DirectoryChooser();

        // Show the dialog and get the selected directory
        File f = directoryChooser.showDialog(pStage);

        if (f == null) {
            // If no directory was selected, prompt the user again
            selectDirectoryToOpen();
        } else {
            // If a directory was selected, open the main application stage
            new MainStage(pStage, f).showMainStage();
        }
    }

}
