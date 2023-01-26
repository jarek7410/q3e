package com.example.q3e;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class HelloApplication extends Application {

    static Logger logger =  LogManager.getLogger("Main");


    @Override
    public void start(Stage stage) throws IOException {
        logger.info("load menu");
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("start.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Start start=fxmlLoader.getController();
        start.setup();
        stage.setTitle("quizesses!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        logger.info("program start");
        launch();
    }

}