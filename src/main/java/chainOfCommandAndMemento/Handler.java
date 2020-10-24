package chainOfCommandAndMemento;

public interface Handler {
    void setNext(Handler handler);
    void handle(Photo photo);
}
