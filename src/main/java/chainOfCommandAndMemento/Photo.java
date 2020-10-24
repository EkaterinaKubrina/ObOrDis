package chainOfCommandAndMemento;

public class Photo {
    private String name;
    private int size;
    private boolean retouch;
    private boolean processing;
    private String format;

    public Photo(String name, int size, String format) {
        this.name = name;
        this.size = size;
        this.format = format;
        this.retouch = false;
        this.processing = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isRetouch() {
        return retouch;
    }

    public void setRetouch(boolean retouch) {
        this.retouch = retouch;
    }

    public boolean isProcessing() {
        return processing;
    }

    public void setProcessing(boolean processing) {
        this.processing = processing;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Memento makeSnapshot(){
        return new Memento(name, size, retouch, processing, format);
    }

    public void restore(Memento memento){
        setName(memento.getName());
        setSize(memento.getSize());
        setFormat(memento.getFormat());
        setRetouch(memento.isRetouch());
        setProcessing(memento.isProcessing());
    }
}
