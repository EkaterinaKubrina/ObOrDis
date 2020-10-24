package chainOfCommandAndMemento;

public class Formatting implements Handler{
    private Handler handlerNext;
    private Memento memento;

    public Formatting() {
        this.handlerNext = null;
        this.memento = null;
    }

    @Override
    public void setNext(Handler handler) {
        this.handlerNext = handler;
    }

    @Override
    public void handle(Photo photo) {
        if(!photo.getFormat().equals("RAW")){
            memento = photo.makeSnapshot();
            formatting(photo);
        }
        if(handlerNext!=null){
            handlerNext.handle(photo);
        }
        else {
            System.out.println("Обработка завершена\n");
        }
    }

    public Memento getSnapshot(){
        if(memento!=null){
            System.out.println("Форматирование фотографии отменено");
            return memento;
        }
        else {
            throw new NullPointerException("Нет снимка");
        }
    }

    private void formatting(Photo photo){
        photo.setSize(photo.getSize()+40);
        photo.setFormat("RAW");
    }
}
