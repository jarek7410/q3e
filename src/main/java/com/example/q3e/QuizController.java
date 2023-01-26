package com.example.q3e;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class QuizController {
    static Logger logger =  LogManager.getLogger("QuizController");
    private int nrOfLine;
    private List<Line> quastions;
    private String name;
    QuizController(String name){
        Line.iterId=0;
        this.name=name;
        quastions=new ArrayList<>();
    }

    public static QuizController mock() {
        QuizController qz=new QuizController("test");
        List<String> c=new ArrayList<>();
        c.add("one");
        c.add("two");
        List<String> w=new ArrayList<>();
        w.add("three");
        w.add("thour");
        qz.addQuestion("question",c,w).addQuestion("question",w,c);
        return qz;
    }
    static public Question makeQuestion(String question, List<String> corectAns, List<String> wrongAns,int id){
        if(wrongAns.size()+corectAns.size()>Line.nrOfAnswer){
            logger.error("wrong number of answer\nmax is "+Line.nrOfAnswer);
            return null;
        }
        //logger.info("quastion "+question+" was created");
        return new Line()
                .setQuastion(question)
                .setWrongAnswer(wrongAns)
                .setCorreAnswer(corectAns)
                .setid(id);
    }
    public QuizController addQuestion(String question, List<String> corectAns, List<String> wrongAns){
        if(wrongAns.size()+corectAns.size()>Line.nrOfAnswer){
            logger.error("wrong number of answer\nmax is "+Line.nrOfAnswer);
            return this;
        }
        quastions.add(new Line()
                .setQuastion(question)
                .setWrongAnswer(wrongAns)
                .setCorreAnswer(corectAns));
        logger.info("quastion "+question+" was added");
        return this;
    }
    public Document getDocument(){
        Document out=new Document("name",name)
                .append("Lines",Line.iterId);
        List<Document> d=new ArrayList<>();
        for(Line l:quastions){
            d.add(l.getDocumet());
        }
        out.append("lines",d);
        return out;
    }
    static public QuizController setFromDocument(Document document){
        return startFromDocument(document);
    }
    static public QuizController  startFromDocument(Document document){
        QuizController out=new QuizController(document.getString("name"));
        List<Document> d=document.getList("lines",Document.class);
        for(Document dd:d){
            out.addQuestion(dd.getString("question"),
                    dd.getList("cor",String.class),
                    dd.getList("wrong",String.class));
        }
        return out;
    }


    public String getName() {
        return name;
    }

    public QuizController setName(String name) {
        this.name = name;
        return this;
    }

    public int getNrOfLine() {
        return quastions.size();
    }

    public List<Line> getQuastions() {
        return quastions;
    }
    public Iterator<Line> getIteratorQuestions(){
        return quastions.iterator();
    }
}
class Line extends Question{
    static int iterId =1;
    int id;
    String question;
    static int nrOfAnswer=4;
    List<String> wrongAnswer;
    List<String> correAnswer;
    Line(){
        id= iterId;
        iterId++;
    }
    Document getDocumet(){
        Document d=new Document("id",id).append("question",question)
                .append("cor",correAnswer).append("wrong",wrongAnswer);
        return d;
    }
    protected Line setid(int id){
        this.id=id;
        return this;
    }

    public int getNrOfAnswer() {
        return nrOfAnswer;
    }

    public int getId() {
        return id;
    }

    public static int getIterId() {
        return iterId;
    }

    public String getQuastion() {
        return question;
    }

    public Line setQuastion(String quastion) {
        this.question = quastion;
        return this;
    }

    @Override
    public boolean ansIsCorrect() {
        if(null == ans){
            return false;
        }
        for(String s:correAnswer){
            if(s.equals(ans)){
                return true;
            }
        }
        return false;
    }

    public List<String> getWrongAnswer() {
        return wrongAnswer;
    }

    public Line setWrongAnswer(List<String> wrongAnswer) {
        this.wrongAnswer = wrongAnswer;
        return this;
    }

    public List<String> getCorreAnswer() {
        return correAnswer;
    }

    public Line setCorreAnswer(List<String> correAnswer) {
        this.correAnswer = correAnswer;
        return this;
    }
}
