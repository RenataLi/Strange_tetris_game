module ru.hse.jigsaw{
    requires javafx.controls;
    requires javafx.fxml;


    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    opens ru.hse.jigsaw to javafx.fxml;
    exports ru.hse.jigsaw;
}