module com.example.wordcounter {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.wordcounter to javafx.fxml;
    exports com.example.wordcounter;
}