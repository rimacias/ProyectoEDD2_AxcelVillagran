module Proyecto {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    opens  Proyecto to javafx.fxml;
    exports Proyecto;
}
