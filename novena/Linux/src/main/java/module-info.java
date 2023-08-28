module com.example.novena {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires kotlinx.coroutines.core;
    requires Java.WebSocket;
    requires io.ktor.client.core;
    requires io.ktor.client.cio;
    //requires kotlin.csv.jvm;
    //requires MyOS.assembly;

    opens com.example.novena to javafx.fxml;
    exports com.example.novena;
}