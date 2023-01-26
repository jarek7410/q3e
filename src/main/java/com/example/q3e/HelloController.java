package com.example.q3e;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class HelloController {

    private static Logger logger = LogManager.getLogger("helloControler");
    @FXML
    private Label welcomeText;
    @FXML
    private Label quest;
    private QuizController qz;
    private List<Line> line;
    private int lineId;
    private List<Button> ans = (new ArrayList<>());
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
    protected void testAction() {
        System.out.println("work");
    }

    @FXML
    protected void nextQ() {
        logger.trace("next question: " + lineId);

        lineId += 1;
        if (lineId >= line.size()) {
            lineId = 0;
        }
        changeQ(lineId);
    }

    @FXML
    protected void backQ() {
        logger.trace("previous question: " + lineId );
        lineId -= 1;
        if (lineId < 0) {
            lineId = line.size()-1;
        }
        changeQ(lineId);
    }
    @FXML
    protected void ansButton(ActionEvent event){
        Button b=(Button)event.getSource();
        logger.trace("answ chosen:"+b.getText());
        quz.ans=b.getText();
    }

    public HelloController setQz(QuizController qz) {
        return setQz(qz, 0);
    }

    public HelloController setQz(QuizController q, int n) {
        ans.add(id1);
        ans.add(id2);
        ans.add(id3);
        ans.add(id4);
        this.qz = q;
        logger.info("questions: " + qz.getNrOfLine());
        changeQ(n);
        logger.info("quiz start setup");
        return this;
    }
    public void changeQ(int n){
        line = qz.getQuastions();
        lineId = n;
        quz = line.get(lineId);
        List<String> answers = new ArrayList<>();
        answers.addAll(quz.getWrongAnswer());
        answers.addAll(quz.getCorreAnswer());
        quest.setText(quz.getQuastion());
        welcomeText.setText((lineId + 1) + "/" + qz.getNrOfLine());
        Collections.shuffle(answers);
        for (int i = 0; i < 4; i++) {
            ans.get(i).setText(answers.get(i));
        }
    }
    @FXML
    protected void summarize(ActionEvent event) throws IOException {
        int points=0;

        for(Question q:line){
            if(q.ansIsCorrect()){
                points++;
                logger.trace("Q"+q.getId()+": "+q.ans+" was correct");
            }
        }
        logger.trace("Summary:"+points+" points");

        Stage stage;
        Scene scene;
        Parent root;
        logger.info("action: SUMMARY");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Qsummary.fxml"));
        root=loader.load();

        Qsummary take=loader.getController();
        take.setResult(points+"/"+qz.getNrOfLine());

        //root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}