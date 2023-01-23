package com.example.q3e;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.io.IOException;
import java.util.Objects;

public class Start {
    private Logger logger= LogManager.getLogger("Start menu");
    @FXML
    private TextField serverdb;
    @FXML
    private TextField Qname;

    private Stage stage;

    private Scene scene;
    private Parent root;
    @FXML
    protected void takeQ(ActionEvent event) throws IOException {
        logger.debug(serverdb.getText()+ " -> "+Qname.getText());
        DatebaseActions db = new DatebaseActions(serverdb.getText());
        db.setQuiz(Qname.getText());
        Document col=db.getQuiz();
        logger.debug("db doc loaded");
        QuizController qz=QuizController.setFromDocument(col);
        logger.info("action: TAKE "+Qname.getText());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        root=loader.load();

        HelloController take=loader.getController();
        take.setQz(qz);

        //root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
