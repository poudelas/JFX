package sample;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void init() throws Exception {
        //Heavy Loading
        Thread.sleep(5000);
    }


    @Override
    public void start(Stage primaryStage) throws Exception{

        BorderPane root = new BorderPane(new Label("Loading complete!"));
        Scene scene = new Scene(root);
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        LauncherImpl.launchApplication(Main.class, RMSPreloader.class, args);
    }
}
