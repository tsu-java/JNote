package ge.tsu.jnote;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.StringConverter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class State {

    private ObjectProperty<Path> path = new SimpleObjectProperty<>();
    private ObjectProperty<byte[]> text = new SimpleObjectProperty<>();

    public Path getPath() {
        return path.get();
    }

    public ObjectProperty<Path> pathProperty() {
        return path;
    }

    public void setPath(Path path) {
        this.path.set(path);
    }

    public byte[] getText() {
        return text.get();
    }

    public ObjectProperty<byte[]> textProperty() {
        return text;
    }

    public void setText(byte[] text) {
        this.text.set(text);
    }

    public void clear() {
        setPath(null);
        setText(null);
    }

    public static class TitleConverter extends StringConverter<Path> {
        @Override
        public String toString(Path path) {
            if (path == null) {
                return "Untitled - Notepad";
            }
            return path.getFileName().toString() + " - Notepad";
        }

        @Override
        public Path fromString(String title) {
            throw new RuntimeException("This method should never be called!");
        }
    }

    public static class TextConverter extends StringConverter<byte[]> {

        private Charset charset = StandardCharsets.UTF_8;

        public TextConverter() {
        }

        public TextConverter(Charset charset) {
            this.charset = charset;
        }

        @Override
        public String toString(byte[] strBytes) {
            if (strBytes == null || strBytes.length == 0) {
                return new String(new byte[0], charset);
            }
            return new String(strBytes, charset);
        }

        @Override
        public byte[] fromString(String string) {
            return string.getBytes(charset);
        }
    }

}
