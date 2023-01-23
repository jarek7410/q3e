package com.example.q3e;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class HelloController {

    private static Logger logger =  LogManager.getLogger("helloControler");
    @FXML
    private Label welcomeText;
    @FXML
    private Label quest;
    private QuizController qz;
    private List<Line> line;
    private int lineId;
    private List<Button> ans=(new  ArrayList<>());
    private Question quz;
    @FXML
    private Button id4;
    @FXML
    private Button id3;
    @FXML
    private Button id2;
    @FXML
    private Button id1;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    @FXML
    protected void testAction(){
        System.out.println("work");
    }

    public HelloController setQz(QuizController q) {
        ans.add(id1);
        ans.add(id2);
        ans.add(id3);
        ans.add(id4);
        this.qz = q;
        logger.info("questions: "+qz.getNrOfLine());
        line=qz.getQuastions();
        lineId=0;
        quz= line.get(lineId);
        List<String> answers=new ArrayList<>();
        answers.addAll(quz.getWrongAnswer());
        answers.addAll(quz.getCorreAnswer());
        quest.setText(quz.getQuastion());
        welcomeText.setText((lineId + 1) +"/"+ qz.getNrOfLine());
        Collections.shuffle(answers);
        for (int i = 0; i <4; i++) {
            ans.get(i).setText(answers.get(i));
        }
        logger.info("quiz start setup");
        return this;
    }
}