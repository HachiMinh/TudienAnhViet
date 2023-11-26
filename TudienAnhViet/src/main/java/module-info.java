module com.example.tudienanhviet {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;
    requires java.desktop;
    requires voicerss.tts;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.tudienanhviet to javafx.fxml;
    exports com.example.tudienanhviet;
    opens com.example.tudienanhviet.zones to javafx.fxml;
    exports com.example.tudienanhviet.zones;
    exports com.example.tudienanhviet.Database;
}