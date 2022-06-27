module ge.tsu.jnote {
    requires javafx.controls;
    requires javafx.fxml;


    opens ge.tsu.jnote to javafx.fxml;
    exports ge.tsu.jnote;
}