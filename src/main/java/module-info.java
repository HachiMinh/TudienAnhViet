module com.example.englishdictionaryapp.GUI {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.englishdictionaryapp to javafx.fxml;
    exports com.example.englishdictionaryapp.GUI;
    exports com.example.englishdictionaryapp.Database;

}