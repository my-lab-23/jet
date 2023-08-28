module com.example.sha {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    //requires kotlin.csv.jvm;
    //requires MyOS.assembly;

    opens com.example.sha to javafx.fxml;
    exports com.example.sha;
}