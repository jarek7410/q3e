module com.example.q3e {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.apache.logging.log4j;
    requires org.mongodb.driver.core;
    requires org.mongodb.bson;
    requires org.mongodb.driver.sync.client;

    opens com.example.q3e to javafx.fxml;
    exports com.example.q3e;
}