module com.example.lehakoe {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.lehakoe to javafx.fxml;
    exports com.example.lehakoe;
}