package chainOfCommandAndMemento;

public class Memento extends Photo{

    public Memento(String name, int size, boolean retouch, boolean processing, String format) {
        super(name, size, format);
        super.setRetouch(retouch);
        super.setProcessing(processing);
    }
}
