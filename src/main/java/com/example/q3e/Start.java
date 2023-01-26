package com.example.q3e;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Effect;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.io.IOException;
import java.util.Objects;

public class Start {
    private Logger logger= LogManager.getLogger("Start menu");
    @FXML
    private ChoiceBox<String> serverdb;
    @FXML
    private TextField Qname;

    private Stage stage;

    private Scene scene;
    private Parent root;
    @FXML
    protected void takeQ(ActionEvent event) throws IOException {
        logger.info("load quiz");
        logger.debug(serverdb.getValue()+ " -> "+Qname.getText());
        DatebaseActions db = new DatebaseActions(serverdb.getValue());
        db.setQuiz(Qname.getText());
        Document col=db.getQuiz();
        if(null==col){
            Alert alert = new Alert(Alert.AlertType.ERROR, "error\ndatabase unrechable", ButtonType.OK);
            alert.showAndWait();
            logger.fatal("cannot connect to database");
            return;
        }
        logger.debug("db doc loaded");
        QuizController qz=QuizController.setFromDocument(col);
        logger.debug("action: TAKE -> "+Qname.getText()+" ");
        logger.info("load quiz taking window");
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
    public void setup(){
        String s[]={"server","localhost"};
        serverdb.setItems(FXCollections.observableArrayList(s));
    }

    public void newQ(ActionEvent event) throws IOException {
        logger.info("load new quize window");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("newQuize.fxml"));
        root=loader.load();

        QuizCreatorController take=loader.getController();
        take.setQz(serverdb.getValue(),Qname.getText());

        //root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
