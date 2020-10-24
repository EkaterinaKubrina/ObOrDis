package chainOfCommandAndMemento;

public class Processing implements Handler {
    private Handler handlerNext;
    private Memento memento;

    public Processing() {
        this.handlerNext = null;
        this.memento = null;
    }

    @Override
    public void setNext(Handler handler) {
        this.handlerNext = handler;
    }

    @Override
    public void handle(Photo photo) {
        if (!photo.isProcessing()) {
            memento = photo.makeSnapshot();
            setColor();
            setWhiteNoise();
            photo.setProcessing(true);
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
            System.out.println("Обработка фотографии отменена");
            return memento;
        }
        else {
            throw new NullPointerException("Нет снимка");
        }
    }

    private void setColor() {
        System.out.println("Настроен цвет...");
    }


    private void setWhiteNoise() {
        System.out.println("Убран белый шум...");
    }
}