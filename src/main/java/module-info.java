module ge.tsu.jnote {
    requires javafx.controls;
    requires javafx.fxml;
    requires slf4j.api;
    requires org.fxmisc.richtext;
    requires reactfx;

    opens ge.tsu.jnote to javafx.fxml;
    exports ge.tsu.jnote;
}