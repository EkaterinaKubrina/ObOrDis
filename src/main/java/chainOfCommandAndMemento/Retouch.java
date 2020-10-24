package chainOfCommandAndMemento;

public class Retouch implements Handler{
    private Handler handlerNext;
    private Memento memento;

    public Retouch() {
        this.handlerNext = null;
        this.memento = null;
    }

    @Override
    public void setNext(Handler handler) {
        this.handlerNext = handler;
    }


    @Override
    public void handle(Photo photo) {
        if(!photo.isRetouch()){
            memento = photo.makeSnapshot();
            retouching();
            photo.setRetouch(true);
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
            System.out.println("Ретушь фотографии отменена");
            return memento;
        }
        else {
            throw new NullPointerException("Нет снимка");
        }
    }

    private void retouching(){
        System.out.println("Фотография отретуширована...");
    }
}
