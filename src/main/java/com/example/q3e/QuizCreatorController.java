package com.example.q3e;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizCreatorController {
    private static Logger logger = LogManager.getLogger("QuizCreator");
    public TextField idw1;
    public TextField idc;
    public TextField idw2;
    public TextField idw3;
    @FXML
    public Button next;
    private String serverdb;
    private String Qname;
    @FXML
    private Label welcomeText;
    @FXML
    private TextArea quest;
    private QuizController qz;
    private List<Line> line;
    private Question quz;
    private int lineId;
    private List<TextField> ans;

    @FXML
    protected void nextQ() {
        logger.trace("next question: " + lineId);
            lineId += 1;
            logger.trace("lineId:"+lineId+" line.size:"+line.size());
            if (lineId > line.size()) {
                lineId--;
                String correct=idc.getText();
                String wrong1=idw1.getText();
                String wrong2=idw2.getText();
                String wrong3=idw3.getText();
                String question=quest.getText();
                if((question.isBlank()||correct.isBlank())){
                    //lineId--;
                    logger.trace("not correct question");
                }else {
                    List<String> w=new ArrayList<>();
                    w.add(wrong1);
                    w.add(wrong2);
                    w.add(wrong3);
                    List<String> c=new ArrayList<>();
                    c.add(correct);
                    quz=QuizController.makeQuestion(question,c,w,lineId);
                    line.add((Line) quz);
                    logger.trace("Q id: "+(((Line) quz).id));
                    logger.debug("question add "+question);
                    lineId++;
                }
            }else {
                lineId--;
                String correct = idc.getText();
                String wrong1 = idw1.getText();
                String wrong2 = idw2.getText();
                String wrong3 = idw3.getText();
                String question = quest.getText();
                if ((question.isBlank() || correct.isBlank())) {
                    //lineId--;
                    logger.trace("not correct question");
                } else {
                    List<String> w = new ArrayList<>();
                    w.add(wrong1);
                    w.add(wrong2);
                    w.add(wrong3);
                    List<String> c = new ArrayList<>();
                    c.add(correct);
                    quz = QuizController.makeQuestion(question, c, w, lineId);
                    line.set(lineId,(Line) quz);
                    logger.trace("Q id: " + (((Line) quz).id));
                    logger.trace("question add " + question);
                    lineId++;
                }
            }
        changeQ(lineId);
    }

    @FXML
    protected void backQ() {
        logger.trace("previous question: " + lineId );
        lineId -= 1;
        if (lineId < 0) {
            lineId = line.size()-1;
            if(lineId<0){
                lineId=0;
            }
        }
        changeQ(lineId);
    }
    public void changeQ(int n){
        line = qz.getQuastions();
        lineId = n;
        if(qz.getNrOfLine()>5){
            logger.info("dsd");
        }
        welcomeText.setText((lineId + 1) + "/" + qz.getNrOfLine());
        logger.trace("number of Q: "+line.size()+" ,n: "+n);
        if(line.size()<=n) {
            idc.setText("");
            idw1.setText("");
            idw2.setText("");
            idw3.setText("");
            quest.setText("");
            next.setText("add");
            return;
        }
        quz = line.get(lineId);
        logger.trace("next: is "+quz.getId()+" ");
        List<String> wrong =quz.getWrongAnswer();
        List<TextField> ans=new ArrayList<>();
        ans.add(idw1);
        ans.add(idw2);
        ans.add(idw3);
        var itin=wrong.iterator();
        var itprint=ans.iterator();
        TextField tf;
        while(itin.hasNext()&&itprint.hasNext()){
            itprint.next().setText(itin.next());
        }
        idc.setText(quz.getCorreAnswer().iterator().next());
        quest.setText(quz.getQuastion());
        welcomeText.setText((lineId + 1) + "/" + qz.getNrOfLine());
        next.setText("next");
    }
    public void setQz(String dbhost, String qnameText) {
        qz=new QuizController(qnameText);
        this.serverdb=dbhost;
        this.Qname=qnameText;
        ans=new ArrayList<>();
        idc.setStyle("-fx-background-color: #badc58");
        ans.add(idc);
        ans.add(idw1);
        ans.add(idw2);
        ans.add(idw3);
        changeQ(0);
    }

    public void toStart(ActionEvent event) throws IOException {
        Stage stage;
        Scene scene;
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("start.fxml"));
        Start start=loader.getController();
        start.setup();
        root=loader.load();
        //root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        stage = (Stage) ((MenuItem)event.getSource()).getParentPopup().getOwnerWindow();;
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void saveQ(ActionEvent event) {
        DatebaseActions db = new DatebaseActions(serverdb);
        db.setQuiz(Qname);
        if(db.saveQuiz(qz.getDocument())) {
            new Alert(Alert.AlertType.INFORMATION, "saved to db",ButtonType.OK).show();
        }else {
            new Alert(Alert.AlertType.WARNING, "cannot save to db",ButtonType.OK).showAndWait();
        }

    }

}
