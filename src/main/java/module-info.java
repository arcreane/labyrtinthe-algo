module fr.coding.labyrinth {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens fr.coding.labyrinth to javafx.fxml;
    exports fr.coding.labyrinth;
}