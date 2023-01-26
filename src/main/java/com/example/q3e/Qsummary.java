package com.example.q3e;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class Qsummary {
    public Label result;

    public void toStart(ActionEvent event) throws IOException {
        Stage stage;
        Scene scene;
        Parent root;
        HelloApplication.logger.info("load menu");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start.fxml"));
        root=loader.load();
        //root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void setResult(String s){
        result.setText(s);
    }
}
