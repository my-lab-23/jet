module com.example.trenta4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires kotlinx.coroutines.core;
    requires io.ktor.client.core;
    requires io.ktor.client.cio;
    requires io.ktor.io;
    //requires kotlin.csv.jvm;
    //requires MyOS.assembly;

    opens com.example.trenta4 to javafx.fxml;
    exports com.example.trenta4;
}
