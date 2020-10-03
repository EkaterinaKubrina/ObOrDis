package bridgeAndDecoratorAndAdapter;

public class DrawingOnTheCup extends Cup{
    private static int priceDrawingOnTheCup = 5;
    private String drawing;

    public DrawingOnTheCup(Order milkshake, String drawing) {
        super(milkshake);
        this.drawing = drawing;
    }

    public String getDrawing() {
        return drawing;
    }

    public void setDrawing(String drawing) {
        this.drawing = drawing;
    }

    public int getPrice() {
        return super.getPrice()+priceDrawingOnTheCup;
    }

}
