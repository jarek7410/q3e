package com.example.q3e;

import com.mongodb.*;
import com.mongodb.client.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DatebaseActions {

    private static Logger logger =  LogManager.getLogger();
    private String appdb;
    private String mongodb="d3e";
    private String Qname;

    public DatebaseActions(String dbhost) {
        appdb="mongodb://"+dbhost;
    }
    public DatebaseActions setQuiz(String Qname){
        this.Qname ="q3e_"+Qname;
        return this;
    }

    public static void main(String[] args) {
        DatebaseActions db=new DatebaseActions("localhost");
        db.setQuiz("test");
        QuizController qz=QuizController.mock();
        qz.setName(db.Qname);
        db.saveQuiz(qz.getDocument());
        qz=QuizController.startFromDocument(db.getQuiz());
        System.out.println(qz.getNrOfLine());
    }
    public boolean saveQuiz(Document Q){
        MongoDatabase db;
        if(!appdb.contains("localhost")){
        ConnectionString connectionString = new ConnectionString("mongodb+srv://jarek:IJMAxrNYI9JYl74s@cluster0.ausgoja.mongodb.net/?retryWrites=true&w=majority");
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        db = mongoClient.getDatabase("test");
        }else{
            MongoClient mongoClient = MongoClients.create(appdb);
            db =mongoClient.getDatabase(mongodb);
        }
        try{
            db.createCollection(Qname);
            MongoCollection<Document> col=db.getCollection(Qname);
            col.insertOne(Q);
            logger.info("quiz pushed into db: "+appdb+" ->"+mongodb+" ->"+Qname);
        }catch (MongoException e){
            logger.error(e.getMessage());
            if(48==e.getCode()){

                try(MongoClient mongoClient = MongoClients.create(appdb)) {
                    MongoCollection<Document> col = db.getCollection(Qname);
                    col.drop();
                    logger.debug("put by drop of old collection");
                    db.createCollection(Qname);
                    col=db.getCollection(Qname);
                    col.insertOne(Q);
                    logger.info("quiz pushed into db: "+appdb+" ->"+mongodb+" ->"+Qname);
                }
                catch (MongoException ee){
                    logger.fatal(ee.getMessage());
                    return false;
                }
            }
        }
        return true;
    }
    public Document getQuiz(){
        MongoDatabase db;
        if(!appdb.contains("localhost")) {
            ConnectionString connectionString = new ConnectionString("mongodb+srv://jarek:IJMAxrNYI9JYl74s@cluster0.ausgoja.mongodb.net/?retryWrites=true&w=majority");
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .serverApi(ServerApi.builder()
                            .version(ServerApiVersion.V1)
                            .build())
                    .build();
            MongoClient mongoClient = MongoClients.create(settings);
            db = mongoClient.getDatabase("test");
        }else {
            MongoClient mongoClient = MongoClients.create(appdb);
            logger.info(appdb + " set as working db location");
            db = mongoClient.getDatabase(mongodb);
        }
        MongoCollection<Document> collection = db.getCollection(Qname);

        try {
            Document out =collection.find().first();
            logger.info("quiz loaded");
            return out;
        }catch (Exception e){
            return null;
        }
    }


}
